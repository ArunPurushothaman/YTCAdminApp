package com.ytcadmin.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "EMPLOYEE_TITLE")
@NamedQueries({
	@NamedQuery(name="DalEmployeeTitle.getAllDetails", query = "select o from DalEmployeeTitle o")
})
public class DalEmployeeTitle extends DalModel{
	
	/**
	 * Default serial version number.
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;

	@Column(name="TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
