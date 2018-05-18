package com.ytcadmin.constant;

public class QueryConstant {

	public static final String GET_ABSENCE_HISTORY="select * from EMPLOYEE_ABSENCE_CALL_IN where EMPLOYEE_NAME=:name order by CREATED_DATE desc";
	
	public static final String ALL_EMP_NAME_LIST="select EMP_FULL_NAME FROM EMPLOYEE_MASTER";
	
	public static final String IS_VALID_USER="SELECT * FROM USER_MASTER WHERE LOGIN_ID=:login AND PASSWORD=HASHBYTES('SHA2_512', :pwd)";
	
	public static final String GET_EMP_SHIFT="select * from EMPLOYEE_SHIFT WHERE EMP_ID=:id AND START_DATE<=DATEADD(DAY, DATEDIFF(DAY, 0, GETDATE()), 0) AND END_DATE>=DATEADD(DAY, DATEDIFF(DAY, 0, GETDATE()), 0)";
	
	public static final String GET_ABSENCE_REPORT="SELECT empcallin.ID\n      ,[EMPLOYEE_NAME]\n      ,empmast.EMP_FULL_NAME MANAGER_NAME\n      ,empdept.DEPARTMENT_CODE\n\t  ,empdept.DEPARTMENT_DESC\n      ,empshift.SHIFT_NAME\n      ,[PHONE_NO]\n      ,[TIME_OF_CALL]\n      ,[ABSENCE_DATE]\n      ,[ABSENCE_DAYS]\n      ,empreason.REASON as ABSENCE_REASON\n      ,empsick.REASON as SICK_REASON\n      ,[COMMENTS]\n      ,[CALL_TAKEN_BY]\n      ,[DATE] CALL_TAKEN_DATE\n      ,[EXTN] \n      ,[TIME] CALL_TAKEN_TIME\n  FROM EMPLOYEE_ABSENCE_CALL_IN empcallin\n  left join SHIFT_MASTER empshift on empcallin.SHIFT_ID = empshift.id\n  left join DEPARTMENT_MASTER empdept on empcallin.DEPARTMENT_ID = empdept.id\n  left join ABSENCE_REASON_MASTER empreason on empcallin.ABSENCE_REASON = empreason.id\n  left join SICK_REASON_MASTER empsick on empcallin.SICK_REASON = empsick.id\n  left join EMPLOYEE_MASTER empmast on empcallin.MANAGER_ID = empmast.id\n ";
	
	public static final String EMP_HIERARCHY_LIST="select BASE_EMP_FULL_NAME from EMPLOYEE_HIERARCHY where BASE_EMP_ASSOCIATE_ID=:login or LVL1_EMP_ASSOCIATE_ID=1 or LVL2_EMP_ASSOCIATE_ID=:login or LVL3_EMP_ASSOCIATE_ID=:login or LVL4_EMP_ASSOCIATE_ID=:login or LVL5_EMP_ASSOCIATE_ID=:login";
	
	public static final String DELETE_EMP_ABSENCE_RECORD="UPDATE EMPLOYEE_ABSENCE_CALL_IN SET STATUS='DELETED', MODIFIED_BY=:user, MODIFIED_DATE=SYSDATETIME() WHERE ID=:id";
	
	public static final String GET_MANAGER_LIST="select * from EMPLOYEE_MASTER where id in ( SELECT DISTINCT(MANAGER_ID) FROM EMPLOYEE_ABSENCE_CALL_IN)";
	
}
