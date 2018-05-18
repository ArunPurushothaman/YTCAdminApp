package com.ytcadmin.dal.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE_MASTER")
@NamedQueries({
@NamedQuery(name="DalEmployeeMaster.getAllEmployeeDetails", query = "select o from DalEmployeeMaster o"),
@NamedQuery(name="DalEmployeeMaster.getEmployeeDetailByName", query = "select o from DalEmployeeMaster o where o.fullName in (:name)"),
@NamedQuery(name="DalEmployeeMaster.getReportingEmployeeDetail", query = "select o from DalEmployeeMaster o where o.managerId in (:id)")
})
public class DalEmployeeMaster extends DalModel {
	
	/**
	 * default serial version.
	 */
	private static final long serialVersionUID = 1L;

	private String firstName;
	
	private String lastName;
	
	private String fullName;
	
	private String associateId;
	
	private String companyCode;
	
	private String fileNumber;
	
	private String jobCode;
	
	private String primaryPhone;
	
	private String workExtn;
	
	private String workEmail;
	
	private String jobTitileId;
	
	private String departmentId;
	
	private String positionId;
	
	private Calendar positionEffDate;
	
	private String positionStatus;
	
	private String managerId;
	
	private String managerAssociateId;
	

	/**
	 * @return the firstName
	 */
	@Column(name="EMP_FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	@Column(name="EMP_LAST_NAME")
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the fullName
	 */
	@Column(name="EMP_FULL_NAME")
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the associateId
	 */
	@Column(name="EMP_ASSOCIATE_ID")
	public String getAssociateId() {
		return associateId;
	}

	/**
	 * @param associateId the associateId to set
	 */
	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}

	/**
	 * @return the companyCode
	 */
	@Column(name="EMP_COMPANY_CODE")
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the fileNumber
	 */
	@Column(name="EMP_FILE_NUMBER")
	public String getFileNumber() {
		return fileNumber;
	}

	/**
	 * @param fileNumber the fileNumber to set
	 */
	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

	/**
	 * @return the jobCode
	 */
	@Column(name="EMP_JOB_CODE")
	public String getJobCode() {
		return jobCode;
	}

	/**
	 * @param jobCode the jobCode to set
	 */
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	/**
	 * @return the primaryPhone
	 */
	@Column(name="EMP_PRIMARY_PHONE")
	public String getPrimaryPhone() {
		return primaryPhone;
	}

	/**
	 * @param primaryPhone the primaryPhone to set
	 */
	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	/**
	 * @return the workExtn
	 */
	@Column(name="EMP_WORK_EXTENSION")
	public String getWorkExtn() {
		return workExtn;
	}

	/**
	 * @param workExtn the workExtn to set
	 */
	public void setWorkExtn(String workExtn) {
		this.workExtn = workExtn;
	}

	/**
	 * @return the workEmail
	 */
	@Column(name="EMP_WORK_EMAIL")
	public String getWorkEmail() {
		return workEmail;
	}

	/**
	 * @param workEmail the workEmail to set
	 */
	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}

	/**
	 * @return the jobTitileId
	 */
	@Column(name="EMP_JOB_TITLE_ID")
	public String getJobTitileId() {
		return jobTitileId;
	}

	/**
	 * @param jobTitileId the jobTitileId to set
	 */
	public void setJobTitileId(String jobTitileId) {
		this.jobTitileId = jobTitileId;
	}

	/**
	 * @return the departmentId
	 */
	@Column(name="EMP_DEPARTMENT_ID")
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
	 * @return the positionId
	 */
	@Column(name="EMP_POSITION_ID")
	public String getPositionId() {
		return positionId;
	}

	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	/**
	 * @return the positionEffDate
	 */
	@Column(name="EMP_POSITION_EFF_DATE")
	public Calendar getPositionEffDate() {
		return positionEffDate;
	}

	/**
	 * @param positionEffDate the positionEffDate to set
	 */
	public void setPositionEffDate(Calendar positionEffDate) {
		this.positionEffDate = positionEffDate;
	}

	/**
	 * @return the positionStatus
	 */
	@Column(name="EMP_POSITION_STATUS")
	public String getPositionStatus() {
		return positionStatus;
	}

	/**
	 * @param positionStatus the positionStatus to set
	 */
	public void setPositionStatus(String positionStatus) {
		this.positionStatus = positionStatus;
	}

	/**
	 * @return the managerId
	 */
	@Column(name="MANAGER_ID")
	public String getManagerId() {
		return managerId;
	}

	/**
	 * @param managerId the managerId to set
	 */
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	/**
	 * @return the managerAssociateId
	 */
	@Column(name="MANAGER_ASSOCIATE_ID")
	public String getManagerAssociateId() {
		return managerAssociateId;
	}

	/**
	 * @param managerAssociateId the managerAssociateId to set
	 */
	public void setManagerAssociateId(String managerAssociateId) {
		this.managerAssociateId = managerAssociateId;
	}

		

}
