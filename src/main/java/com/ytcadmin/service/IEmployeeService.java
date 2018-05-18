/**
 * 
 */
package com.ytcadmin.service;


import java.util.List;

import com.ytcadmin.common.model.DropDown;
import com.ytcadmin.common.model.Employee;
import com.ytcadmin.dal.model.DalUserMaster;

/**
 * @author 164919
 *
 */
public interface IEmployeeService {
	List<Employee> getDetail(String loginId);
	
	List<String> getQueryResult(String empId);

	DalUserMaster getEmployeeDetail(Integer loginId);

	
	boolean isValidDBLogin(String theUserid, String thePassword);

	List<DropDown> getAllDealerDropDownList();


	

}
