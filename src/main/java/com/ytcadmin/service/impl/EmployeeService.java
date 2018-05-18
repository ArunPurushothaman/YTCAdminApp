package com.ytcadmin.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ytcadmin.common.model.DropDown;
import com.ytcadmin.common.model.Employee;
import com.ytcadmin.constant.QueryConstant;
import com.ytcadmin.dal.IDataAccessLayer;
import com.ytcadmin.dal.model.DalUserMaster;
import com.ytcadmin.mail.intf.IYTCAdminMailConnectorService;
import com.ytcadmin.service.IEmployeeService;
import com.ytcadmin.service.ServiceContext;


@Component
@Configuration
@PropertySource("classpath:/config/ytcadmin.properties")
public class EmployeeService implements IEmployeeService {
	
	
	static Logger logger = Logger.getLogger(EmployeeService.class);

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	private IDataAccessLayer baseDao;

	@Autowired
	private IYTCAdminMailConnectorService ytcAdminMailConnectorService;

	@Autowired
	private Environment env;
	
	@Autowired
	private ServiceContext serviceContext;

	public EmployeeService() {

	}


	@Override
	public List<Employee> getDetail(String loginId) {
		logger.info("inside com.ytcadmin.service.impl.EmployeeService.getDetail loginId: "+loginId);
		CriteriaQuery<DalUserMaster> criteria = entityManager.getCriteriaBuilder().createQuery(DalUserMaster.class);
		Root<DalUserMaster> data = criteria.from(DalUserMaster.class); 
		criteria.select(data);
		criteria.where(entityManager.getCriteriaBuilder().equal(data.get("LOGIN_ID"), loginId));
		List<DalUserMaster> dalEmployee = entityManager.createQuery(criteria).getResultList();
		List<Employee> employeeList = new ArrayList<>();

		for(DalUserMaster dalEmp : dalEmployee){
			Employee employee = new Employee();
			employee.setACTIVE(dalEmp.getACTIVE());
			employee.setBUSINESS_UNIT(dalEmp.getBUSINESS_UNIT());
			employee.setEMAIL(dalEmp.getEMAIL());
			employee.setEMP_ID(dalEmp.getId());
			employee.setFIRST_NAME(dalEmp.getFIRST_NAME());
			employee.setLAST_NAME(dalEmp.getLAST_NAME());
			employee.setLOGIN_ID(dalEmp.getLOGIN_ID());
			employee.setMANAGER_ID(dalEmp.getMANAGER_ID());
			employee.setTITLE_ID(String.valueOf(dalEmp.getTITLE().getId()));
			employee.setSECURITY(dalEmp.getSECURITY());
			employee.setROLE_ID(dalEmp.getROLE_ID());
			employee.setLdapUser(dalEmp.getLdapUser());
			employee.setFullName(dalEmp.getFullName());
			employeeList.add(employee);

		}
		return employeeList;
	}

	@Override
	public DalUserMaster getEmployeeDetail(Integer loginId) {
		DalUserMaster dalEmployee = baseDao.getEntityById(DalUserMaster.class,loginId);
		return dalEmployee;
	}


	//	SELECT * FROM [INTERFACE].[dbo].[W_YTC_EMPLOYEE_DH]
	//			where BASE_EMP_ID = 101 or LVL1_EMP_ID = 101 or LVL2_EMP_ID = 101 or LVL3_EMP_ID = 101 or LVL4_EMP_ID = 101 or LVL5_EMP_ID = 101 -- Andrew Martin Example

	@SuppressWarnings("unchecked")
	public List<String> getQueryResult(String queryString) {
		Query query = entityManager.createNativeQuery(queryString);
		List<String> results = query.getResultList();
		return results;	                
	}


	public List<String> getAllEmployeeNames() {

		logger.info("Inside getAllEmployeeNames");
		Map<String, Object> queryParams = new HashMap<>();
		List<String> results = baseDao.getListFromNativeQuery(QueryConstant.ALL_EMP_NAME_LIST, queryParams);
		logger.info("Exiting getAllEmployeeNames");
		return results;	                
	}



	

	public boolean isValidDBLogin(String userId, String pass) {

		logger.info("Inside isValidDBLogin");
		boolean valid=false;
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("login", userId);
		queryParams.put("pwd", pass);
		List<String> results = baseDao.getListFromNativeQuery(QueryConstant.IS_VALID_USER, queryParams);
		if(!results.isEmpty()){
			valid=true;
		}
		logger.info("isValidDBLogin: "+valid);
		return valid;	                
	}

	

		
	public static String convertDateToString(Date inputDate, String format){
		String convertedDateString = null;

		if(inputDate != null && format != null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			//convertedDateString = sdf.format(new Date());
			convertedDateString = sdf.format(inputDate);
		}
		return convertedDateString;
	}


	public List<DropDown> getAllDealerDropDownList(){
		logger.info("Inside getAllDealerDropDownList");
		List<DropDown> dropdownList = null;
		Map<String, Object> queryParams = new HashMap<>();
		List<String> dalEmployeeList =   baseDao.getListFromNativeQuery(QueryConstant.GET_DEALER, queryParams);
		if(dalEmployeeList != null){
			for(String val : dalEmployeeList){
				DropDown dropDown = new DropDown();
				dropDown.setKey(val);
				dropDown.setValue(val);
				if(dropdownList == null){
					dropdownList = new ArrayList<DropDown>();
				}
				dropdownList.add(dropDown);
			}
		}

		logger.info("Exiting getAllDealerDropDownList");
		return dropdownList;
	}


	
}


