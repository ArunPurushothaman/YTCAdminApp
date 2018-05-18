/**
 * 
 */
package com.ytcadmin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytcadmin.common.model.Employee;
import com.ytcadmin.common.model.ReportModel;
import com.ytcadmin.service.IEmployeeService;
import com.ytcadmin.service.ServiceContext;



@Controller
@RequestMapping(value = "/")
public class ReportsController extends BaseController {

	
	@Autowired
	private ServiceContext serviceContext;

	IEmployeeService getService(HttpServletRequest request) {
		return getServiceLocator(request).getEmployeeService();
	}
	
	/*@Consumes({ MediaType.APPLICATION_JSON_VALUE })
	@Produces("application/vnd.ms-excel")
	@RequestMapping(value = "/report/v1/generateReport", method = RequestMethod.POST)
	public @ResponseBody byte[] generateReport(HttpServletRequest request,@RequestBody ReportModel reportModel) {
		byte[] response = null;
			response = getService(request).generateAbsenceReport(reportModel);
		return response;
	}*/
	


	
	@RequestMapping(value = "/v1/getHierarchyEmployeeNames", method = RequestMethod.GET)
	public @ResponseBody  List<String> getHierarchyList(HttpServletRequest request) {
		 List<String> nameList =new ArrayList<String>(); 
		 Employee employee = null;
			if(serviceContext != null){
				employee = serviceContext.getEmployee();
			}
		 //nameList=getService(request).getHierarchyList("");
		
		return nameList;
		
	}
}
