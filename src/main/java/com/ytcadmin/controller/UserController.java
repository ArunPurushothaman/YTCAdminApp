package com.ytcadmin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.cfg.Configuration;
//import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.tiredex.yoko.utils.LdapUtil;
import com.ytcadmin.common.model.DropDown;
import com.ytcadmin.common.model.Employee;
import com.ytcadmin.common.model.Order;
import com.ytcadmin.common.result.ListResult;
import com.ytcadmin.common.result.ModelResult;
import com.ytcadmin.constant.ProgramConstant;

import com.ytcadmin.dal.model.DalUserMaster;
import com.ytcadmin.service.IEmployeeService;
import com.ytcadmin.service.ServiceContext;

@Controller
// @RequestMapping(value = "/")
public class UserController extends BaseController {
	
	
	UserController(){
		InputStream is = UserController.class.getResourceAsStream("/log4j.properties"); 
		Properties p = new Properties(); 
		try {
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		PropertyConfigurator.configure(p);	
	}
	
 

	static Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private ServiceContext serviceContext;
	
	@RequestMapping(value = "/getEmployee/{loginId}", method = RequestMethod.GET)
	public @ResponseBody ListResult<Employee> getDetail(HttpServletRequest request, @PathVariable String loginId) {
		logger.info("UserController /getEmployee/{loginId}");
		return new ListResult<Employee>(getService(request).getDetail(loginId));
	}

	@RequestMapping(value = "/getEmployeeHierarchy/{empId}", method = RequestMethod.GET)
	public @ResponseBody List<String> getEmployeeHierarchy(HttpServletRequest request, @PathVariable Integer empId) {
		logger.info("UserController /getEmployeeHierarchy/{empId}"+empId);
		List<String> aList = getHierarchyQueryResult(request, empId);
		logger.info("UserController getHiearchyQueryResult: ["+aList+"]");
		return aList;
	}
	
	@RequestMapping(value = "/getEmployeeDetail/{empId}", method = RequestMethod.GET)
	public @ResponseBody DalUserMaster getEmployeeDetail(HttpServletRequest request, @PathVariable Integer empId) {
		logger.info("UserController /getEmployeeDetail/{empId}"+empId);
		
		return (getService(request).getEmployeeDetail(empId));
		//return aList;
	}
	
	
	@RequestMapping(value = { "/logout" })
	public String logoutUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("UserController.presentHomePage");
		request.getSession().removeAttribute("EMPLOYEE_INFO");
		request.getSession().removeAttribute("EMPLOYEE_HIERARCHY");
		if(serviceContext != null){
			serviceContext.setEmployee(null);
		}
		request.getSession().invalidate();
        return "login";
	}
	
	@RequestMapping(value = { "/browserclose" })
	public void browserClose(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("UserController.presentHomePage");
		request.getSession().removeAttribute("EMPLOYEE_INFO");
		request.getSession().removeAttribute("EMPLOYEE_HIERARCHY");
		if(serviceContext != null){
			serviceContext.setEmployee(null);
		}
		request.getSession().invalidate();
	}


	
	/**
	 * This method is added for redirecting to Index page after proper login.
	 * @return String.
	 */
	@RequestMapping(value = { "/indexRedirect" })
	public String indexNavigationAfterLogin(HttpServletRequest request, Model model) {
		logger.info("UserController.presentHomePage");
		String returnModel = null;
		int timeout=(request.getSession().getMaxInactiveInterval()-300);
		if(serviceContext == null || serviceContext.getEmployee() == null){
			returnModel = "login";
		}
		else{
			List<Employee> employeeList = null;
			String userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			model.addAttribute("loginUserNameValue", userName);
			employeeList = new ArrayList<Employee>();
			employeeList.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employeeList);
			returnModel = "employee-absence-form";
		}
		model.addAttribute("timeout", timeout*1000);
		
		return returnModel;
	}
	
	@RequestMapping(value = "processLogin", method = { RequestMethod.GET } )
	public String navigateToLoginPageOrDashboard(HttpServletRequest request, Model model) {
		String userName = null;
		String returnModel = null;
		int timeout=(request.getSession().getMaxInactiveInterval()-300);
		if(request != null){
			String theUserid = request.getParameter("userid");
			if(theUserid != null && !theUserid.isEmpty()){
				return "login";
			}
		}
		if(serviceContext != null && serviceContext.getEmployee() != null){
			userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			model.addAttribute("loginUserNameValue", userName);
			List<Employee> employee=new ArrayList<Employee>();
			employee.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employee);
			returnModel = "employee-absence-form";
		}
		if(serviceContext == null || userName == null){
			returnModel = "login";
		}
		model.addAttribute("timeout", timeout*1000);
		return returnModel;
	}

	@RequestMapping(value = "processLogin", method = { RequestMethod.POST } )
	public String validateLoginPage(HttpServletRequest request, Model model) {
		int timeout=(request.getSession().getMaxInactiveInterval()-300);
		logger.info("UserController.processLogin");
		List<Employee> employee = null;
		List<Employee> employeeTmp = null;
		if((serviceContext.getEmployee() != null)&&("1".equalsIgnoreCase(serviceContext.getEmployee().getACTIVE()))){
			String userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
			model.addAttribute("loginUserNameValue", userName);
			employee=new ArrayList<Employee>();
			employee.add(serviceContext.getEmployee());
			model.addAttribute("EmployeeInfo", employee);
			model.addAttribute("timeout", timeout*1000);
			return "employee-absence-form";
		}
		
		String destination = "loginError";
		String theUserid = request.getParameter("userid");
		logger.info("UserController.processLogin userid: " + theUserid);
		String thePassword = request.getParameter("userpassword");
		boolean isValidUser = false;
		//boolean isTestUser =  thePassword.isEmpty();

		try {
			
			String loginID = theUserid.substring(theUserid.indexOf("\\")+1);
			logger.info("UserController.processLogin loginID: "+loginID);
			
//          get all data from employee table that matches the loginID
			employeeTmp = getService(request).getDetail(loginID);	
			
			if((!employeeTmp.isEmpty())/*&&(!isTestUser)*/){
				
				if("N".equalsIgnoreCase(employeeTmp.get(0).getLdapUser())){
					/*validate user via DB*/
					isValidUser = getService(request).isValidDBLogin(theUserid, thePassword);
				}else{
					/*validate user via LDAP*/
					isValidUser = isValidLDAPLogin(theUserid, thePassword);
				}
				
			}

		
		if (isValidUser) {
						
//			valid user
//			Employee employeeInfo = null;
//				Remove domain info from the userid input
/*				String loginID = theUserid.substring(theUserid.indexOf("\\")+1);
				logger.info("UserController.processLogin loginID: "+loginID);
				
//              get all data from employee table that matches the loginID
				employee = getService(request).getDetail(loginID);*/
				employee=employeeTmp;
				request.getSession().setAttribute("EMPLOYEE_INFO", employee.get(0).getEMP_ID());
				logger.info("UserController.processLogin, employee employee.get(0).getEMP_ID(): "+employee.get(0).getEMP_ID());
				logger.info("UserController.processLogin, employee employee.get(0).getId(): "+employee.get(0).getId());
				logger.info("UserController.processLogin employee: "+employee.toString());
				model.addAttribute("EmployeeInfo", employee);
				if(serviceContext != null && employee.get(0) != null){
					serviceContext.setEmployee(employee.get(0));
					String userName = serviceContext.getEmployee().getFIRST_NAME() + ProgramConstant.NAME_DELIMITER + serviceContext.getEmployee().getLAST_NAME();
					model.addAttribute("loginUserNameValue", userName);
				}

//				get employee hierarchy that matches the employee id
				List<String> list = getHierarchyQueryResult(request, employee.get(0).getEMP_ID());
				logger.info("UserController.processLogin getEmployeeHierarchy: "+list.toString());
				request.getSession().setAttribute("EMPLOYEE_HIERARCHY", employee);
				model.addAttribute("EmployeeHierarchyInfo", list);
			
		} else {
			model.addAttribute("LoginErrorMessage",
					"*** Your id " + theUserid + " or your password is not valid. Please check and try again.");
			request.getSession().setAttribute("LoginErrorMessage", "*** Your id " + theUserid + " or your password is not valid. Please check and try again.");
		}

		} catch(Exception e) {
			logger.info("Error occured while retrieving employee info: "+e);
		}
		finally {
			try {
			if (employee == null) { 
				logger.info("UserController.processLogin finally, employee is null");
				model.addAttribute("ErrorMessage", "Login failed");
			} else {
				if (employee.size() > 0) {
					logger.info("UserController.processLogin finally, employee is not null");
					String activeStatus = employee.get(0).getACTIVE();
					logger.info("UserController.processLogin finally, employee activeStatus: "+activeStatus);
					if (activeStatus.equalsIgnoreCase("1")) {
						destination = "employee-absence-form";
					}
				}
			} 
			} catch (Exception e) {
				logger.info("Error occured while retrieving employee info: "+e);
			}
		}	
		model.addAttribute("timeout", timeout*1000);
		 return destination;
	}


	boolean isValidLDAPLogin(String theUserid, String thePassword) {

		logger.info("UserController.isValidLDAPLogin userid: " + theUserid);
		
		//Code added to include domain 'CA\' in the user id
		/*if(!theUserid.toUpperCase().startsWith("CA\\")){
			theUserid="CA\\"+theUserid;
		}*/
		
		LdapUtil ldapUtil = new LdapUtil();
		boolean isValidUser = ldapUtil.isValidYtcUser(theUserid, thePassword);

		logger.info("UserController.isValidLDAPLogin isValidUser = " + isValidUser);

		return isValidUser;
	}


	
	public List<String> getHierarchyQueryResult(HttpServletRequest request,Integer empId) {
		List<String> resultList = null;
		String queryString = "SELECT * FROM EMPLOYEE_HIERARCHY where BASE_EMP_ID = '"+ empId+"' or LVL1_EMP_ID = '"+empId+"' or LVL2_EMP_ID = '"+empId+
				"' or LVL3_EMP_ID = '"+empId+"' or LVL4_EMP_ID = '"+empId+"' or LVL5_EMP_ID = '"+empId+"'";
		resultList = getService(request).getQueryResult(queryString);
		return resultList;
	}



	
	
	@RequestMapping(value = "/v1/getAllDealerDropDownList", method = RequestMethod.GET)
	public @ResponseBody List<DropDown> getAllDealerDropDownList(HttpServletRequest request) {
		List<DropDown> employeeAbsenceDropDown = null;
		try{
			employeeAbsenceDropDown = (List<DropDown>)(getService(request).getAllDealerDropDownList());
		}catch(Exception e){
			logger.error("Exception in getAllDealerDropDownList: ",e);
			throw e;
		}
		return employeeAbsenceDropDown;
	}
	
	@RequestMapping(value = "/v1/uploadOrder", method = RequestMethod.POST)
	public @ResponseBody ModelResult<Order> saveReconCreditPaymentData(HttpServletRequest request,  @RequestBody List<Order> orderList) {
		
		//return new ModelResult<Order>(getService(request).saveReconCreditPaymentData(reconDataList, serviceContext.getEmployee()));
		System.out.println("");
		
		

        /* DealerServicesPort proxy = new DealerWebServices().getWebServicePort();
         //System.out.println(prefix + "serv.getWebServicePortAddress() = [" + serv.getWebServicePortAddress() + ']');

         
         
         LoginAndOrderReq req = new LoginAndOrderReq();
         
         TransactionReq tReq = new TransactionReq();
         tReq.setTransactionId("SFT6959");
         req.setTransactionRequest(tReq);
         
         LoginInfo login = new LoginInfo();
         login.setUserId("TEST_LOGIN_4");        // udt 24291, sap 10000620, Ben Tire
         login.setPassword("al");
         req.setLoginCredentials(login);
         
         
         
         
         com.ytcadmin.client.Order orderDetails = new  com.ytcadmin.client.Order();
         
         orderDetails.setShipToNumber("20002363");   // udt 24291001, sap 20002363, BEN TIRE, TOLEDO IL
         
          
          List<OrderLineItemDetails> OrderLineItemDetailsList=new ArrayList<OrderLineItemDetails>();
          for (Iterator iterator = orderList.iterator(); iterator.hasNext();) {
			Order order = (Order) iterator.next();
			OrderLineItemDetails orderItem = new OrderLineItemDetails();
			orderItem.setPartNumber(order.getProduct());
			orderItem.setQuantity( BigInteger.valueOf(order.getOrder()));
			OrderLineItemDetailsList.add(orderItem);
		}
          OrderLineItems orderLineItems=new OrderLineItems();
          orderLineItems.getItemDetail().addAll(OrderLineItemDetailsList);
          
          orderDetails.setOrderItems(orderLineItems);

         
         orderDetails.setPoNumber("SFT6959");
         orderDetails.setOrderedBy("Stephen Smith");
         orderDetails.setOrderOrShippingNotes("");
         orderDetails.setEMail("alain.graziani@yokohamatire.com");
         orderDetails.setPhoneNumber("3177982103");
         
         req.setOrderDetails(orderDetails);
         

         

         
         System.out.println("calling web service");
         OrderResp resp = proxy.loginAndOrderOperation(req);
         System.out.println( "back from web service");
         		
         TransactionResp results = resp.getTransactionResponse();
         
         if (results.isIsTransactionSuccessful().booleanValue()) {
             OrderConfirmation ord = resp.getConfirmedOrder();
             String oNum = ord.getOrderNumber();
             System.out.println( new Date() + "> orderNum = [" + oNum + ']');
         } // if
         else {
             System.out.println( new Date() + "> search failed; " + resp.getTransactionResponse().getError().get(0));
         }*/
		
		return null;
	}
	
	IEmployeeService getService(HttpServletRequest request) {
		return getServiceLocator(request).getEmployeeService();
	}


}
