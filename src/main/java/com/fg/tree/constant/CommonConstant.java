package com.fg.tree.constant;

public class CommonConstant {
	
	public static final String USER_PASSWORD_INVALID = "*  Username/Password invalid";
	
	public static final String USER_PASSWORD_INVALID_CODE = "*  Username/Password invalid";
	
	public static final String USER_ALREADY_EXIST = "User already exist";
	
	public static final String FILE_STATUS_UPLOAD = "Uploaded";
	
	public static final String FILE_STATUS_DOWNLOAD = "Downloaded";
	
	public static final String FILE_STATUS_VIEW = "Viewed";
	
	public static final String BATCH_STATUS = "Done";
	
	public static final String BATCH_STATUS_NO_DATA = "NoData";
	
	public static final long USER_ALREADY_EXIST_CODE = -101;
	
	public static final String UNIQUE_SYSADMIN_CHECK = "Only one user can allow sysadmin role.";
	
	public static final long UNIQUE_SYSADMIN_CHECK_CODE = -102;
	
	public static final long UNIQUE_CREATE_USER_RIGHTS_CODE = -103;
	
	public static final String UNIQUE_CREATE_USER_RIGHTS = "Aceess should not allow with same folder.";
	
	public static final String SESSION_USER_VO = "SESSION_USER_VO";
	
	public static final String CHILD_LIST = "CHILD_LIST";
	
	public static final String PARENT_LIST = "PARENT_LIST";
		
	//Production
	//public static final String FILE_PATH = "/app/DMS/Files/";
	//public static final String FILE_PATH_FOLDER = "/app/DMS/Files/";
	//public static final String REPORT_PATH = "/app/DMS/Files/Reports/";
	
	//QA
	public static final String FILE_PATH = "/app/DMSQA/Files/";	
	public static final String FILE_PATH_FOLDER = "/app/DMSQA/Files/";
	//C:\Users\455810\Desktop\temp\QMS-1.0.0\BOOT-INF\classes\com\fg\tree\constant
	public static final String REPORT_PATH = "/app/DMSQA/Files/Reports/";
	
	//local
	//public static final String REPORT_PATH = "D:/app/DMSQA/Files/Reports/";
	
	
	public static final String CREATE_MODULE = "Directory Created Successfully.";
	
	public static final String MODULE_ID = "MODULE_ID";
	
	public static final String PARENT_FOLDER_ID = "PARENT_FOLDER_ID";
	
	public static final String ALREADY_AVAILABLE_MODULE = "Directory already available.";
	
	public static final String FILE_DETAILS_LIST = "FILE_DETAILS_LIST";
	
	public static final String INACTIVE_FILE_LIST = "INACTIVE_FILE_LIST";
	
	public static final String DEACTIVE_FILE_LIST = "DEACTIVE_FILE_LIST";
		
	public static final String FULL_FILE_LOC_PATH = "FULL_FILE_LOC_PATH";
	
	public static final long USER_NOT_EXIST_CODE = -103;

	public static final String USER_NOT_EXIST_MESSAGE = "User not exist";
		
	public static final String PASSWORD_UPDATED_MESSAGE = "Password Updated Successfully.";
	
	public static final String USER_EMAIL_EXIST_MESSAGE = "EmailId already available.";
	
	//public static final String EMAIL_HOST_NAME="10.0.28.133";
	public static final String EMAIL_HOST_NAME="10.0.28.18";
	
	public static final String EMAIL_PORT="587";
	
	/*public static final String EMAIL_USER_NAME="fbcustomer@futuregroup.in";*/
	public static final String EMAIL_USER_NAME="admiad";
	
	public static final String EMAIL_FROM_NAME="Admin.mdm@futuregroup.in";
	
/*	public static final String EMAIL_PASSWORD="vistexho123";*/
	public static final String EMAIL_PASSWORD="india123";
	
	public static final String EMAIL_SMTP_AUTH="false";
	
	public static final String EMAIL_SMTP_SSL_ENABLE="true";
	
	public static final String EMAIL_PROTOCOL="smtp";
	
	public static final String FORGOT_PASSWORD_SUBJECT="Your OTP for Resetting QMS Password";

	public static final String FORGOT_PASSWORD_BODY_1="Your One Time Password (OTP) for resetting the password for your QMS account is";
	
	public static final String FORGOT_PASSWORD_BODY_2="Please enter this code in the OTP code box listed in page.";
	
	public static final String FORGOT_PASSWORD_BODY_3="Note: Please note this will be valid for next 20 minutes only.";
	
	public static final int OTP_VALID_TIME=20;
}

