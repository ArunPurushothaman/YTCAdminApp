package com.ytcadmin.dal.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE_DEPT")

public class DalEmployeeDepartment extends DalAuditableModel {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer employeeId;
	
	private String departmentId;
	
	private Calendar startDate;
	
	private Calendar endDate;

	//private DalEmployeeAbsenceCall dalEmployeeAbsenceCallDept;
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
	 * @return the departmentId
	 */
	@Column(name="DEPARTMENT_ID")
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
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
	 * @return the dalEmployeeAbsenceCallDept
	 *//*
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "ABSENCE_ID", referencedColumnName = "ID")
	public DalEmployeeAbsenceCall getDalEmployeeAbsenceCallDept() {
		return dalEmployeeAbsenceCallDept;
	}

	*//**
	 * @param dalEmployeeAbsenceCallDept the dalEmployeeAbsenceCallDept to set
	 *//*
	public void setDalEmployeeAbsenceCallDept(DalEmployeeAbsenceCall dalEmployeeAbsenceCallDept) {
		this.dalEmployeeAbsenceCallDept = dalEmployeeAbsenceCallDept;
	}*/

	
	
	
}
