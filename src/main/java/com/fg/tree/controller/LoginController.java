package com.fg.tree.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fg.tree.constant.CommonConstant;
import com.fg.tree.dao.IDocumentDetailDAO;
import com.fg.tree.dao.IFolderDAO;
import com.fg.tree.dao.IUserDAO;
import com.fg.tree.model.ErrorMessageVO;
import com.fg.tree.model.LoginHistory;
import com.fg.tree.model.MstBatch;
import com.fg.tree.model.MstContent;
import com.fg.tree.model.MstModule;
import com.fg.tree.model.MstUser;
import com.fg.tree.model.MstUserAccess;
import com.fg.tree.model.PagerModel;
import com.fg.tree.model.TxnFolderDetail;
import com.fg.tree.model.UserForReportVO;
import com.fg.tree.repository.BatchPageRepository;
import com.fg.tree.repository.BatchRepository;
import com.fg.tree.repository.LoginHistoryRepository;

@Controller
public class LoginController {
	
	List<ErrorMessageVO> errorMessageVOs = null;
	
	@Autowired
	IUserDAO userService;
	
	@Autowired
	IFolderDAO iFolderDAO;
	
	@Autowired
	IDocumentDetailDAO iDocumentDetailDAO;
	
	@Autowired
	BatchRepository batchrepository;

	
	@Autowired
	LoginHistoryRepository loginhistoryrepository;

	@Autowired
	BatchPageRepository pagebatchRepository;
	
	private static final int BUTTONS_TO_SHOW = 3;
	private static final int INITIAL_PAGE = 0;
	//private static final int INITIAL_PAGE_SIZE = 5;
	//private static final int[] PAGE_SIZES = { 5, 10 };
	private static final int INITIAL_PAGE_SIZE = 20;
	private static final int[] PAGE_SIZES = { 20, 40 };
	
	
	
	@RequestMapping(path = "/landing", method = RequestMethod.GET)
	public String landing(Model model) throws Exception {
		model.addAttribute("user", new MstUser());
		return "landing";
	}
	
	
	/*Added by Bindhu(14-06-2019) */
	@RequestMapping(path="/reports/{value}",method = RequestMethod.GET)
	public ModelAndView report(@PathVariable String value, Model model,@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page,RedirectAttributes attr)
			throws Exception {
		
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
			
		ModelAndView modelandview = new ModelAndView();
		
		model.addAttribute("usagereport", new MstBatch());
		Page<MstBatch> batch = pagebatchRepository.getAllBatchData(new PageRequest(evalPage, evalPageSize,Sort.Direction.DESC,"batchId"));
		if(batch.getTotalElements()>0) {
		modelandview.addObject("batchdata", batch);
		modelandview.addObject("navigation", "enabled");
		PagerModel pager = new PagerModel(batch.getTotalPages(), batch.getNumber(), BUTTONS_TO_SHOW);
		// evaluate page size
		modelandview.addObject("selectedPageSize", evalPageSize);
		modelandview.addObject("pageName", "reportsPage");
		// add page sizes
		modelandview.addObject("pageSizes", PAGE_SIZES);
		// add pager
		modelandview.addObject("pager", pager);
		}else {
			
			modelandview.addObject("navigation", "disable");
		}
		
		
		modelandview.setViewName("reports_page");
		
		return modelandview ;
		
	}
	
	@PostMapping(path = "/generateReport", params = "action=report")
	public ModelAndView createReport(@Valid MstBatch mstbatch, Model model, BindingResult result, HttpSession session,
			RedirectAttributes attr) throws Exception  {	
		System.out.println("Inside:createReport()--->which creates entry in mst_batch");
		ModelAndView modelandview = new ModelAndView();
		System.out.println(mstbatch.getStartDate());
		System.out.println(mstbatch.getEndDate());
		System.out.println(mstbatch.getUserId());
		String status = "pending";
		batchrepository.saveDatedetails(mstbatch.getStartDate(),mstbatch.getEndDate(),status,mstbatch.getUserId());
		MstBatch latestBatchId = batchrepository.getLatestBatchId();
		
		
		Date startDate = latestBatchId.getStartDate();
		Date endDate = latestBatchId.getEndDate();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String start = format.format(startDate);
		String end = format.format(endDate);
		
		
		attr.addFlashAttribute("BatchMessage", "BatchId: "+latestBatchId.getBatchId()+" is allocated for Report between Dates "+start+" and "+end+"....!!");
		
		modelandview.setViewName("redirect:/reports/report");
		
		return modelandview;
	
	}
	/*End by Bindhu(14-06-2019) */
	
	@SuppressWarnings({ "unchecked", "resource" })
	@RequestMapping(value = "downloadReport/{fileName}", method = RequestMethod.GET)
	public void downloadReport(@PathVariable String fileName, HttpSession session, HttpServletResponse response,
			@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page)
			throws Exception {
		
		
		
		FileInputStream fileIn = null;
		OutputStream outStream = null;
		
		File newFile = new File(CommonConstant.REPORT_PATH + "/" + fileName+".xlsx");
		System.out.println("Filelocation:::::::------>"+newFile.getAbsolutePath());
		fileIn = new FileInputStream(newFile);
		try {
		String mimeType = "application/octet-stream";
		response.setContentType(mimeType);
		response.setContentLength((int) newFile.length());
		
		
		// forces download
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", newFile.getName());
		response.setHeader(headerKey, headerValue);

		
		
		// obtains response's output stream
		outStream = response.getOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead = -1;

		while ((bytesRead = fileIn.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileIn.close();
				outStream.flush();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}	
		

	@RequestMapping(path = "/login", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView getLogin(@ModelAttribute MstUser user, HttpServletRequest request, Model model, RedirectAttributes redirectAttrs, @RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) throws UnknownHostException {
		
		
		Date now = new Date();
		
		LocalDate date = LocalDate.now();
		java.sql.Date dateSql = new java.sql.Date(now.getTime());
		Time timeSql = new Time(now.getTime());
		
		System.out.println("Login Date:::"+dateSql);
		System.out.println("Login time:::"+timeSql);
		
		
		
        String loggedInUserSystemIP="";
		String remoteIP = request.getRemoteAddr();
				
		if(remoteIP.startsWith("0:0:0:0:0:0:0:1")){
			loggedInUserSystemIP="127.0.0.1";
		}else{
			loggedInUserSystemIP=remoteIP;
		}
		//System.out.println("accessedSystemIP--->" + loggedInUserSystemIP);
        
		/*System.out.println(dateSql);
		System.out.println(timeSql);*/
	
		// Evaluate page size. If requested parameter is null, return initial
		// page size
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		// Evaluate page. If requested parameter is null or less than 0 (to
		// prevent exception), return initial size. Otherwise, return value of
		// param. decreased by 1.
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
		
		String username = null;
		String password = null;
		ModelAndView modelAndView = new ModelAndView();
		MstUser sessionUser = (MstUser) model.asMap().get("user");
		
		
		if (sessionUser != null) {
			username = sessionUser.getUserName();
			password = sessionUser.getPassword();
		} else {
			username = user.getUserName();
			password = user.getPassword();
		}
		
		errorMessageVOs = new ArrayList<ErrorMessageVO>();
		ErrorMessageVO errorMessageVO = null;
		
		HttpSession session = request.getSession(true);
		List<MstModule> parentList = new ArrayList<MstModule>();
		List<MstUserAccess> childList = new ArrayList<MstUserAccess>();
		List<Integer> moduleList = new ArrayList<Integer>();
		//System.out.println("Login controller :::" + session.getAttribute("hiddenFlag"));
		
		
		try {
			MstUser mstUser = userService.getUserDetail(username, password);
			
			session.setAttribute(CommonConstant.SESSION_USER_VO, mstUser);	
			if (mstUser != null && mstUser.getUserId() > 0) {
				
				model.addAttribute("username", mstUser.getUserId());
				session.setAttribute(CommonConstant.SESSION_USER_VO, mstUser);
				
				//System.out.println("Logged In User DateTime ---->" +dateSql +" "+ timeSql +" " + "---->" + mstUser.getUserName());

				int userID = mstUser.getUserId();
				
				/*---Added by Bindhu to insert the login details of user in mst_login_history----*/
		
				if(session.getAttribute("hiddenFlag")=="true") {
					
					LoginHistory logintimeofuser = loginhistoryrepository.getloginDetailsofUser(userID,dateSql);
						if(logintimeofuser!=null) {
							
							Date Time = logintimeofuser.getLoginTime();
							Date dateofuser = logintimeofuser.getLoginDate();
							SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
							Date  currenttime = format.parse(timeSql.toString());
							Date DBtime = format.parse(Time.toString());
							//System.out.println(DBtime.compareTo(currenttime));
							
							
									if(DBtime.compareTo(currenttime)!=0) {
										
										loginhistoryrepository.deletePreviousLogindetails(userID,dateofuser,Time);
										
									}
						}
									
				loginhistoryrepository.saveDetails(userID,timeSql,dateSql, loggedInUserSystemIP);
					
				}
					
			/* -----End by Bindhu-----  */
			
				/*
				 * Prepare Code for get that folder list which have allow access on same user
				*/
				List<MstUserAccess> modules = iDocumentDetailDAO.searchHierarchy(mstUser.getUserId());
				
				if (modules.size() > 0) {
					for (MstUserAccess mstUserAccess : modules) {
						if (mstUserAccess.getMstModule().getParentId() != null && mstUserAccess.getMstModule().getParentId().equalsIgnoreCase("#")) {
							parentList.add(mstUserAccess.getMstModule());
						} else {
							//childDownloadMap.put(mstUserAccess.getUserAccessId(),mstUserAccess.getDownload());
							childList.add(mstUserAccess);
						}
						moduleList.add(mstUserAccess.getMstModule().getModuleId());
					}
				}
				/*
				 * Check condition whether user is for document reader then direct re-direct to
				 * fetch file details.
				 */
				/*if (mstUser.getRoleId() == 3 || mstUser.getRoleId()==2) {
					Page<TxnDocumentDetail> documentDetails = iFolderDAO.getDetailsForReader(moduleList, new PageRequest(evalPage, evalPageSize));
					
					String fileRec = "enabled"; 
					modelAndView.addObject("filerec", fileRec);
					
					if (documentDetails.getTotalElements() > 0) {
						session.setAttribute(CommonConstant.FILE_DETAILS_LIST, documentDetails);
						modelAndView.addObject("navigation", "enabled");
						modelAndView.addObject("fileDetailList", documentDetails);
						PagerModel pager = new PagerModel(documentDetails.getTotalPages(), documentDetails.getNumber(),
								BUTTONS_TO_SHOW);
						// evaluate page size
						modelAndView.addObject("selectedPageSize", evalPageSize);
						// add page sizes
						modelAndView.addObject("pageSizes", PAGE_SIZES);
						// add pager
						modelAndView.addObject("pager", pager);
						modelAndView.addObject("location", "SHOW Details");
					}else {
						modelAndView.addObject("navigation", "disable");
					}
					modelAndView.setViewName("open_folder_name");
				} else {*/
					session.setAttribute(CommonConstant.CHILD_LIST, childList);
					model.addAttribute("parentList", parentList);
					session.setAttribute(CommonConstant.PARENT_LIST, parentList);
					model.addAttribute("folderDetail", new TxnFolderDetail());
					modelAndView.setViewName("folder_page");
				//}
			} else {
				errorMessageVO = new ErrorMessageVO();
				errorMessageVO.setErrorMessage(CommonConstant.USER_PASSWORD_INVALID);
				errorMessageVOs.add(errorMessageVO);
				session.setAttribute(CommonConstant.USER_PASSWORD_INVALID, errorMessageVO);
				redirectAttrs.addFlashAttribute("errorMsg", errorMessageVOs);
				modelAndView.setViewName("redirect:QMS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return modelAndView;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/QMS", method = RequestMethod.GET)
	public String getHome(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute("user", new MstUser());

		HttpSession session = request.getSession(true);
		session.setAttribute("hiddenFlag", "true");
		
		List<ErrorMessageVO> messageVOs = (ArrayList<ErrorMessageVO>) model.asMap().get("errorMsg");
		if (messageVOs != null && messageVOs.size() > 0) {
			model.addAttribute("errorMsg", messageVOs);
		}
		
		MstContent data = iDocumentDetailDAO.showContent();
		//System.out.println("Data from databse-->" + data.getContent());
		model.addAttribute("Content", data.getContent());
		
		return "login";
	}
	
	@RequestMapping(value = "/getUserDataForReport", method = RequestMethod.POST)
	@ResponseBody
	public List<UserForReportVO>  getAllUserData() throws Exception{
		
		List<UserForReportVO> UserDataList = new  ArrayList<UserForReportVO>();
		UserForReportVO userReportVO;
		
		List<MstUser> userRecords = userService.getAllUsers();
		for (MstUser mstUser : userRecords) {
			
			userReportVO = new UserForReportVO();
			userReportVO.setUsername(mstUser.getUserName());
			userReportVO.setUserId(mstUser.getUserId());
			userReportVO.setFirstname(mstUser.getFirstName());
			userReportVO.setLastname(mstUser.getLastName());
			UserDataList.add(userReportVO);	
			
		}
		return UserDataList;
	
	}
	
	
	
	@RequestMapping(path = "/forgotpassword", method = RequestMethod.GET)
	public String fogotpassword(Model model) throws Exception {
		model.addAttribute("forgotuser", new MstUser());
		return "forgotPassword";
		
	}
	@PostMapping(value = "/updatePassword")
	public String updatePassword(@Valid MstUser user, Model model, BindingResult result, HttpSession session) {
		
		List<ErrorMessageVO> errorMessageVOs = new ArrayList<ErrorMessageVO>();
		String returnVal = null;
		try {
			if (userService.isUserExists(user.getUserName())) {
				ErrorMessageVO errorMessageVO = new ErrorMessageVO();
				errorMessageVO.setErrorMessage(CommonConstant.PASSWORD_UPDATED_MESSAGE);
				errorMessageVOs.add(errorMessageVO);
				model.addAttribute("errorMsg", errorMessageVO);
				model.addAttribute("user", new MstUser());
				userService.updatePassword(user.getPassword(),user.getUserName());
				return "login";
			} else {
				ErrorMessageVO errorMessageVO = new ErrorMessageVO();
				errorMessageVO.setErrorCode(CommonConstant.USER_NOT_EXIST_CODE);
				errorMessageVO.setErrorMessage(CommonConstant.USER_NOT_EXIST_MESSAGE);
				errorMessageVOs.add(errorMessageVO);
				model.addAttribute("errorMsg", errorMessageVO);
				model.addAttribute("forgotuser", new MstUser());
				returnVal = "forgotPassword";
				} 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnVal;
	}
	
	@RequestMapping(path = "/verifyuser", method = RequestMethod.POST)
	@ResponseBody
	public ErrorMessageVO verifyuser(HttpServletRequest request, HttpServletResponse response,HttpSession session,Model model) {
		System.out.println("UserLoginController.verifyuser()");		
		MstUser users=null;
		ErrorMessageVO errorMessageVO=new ErrorMessageVO();
		String userName=request.getParameter("userName");
		try {
			 users = userService.getUserDataFromUserName(userName);
			if(users!=null && users.getEmailId()!=null){
				System.out.println("users.getEmailId()--->"+users.getEmailId());
				boolean issent=userService.sendEmailForForgotPassword(users);
				users.setPinId(0);
				System.out.println("issent-->"+issent);
				errorMessageVO.setErrorMessage("User Verified!...");
			}else{
				errorMessageVO.setErrorMessage("User Not Valid!...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	//	model.addAttribute("forgotuser", users);
		return errorMessageVO;
	}
	
	@RequestMapping(path = "/verifyPin", method = RequestMethod.POST)
	@ResponseBody
	public ErrorMessageVO verifyPin(HttpServletRequest request, HttpServletResponse response,HttpSession session,Model model) {
		System.out.println("UserLoginController.verifyPin()");		
		MstUser users=null;
		ErrorMessageVO errorMessageVO=new ErrorMessageVO();
		try {
			String otp=request.getParameter("pin");
			String userName=request.getParameter("userName");
			
			System.out.println("otp--->"+otp+" userName--->"+userName);
			 users = userService.getUserDataFromUserName(userName);
			if(users!=null && users.getPinCreatedDate()!=null){
				System.out.println("users.getPinCreatedDate()--->"+users.getPinCreatedDate());
				
				Date d1 = null;
				Date currentDate = new Date();
				d1=users.getPinCreatedDate();
			
				long diff = currentDate.getTime() - d1.getTime();
				
				long diffMinutes = diff / (60 * 1000) % 60;
				
				System.out.println("time difference--->"+diffMinutes);
				
				if(diffMinutes<CommonConstant.OTP_VALID_TIME && Integer.parseInt(otp)==users.getPinId()){
					errorMessageVO.setErrorMessage("Pin is valid!...");
				}else{
					errorMessageVO.setErrorMessage("Pin Expired!...");
					
				}
				
			
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//model.addAttribute("forgotuser", users);
		//return "forgotPassword";
		return errorMessageVO;
	}
}
