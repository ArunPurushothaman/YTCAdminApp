package com.ytcadmin.dal.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE_SHIFT")

public class DalEmployeeShift extends DalAuditableModel {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer employeeId;
	
	private String shiftId;
	
	private Calendar startDate;
	
	private Calendar endDate;
	
	//private DalEmployeeAbsenceCall dalEmployeeAbsenceCallShift;

	

	/**
	 * @return the employeeId
	 */
	@Column(name="EMP_ID")
	public Integer getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * @return the shiftId
	 */
	@Column(name="SHIFT_ID")
	public String getShiftId() {
		return shiftId;
	}

	/**
	 * @param shiftId the shiftId to set
	 */
	public void setShiftId(String shiftId) {
		this.shiftId = shiftId;
	}

	/**
	 * @return the startDate
	 */
	@Column(name="START_DATE")
	public Calendar getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	@Column(name="END_DATE")
	public Calendar getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the dalEmployeeAbsenceCallShift
	 *//*
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "SHIFT_ID", referencedColumnName = "SHIFT_ID")
	public DalEmployeeAbsenceCall getDalEmployeeAbsenceCallShift() {
		return dalEmployeeAbsenceCallShift;
	}

	*//**
	 * @param dalEmployeeAbsenceCallShift the dalEmployeeAbsenceCallShift to set
	 *//*
	public void setDalEmployeeAbsenceCallShift(DalEmployeeAbsenceCall dalEmployeeAbsenceCallShift) {
		this.dalEmployeeAbsenceCallShift = dalEmployeeAbsenceCallShift;
	}
*/
	
	
}
