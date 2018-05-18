package com.ytcadmin.constant;

public class QueryConstant {

	public static final String GET_DEALER="  SELECT cmd.CMBLTONM+' | '+ bd.BD_BILLTOID	FROM [dbo].[bulk_dealer] bd JOIN [dbo].[CUSTMSRTDX] cmd ON cmd.CMBLTOID = bd.BD_BILLTOID jOIN [dbo].[USERMSRTDX] umd ON umd.UMUSERID = bd.BD_WSDL_UID ORDER BY cmd.CMBLTONM ASC ";
	
	public static final String ALL_EMP_NAME_LIST="select EMP_FULL_NAME FROM EMPLOYEE_MASTER";
	
	public static final String IS_VALID_USER="SELECT * FROM USER_MASTER WHERE LOGIN_ID=:login AND PASSWORD=HASHBYTES('SHA2_512', :pwd)";	
	
	public static final String EMP_HIERARCHY_LIST="select BASE_EMP_FULL_NAME from EMPLOYEE_HIERARCHY where BASE_EMP_ASSOCIATE_ID=:login or LVL1_EMP_ASSOCIATE_ID=1 or LVL2_EMP_ASSOCIATE_ID=:login or LVL3_EMP_ASSOCIATE_ID=:login or LVL4_EMP_ASSOCIATE_ID=:login or LVL5_EMP_ASSOCIATE_ID=:login";
	
	public static final String GET_MANAGER_LIST="select * from EMPLOYEE_MASTER where id in ( SELECT DISTINCT(MANAGER_ID) FROM EMPLOYEE_ABSENCE_CALL_IN)";
	
}
