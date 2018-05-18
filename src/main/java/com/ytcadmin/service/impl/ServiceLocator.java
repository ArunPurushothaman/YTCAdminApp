package com.ytcadmin.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.ytcadmin.mail.intf.IYTMMMailConnectorService;
import com.ytcadmin.service.IEmployeeService;
import com.ytcadmin.service.IServiceLocator;



public class ServiceLocator implements IServiceLocator, ApplicationContextAware
{
    //private static final Logger logger = LoggerFactory.getLogger(ServiceLocator.class);

    ApplicationContext appContext;
    
    private IEmployeeService employeeService;
    
    //private IYTMMMailConnectorService ytmmMailConnectorService;
    

    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;

    }

    @Override
    public IEmployeeService getEmployeeService() {
        if (employeeService == null) {
        	employeeService = (IEmployeeService) appContext.getBean("employeeService");
        }
        return employeeService;
    }
    
   /* @Override
    public IYTMMMailConnectorService getYTMMMailConnectorService() {
        if (ytmmMailConnectorService == null) {
        	ytmmMailConnectorService = (IYTMMMailConnectorService) appContext.getBean("ytmmMailConnectorService");
        }
        return ytmmMailConnectorService;
    }*/
	
  
}
