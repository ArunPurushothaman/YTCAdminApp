package com.ytcadmin.dal.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE_ABSENCE_RECORD")
public class DalEmployeeAbsence extends DalModel {
	
	/**
	 * default serial version.
	 */
	private static final long serialVersionUID = 1L;

	private String FirstName;
	
	private String LastName;
		
	private String businessUnit;
	
	private String email;
	
	private String manager;
	
	private String timeOfCall;
	
	private Calendar absenceDate;
	
	private String shift;
	
	private String department;
	
	private String phoneNo;
	
	private String absenceReason;
	
	private String sickReason;
	
	private String point;
	
	private String comments;
	
	private String callTakenBy;
	
	private Calendar date;
	
	private String extn;
	
	private String notificationTo;
	
	private String time;
	
	

	
	public DalEmployeeAbsence() {
		
	}




	/**
	 * @return the firstName
	 */
	@Column(name="FIRST_NAME")
	public String getFirstName() {
		return FirstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	@Column(name="LAST_NAME")
	public String getLastName() {
		return LastName;
	}


	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		LastName = lastName;
	}

	/**
	 * @return the businessUnit
	 */
	@Column(name="BUSINESS_UNIT")
	public String getBusinessUnit() {
		return businessUnit;
	}

	/**
	 * @param businessUnit the businessUnit to set
	 */
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	/**
	 * @return the email
	 */
	@Column(name="EMAIL")
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	public String getTimeOfCall() {
		return timeOfCall;
	}

	/**
	 * @param timeOfCall the timeOfCall to set
	 */
	public void setTimeOfCall(String timeOfCall) {
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
	 * @return the shift
	 */
	@Column(name="SHIFT")
	public String getShift() {
		return shift;
	}

	/**
	 * @param shift the shift to set
	 */
	public void setShift(String shift) {
		this.shift = shift;
	}

	/**
	 * @return the department
	 */
	@Column(name="DEPARTMENT")
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
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
	 * @return the notificationTo
	 */
	@Column(name="NOTIFICATION_TO")
	public String getNotificationTo() {
		return notificationTo;
	}
	/**
	 * @param notificationTo the notificationTo to set
	 */
	public void setNotificationTo(String notificationTo) {
		this.notificationTo = notificationTo;
	}

	/**
	 * @return the time
	 */
	@Column(name="TIME")
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	
}
