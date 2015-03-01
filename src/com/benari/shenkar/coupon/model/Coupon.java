package com.benari.shenkar.coupon.model;

import java.util.*;

import javax.persistence.*;

import org.apache.log4j.Logger;

@Entity
@Table (name="coupons")
public class Coupon {
	@Id
    @GeneratedValue
    @Column (name="Id")
	private int id;
	@Column (nullable=false, name="Name")
	private String name;
	@Column (nullable=false, name="Price")
	private float price;
	@Column (nullable=true, name="DateCreated", columnDefinition = "datetime")
	private Date dateCreated;
	@Column (nullable=false, name="DateEnds", columnDefinition = "datetime")
	private Date dateEnds;
	@Column (nullable=false, name="CategoryNumber")
	private int categoryNumber;
	
	private double xAxis;
	private double yAxis;
	
	final static Logger logger = Logger.getLogger(Coupon.class);
	
	// default c'tor
	public Coupon(){
		setX(); setY();
	}
	
	public Coupon(int id, String name, float price,
			Date dateEnds, int categoryNumber) {
		super();
		this.setId(id);
		this.setName(name);
		this.setPrice(price);
		this.setDateEnds(dateEnds);
		this.setCategoryNumber(categoryNumber);
		this.setDateCreated(new Date());
		// sets a random location of a coupon.
		setX(); setY();
	}
	/* Setters & Getters */
	
	public double getX(){
		return this.xAxis;
	}
	
	public double getY(){
		return this.yAxis;
	}
	
	public void setX(){
		this.xAxis = 45.12345 * Math.random();
	}
	
	public void setY(){
		this.yAxis = 45.12345 * Math.random();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Date getDateEnds() {
		return dateEnds;
	}

	public void setDateEnds(Date dateEnds) {
		this.dateEnds = dateEnds;
	}

	public int getCategoryNumber() {
		return categoryNumber;
	}

	public void setCategoryNumber(int categoryNumber) {
		this.categoryNumber = categoryNumber;
	}
	
	

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", name=" + name + ", price=" + price
				+ ", dateCreated=" + dateCreated + ", dateEnds=" + dateEnds
				+ ", categoryNumber=" + categoryNumber + "x="+xAxis+" y="+yAxis+"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categoryNumber;
		result = prime * result
				+ ((dateCreated == null) ? 0 : dateCreated.hashCode());
		result = prime * result
				+ ((dateEnds == null) ? 0 : dateEnds.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(price);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coupon other = (Coupon) obj;
		if (categoryNumber != other.categoryNumber)
			return false;
		if (dateCreated == null) {
			if (other.dateCreated != null)
				return false;
		} else if (!dateCreated.equals(other.dateCreated))
			return false;
		if (dateEnds == null) {
			if (other.dateEnds != null)
				return false;
		} else if (!dateEnds.equals(other.dateEnds))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		return true;
	}
	
	
	
}
