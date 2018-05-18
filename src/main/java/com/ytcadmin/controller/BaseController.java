package com.ytcadmin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.ytcadmin.service.IServiceLocator;
import com.ytcadmin.service.impl.ServiceLocator;


/**
 * The base class for all controllers. Controllers implement the product's RESTful API.
 */
public abstract class BaseController {
	//private static final transient Logger log = LoggerFactory.getLogger("BaseController");

	@Autowired
	protected ServiceLocator serviceLocator;


	protected BaseController() {
	}

	protected IServiceLocator getServiceLocator(HttpServletRequest request) {
		return serviceLocator;
	}

	protected IServiceLocator getServiceLocator() {
		return serviceLocator;
	}
}
