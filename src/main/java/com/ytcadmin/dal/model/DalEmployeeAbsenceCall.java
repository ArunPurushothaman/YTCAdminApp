package com.ytcadmin.dal.model;

import java.sql.Time;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE_ABSENCE_CALL_IN")
@NamedQueries({
	@NamedQuery(name="DalEmployeeAbsenceCall.getAllDetails", query = "select o from DalEmployeeAbsenceCall o where o.employeeId=:id order by o.createdDate desc")
})
public class DalEmployeeAbsenceCall extends DalAuditableModel {
	
	/**
	 * default serial version.
	 */
	private static final long serialVersionUID = 1L;

	private String shiftId;
	
	private String departmentId;
		
	private String employeeName;
	
	private Integer employeeId;
			
	private Time timeOfCall;
	
	private String manager;
	
	private String phoneNo;
	
	private String absenceReason;
	
	private Calendar absenceDate;
	
	private Integer absenceDays;
	
	private String sickReason;
	
	private String point;
	
	private String comments;
	
	private String callTakenBy;
	
	private Calendar date;
	
	private String extn;
		
	private Time time;
	
	private String status;
	
	//private  DalEmployeeShift employeeShift;	

	//private DalEmployeeDepartment employeeDepartment;
	
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
	 * @return the employeeName
	 */
	@Column(name="EMPLOYEE_NAME")
	public String getEmployeeName() {
		return employeeName;
	}

	

	/**
	 * @param employeeName the employeeName to set
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	
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
	 * @return the manager
	 */
	@Column(name="MANAGER_ID")
	public String getManager() {
		return manager;
	}
	/**
	 * @param manager the manager to set
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	/**
	 * @return the timeOfCall
	 */
	@Column(name="TIME_OF_CALL")
	public Time getTimeOfCall() {
		return timeOfCall;
	}

	/**
	 * @param timeOfCall the timeOfCall to set
	 */
	public void setTimeOfCall(Time timeOfCall) {
		this.timeOfCall = timeOfCall;
	}

	/**
	 * @return the absenceDate
	 */
	@Column(name="ABSENCE_DATE")
	public Calendar getAbsenceDate() {
		return absenceDate;
	}


	/**
	 * @param absenceDate the absenceDate to set
	 */
	public void setAbsenceDate(Calendar absenceDate) {
		this.absenceDate = absenceDate;
	}

	
	/**
	 * @return the absenceDays
	 */
	@Column(name="ABSENCE_DAYS")
	public Integer getAbsenceDays() {
		return absenceDays;
	}

	/**
	 * @param absenceDays the absenceDays to set
	 */
	public void setAbsenceDays(Integer absenceDays) {
		this.absenceDays = absenceDays;
	}

	/**
	 * @return the phoneNo
	 */
	@Column(name="PHONE_NO")
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


	/**
	 * @return the absenceReason
	 */
	@Column(name="ABSENCE_REASON")
	public String getAbsenceReason() {
		return absenceReason;
	}


	/**
	 * @param absenceReason the absenceReason to set
	 */
	public void setAbsenceReason(String absenceReason) {
		this.absenceReason = absenceReason;
	}

	/**
	 * @return the sickReason
	 */
	@Column(name="SICK_REASON")
	public String getSickReason() {
		return sickReason;
	}

	/**
	 * @param sickReason the sickReason to set
	 */
	public void setSickReason(String sickReason) {
		this.sickReason = sickReason;
	}

	/**
	 * @return the point
	 */
	@Column(name="POINT")
	public String getPoint() {
		return point;
	}

	/**
	 * @param point the point to set
	 */
	public void setPoint(String point) {
		this.point = point;
	}
	
	/**
	 * @return the comments
	 */
	@Column(name="COMMENTS")
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the callTakenBy
	 */
	@Column(name="CALL_TAKEN_BY")
	public String getCallTakenBy() {
		return callTakenBy;
	}

	/**
	 * @param callTakenBy the callTakenBy to set
	 */
	public void setCallTakenBy(String callTakenBy) {
		this.callTakenBy = callTakenBy;
	}

	/**
	 * @return the date
	 */
	@Column(name="DATE")
	public Calendar getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}

	/**
	 * @return the extn
	 */
	@Column(name="EXTN")
	public String getExtn() {
		return extn;
	}
	/**
	 * @param extn the extn to set
	 */
	public void setExtn(String extn) {
		this.extn = extn;
	}


	/**
	 * @return the time
	 */
	@Column(name="TIME")
	public Time getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Time time) {
		this.time = time;
	}

	/**
	 * @return the status
	 */
	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	
	
	/**
	 * @return the employeeShift
	 *//*
	@OneToOne(mappedBy = "dalEmployeeAbsenceCallShift", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	public  DalEmployeeShift getEmployeeShift() {
		return employeeShift;
	}

	*//**
	 * @param employeeShift the employeeShift to set
	 *//*
	public void setEmployeeShift( DalEmployeeShift employeeShift) {
		this.employeeShift = employeeShift;
	}*/

	/**
	 * @return the employeeDepartment
	 *//*
	@OneToOne(mappedBy = "dalEmployeeAbsenceCallDept", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	public DalEmployeeDepartment getEmployeeDepartment() {
		return employeeDepartment;
	}

	*//**
	 * @param employeeDepartment the employeeDepartment to set
	 *//*
	public void setEmployeeDepartment(DalEmployeeDepartment employeeDepartment) {
		this.employeeDepartment = employeeDepartment;
	}*/

	
}
