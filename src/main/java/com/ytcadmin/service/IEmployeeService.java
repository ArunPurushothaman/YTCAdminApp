/**
 * 
 */
package com.ytcadmin.service;


import java.util.List;

import com.ytcadmin.common.model.DropDown;
import com.ytcadmin.common.model.Employee;
import com.ytcadmin.common.model.EmployeeAbsence;
import com.ytcadmin.common.model.EmployeeAbsenceDropDown;
import com.ytcadmin.common.model.EmployeeMaster;
import com.ytcadmin.common.model.ReportModel;
import com.ytcadmin.dal.model.DalEmployeeMaster;
import com.ytcadmin.dal.model.DalUserMaster;

/**
 * @author 164919
 *
 */
public interface IEmployeeService {
	List<Employee> getDetail(String loginId);
	
	List<String> getQueryResult(String empId);

	DalUserMaster getEmployeeDetail(Integer loginId);

	List<String> getAllEmployeeNames();

	//List<String> getManagerName(String employeeName);

	EmployeeAbsence saveEmployeeAbsenceDetail(EmployeeAbsence employeeAbsence);

	EmployeeAbsenceDropDown getEmployeeAbsenceDropDown();

	List<EmployeeAbsence> getEmployeeAbsenceHistory(String employeeName);

	List<DalEmployeeMaster> getEmployeeByName(String employeeName);
	
	EmployeeMaster getAbsentEmployeeDetail(String employeeName);

	boolean isValidDBLogin(String theUserid, String thePassword);

	//byte[] generateAbsenceReport(ReportModel reportModel);

	//List<String> getHierarchyList(String login);

	List<EmployeeAbsence> getEmployeeAbsenceReport(ReportModel reportModel);

	String removeEmployeeAbsenceRecord(String id);

	EmployeeAbsence getEmployeeAbsenceRecord(String id);

	EmployeeAbsence updateEmployeeAbsenceDetail(EmployeeAbsence employeeAbsence);

	List<DropDown> getAllDealerDropDownList();


	

}
