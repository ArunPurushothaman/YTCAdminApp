package com.ytcadmin.service.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import org.springframework.transaction.annotation.Transactional;

import com.ytcadmin.common.model.DropDown;
import com.ytcadmin.common.model.EmailDetails;
import com.ytcadmin.common.model.Employee;
import com.ytcadmin.common.model.EmployeeAbsence;
import com.ytcadmin.common.model.EmployeeAbsenceDropDown;
import com.ytcadmin.common.model.EmployeeMaster;
import com.ytcadmin.common.model.ReportModel;
import com.ytcadmin.constant.QueryConstant;
import com.ytcadmin.dal.IDataAccessLayer;
import com.ytcadmin.dal.model.DalAbsenceReasonMaster;
import com.ytcadmin.dal.model.DalDepartmentMaster;
import com.ytcadmin.dal.model.DalEmployeeAbsenceCall;
import com.ytcadmin.dal.model.DalEmployeeMaster;
import com.ytcadmin.dal.model.DalShiftMaster;
import com.ytcadmin.dal.model.DalSickReasonMaster;
import com.ytcadmin.dal.model.DalUserMaster;
import com.ytcadmin.mail.intf.IYTMMMailConnectorService;
import com.ytcadmin.service.IEmployeeService;
import com.ytcadmin.service.ServiceContext;
import com.ytcadmin.service.util.PdfGenerator;

@Component
@Configuration
@PropertySource("classpath:/config/ytmm.properties")
public class EmployeeService implements IEmployeeService {
	
	
	static Logger logger = Logger.getLogger(EmployeeService.class);

	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	private IDataAccessLayer baseDao;

	@Autowired
	private IYTMMMailConnectorService ytmmMailConnectorService;

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



	public EmployeeMaster getAbsentEmployeeDetail(String employeeName) {	   
		
		logger.info("Inside getAbsentEmployeeDetail:" +employeeName);
		List<DalEmployeeMaster> results=getEmployeeByName(employeeName);
		EmployeeMaster employeeMaster=new EmployeeMaster();
		//Map<String, Object> queryParams = new HashMap<>();
		
		if(!results.isEmpty()){
			DalEmployeeMaster dalEmployeeMaster=results.get(0);
			employeeMaster.setManagerName(baseDao.getById(DalEmployeeMaster.class, Integer.parseInt(dalEmployeeMaster.getManagerId())).getFullName());
			employeeMaster.setPrimaryPhone(dalEmployeeMaster.getPrimaryPhone());
			employeeMaster.setDepartmentId(dalEmployeeMaster.getDepartmentId());
			/*queryParams.put("id", dalEmployeeMaster.getId());
			List<DalEmployeeShift> shift = baseDao.getlist(DalEmployeeShift.class,QueryConstant.GET_EMP_SHIFT, queryParams);
			if(!shift.isEmpty()){
				employeeMaster.setShiftId(shift.get(0).getShiftId());
			}*/
		}
		logger.info("Exiting getAbsentEmployeeDetail");
		return employeeMaster;
	}

	public List<DalEmployeeMaster> getEmployeeByName(String employeeName) {

		logger.info("Inside getEmployeeByName "+ employeeName);
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("name", employeeName);
		List<DalEmployeeMaster> results = baseDao.getListFromNamedQueryWithParameter("DalEmployeeMaster.getEmployeeDetailByName", queryParams);
		logger.info("Exiting getEmployeeByName");
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

	
	public EmployeeAbsence updateEmployeeAbsenceDetail(EmployeeAbsence employeeAbsence) {
		
		logger.info("Inside updateEmployeeAbsenceDetail "+employeeAbsence.getEmployeeName());
		DalEmployeeAbsenceCall dalemployeeAbsence=baseDao.getById(DalEmployeeAbsenceCall.class, employeeAbsence.getId());
		
		//DalEmployeeMaster dalemp=getEmployeeByName(employeeAbsence.getEmployeeName()).get(0);
		SimpleDateFormat format=new SimpleDateFormat("hh:mm a");	
		SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");	
		dalemployeeAbsence.setAbsenceDays(employeeAbsence.getAbsenceDays());
		dalemployeeAbsence.setAbsenceReason(employeeAbsence.getAbsenceReason());
		dalemployeeAbsence.setCallTakenBy(employeeAbsence.getCallTakenBy());
		dalemployeeAbsence.setComments(employeeAbsence.getComments());
		
		dalemployeeAbsence.setDepartmentId(employeeAbsence.getDepartment());
		dalemployeeAbsence.setExtn(employeeAbsence.getExtn());
		//dalemployeeAbsence.setEmployeeName(dalemp.getFullName());
		//dalemployeeAbsence.setEmployeeId(dalemp.getId());
		//dalemployeeAbsence.setManager(dalemp.getManagerId());
		dalemployeeAbsence.setPhoneNo(employeeAbsence.getPhoneNo());
		dalemployeeAbsence.setPoint(employeeAbsence.getPoint());
		dalemployeeAbsence.setShiftId(employeeAbsence.getShift());
		dalemployeeAbsence.setSickReason(employeeAbsence.getSickReason());
		try {
			dalemployeeAbsence.setTime(new Time(format.parse(employeeAbsence.getTime()).getTime()));
			dalemployeeAbsence.setTimeOfCall(new Time(format.parse(employeeAbsence.getTimeOfCall()).getTime()));
			Calendar cal = Calendar.getInstance();
			cal.clear();
			cal.setTime(dateFormat.parse(employeeAbsence.getAbsenceDate()));
			dalemployeeAbsence.setAbsenceDate(cal);
			Calendar call = Calendar.getInstance();
			call.clear();
			call.setTime(dateFormat.parse(employeeAbsence.getDate()));
			dalemployeeAbsence.setDate(call);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("Exception: ",e);
		}
		
		baseDao.update(dalemployeeAbsence);
		employeeAbsence.setManager("update");
		logger.info("Exiting updateEmployeeAbsenceDetail ");
		return employeeAbsence;
	}
	

	public EmployeeAbsence saveEmployeeAbsenceDetail(EmployeeAbsence employeeAbsence) {

		

		EmailDetails emailDetails = new EmailDetails();
		List<String> toEmailIdList = new ArrayList<String>();	


		toEmailIdList.add("ArunThomas.Purushothaman@yokohamatire.com");
		emailDetails.setFromAddress(env.getProperty("mail.ytmm.from"));
		emailDetails.setToAddress(toEmailIdList);
		emailDetails.setSubject("Test Calendar ");
		emailDetails.setText("TEst body");
/*		Map<String, byte[]> attachment=new HashMap<String, byte[]>();
		attachment.put("Emp_Call_Report_Off_Form_"+dalemployeeAbsence.getEmployeeName()+".pdf", pdfArray);
		emailDetails.setAttachment(attachment);*/
		
		logger.info("About to send email");
		ytmmMailConnectorService.sendEmail(emailDetails);
		logger.info("Mail sent successfully");

		return employeeAbsence;	                
	}
	
/*	public EmployeeAbsence saveEmployeeAbsenceDetail(EmployeeAbsence employeeAbsence) {

		logger.info("Inside saveEmployeeAbsenceDetail "+employeeAbsence.getEmployeeName());
		DalEmployeeMaster dalemp=getEmployeeByName(employeeAbsence.getEmployeeName()).get(0);
		DalEmployeeAbsenceCall dalemployeeAbsence=new DalEmployeeAbsenceCall();
		SimpleDateFormat format=new SimpleDateFormat("hh:mm a");	
		SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");	
		dalemployeeAbsence.setAbsenceDays(employeeAbsence.getAbsenceDays());
		dalemployeeAbsence.setAbsenceReason(employeeAbsence.getAbsenceReason());
		dalemployeeAbsence.setCallTakenBy(employeeAbsence.getCallTakenBy());
		dalemployeeAbsence.setComments(employeeAbsence.getComments());
		
		dalemployeeAbsence.setDepartmentId(employeeAbsence.getDepartment());
		dalemployeeAbsence.setExtn(employeeAbsence.getExtn());
		dalemployeeAbsence.setEmployeeName(dalemp.getFullName());
		dalemployeeAbsence.setEmployeeId(dalemp.getId());
		dalemployeeAbsence.setManager(dalemp.getManagerId());
		dalemployeeAbsence.setPhoneNo(employeeAbsence.getPhoneNo());
		dalemployeeAbsence.setPoint(employeeAbsence.getPoint());
		dalemployeeAbsence.setShiftId(employeeAbsence.getShift());
		dalemployeeAbsence.setSickReason(employeeAbsence.getSickReason());
		dalemployeeAbsence.setStatus("ACTIVE");
		
		try {
			dalemployeeAbsence.setTime(new Time(format.parse(employeeAbsence.getTime()).getTime()));
			dalemployeeAbsence.setTimeOfCall(new Time(format.parse(employeeAbsence.getTimeOfCall()).getTime()));
			Calendar cal = Calendar.getInstance();
			cal.clear();
			cal.setTime(dateFormat.parse(employeeAbsence.getAbsenceDate()));
			dalemployeeAbsence.setAbsenceDate(cal);
			Calendar call = Calendar.getInstance();
			call.clear();
			call.setTime(dateFormat.parse(employeeAbsence.getDate()));
			dalemployeeAbsence.setDate(call);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("Exception: ",e);
		}
		

		dalemployeeAbsence=baseDao.create(dalemployeeAbsence);
		//baseDao.create(dalEmployeeShift);
		//baseDao.create(dalEmployeeDepartment);
		logger.info("Absence record saved");
		
		byte [] pdfArray=new PdfGenerator().generatePdf(dalemployeeAbsence,baseDao);

		EmailDetails emailDetails = new EmailDetails();
		List<String> toEmailIdList = new ArrayList<String>();	

		if(env.getProperty("mail.environment").equalsIgnoreCase("PROD")){
			List<String> ccEmailIdList = new ArrayList<String>();
			String managerEmail=baseDao.getById(DalEmployeeMaster.class, Integer.parseInt(dalemployeeAbsence.getManager())).getWorkEmail();
			if((null==managerEmail)||("".equalsIgnoreCase(managerEmail))){
				managerEmail=null;
				employeeAbsence.setManager(managerEmail);
				toEmailIdList=null;
			}else{
				toEmailIdList.add(managerEmail);
			}			
			ccEmailIdList.addAll(Arrays.asList(env.getProperty("mail.ytmm.absence.to").split("\\s*,\\s*")));
			emailDetails.setCcAddress(ccEmailIdList);
		}else{
			toEmailIdList.addAll(Arrays.asList(env.getProperty("mail.ytmm.absence.to").split("\\s*,\\s*")));
		}
		toEmailIdList.clear();
		toEmailIdList.add("ArunThomas.Purushothaman@yokohamatire.com");
		emailDetails.setFromAddress(env.getProperty("mail.ytmm.from"));
		emailDetails.setToAddress(toEmailIdList);
		emailDetails.setSubject("YTMM Employee Call/Report-Off Form - "+dalemp.getFullName());
		emailDetails.setText("Dear User, <br><br>Please find attached the YTMM Employee Call/Report-Off Form for "+dalemp.getFullName()+".<br>"
				+ "<br>For questions, please contact HR.<br><br>Thank you!");
		Map<String, byte[]> attachment=new HashMap<String, byte[]>();
		attachment.put("Emp_Call_Report_Off_Form_"+dalemployeeAbsence.getEmployeeName()+".pdf", pdfArray);
		emailDetails.setAttachment(attachment);
		
		logger.info("About to send email");
		ytmmMailConnectorService.sendEmail(emailDetails);
		logger.info("Mail sent successfully");

		return employeeAbsence;	                
	}*/

	public EmployeeAbsenceDropDown getEmployeeAbsenceDropDown(){
		logger.info("Inside getEmployeeAbsenceDropDown");
		EmployeeAbsenceDropDown employeeAbsenceDropDown=new EmployeeAbsenceDropDown();
		employeeAbsenceDropDown.setDepartmentList(getDepartmentDropDownList());
		employeeAbsenceDropDown.setShiftList(getShiftDropDownList());
		employeeAbsenceDropDown.setAbsenceReasonList(getAbsenceReasonDropDownList());
		employeeAbsenceDropDown.setSickReasonList(getSickReasonDropDownList());
		employeeAbsenceDropDown.setManagerList(getManagerDropDownList());		
		employeeAbsenceDropDown.setEmployeeList(getAllEmployeeDropDownList());
		logger.info("Exiting getEmployeeAbsenceDropDown");
		return employeeAbsenceDropDown;

	}



	private List<DropDown> getShiftDropDownList(){
		logger.info("Inside getShiftDropDownList");
		List<DropDown> dropdownList = null;
		List<DalShiftMaster> dalShiftList =  baseDao.getListFromNamedQuery("DalShiftMaster.getAllDetails");
		if(dalShiftList != null){
			for(DalShiftMaster dalShift : dalShiftList){
				DropDown dropDown = new DropDown();
				dropDown.setKey(dalShift.getShiftName());
				dropDown.setValue(dalShift.getId().toString());
				if(dropdownList == null){
					dropdownList = new ArrayList<DropDown>();
				}
				dropdownList.add(dropDown);
			}
		}

		logger.info("Exiting getShiftDropDownList");

		return dropdownList;
	}

	private List<DropDown> getDepartmentDropDownList(){
		logger.info("Inside getDepartmentDropDownList");
		List<DropDown> dropdownList = null;
		List<DalDepartmentMaster> dalDepartmentList =  baseDao.getListFromNamedQuery("DalDepartmentMaster.getAllDetails");
		if(dalDepartmentList != null){
			for(DalDepartmentMaster dalDepartment : dalDepartmentList){
				DropDown dropDown = new DropDown();
				dropDown.setKey(dalDepartment.getDepartmentDesc()+" ("+dalDepartment.getDepartmentCode()+")");
				dropDown.setValue(dalDepartment.getId().toString());
				if(dropdownList == null){
					dropdownList = new ArrayList<DropDown>();
				}
				dropdownList.add(dropDown);
			}
		}

		logger.info("Exiting getDepartmentDropDownList");

		return dropdownList;
	}

	private List<DropDown> getAbsenceReasonDropDownList(){
		logger.info("Inside getAbsenceReasonDropDownList");
		List<DropDown> dropdownList = null;
		List<DalAbsenceReasonMaster> dalAbsenceReasonList =  baseDao.getListFromNamedQuery("DalAbsenceReasonMaster.getAllDetails");
		if(dalAbsenceReasonList != null){
			for(DalAbsenceReasonMaster dalAbsenceReason : dalAbsenceReasonList){
				DropDown dropDown = new DropDown();
				dropDown.setKey(dalAbsenceReason.getReason());
				dropDown.setValue(dalAbsenceReason.getId().toString());
				if(dropdownList == null){
					dropdownList = new ArrayList<DropDown>();
				}
				dropdownList.add(dropDown);
			}
		}

		logger.info("Exiting getAbsenceReasonDropDownList");
		return dropdownList;
	}


	private List<DropDown> getSickReasonDropDownList(){
		logger.info("Inside getSickReasonDropDownList");
		List<DropDown> dropdownList = null;
		List<DalSickReasonMaster> dalSickReasonList =  baseDao.getListFromNamedQuery("DalSickReasonMaster.getAllDetails");
		if(dalSickReasonList != null){
			for(DalSickReasonMaster dalSickReason : dalSickReasonList){
				DropDown dropDown = new DropDown();
				dropDown.setKey(dalSickReason.getReason());
				dropDown.setValue(dalSickReason.getId().toString());
				if(dropdownList == null){
					dropdownList = new ArrayList<DropDown>();
				}
				dropdownList.add(dropDown);
			}
		}

		logger.info("Exiting getSickReasonDropDownList");
		return dropdownList;
	}
	
	private List<DropDown> getManagerDropDownList(){
		logger.info("Inside getManagerDropDownList");
		List<DropDown> dropdownList = null;
		Map<String, Object> queryParams = new HashMap<>();
		List<Object[]> dalMangerList =  baseDao.getListFromNativeQuery(QueryConstant.GET_MANAGER_LIST, queryParams);
		if(dalMangerList != null){
			for(Object[] dalEmployeeMaster : dalMangerList){
				DropDown dropDown = new DropDown();
				dropDown.setKey(dalEmployeeMaster[7].toString());
				dropDown.setValue(dalEmployeeMaster[0].toString());
				if(dropdownList == null){
					dropdownList = new ArrayList<DropDown>();
				}
				dropdownList.add(dropDown);
			}
			
		}

		logger.info("Exiting getManagerDropDownList");
		return dropdownList;
	}
	
	public List<DropDown> getAllEmployeeDropDownList(){
		logger.info("Inside getAllEmployeeDropDownList");
		List<DropDown> dropdownList = null;
		List<DalEmployeeMaster> dalEmployeeList =   baseDao.getListFromNamedQuery("DalEmployeeMaster.getAllEmployeeDetails");
		if(dalEmployeeList != null){
			for(DalEmployeeMaster dalEmployeeMaster : dalEmployeeList){
				DropDown dropDown = new DropDown();
				dropDown.setKey(dalEmployeeMaster.getFullName());
				dropDown.setValue(dalEmployeeMaster.getId().toString());
				if(dropdownList == null){
					dropdownList = new ArrayList<DropDown>();
				}
				dropdownList.add(dropDown);
			}
		}

		logger.info("Exiting getAllEmployeeDropDownList");
		return dropdownList;
	}

	@Transactional
	public List<EmployeeAbsence> getEmployeeAbsenceHistory(String name){
		logger.info("Inside getEmployeeAbsenceHistory");
		List<EmployeeAbsence> absenceList = new ArrayList<EmployeeAbsence>();
		List<DalEmployeeMaster> empList=getEmployeeByName(name);
		DalEmployeeMaster dalemp=new DalEmployeeMaster();
		SimpleDateFormat format=new SimpleDateFormat("hh:mm a");
		if(!empList.isEmpty()){
			dalemp=empList.get(0);
		}
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("id", dalemp.getId());
		List<DalEmployeeAbsenceCall> DalEmployeeAbsenceCallList =  baseDao.getListFromNamedQueryWithParameter("DalEmployeeAbsenceCall.getAllDetails",queryParams);
		if(DalEmployeeAbsenceCallList != null){
			for(DalEmployeeAbsenceCall dalEmployeeAbsenceCall : DalEmployeeAbsenceCallList){
				EmployeeAbsence employeeAbsence=new EmployeeAbsence();
				employeeAbsence.setId(dalEmployeeAbsenceCall.getEmployeeId());
				employeeAbsence.setEmployeeName(dalEmployeeAbsenceCall.getEmployeeName());
				employeeAbsence.setShift(baseDao.getById(DalShiftMaster.class, Integer.parseInt(dalEmployeeAbsenceCall.getShiftId())).getShiftName());
				employeeAbsence.setDepartment(baseDao.getById(DalDepartmentMaster.class, Integer.parseInt(dalEmployeeAbsenceCall.getDepartmentId())).getDepartmentDesc());
				employeeAbsence.setManager(baseDao.getById(DalEmployeeMaster.class, Integer.parseInt(dalEmployeeAbsenceCall.getManager())).getFullName());
				//employeeAbsence.setAbsenceDate(dalEmployeeAbsenceCall.getAbsenceDate().getTime());
				employeeAbsence.setDisplayAbsenceDate(convertDateToString(dalEmployeeAbsenceCall.getAbsenceDate().getTime(), "MM/dd/yyyy"));
				employeeAbsence.setAbsenceDays(dalEmployeeAbsenceCall.getAbsenceDays());
				employeeAbsence.setAbsenceReason(baseDao.getById(DalAbsenceReasonMaster.class, Integer.parseInt(dalEmployeeAbsenceCall.getAbsenceReason())).getReason());
				employeeAbsence.setSickReason(baseDao.getById(DalSickReasonMaster.class, Integer.parseInt(dalEmployeeAbsenceCall.getSickReason())).getReason());
				employeeAbsence.setPhoneNo(dalEmployeeAbsenceCall.getPhoneNo());
				employeeAbsence.setPoint(dalEmployeeAbsenceCall.getPoint());
				employeeAbsence.setCallTakenBy(dalEmployeeAbsenceCall.getCallTakenBy());
				//employeeAbsence.setDate(dalEmployeeAbsenceCall.getDate().getTime());
				employeeAbsence.setDisplayDate(convertDateToString(dalEmployeeAbsenceCall.getDate().getTime(), "MM/dd/yyyy"));
				employeeAbsence.setExtn(dalEmployeeAbsenceCall.getExtn());
				employeeAbsence.setTime(format.format(dalEmployeeAbsenceCall.getTime()));
				employeeAbsence.setTimeOfCall(format.format(dalEmployeeAbsenceCall.getTimeOfCall()));

				absenceList.add(employeeAbsence);
			}
		}
		logger.info("Exiting getEmployeeAbsenceHistory");

		return absenceList;
	}
	
	@Transactional
	public List<EmployeeAbsence> getEmployeeAbsenceReport(ReportModel input){
		logger.info("Inside getEmployeeAbsenceReport");
		List<EmployeeAbsence> absenceList = new ArrayList<EmployeeAbsence>();
		Map<String, Object> queryParams = new HashMap<>();
		String sqlWhere="where empcallin.status='ACTIVE' and";
		if(!input.getManager().isEmpty()){
			sqlWhere=sqlWhere+" empcallin.MANAGER_ID in (:manager) and";
			queryParams.put("manager", input.getManager());
		}
		if(!input.getEmployee().isEmpty()){
			sqlWhere=sqlWhere+" empcallin.EMP_ID in (:emp) and";
			queryParams.put("emp", input.getEmployee());			 
		}
		if(!input.getDepartment().isEmpty()){
			sqlWhere=sqlWhere+" empcallin.DEPARTMENT_ID in (:dept) and";
			queryParams.put("dept", input.getDepartment());
		}
		if(!input.getShift().isEmpty()){
			sqlWhere=sqlWhere+"  empcallin.SHIFT_ID in (:shift) and";
			queryParams.put("shift", input.getShift());
		}
		
		if(!"".equalsIgnoreCase(input.getStartDate())){
			sqlWhere=sqlWhere+"  empcallin.ABSENCE_DATE>=:startdate and";
			queryParams.put("startdate", input.getStartDate());
		}
		if(!"".equalsIgnoreCase(input.getEndDate())){
			sqlWhere=sqlWhere+"  empcallin.ABSENCE_DATE<=:enddate and";
			queryParams.put("enddate", input.getEndDate());
		}
		String sql=QueryConstant.GET_ABSENCE_REPORT;
		if(!"".equalsIgnoreCase(sqlWhere)){
			sql=sql+sqlWhere.substring(0, sqlWhere.length()-3);
		}
		sql= sql+" order by empcallin.ID";
		
		logger.info("getEmployeeAbsenceReport SQL: "+sql);
		List<Object[]> results = baseDao.getListFromNativeQuery(sql, queryParams);
		
		SimpleDateFormat format=new SimpleDateFormat("hh:mm a");		
		
		if((results != null)&&(!results.isEmpty())){
			for(Object[] dalEmployeeAbsenceCall : results){
				EmployeeAbsence employeeAbsence=new EmployeeAbsence();
				employeeAbsence.setId(Integer.parseInt(dalEmployeeAbsenceCall[0].toString()));
				employeeAbsence.setEmployeeName(dalEmployeeAbsenceCall[1].toString());
				employeeAbsence.setShift(dalEmployeeAbsenceCall[5].toString());
				employeeAbsence.setPoint(dalEmployeeAbsenceCall[3].toString());
				employeeAbsence.setDepartment(dalEmployeeAbsenceCall[4].toString());
				employeeAbsence.setManager(dalEmployeeAbsenceCall[2].toString());
				employeeAbsence.setDisplayAbsenceDate(convertDateToString(((Timestamp) dalEmployeeAbsenceCall[8]), "MM/dd/yyyy"));
				employeeAbsence.setAbsenceDays(Integer.parseInt(dalEmployeeAbsenceCall[9].toString()));
				employeeAbsence.setAbsenceReason(dalEmployeeAbsenceCall[10].toString());
				employeeAbsence.setSickReason(dalEmployeeAbsenceCall[11].toString());
				employeeAbsence.setPhoneNo(dalEmployeeAbsenceCall[6].toString());
				employeeAbsence.setComments(dalEmployeeAbsenceCall[12].toString());
				employeeAbsence.setCallTakenBy(dalEmployeeAbsenceCall[13].toString());
				employeeAbsence.setDisplayDate(convertDateToString(((Timestamp) dalEmployeeAbsenceCall[14]), "MM/dd/yyyy"));
				employeeAbsence.setExtn(dalEmployeeAbsenceCall[15].toString());
				employeeAbsence.setTime(format.format((Time)dalEmployeeAbsenceCall[16]));
				employeeAbsence.setTimeOfCall(format.format((Time)dalEmployeeAbsenceCall[7]));

				absenceList.add(employeeAbsence);
			}
		}
		logger.info("Exiting getEmployeeAbsenceReport");

		return absenceList;
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




	@Override
	public String removeEmployeeAbsenceRecord(String id) {
		logger.info("Inside removeEmployeeAbsenceRecord"+ id);
		String status="fail";
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("id", id);
		queryParams.put("user", serviceContext.getEmployee().getEMP_ID());
		baseDao.updateNative(QueryConstant.DELETE_EMP_ABSENCE_RECORD, queryParams);
		status="success";
		logger.info("Exiting removeEmployeeAbsenceRecord");
		return status;
	}




	@Override
	public EmployeeAbsence getEmployeeAbsenceRecord(String id) {
		logger.info("Inside getEmployeeAbsenceRecord"+ id);
		SimpleDateFormat format=new SimpleDateFormat("hh:mm a");
		DalEmployeeAbsenceCall dalEmployeeAbsenceCall=baseDao.getById(DalEmployeeAbsenceCall.class, Integer.parseInt(id));
		
		EmployeeAbsence employeeAbsence=new EmployeeAbsence();
		employeeAbsence.setId(dalEmployeeAbsenceCall.getEmployeeId());
		employeeAbsence.setEmployeeName(dalEmployeeAbsenceCall.getEmployeeName());
		employeeAbsence.setShift(dalEmployeeAbsenceCall.getShiftId());
		employeeAbsence.setDepartment(dalEmployeeAbsenceCall.getDepartmentId());
		employeeAbsence.setManager(baseDao.getById(DalEmployeeMaster.class, Integer.parseInt(dalEmployeeAbsenceCall.getManager())).getFullName());
		employeeAbsence.setAbsenceDate(convertDateToString(dalEmployeeAbsenceCall.getAbsenceDate().getTime(), "MM/dd/yyyy"));
		//employeeAbsence.setDisplayAbsenceDate(convertDateToString(dalEmployeeAbsenceCall.getAbsenceDate().getTime(), "MM/dd/yyyy"));
		employeeAbsence.setAbsenceDays(dalEmployeeAbsenceCall.getAbsenceDays());
		employeeAbsence.setAbsenceReason(dalEmployeeAbsenceCall.getAbsenceReason());
		employeeAbsence.setSickReason(dalEmployeeAbsenceCall.getSickReason());
		employeeAbsence.setPhoneNo(dalEmployeeAbsenceCall.getPhoneNo());
		employeeAbsence.setPoint(dalEmployeeAbsenceCall.getPoint());
		employeeAbsence.setCallTakenBy(dalEmployeeAbsenceCall.getCallTakenBy());
		employeeAbsence.setDate(convertDateToString(dalEmployeeAbsenceCall.getDate().getTime(), "MM/dd/yyyy"));
		//employeeAbsence.setDisplayDate(convertDateToString(dalEmployeeAbsenceCall.getDate().getTime(), "MM/dd/yyyy"));
		employeeAbsence.setExtn(dalEmployeeAbsenceCall.getExtn());
		employeeAbsence.setTime(format.format(dalEmployeeAbsenceCall.getTime()));
		employeeAbsence.setTimeOfCall(format.format(dalEmployeeAbsenceCall.getTimeOfCall()));
		employeeAbsence.setComments(dalEmployeeAbsenceCall.getComments());
		logger.info("Exiting getEmployeeAbsenceRecord");
		return employeeAbsence;
	}


	public List<DropDown> getAllDealerDropDownList(){
		logger.info("Inside getAllDealerDropDownList");
		List<DropDown> dropdownList = null;
		Map<String, Object> queryParams = new HashMap<>();
		List<String> dalEmployeeList =   baseDao.getListFromNativeQuery("select CMBLTONM from CUSTMSRTDX", queryParams);
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

	/*
	@Override
	public List<String> getHierarchyList(String login) {
		

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("login", login);

		List<String> results = baseDao.getListFromNativeQuery(QueryConstant.EMP_HIERARCHY_LIST, queryParams);
		
		return results;
	}*/
	
}


