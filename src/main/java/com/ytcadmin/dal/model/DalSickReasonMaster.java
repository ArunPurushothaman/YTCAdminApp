package com.ytcadmin.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "SICK_REASON_MASTER")
@NamedQueries({
	@NamedQuery(name="DalSickReasonMaster.getAllDetails", query = "select o from DalSickReasonMaster o")
})
public class DalSickReasonMaster extends DalAuditableModel {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	private String reason;

	/**
	 * @return the reason
	 */
	@Column(name="REASON")
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
	
	
}
