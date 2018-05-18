package com.ytcadmin.dal.model;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "SHIFT_MASTER")
@NamedQueries({
	@NamedQuery(name="DalShiftMaster.getAllDetails", query = "select o from DalShiftMaster o")
})
public class DalShiftMaster extends DalModel {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	private String shiftName;
	
	private Time shiftStartTime;
	
	private Time shiftEndTime;
	
	private String shiftTimeZone;

	/**
	 * @return the shiftName
	 */
	@Column(name="SHIFT_NAME")
	public String getShiftName() {
		return shiftName;
	}

	/**
	 * @param shiftName the shiftName to set
	 */
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	/**
	 * @return the shiftStartTime
	 */
	@Column(name="SHIFT_START_TIME")
	public Time getShiftStartTime() {
		return shiftStartTime;
	}

	/**
	 * @param shiftStartTime the shiftStartTime to set
	 */
	public void setShiftStartTime(Time shiftStartTime) {
		this.shiftStartTime = shiftStartTime;
	}

	/**
	 * @return the shiftEndTime
	 */
	@Column(name="SHIFT_END_TIME")
	public Time getShiftEndTime() {
		return shiftEndTime;
	}

	/**
	 * @param shiftEndTime the shiftEndTime to set
	 */
	public void setShiftEndTime(Time shiftEndTime) {
		this.shiftEndTime = shiftEndTime;
	}

	/**
	 * @return the shiftTimeZone
	 */
	@Column(name="SHIFT_TIME_ZONE")
	public String getShiftTimeZone() {
		return shiftTimeZone;
	}

	/**
	 * @param shiftTimeZone the shiftTimeZone to set
	 */
	public void setShiftTimeZone(String shiftTimeZone) {
		this.shiftTimeZone = shiftTimeZone;
	}	
	
	
}
