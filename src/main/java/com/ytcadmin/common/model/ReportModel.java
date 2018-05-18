package com.ytcadmin.common.model;

import java.util.List;

public class ReportModel {
	private List<String> employee;
	private List<String> manager;
	private List<String> shift;
	private List<String> department;
	private String startDate;
	private String endDate;
	
	
	
	/**
	 * @return the employee
	 */
	public List<String> getEmployee() {
		return employee;
	}
	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(List<String> employee) {
		this.employee = employee;
	}
	/**
	 * @return the manager
	 */
	public List<String> getManager() {
		return manager;
	}
	/**
	 * @param manager the manager to set
	 */
	public void setManager(List<String> manager) {
		this.manager = manager;
	}
	/**
	 * @return the shift
	 */
	public List<String> getShift() {
		return shift;
	}
	/**
	 * @param shift the shift to set
	 */
	public void setShift(List<String> shift) {
		this.shift = shift;
	}
	/**
	 * @return the department
	 */
	public List<String> getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(List<String> department) {
		this.department = department;
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	
	
}
