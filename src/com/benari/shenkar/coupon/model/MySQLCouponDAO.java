package com.benari.shenkar.coupon.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import com.benari.shenkar.coupon.model.Users;
import com.benari.shenkar.coupon.model.interfaces.ICategoryCouponDAO;
import com.benari.shenkar.coupon.model.interfaces.ICouponDAO;
import com.benari.shenkar.coupon.model.interfaces.IUsersCouponDAO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


public class MySQLCouponDAO implements ICategoryCouponDAO, ICouponDAO, IUsersCouponDAO{

	private static MySQLCouponDAO instance = null;
	private SessionFactory factory = null;
	PreparedStatement statement = null;
    ResultSet rs = null;
    final static Logger logger = Logger.getLogger(MySQLCouponDAO.class);
    
	private MySQLCouponDAO() {
		// configuration.
		try {
			Class.forName("com.mysql.jdbc.Driver");
			factory = new AnnotationConfiguration().configure().buildSessionFactory();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("MySQLCouponDAO - Couln't login - error");
			logger.info("MySQLCouponDAO - Couln't login - info");
		}
	}
	
	public static MySQLCouponDAO getInstance(){
		if(instance == null) instance = new MySQLCouponDAO();
		return instance;
	}
	
	/**
	 * 
	 * User Implementation
	 * @throws NoSuchAlgorithmException 
	 * 
	 **/
	
	
	public StringBuffer hashPassword(String pword) throws NoSuchAlgorithmException{
		/**
		 * This function gets a password and hashes it (MD5 Style).
		 */
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(pword.getBytes());
		byte byteData[] = md.digest();
		//convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        return sb;
	}
	
	@Override
	public boolean addNewUser(String uname, String pword) throws UserException {
		/**
		 * This function adds a user to the system.
		 * On the way, hassPassword() hashes the password we want to store in DB.
		 */
		Session session = factory.openSession();
		Transaction tx = null;
		String hql = "FROM Users where UserName = :uname";
		Query query = session.createQuery(hql);
		query.setString("uname",uname);
		List list = query.list();
		if(list.isEmpty()){
			try {
				// MD5 Encryption
		        StringBuffer sb = hashPassword(pword);		        
		        Users nu = new Users(0,uname,sb.toString(),false);
				tx = session.beginTransaction();
				session.save(nu);
				session.getTransaction().commit();
				return true;
			} catch (NoSuchAlgorithmException e) {
				logger.error("addNewUser - Could'nt add a new user");
				e.printStackTrace();
			}
		}	
		return false;
	}

	@Override
	public boolean validateUser(String uname, String pword)
			throws UserException {
		/*
		 * this function validates the input of user (username and password) in order to connect.
		 * wether user has entered the correct details or not.
		 * returns true if details are correct.
		 */
		try {
			// MD5 Encryption
			Session session = factory.openSession();
	        StringBuffer sb = hashPassword(pword);
			String hql = "FROM Users where userName = :uname AND userPassword = :pword";
			Query query = session.createQuery(hql);
			query.setString("uname",uname);
			query.setString("pword",sb.toString());
			List list = query.list();
			
			if(list.isEmpty()){
				return false;
			}
			return true;
			
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("validateUser - validate user");
			return false;
		}
	}

	@Override
	public Users getUserDetails(String uname) throws UserException {
		/**
		 * returns a User details by its username.
		 */
		Session session = factory.openSession();
		Transaction tx = null;
		try
		{

			String hql = "FROM Users where userName = :uname";
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setString("uname",uname);
			session.getTransaction().commit();
			List<Users> un = (List<Users>)query.list();
			// TODO Auto-generated method stub
			if(!un.isEmpty()){
				return un.get(0);
			}
		}
		catch (HibernateException e)
		{
			logger.error("getUserDetails - Cannot get user details");
			tx = session.getTransaction();
			if (tx.isActive()) tx.rollback();
		}
		finally
		{
			if(session!=null) session.close();
		}
		
		return null;
	}

	@Override
	public Collection<Users> getAllUsers() throws UserException {
		/**
		 * This function returns a list of all existing users (NO ADMINS AT ALL).
		 */
		Session session = factory.openSession();
		Transaction tx = null;
		List list = null;
		try {
			String hql = "FROM Users WHERE isAdmin = 0";
			Query query = session.createQuery(hql);
			list = query.list();
			session.getTransaction().commit();
			return list;
		}
		catch (HibernateException e)
		{
			logger.error("getAllUsers - some error");
			tx = session.getTransaction();
			if (tx.isActive()) tx.rollback();
		}
		finally
		{
			if(session!=null) session.close();
		}
		return list;
	}

	@Override
	public Collection<Users> getAllAdmins() throws UserException {
		/**
		 * This function returns a list of all existing admins (NO REGULAR USERS AT ALL).
		 */
		Session session = factory.openSession();
		Transaction tx = null;
		List list = null;
		try{
			String hql = "FROM Users WHERE isAdmin = 1";
			Query query = session.createQuery(hql);
			session.getTransaction().commit();
			list = query.list();
			return list;
		}
		catch (HibernateException e)
		{
			logger.error("getAllAdmins - error");
			tx = session.getTransaction();
			if (tx.isActive()) tx.rollback();
		}
		finally
		{
			if(session!=null) session.close();
		}
		
		// TODO Auto-generated method stub
		return list;
	}
	
	@Override
	public boolean deleteUser(int userId) throws UserException {
		/**
		 * Deletes a user according to user Id
		 */
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			String hql = "DELETE FROM Users WHERE userId = " + userId;
			Query query = session.createQuery(hql);
			int rowsExecute = query.executeUpdate();
			if(rowsExecute != 0){
				return true;
			}
		}
		catch (HibernateException e)
		{
			tx = session.getTransaction();
			if (tx.isActive()) tx.rollback();
		}
		finally
		{
			if(session!=null) session.close();
		}
		return false;
	}
	
	
	/**
	 * 
	 * Coupon Implementation
	 * 
	 **/
	
	@Override
	public void addCoupon(Coupon newCoupon) throws CouponException {
		/**
		 * this function recieves a coupon and inserts it to the database.
		 */
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(newCoupon);
			session.getTransaction().commit();
			//return true;
		}catch(Exception e){
			e.printStackTrace();
			if(tx != null) tx.rollback();
			throw new CouponException("Problem adding a coupon");
		}finally{
			if(session != null) session.close();
		}
	}

	@Override
	public boolean updateCoupon(int id, String name, float price, Date dateEnds, int category) throws CouponException {
		/**
		 * this function recieves coupon details, turn the into a coupon object and updates it within the DB.
		 */
		Session session = null;
		try
		{
			session = factory.openSession();
			session.beginTransaction();
			Coupon nc = new Coupon(id, name, price, dateEnds, category);
			session.update(nc);
			session.getTransaction().commit();
			return true;
		}
		catch (HibernateException e)
		{
			Transaction tx = session.getTransaction();
			if (tx.isActive()) tx.rollback();
			return false;
		}
		finally
		{
			if(session!=null) session.close();
		}
	}

	@Override
	public boolean removeCoupon(int id) throws CouponException {
		/**
		 * this function a coupon id and deletes it from the database.
		 */
		Session session = null;
		Transaction tx = null;
		try
		{
			session = factory.openSession();
			tx = session.beginTransaction();
			Coupon c = (Coupon)session.get(Coupon.class,id);
			session.delete(c);
			session.getTransaction().commit();
			return true;
		}
		catch (HibernateException e)
		{
			if (tx.isActive()) tx.rollback();
			return false;
		}
		finally
		{
			if(session!=null) session.close();
		}
	}

	@Override
	public Collection<Coupon> getAllCoupons() throws CouponException {
		// get all coupons.
		Session session = factory.openSession();
		String hql = "FROM Coupon";
		Query query = session.createQuery(hql);
		List list = query.list();
		// TODO Auto-generated method stub
		return list;
	}
	
	@Override
	public Collection<Coupon> getGroupOfCoupons(int groupId)
			throws CouponException {
		/**
		 * Get All coupons that have the same category. 
		 */
		Session session = factory.openSession();
		String hql = "FROM Coupon where categoryNumber = :cnum";
		Query query = session.createQuery(hql);
		query.setString("cnum",Integer.toString(groupId));
		List list = query.list();
		// TODO Auto-generated method stub
		return list;
	}

	public Coupon getCoupon(int id) throws CouponException{
		// this function returns a Coupon according to its id.
		Session session = factory.openSession();
		String hql = "FROM Coupon where id = :id";
		Query query = session.createQuery(hql);
		query.setString("id",Integer.toString(id));
		List<Coupon> nc = (List<Coupon>)query.list();
		// TODO Auto-generated method stub
		if(!nc.isEmpty()) return nc.get(0);
		return null;
	}
	
	/**
	 * 
	 * Coupon Category Implementation
	 * 
	 **/
	
	@Override
	public Collection<CouponCategory> getAllCategories() throws CouponException {
		// get all existing coupon categories.
		Session session = factory.openSession();
		String hql = "FROM CouponCategory";
		Query query = session.createQuery(hql);
		List list = query.list();
		return list;
	}

	/*
	@Override
	public boolean addCategory(CouponCategory newCategory) throws CouponException{
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(newCategory);
			session.getTransaction().commit();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			if(tx != null) tx.rollback();
			throw new CouponException("Problem adding a category");

		}finally{
			if(session != null) session.close();
		}
	}
	
	@Override
	public boolean deleteCategory(int id) throws CouponException{
		return false;
		
	}
	*/

	
}
