package com.ytcadmin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ytcadmin.common.model.Employee;
import com.ytcadmin.service.ServiceContext;


@Controller
@RequestMapping("/")
public class NavigationController{

	@Autowired
	private ServiceContext serviceContext;
	
	@RequestMapping(value ={ "/","/login"}, method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model) {
		//Need to call service implementation here
		if(serviceContext != null){
			serviceContext.setEmployee(null);
		}
		String returnModel = "login";
		return returnModel;
	}
	
	
	
	
	
	
	@RequestMapping(value ={"/upload"}, method = RequestMethod.GET)
	public String getBulkUpload(HttpServletRequest request, Model model) {
		String userName = null;
		String returnModel = "ytc-admin";
		List<Employee> employee = null;
		/*if (serviceContext != null && serviceContext.getEmployee() != null) {
			userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER
					+ serviceContext.getEmployee().getLAST_NAME();
			returnModel = "employee-absence-report";
			model.addAttribute("loginUserNameValue", userName);
			employee = new ArrayList<Employee>();
			employee.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employee);
		}

		if (serviceContext == null || userName == null) {
			returnModel = "login";
		}*/

		return returnModel;
	}
	
	
	@RequestMapping(value ={"/addBulkUpload"}, method = RequestMethod.GET)
	public String addBulkUpload(HttpServletRequest request, Model model) {
		String userName = null;
		String returnModel = "upload-bulk";
		List<Employee> employee = null;
		/*if (serviceContext != null && serviceContext.getEmployee() != null) {
			userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER
					+ serviceContext.getEmployee().getLAST_NAME();
			returnModel = "employee-absence-report";
			model.addAttribute("loginUserNameValue", userName);
			employee = new ArrayList<Employee>();
			employee.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employee);
		}

		if (serviceContext == null || userName == null) {
			returnModel = "login";
		}*/

		return returnModel;
	}
	
}

