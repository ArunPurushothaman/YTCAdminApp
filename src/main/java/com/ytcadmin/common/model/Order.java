package com.ytcadmin.common.model;

public class Order extends Model {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String po;
	private String date;
	private String tireShop;
	private String product;
	private Integer order;
	private String description;
	private String dealer;
	private String customerEmail;
	private String customerPhone;
	private String orderBy;
	/**
	 * @return the po
	 */
	public String getPo() {
		return po;
	}
	/**
	 * @param po the po to set
	 */
	public void setPo(String po) {
		this.po = po;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the tireShop
	 */
	public String getTireShop() {
		return tireShop;
	}
	/**
	 * @param tireShop the tireShop to set
	 */
	public void setTireShop(String tireShop) {
		this.tireShop = tireShop;
	}
	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}
	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the dealer
	 */
	public String getDealer() {
		return dealer;
	}
	/**
	 * @param dealer the dealer to set
	 */
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	/**
	 * @return the customerEmail
	 */
	public String getCustomerEmail() {
		return customerEmail;
	}
	/**
	 * @param customerEmail the customerEmail to set
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	/**
	 * @return the customerPhone
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}
	/**
	 * @param customerPhone the customerPhone to set
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	
	
	}
