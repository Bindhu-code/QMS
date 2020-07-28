package com.fg.tree.dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fg.tree.constant.CommonConstant;
import com.fg.tree.model.MstRole;
import com.fg.tree.model.MstUser;
import com.fg.tree.model.MstUserAccess;
import com.fg.tree.repository.LoginRepository;
import com.fg.tree.repository.RoleRepository;
import com.fg.tree.repository.UserAccessRepository;

@Service
@Transactional
public class UserDAOImpl implements IUserDAO {

	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private RoleRepository roleRepository; 

	@Autowired 
	private UserAccessRepository userAccessRepository;
	
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private  JavaMailSender  javaMailSender;  
	
	@Override
	public MstUser getUserDetail(String username, String password) throws Exception {

		MstUser mstUser = loginRepository.getUserDetail(username, password);
		return mstUser;
	}
	
	@Override
	public List<MstRole> getRole() throws Exception {
		List<MstRole> mstroles = roleRepository.findAll();
		return mstroles;
	}
	
	public List<MstUser> getAllUsers() throws Exception {
		List<MstUser> mstusers = loginRepository.findAll();
		return mstusers;
	}
	public void updateUserDetail(String firstname,String lastname,String contact,String emailid,int roleid,int userid) throws Exception{
		loginRepository.updateUserDetail(firstname, lastname, contact, emailid, roleid, userid);
	}
	
	public boolean isUserExists(String userName)throws Exception{
		boolean isExist = false;
		
		MstUser listOfUser = loginRepository.isUserExists(userName);
		
		if(listOfUser != null)
		{
			isExist = true;
		}
		
		return isExist;
	}

	public boolean isUserAccessExists(int userId,int moduleId)throws Exception{
		boolean isExist = false;
		
		MstUserAccess listOfUser = userAccessRepository.isUserAccessExists(userId, moduleId);
		
		if(listOfUser != null)
		{
			isExist = true;
		}
		
		return isExist;
	}
	public void saveUserAccess(MstUserAccess userAccess)throws Exception {
		try{
		userAccessRepository.save(userAccess);
		sessionFactory.getCurrentSession().beginTransaction().commit();
		}finally {
			sessionFactory.getCurrentSession().clear();
			sessionFactory.getCurrentSession().flush();
		}
	}
	
	public MstUser getUderDataFromrole(int role)throws Exception{
		MstUser user=null;
		try{
			user=loginRepository.isSysAdminExists(role);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return user;
	}
	
	public void updatePassword(String password,String username) throws Exception{
		loginRepository.updatePassword(password,username);
	}
	
	public MstUser getUserDataFromUserName(String userName)throws Exception{
		return loginRepository.isUserExists(userName);
	}
	
	public boolean sendEmailForForgotPassword(MstUser userVO) {
        
    	boolean isSent=false;
    	try{
        String subject = CommonConstant.FORGOT_PASSWORD_SUBJECT;
        String otp=generateOTP(4);
        String body = CommonConstant.FORGOT_PASSWORD_BODY_1 +"  "+otp+"  "+CommonConstant.FORGOT_PASSWORD_BODY_2 +CommonConstant.FORGOT_PASSWORD_BODY_3;
        StringBuilder messageBody = new StringBuilder();
        messageBody.append(body);
        String emailIds[] = { userVO.getEmailId().trim() };
        loginRepository.updateOtp(Integer.valueOf(otp),userVO.getUserName());
        isSent= sendMail(messageBody.toString(), subject,null, emailIds);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        if(isSent==true){
        	
        	return true;
        }
        else
        	return false;
       
    }
	public boolean sendMail(String emailBody, String emailSubject,List<String> attachmentList, String... emailIds)
    {
    	
    	boolean mailSent=true;
    	try{
   	 if (emailIds == null || emailIds.length == 0) {
            return false;
        }
  	 	SimpleMailMessage message = new SimpleMailMessage();
  	 	message.setTo(emailIds[0]);
		message.setFrom(CommonConstant.EMAIL_FROM_NAME);
		message.setSubject(emailSubject);
		message.setText(emailBody);
		message.setSentDate(new Date());
		javaMailSender.send(message);
    	}catch(Exception e){
    		System.out.println("...>"+e.getMessage());
    	}
		return mailSent;
	}
	
	 public static String generateOTP(int otpLengthNumber)
	 	{
	 		
	         String otp = new String();
	         
	         int otpSample=0;
	         
	         for(int i=0;i<otpLengthNumber;i++)
	         {
	             otp=otp+"9";
	         }

	         otpSample=Integer.parseInt(otp);

	         SecureRandom prng;
	         
	         try
	         {
	         	
	             prng = SecureRandom.getInstance("SHA1PRNG"); //Number Generation Algorithm
	             
	             otp = new Integer(prng.nextInt(otpSample)).toString();
	             
	             otp = (otp.length() < otpLengthNumber) ? padleft(otp, otpLengthNumber, '0') : otp;
	             
	         } catch (NoSuchAlgorithmException e)
	         {
	         	e.printStackTrace();
	         }

//	         If generated OTP exists in DB -regenerate OTP again
	         boolean otpExists=false;
	         
	         if (otpExists)
	         {
	             generateOTP(otpLengthNumber);
	         }else
	         {
	            	return otp;
	         }
	         
	         return otp;
	     }
	 
	 private static String padleft(String s, int len, char c)
 	 { //Fill with some char or put some logic to make more secure
 	 
 		 s = s.trim();
 	     
 		 StringBuffer d = new StringBuffer(len);
 	     
 		 int fill = len - s.length();
 	     
 		 while (fill-- > 0)
 		 {
 			 d.append(c);
 		 }  
 		 d.append(s);
 	     
 		 return d.toString();
 	  }
	 

	

	

	@Override
	public void removeDependentFolderUseraccess(int moduleList) {
		
		userAccessRepository.removeDependency(moduleList);
		
	}

	@Override
	public void deleteuseraccess(int moduleId) {
		
		userAccessRepository.deleteuseraccess(moduleId);
		
	}

	@Override
	public List<MstUser> getAllFilereadersUserId() {
		
		List<MstUser> userids = loginRepository.getAllFilereadersUserId();
		
		return userids;
	}

	@Override
	public MstUserAccess addUseraccessDetails(MstUserAccess useraccess) {
		
		MstUserAccess useraccess1 = userAccessRepository.save(useraccess);
		
		return useraccess1;
	}


	
	 
	 
	 	
}
