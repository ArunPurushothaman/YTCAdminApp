package com.ytcadmin.dal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "DEPARTMENT_MASTER")
@NamedQueries({
	@NamedQuery(name="DalDepartmentMaster.getAllDetails", query = "select o from DalDepartmentMaster o")
})
public class DalDepartmentMaster extends DalModel {

	/**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	
	private String departmentCode;
	
	private String departmentDesc;

	/**
	 * @return the departmentCode
	 */
	@Column(name="DEPARTMENT_CODE")
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * @param departmentCode the departmentCode to set
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * @return the departmentDesc
	 */
	@Column(name="DEPARTMENT_DESC")
	public String getDepartmentDesc() {
		return departmentDesc;
	}

	/**
	 * @param departmentDesc the departmentDesc to set
	 */
	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	
		
}
