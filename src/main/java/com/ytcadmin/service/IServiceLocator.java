package com.ytcadmin.service;

import com.ytcadmin.mail.intf.IYTMMMailConnectorService;

public interface IServiceLocator {

	IEmployeeService getEmployeeService();

	//IYTMMMailConnectorService getYTMMMailConnectorService();

}
