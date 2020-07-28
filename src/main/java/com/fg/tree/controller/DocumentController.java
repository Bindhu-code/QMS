package com.fg.tree.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fg.tree.constant.CommonConstant;
import com.fg.tree.dao.IDocumentDetailDAO;
import com.fg.tree.dao.IFolderDAO;
import com.fg.tree.dao.IModuleDAO;
import com.fg.tree.dao.IUserDAO;
import com.fg.tree.model.AttributeVO;
import com.fg.tree.model.AuthorizationvaluesVO;
import com.fg.tree.model.ErrorMessageVO;
import com.fg.tree.model.LoginHistory;
import com.fg.tree.model.ModulesList;
import com.fg.tree.model.MstAttributeConfig;
import com.fg.tree.model.MstBatch;
import com.fg.tree.model.MstCategory;
import com.fg.tree.model.MstContent;
import com.fg.tree.model.MstDocType;
import com.fg.tree.model.MstHeader;
import com.fg.tree.model.MstModule;
import com.fg.tree.model.MstSubCat;
import com.fg.tree.model.MstUser;
import com.fg.tree.model.MstUserAccess;
import com.fg.tree.model.MstUserAccessVO;
import com.fg.tree.model.PagerModel;
import com.fg.tree.model.ResponseVO;
import com.fg.tree.model.TxnDocumentDetail;
import com.fg.tree.model.TxnDocumentDetailVO;
import com.fg.tree.model.TxnFolderDetail;
import com.fg.tree.repository.AttrributeConfigRepository;
import com.fg.tree.repository.BatchPageRepository;
import com.fg.tree.repository.BatchRepository;
import com.fg.tree.repository.DocumentDetailRepository;
import com.fg.tree.repository.FolderRepository;
import com.fg.tree.repository.LoginHistoryRepository;
import com.fg.tree.repository.ModuleRepository;
import com.fg.tree.repository.UserAccessRepository;

@Controller
public class DocumentController {

	Logger log = LoggerFactory.getLogger(DocumentController.class);
	List<ErrorMessageVO> errorMessageVOs = null;

	@Autowired
	IFolderDAO iFolderDAO;

	@Autowired
	IDocumentDetailDAO iDocumentDetailDAO;

	@Autowired
	IModuleDAO imoduleDao;

	@Autowired
	IUserDAO iuserdao;

	@Autowired
	DocumentDetailRepository documentdetailrepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	FolderRepository folderRepository;

	@Autowired
	UserAccessRepository useraccessrepository;

	@Autowired
	AttrributeConfigRepository attrributeConfigRepository;

	@Autowired
	LoginHistoryRepository loginhistoryRepository;

	@Autowired
	BatchRepository batchRepository;
	
	@Autowired
	BatchPageRepository  pagebatchRepository;

	private static final int BUTTONS_TO_SHOW = 3;
	private static final int INITIAL_PAGE = 0;

	//private static final int INITIAL_PAGE_SIZE = 5;
	//private static final int[] PAGE_SIZES = { 5, 10 };
	//private static final int INITIAL_PAGE_SIZE = 10;
	//private static final int[] PAGE_SIZES = { 10, 20 };

	private static final int INITIAL_PAGE_SIZE = 20;
	private static final int[] PAGE_SIZES = { 20, 40 };

	
	

	@RequestMapping(path = "/tiles", method = RequestMethod.GET)
	public ModelAndView getTilesView() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("tiles");
		return modelAndView;
	}

	@RequestMapping(path = "/index", method = RequestMethod.GET)
	public ModelAndView getHome() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@RequestMapping(path = "/quality", method = RequestMethod.GET)
	public ModelAndView compliance() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("quality");
		return modelAndView;
	}

	@RequestMapping(path = "/addContent", method = RequestMethod.GET)
	public String addContent(Model model) throws Exception {

		model.addAttribute("createContent", new MstContent());
		return "content";
	}

	@PostMapping(value = "/saveContent", params = "action=save")
	public ModelAndView createcontent(@Valid MstContent mstContent, Model model, BindingResult result,
			HttpSession session, RedirectAttributes attr) throws Exception {

		ModelAndView modelandview = new ModelAndView();
		String Data = mstContent.getContent();
		MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
		String CreatedBy = mstUser.getUserName();

		iDocumentDetailDAO.updateContent(Data, CreatedBy);
		attr.addFlashAttribute("message", "Data is updated Successfully");
		modelandview.setViewName("redirect:/addContent");
		return modelandview;
	}

	@RequestMapping(path = "/addNewFolder/{value}", method = RequestMethod.GET)
	public String addnewFolder(@PathVariable String value, Model model) throws Exception {

		if (value.equals("parent")) {
			model.addAttribute("createparent", new MstModule());
			
			model.addAttribute("PageValue", value);
		} else if (value.equals("child")) {

			model.addAttribute("createchild", new MstModule());
			
			model.addAttribute("PageValue", value);
			List<MstModule> modulelist = imoduleDao.getParentFolderNames();
			System.out.println("Content:::::----->" + modulelist.size());
			model.addAttribute("ParentFolders", modulelist);

		} else if (value.equals("remove")) {
			model.addAttribute("removechild", new MstModule());
			model.addAttribute("PageValue", value);
			List<MstModule> modulelist = imoduleDao.getParentFolderNames();
			model.addAttribute("ParentFolders", modulelist);
		} else if (value.equals("removeparent")) {

			model.addAttribute("removeparent", new MstModule());
			model.addAttribute("PageValue", value);
			List<MstModule> modulelist = imoduleDao.getParentFolderNames();
			model.addAttribute("ParentFolders", modulelist);

		} else if (value.equals("secondchild")) {
			model.addAttribute("secondchild", new MstModule());
			model.addAttribute("PageValue", value);
			List<MstModule> modulelist = imoduleDao.getParentFolderNames();
			model.addAttribute("ParentFolders", modulelist);

		} else if (value.equals("thirdchild")) {
			model.addAttribute("thirdchild", new MstModule());
			model.addAttribute("PageValue", value);
			List<MstModule> modulelist = imoduleDao.getParentFolderNames();
			model.addAttribute("ParentFolders", modulelist);
		} else if (value.equals("removesecondchild")) {

			model.addAttribute("removesecondchild", new MstModule());
			model.addAttribute("PageValue", value);
			List<MstModule> modulelist = imoduleDao.getParentFolderNames();
			model.addAttribute("ParentFolders", modulelist);
		} else {
			model.addAttribute("removethirdchild", new MstModule());
			model.addAttribute("PageValue", value);
			List<MstModule> modulelist = imoduleDao.getParentFolderNames();
			model.addAttribute("ParentFolders", modulelist);

		}

		return "folder_new";

	}

	@PostMapping(path = "/addParent", params = "action=create")
	public ModelAndView addParent(@Valid MstModule mstModule, Model model, BindingResult result, HttpSession session,
			RedirectAttributes attr) throws Exception {

		ModelAndView modelandview = new ModelAndView();
		String name = mstModule.getModuleName();
		System.out.println("Module name:---->" + name);
		MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);

		MstModule module2 = moduleRepository.searchDirectory(name);

		if (module2 != null) {

			attr.addFlashAttribute("errorMsg", "Folder already available...!");
			
		} else {
			mstModule.setParentId("#");
			mstModule.setCreatedBy(mstUser.getUserName());
			MstModule module = imoduleDao.addParent(mstModule);
			attr.addFlashAttribute("Msg", "Folder is successfully created");

			/* Start : Code Change By Bindhu : For FileReader default access 26-04-2019 */
			MstModule parentmodule = imoduleDao.getParentId(name);

			System.out.println("ModuleId of newly created folder::::---->" + parentmodule.getModuleId());

			List<MstUser> user = iuserdao.getAllFilereadersUserId();

			for (MstUser userId : user) {
				MstUserAccess useraccess1 = new MstUserAccess();

				useraccess1.setUserId(userId.getUserId());
				useraccess1.setMstModule(parentmodule);

				MstUserAccess useraccess = iuserdao.addUseraccessDetails(useraccess1);
				userId.getUserId();
			}
			/* End : Code Change By Bindhu : For FileReader default access 26-04-2019 */

		}

		modelandview.setViewName("redirect:/addNewFolder/parent");
		return modelandview;

	}

	@PostMapping(path = "/addChild", params = "action=create")
	public ModelAndView addChild(@Valid MstModule mstModule, Model model, BindingResult result, HttpSession session,
			RedirectAttributes attr) throws Exception {

		ModelAndView modelandview = new ModelAndView();
		String[] name = mstModule.getModuleName().split(",");

		System.out.println("Module name:---->" + name[1]);
		System.out.println("ModuleID:---->" + name[0]);
		MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
		mstModule.setCreatedBy(mstUser.getUserName());
		MstModule module2 = moduleRepository.searchDirectory(name[1]);

		if (module2 != null) {

			attr.addFlashAttribute("errorMsg", "Sub-folder already available...!");
			System.out.println("Already available");
		} else {
			mstModule.setParentId(name[0]);
			mstModule.setCreatedBy(mstUser.getUserName());
			mstModule.setModuleName(name[1]);
			MstModule module = imoduleDao.addChild(mstModule);
			MstModule parentfoldername = imoduleDao.getParent(Integer.parseInt(name[0]));
			System.out.println("ParentFoldername::::::::::::::::---------------" + parentfoldername.getModuleName());
			// File directory = new File(CommonConstant.FILE_PATH_FOLDER + "\\" +
			// parentfoldername.getModuleName());
			File directory = new File(CommonConstant.FILE_PATH_FOLDER + parentfoldername.getModuleName());

			System.out.println("File Path:::::::::---->" + directory);
			System.out.println("directoory there????:::::----------->" + directory.exists());

			if (directory.exists()) {

				// File folder = new File(directory + "\\" + name[1]);
				File folder = new File(directory + "/" + name[1]);

				folder.mkdir();
			}

			attr.addFlashAttribute("Msg", "Sub-folder is successfully created");
		}

		modelandview.setViewName("redirect:/addNewFolder/child");
		return modelandview;

	}

	@PostMapping(path = "/removeChild", params = "action=delete")
	public ModelAndView removeChild(@Valid MstModule mstModule, Model model, BindingResult result, HttpSession session,
			RedirectAttributes attr) throws Exception {

		ModelAndView modelandview = new ModelAndView();
		String[] name = mstModule.getModuleName().split(",");

		System.out.println("Module name:---->" + name[1]);
		System.out.println("ModuleID name:---->" + name[0]);

		String childmoduleId = name[1];
		String parentmoduleId = name[0];

		String childmodules = moduleRepository.getAllchildModules(Integer.parseInt(childmoduleId));

		String[] str = childmodules.split(",");

		List<String> getModuleIds = new ArrayList<String>();

		for (String s : str) {
			if (s != null && !s.equalsIgnoreCase("null")) {
				getModuleIds.add(s);
			}
		}

		System.out.println("Before" + getModuleIds);

		Collections.reverse(getModuleIds);
		System.out.println("After" + getModuleIds);

		for (String getPathForFolder : getModuleIds) {

			String dirLoc = iDocumentDetailDAO.getFileLocation(Integer.parseInt(getPathForFolder));
			System.out.println("DirLoc-->" + dirLoc);

			File subdirectory = new File(CommonConstant.FILE_PATH_FOLDER + dirLoc);
			List<TxnDocumentDetail> files = documentdetailrepository.getFiles(Integer.parseInt(getPathForFolder));

			if (files.size() > 0) {
				for (TxnDocumentDetail filenames : files) {
					File filedirectory = new File(
							CommonConstant.FILE_PATH_FOLDER + dirLoc + "/" + filenames.getFileName());
					System.out.println("FilePath:::::----------------->" + filedirectory);
					System.out.println(filedirectory.exists());
					System.out.println(filedirectory.delete());
				}
			}
			subdirectory.delete();

			iuserdao.removeDependentFolderUseraccess(Integer.parseInt(getPathForFolder));
			iDocumentDetailDAO.removeDependentFiles(Integer.parseInt(getPathForFolder));

		}

		String[] str1 = childmodules.split(",");
		System.out.println("Length of strings:::::----->" + str1.length);
		int[] files = new int[childmodules.length()];
		for (int i = 0; i < str1.length; i++) {

			if (str1[i] != null && !str1[i].equalsIgnoreCase("null")) {
				files[i] = Integer.parseInt(str1[i]);
				imoduleDao.deleteSubFolders(files[i]);
			}
		}

		iuserdao.deleteuseraccess(Integer.parseInt(childmoduleId));

		File Parentdirectory = new File(
				CommonConstant.FILE_PATH_FOLDER + iDocumentDetailDAO.getFileLocation(Integer.parseInt(childmoduleId)));
		System.out.println(Parentdirectory.exists());
		System.out.println(Parentdirectory.delete());

		imoduleDao.deleteParentFolder(Integer.parseInt(childmoduleId));

		/*
		 * MstModule parentfoldername =
		 * imoduleDao.getParent(Integer.parseInt(parentmoduleId)); //File directory =
		 * new File(CommonConstant.FILE_PATH_FOLDER + "\\" +
		 * parentfoldername.getModuleName()); File directory = new
		 * File(CommonConstant.FILE_PATH_FOLDER + parentfoldername.getModuleName());
		 * 
		 * 
		 * MstModule childfoldername =
		 * imoduleDao.getChild(Integer.parseInt(childmoduleId)); //File subdirectory =
		 * new File(directory + "\\" + childfoldername.getModuleName()); File
		 * subdirectory = new File(directory + "/" +childfoldername.getModuleName());
		 * 
		 * System.out.println("File path of child::::=------>" + subdirectory);
		 * 
		 * List<TxnDocumentDetail> txnfiles1 =
		 * iDocumentDetailDAO.getFiles(Integer.parseInt(childmoduleId));
		 * 
		 * if (txnfiles1.size() > 0) {
		 * 
		 * for (TxnDocumentDetail filename : txnfiles1) {
		 * 
		 * //File filedirectory = new File(subdirectory + "\\" +
		 * filename.getFileName()); File filedirectory = new File(subdirectory +"/"+
		 * filename.getFileName());
		 * 
		 * 
		 * filedirectory.delete();
		 * 
		 * }
		 * 
		 * } iDocumentDetailDAO.deletetxnfiles(Integer.parseInt(childmoduleId));
		 * subdirectory.delete();
		 * imoduleDao.deleteChildFolder(Integer.parseInt(childmoduleId));
		 * iuserdao.deleteuseraccess(Integer.parseInt(childmoduleId));
		 */
		attr.addFlashAttribute("errorMsg", "Sub-folder is deleted..!!!");
		modelandview.setViewName("redirect:/addNewFolder/remove");
		return modelandview;

	}

	@PostMapping(path = "/removeParent", params = "action=delete")
	public ModelAndView removeParent(@Valid MstModule mstModule, Model model, BindingResult result, HttpSession session,
			RedirectAttributes attr) throws Exception {

		ModelAndView modelandview = new ModelAndView();

		String moduleId = mstModule.getModuleName();
		System.out.println("Folder::::---->" + moduleId);
		String moduleIds = moduleRepository.getAllchildModules(Integer.parseInt(moduleId));
		String[] str = moduleIds.split(",");

		List<String> getModuleIds = new ArrayList<String>();

		for (String s : str) {
			if (s != null && !s.equalsIgnoreCase("null")) {
				getModuleIds.add(s);
			}
		}
		System.out.println("Before" + getModuleIds);

		Collections.reverse(getModuleIds);
		System.out.println("After" + getModuleIds);

		for (String getPathForFolder : getModuleIds) {

			String dirLoc = iDocumentDetailDAO.getFileLocation(Integer.parseInt(getPathForFolder));

			System.out.println("DirLoc-->" + dirLoc);

			File subdirectory = new File(CommonConstant.FILE_PATH_FOLDER + dirLoc);
			List<TxnDocumentDetail> files = documentdetailrepository.getFiles(Integer.parseInt(getPathForFolder));

			if (files.size() > 0) {
				for (TxnDocumentDetail filenames : files) {
					File filedirectory = new File(
							CommonConstant.FILE_PATH_FOLDER + dirLoc + "/" + filenames.getFileName());
					System.out.println("FilePath:::::----------------->" + filedirectory);
					System.out.println(filedirectory.exists());
					System.out.println(filedirectory.delete());
				}
			}
			subdirectory.delete();

			iuserdao.removeDependentFolderUseraccess(Integer.parseInt(getPathForFolder));
			iDocumentDetailDAO.removeDependentFiles(Integer.parseInt(getPathForFolder));

		}

		String[] str1 = moduleIds.split(",");
		System.out.println("Length of strings:::::----->" + str1.length);
		int[] files = new int[moduleIds.length()];
		for (int i = 0; i < str1.length; i++) {

			if (str1[i] != null && !str1[i].equalsIgnoreCase("null")) {
				files[i] = Integer.parseInt(str1[i]);
				imoduleDao.deleteSubFolders(files[i]);
			}
		}

		iuserdao.deleteuseraccess(Integer.parseInt(moduleId));

		File Parentdirectory = new File(
				CommonConstant.FILE_PATH_FOLDER + iDocumentDetailDAO.getFileLocation(Integer.parseInt(moduleId)));
		System.out.println(Parentdirectory.exists());
		System.out.println(Parentdirectory.delete());

		imoduleDao.deleteParentFolder(Integer.parseInt(moduleId));

		/*
		 * if (mstmodule.size() > 0) { for (MstModule mst : mstmodule) {
		 * 
		 * System.out.println("modules:::::----->" + mst.getModuleId());
		 * moduleList.add(mst.getModuleId()); //File subdirectory = new File(directory +
		 * "\\" + mst.getModuleName()); File subdirectory = new File(directory +"/"+
		 * mst.getModuleName());
		 * 
		 * System.out.println("childpath::::::------->" + subdirectory);
		 * List<TxnDocumentDetail> files =
		 * documentdetailrepository.getFiles(mst.getModuleId());
		 * 
		 * if (files.size() > 0) {
		 * 
		 * for (TxnDocumentDetail filenames : files) {
		 * 
		 * filenames.getFileName();
		 * 
		 * //File filedirectory = new File(subdirectory + "\\" +
		 * filenames.getFileName()); File filedirectory = new File(subdirectory +"/"+
		 * filenames.getFileName());
		 * 
		 * filedirectory.delete();
		 * 
		 * } }
		 * 
		 * subdirectory.delete();
		 * 
		 * }
		 * 
		 * iuserdao.removeDependentFolderUseraccess(moduleList);
		 * iDocumentDetailDAO.removeDependentFiles(moduleList); }
		 */

		attr.addFlashAttribute("errorMsg", "Folder is deleted along withs its Sub-folders....");

		modelandview.setViewName("redirect:/addNewFolder/removeparent");
		return modelandview;

	}

	@RequestMapping(path = "/createfolder", method = RequestMethod.POST)
	public @ResponseBody void createFolder(@ModelAttribute TxnFolderDetail folderDetail, HttpSession session,
			Model model) throws Exception {

		MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
		String message = null;
		if (mstUser != null) {
			MstModule module = new MstModule();
			if (folderDetail.getRootName() != null) {
				module.setParentId("#");
				module.setModuleName(folderDetail.getRootName());
				module.setCreatedBy(mstUser.getCreatedBy());
				List<ResponseVO> responseVOs = iDocumentDetailDAO.createDirectory(module);
				if (responseVOs.size() > 0) {
					for (ResponseVO responseVO : responseVOs) {
						if (responseVO.getSuccessMessageVO() != null) {
							message = responseVO.getSuccessMessageVO().getSuccessMessage();
						} else {
							message = responseVO.getErrorMessageVO().getErrorMessage();
						}
					}
				}
			}
		}
		model.addAttribute("message", message);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/getFolderDetails/{moduleId}", method = RequestMethod.GET)
	public ModelAndView getFolderDetails(@PathVariable Integer moduleId, HttpSession session,
			@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page)
			throws Exception {

		// Evaluate page size. If requested parameter is null, return initial
		// page size
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		// Evaluate page. If requested parameter is null or less than 0 (to
		// prevent exception), return initial size. Otherwise, return value of
		// param. decreased by 1.
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		ModelAndView modelAndView = new ModelAndView();
		List<MstUserAccess> modules = new ArrayList<MstUserAccess>();
		Map<Integer, String> fileLoc = new HashMap<Integer, String>();

		session.setAttribute(CommonConstant.MODULE_ID, moduleId);
		String parentId = moduleRepository.findbyParent(moduleId).getParentId();

		session.setAttribute(CommonConstant.PARENT_FOLDER_ID, parentId);

		/*
		 * Prepare File Location path Using Module-Id
		 */
		String dirLoc = iDocumentDetailDAO.getFileLocation(moduleId);
		fileLoc.put(moduleId, CommonConstant.FILE_PATH + dirLoc);
		session.setAttribute(CommonConstant.FULL_FILE_LOC_PATH, fileLoc);
		boolean check = false;
		String fileRec = null;
		modelAndView.addObject("filerec", fileRec);
		/*
		 * Check Child attribute available or not. whether child folder available then
		 * get record from session otherwise get file details record from database.
		 */
		MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
		List<MstUserAccess> mstUserAccessList = (List<MstUserAccess>) session.getAttribute(CommonConstant.CHILD_LIST);
		if (mstUserAccessList != null && mstUserAccessList.size() > 0) {
			for (MstUserAccess mstAccessData : mstUserAccessList) {
				if (mstAccessData.getMstModule().getParentId().equals(String.valueOf(moduleId))) {
					modules.add(mstAccessData);
					check = true;
				}
				if (check) {
					fileRec = "disabled";
					modelAndView.addObject("filerec", fileRec);
					modelAndView.addObject("childFolList", modules);
					String childRec = "enabled";
					modelAndView.addObject("childrec", childRec);
				}
			}
		}

		if (!check && mstUser.getUserId() > 0) {
			//System.out.println("User Id -->" + mstUser.getUserId());
			//System.out.println("moduleId -->" + moduleId);

			Page<TxnDocumentDetail> documentDetails;

			/* Start : Below code tempary commented by Harshil on 16-05-2019 */
			if (mstUser.getRole().getRoleId() == 3) {
				//log.info("Here in 3");
				documentDetails = iFolderDAO.getFolderDetailsForReader(moduleId,
						new PageRequest(evalPage, evalPageSize));
			} else {
				//documentDetails = iFolderDAO.getFolderDetails(mstUser.getUserId(), moduleId, new PageRequest(evalPage, evalPageSize));
			//	log.info("Here in 2");
				documentDetails = iFolderDAO.getFolderDetails(moduleId, new PageRequest(evalPage, evalPageSize));
			}
			/* End : Below code tempary commented by Harshil on 16-05-2019 */

			/*
			 * Page<TxnDocumentDetail> documentDetails =
			 * iFolderDAO.getFolderDetails(mstUser.getUserId(), moduleId, new
			 * PageRequest(evalPage, evalPageSize));
			 */

			fileRec = "enabled";
			modelAndView.addObject("filerec", fileRec);
			String childRec = "disabled";
			modelAndView.addObject("childrec", fileRec);

			if (documentDetails.getTotalElements() > 0) {

				modelAndView.addObject("navigation", "enabled");
				modelAndView.addObject("fileDetailList", documentDetails);
				log.info("Folder Details::"+documentDetails);
				session.setAttribute(CommonConstant.FILE_DETAILS_LIST, documentDetails);

				PagerModel pager = new PagerModel(documentDetails.getTotalPages(), documentDetails.getNumber(),
						BUTTONS_TO_SHOW);
				
				System.out.println("Page numbersss:::"+documentDetails.getTotalPages()+"Numberss"+documentDetails.getNumber());
				// evaluate page size
				modelAndView.addObject("selectedPageSize", evalPageSize);
				// add page sizes
				modelAndView.addObject("pageSizes", PAGE_SIZES);
				// add pager
				modelAndView.addObject("pager", pager);
			} else {
				modelAndView.addObject("navigation", "disable");
			}

		}
		/* call to identify is download allowed or not */
		Boolean flag = documentdetailrepository.downloadAllowed(moduleId, mstUser.getUserId());
		modelAndView.addObject("flag", flag);
		session.setAttribute("flag", flag);
		
		Boolean viewflag = documentdetailrepository.viewAllowed(moduleId, mstUser.getUserId());
		modelAndView.addObject("viewflag", viewflag);
		session.setAttribute("viewflag", viewflag);

		modelAndView.addObject("location", dirLoc);
		modelAndView.addObject("pageName", "open_folder_name");
		modelAndView.addObject("moduleId", moduleId);
		modelAndView.setViewName("open_folder_name");
		return modelAndView;
	}

	/* start Bindhu Added */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getoldFiles/{moduleid}", method = RequestMethod.GET)
	public ModelAndView getoldFiles(@PathVariable int moduleid,HttpSession session, RedirectAttributes attr,
			@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page)
			throws Exception {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		ModelAndView model = new ModelAndView();
		Page<TxnDocumentDetail> oldfiles;
		// List<TxnDocumentDetail> oldfiles = new ArrayList<TxnDocumentDetail>();

		MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
		String username = mstUser.getUserName();
		

		//oldfiles = folderRepository.getPreviousFiles(username, moduleid, new PageRequest(evalPage, evalPageSize));
		oldfiles = folderRepository.getPreviousFiles(moduleid, new PageRequest(evalPage, evalPageSize));
		

		if (oldfiles.getTotalElements() > 0) {

			model.addObject("navigation", "enabled");
			model.addObject("oldFiles", oldfiles);
			session.setAttribute(CommonConstant.FILE_DETAILS_LIST, oldfiles);

			PagerModel pager = new PagerModel(oldfiles.getTotalPages(), oldfiles.getNumber(), BUTTONS_TO_SHOW);

			// evaluate page size
			model.addObject("selectedPageSize", evalPageSize);
			model.addObject("pageName", "previousFiles");
			// add page sizes
			model.addObject("pageSizes", PAGE_SIZES);
			// add pager
			model.addObject("pager", pager);
		} else {
			model.addObject("navigation", "disable");

		}
		model.setViewName("previous_files");

		return model;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getinactiveFiles/{moduleid}", method = RequestMethod.GET)
	public ModelAndView getinactiveFiles(@PathVariable int moduleid,HttpSession session, RedirectAttributes attr,
			@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page)
			throws Exception {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
		ModelAndView model = new ModelAndView();
		Page<TxnDocumentDetail> inactivefiles;

		MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
		String username = mstUser.getUserName();

		inactivefiles = folderRepository.getInactiveFiles(username,moduleid, new PageRequest(evalPage, evalPageSize));

		if (inactivefiles.getTotalElements() > 0) {

			model.addObject("navigation", "enabled");
			model.addObject("InactiveFiles", inactivefiles);
			session.setAttribute(CommonConstant.FILE_DETAILS_LIST, inactivefiles);

			PagerModel pager = new PagerModel(inactivefiles.getTotalPages(), inactivefiles.getNumber(),
					BUTTONS_TO_SHOW);

			// evaluate page size
			model.addObject("selectedPageSize", evalPageSize);
			model.addObject("pageName", "inactiveFiles");
			// add page sizes
			model.addObject("pageSizes", PAGE_SIZES);
			// add pager
			model.addObject("pager", pager);
			System.out.println("Endpage" + pager.getEndPage());
			System.out.println("Start page" + pager.getStartPage());
		} else {
			model.addObject("navigation", "disable");

		}
		model.setViewName("inactive_files");
		attr.addFlashAttribute("message", "Documents are inactivated");
		return model;

	}

	/* End Bindhu Added */

	@RequestMapping(path = "/uploadDocument/{value}", method = RequestMethod.GET)
	public ModelAndView uploadDocument(HttpSession httpSession,@PathVariable("value") boolean value) throws Exception {

		System.out.println("value-->" +value);
		ModelAndView modelAndView = new ModelAndView();

		List<MstHeader> headers = iFolderDAO.getHeaders();
		if (headers.size() > 0) {
			modelAndView.addObject("headers", headers);
		}

		TxnDocumentDetail documentDetail = new TxnDocumentDetail();
		Integer moduleId = (Integer) httpSession.getAttribute(CommonConstant.MODULE_ID);
		if (moduleId != null) {
			documentDetail.setModuleId(moduleId);
		}
		modelAndView.addObject("documentDetails", documentDetail);
		if(value){
			modelAndView.addObject("successMessage", "Data Uploaded Successfully");
		}
		modelAndView.setViewName("uploadform");
		return modelAndView;
	}

	@RequestMapping(path = "/viewArea/{docDetailId}/{headerId}", method = RequestMethod.GET)
	public ModelAndView viewArea(HttpSession httpSession,@PathVariable String docDetailId,@PathVariable String headerId) throws Exception {

		
		
		ModelAndView modelAndView = new ModelAndView();
		
		List<MstHeader> headers = iFolderDAO.getHeaders();
		
		TxnDocumentDetail editHeaderDetail = iFolderDAO.getHeaderDetailForEdit(docDetailId, headerId);
		
		if (headers.size() > 0) {
			modelAndView.addObject("headers", headers);
		}

		
		httpSession.setAttribute("docDetailId", docDetailId);
		modelAndView.addObject("documentDetails", editHeaderDetail);
		
		modelAndView.setViewName("uploadform");
		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@PostMapping(path = "/saveDocumentDetail")
	public ModelAndView saveDocumentDetails(TxnDocumentDetail documentDetail,
			@RequestParam("browsename") MultipartFile[] multipartFile, HttpSession session) throws Exception {
	
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("Doc Detail Id For Edit " + documentDetail.getDocDetailId());

		for (int i = 0; i < multipartFile.length; i++) {

			InputStream inputStream = null;
			OutputStream outputStream = null;
			String actualFileName = multipartFile[i].getOriginalFilename();
					
			actualFileName = actualFileName.substring(actualFileName.lastIndexOf("\\") + 1);

			String removeExtFileName = null;
			String finalFile = null;
			String fileLocation = null;
			Integer moduleId = 0;

			HashMap<Integer, String> locationDir = (HashMap<Integer, String>) session
					.getAttribute(CommonConstant.FULL_FILE_LOC_PATH);
			Set<Entry<Integer, String>> entries = locationDir.entrySet();
			for (Entry<Integer, String> entry : entries) {

				moduleId = entry.getKey();
				fileLocation = entry.getValue();
			}

			MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
			if (mstUser.getUserId() > 0 && moduleId != null) {
				Map<Integer, String> mapDetails = iDocumentDetailDAO.searchUserAccessId(mstUser.getUserId(), moduleId);
				Set<Entry<Integer, String>> set = mapDetails.entrySet();
				for (Entry<Integer, String> entry : set) {
					documentDetail.setUserAccessId(entry.getKey());
					documentDetail.setCreatedBy(entry.getValue());

				}
			}
			
			String[] fileName = actualFileName.split("\\.(?=[^\\.]+$)");
			String withoutExt = fileName[0];
			String ext = fileName[1];

			Integer totalCount = checkVersionFile(fileLocation, withoutExt, mstUser.getUserId());
			File newFile = null;

			if (totalCount > 0) {
				removeExtFileName = actualFileName.substring(0, actualFileName.lastIndexOf("."));
				finalFile = removeExtFileName + "_" + (totalCount) + "." + ext;
				newFile = new File(fileLocation + "/" + finalFile);
			} else {
				finalFile = actualFileName;
				newFile = new File(fileLocation + "/" + finalFile);
			}

			try {
				inputStream = multipartFile[i].getInputStream();

				File parent = newFile.getParentFile();

				if (!parent.exists()) {
					parent.mkdirs();
					if (!newFile.isFile()) {
						newFile.createNewFile();
					}
				} else {
					if (!newFile.isFile()) {
						newFile.createNewFile();
					}
				}

				outputStream = new FileOutputStream(newFile);
				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					outputStream.close();
					inputStream.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
	//		System.out.println("HeaderId --> " + documentDetail.getMstHeader().getHeaderId());
			documentDetail.setStatus(documentDetail.getStatus() != null ? documentDetail.getStatus() : "active");
			documentDetail.setDocPath(fileLocation);
			documentDetail.setFileName(finalFile);
			documentDetail.setModuleId(moduleId);
			documentDetail.getMstHeader().setHeaderId(documentDetail.getMstHeader().getHeaderId());
			Page<TxnDocumentDetail> documentDetails = iDocumentDetailDAO.saveDocumentDetails(documentDetail);

			/*
			 * To store the details in table login_history----Start--- Added by
			 * Bindhu(26-06-2019)
			 */

			
			String docpath = fileLocation.substring(fileLocation.lastIndexOf("/Files/") + 7);
			LoginHistory loginvalues = loginhistoryRepository.getDocumentDetails(mstUser.getUserId());
			
			String action = loginvalues.getAction();
			if (action == null) {
				System.out.println("---------------UPDATE---------------------");
				loginhistoryRepository.saveLoginDetails(docpath, finalFile, loginvalues.getLoginDate(),loginvalues.getLoginTime(), CommonConstant.FILE_STATUS_UPLOAD);
			} else {
				System.out.println("---------------INSERT---------------------");
				LoginHistory login = new LoginHistory();
				login.setAction("Uploaded");
				login.setDocPath(docpath);
				login.setFileName(finalFile);
				login.setLoginDate(loginvalues.getLoginDate());
				login.setLoginTime(loginvalues.getLoginTime());
				login.setUserId(mstUser);
				loginhistoryRepository.save(login);
			}

			/* --End-- */
			String fileRec = "enabled";
			//modelAndView.addObject("filerec", fileRec);
			if (documentDetails.getTotalElements() > 0) {
				PagerModel pager = new PagerModel(documentDetails.getTotalPages(), documentDetails.getNumber(),BUTTONS_TO_SHOW);
				List<String> catList = new ArrayList<String>();
				List<String> subCatList = new ArrayList<String>();
				
				
					/*modelAndView.addObject("selectedPageSize", 4);
					modelAndView.addObject("pageSizes", PAGE_SIZES);
					modelAndView.addObject("pager", pager);
					modelAndView.addObject("fileDetailList", documentDetails);
					modelAndView.addObject("catList", catList);
					modelAndView.addObject("subCatList", subCatList);
					modelAndView.addObject("navigation", "enabled");*/
				
				/* Start : Change By Harshil on 06-04-2019 */
				System.out.println("Redirecting.....");
				//modelAndView.setViewName("redirect:/getFolderDetails/" + moduleId);
				/* End : Change By Harshil on 06-04-2019 */
				  
				/* Start : Below redirection change By Harshil For Redirect on same page after file uploaded as New Changes. */
				modelAndView.setViewName("redirect:/uploadDocument/" + true);
				/* End : Below redirection change By Harshil For Redirect on same page after file uploaded as New Changes. */  
			} else {
				modelAndView.addObject("navigation", "disbled");
			}

		}

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchFolder/{search}", method = RequestMethod.GET)
	public String searchFolder(@PathVariable String search, HttpSession session, Model model,
			@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page)
			throws Exception {
		boolean flag = false;
		List<MstModule> parentList = new ArrayList<MstModule>();
		List<MstModule> mstModules = (ArrayList<MstModule>) session.getAttribute(CommonConstant.PARENT_LIST);
		if (search != null && search.equalsIgnoreCase("ALL")) {
			parentList = mstModules;
		} else {
			if (mstModules != null) {
				flag = true;
				for (MstModule mstModule : mstModules) {
					if (mstModule.getModuleName().toLowerCase().contains(search.toLowerCase())) {
						parentList.add(mstModule);
					}
				}
			}

		}
		model.addAttribute("parentList", parentList);
		model.addAttribute("folderDetail", new TxnFolderDetail());
		model.addAttribute("flag", flag);
		return "folder_page";

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchFile/{search}", method = RequestMethod.GET)
	public ModelAndView searchFile(@PathVariable String search, HttpSession session,
			@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page)
			throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		Page<TxnDocumentDetail> files = null;
		List<TxnDocumentDetail> pages = new ArrayList<TxnDocumentDetail>();
		Page<TxnDocumentDetail> storeFiles = (Page<TxnDocumentDetail>) session
				.getAttribute(CommonConstant.FILE_DETAILS_LIST);
		if (search != null && search.equalsIgnoreCase("ALL")) {
			files = storeFiles;
		} else {
			if (storeFiles != null) {
				for (TxnDocumentDetail detail : storeFiles) {
					if (detail.getFileName().toLowerCase().contains(search.toLowerCase())) {
						pages.add(detail);
					}
				}
			}
			files = new PageImpl<TxnDocumentDetail>(pages);
		}
		modelAndView.addObject("fileDetailList", files);
		// String fileRec = null;
		MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
		if (mstUser != null) {
			if (mstUser.getRole().getRoleId() == 3) {
				modelAndView.addObject("filerec", "disabled");
				// modelAndView.addObject("docreader", "enabled");
			} else {
				modelAndView.addObject("filerec", "enabled");
				// modelAndView.addObject("docreader", "disabled");
			}
		}
		PagerModel pager;

		if (files != null) {

			pager = new PagerModel(files.getTotalPages(), files.getNumber(), BUTTONS_TO_SHOW);
		} else {
			pager = new PagerModel(1, 1, 3);
		}
		// PagerModel pager = new PagerModel(files.getTotalPages(), files.getNumber(),
		// BUTTONS_TO_SHOW);
		Map<Integer, String> fileLoc = (HashMap<Integer, String>) session
				.getAttribute(CommonConstant.FULL_FILE_LOC_PATH);
		String location = null;
		if (fileLoc != null) {
			Set<Entry<Integer, String>> entries = fileLoc.entrySet();
			String fileLocPath = null;
			for (Entry<Integer, String> entry : entries) {
				fileLocPath = entry.getValue();
			}
			location = fileLocPath.replaceAll(CommonConstant.FILE_PATH, "");
		}

		//
		// Evaluate page size. If requested parameter is null, return initial
		// page size
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		// evaluate page size
		modelAndView.addObject("selectedPageSize", evalPageSize);
		modelAndView.addObject("location", location);
		// add page sizes
		modelAndView.addObject("pageSizes", PAGE_SIZES);
		// add pager
		modelAndView.addObject("pager", pager);
		modelAndView.setViewName("open_folder_name");
		return modelAndView;
	}

	@GetMapping("/pagination")
	public ModelAndView pagination(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page, @RequestParam("pageName") String pageName,
			@RequestParam("moduleID") int moduleId, HttpSession session) throws Exception {

		ModelAndView modelAndView = new ModelAndView();

		System.out.println("pageName-->" + pageName);

		if (pageName.equals("inactiveFiles")) {

			modelAndView.setViewName("inactive_files");

			int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
			int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

			MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
			String username = mstUser.getUserName();
			System.out.println("username:---->" + username);

			Page<TxnDocumentDetail> inactivefiles = folderRepository.getInactiveFiles(username,moduleId,
					new PageRequest(evalPage, evalPageSize));

		//	System.out.println("Content:::---------" + inactivefiles.getContent());

			modelAndView.addObject("navigation", "enabled");
			modelAndView.addObject("InactiveFiles", inactivefiles);
			PagerModel pager = new PagerModel(inactivefiles.getTotalPages(), inactivefiles.getNumber(),
					BUTTONS_TO_SHOW);
			// evaluate page size
			modelAndView.addObject("selectedPageSize", evalPageSize);
			// add page sizes
			modelAndView.addObject("pageSizes", PAGE_SIZES);
			// add pager
			modelAndView.addObject("pager", pager);
			modelAndView.addObject("pageName", "inactiveFiles");

			//System.out.println("Page values:------>" + pager.getStartPage() + pager.getEndPage());

		} else if (pageName.equals("open_folder_name")) {

			modelAndView.setViewName("open_folder_name");
			List<Integer> moduleList = new ArrayList<Integer>();
			//
			// Evaluate page size. If requested parameter is null, return initial
			// page size
			int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
			// Evaluate page. If requested parameter is null or less than 0 (to
			// prevent exception), return initial size. Otherwise, return value of
			// param. decreased by 1.
			int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
			
			System.out.println("evalPageSize--->" + evalPageSize);
			System.out.println("evalPage--->" + evalPage);

			MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);

			/*
			 * Prepare Code for get that folder list which have allow access on same user
			 */
			List<MstUserAccess> modules = iDocumentDetailDAO.searchHierarchy(mstUser.getUserId());
			if (modules.size() > 0) {
				for (MstUserAccess mstUserAccess : modules) {
					moduleList.add(mstUserAccess.getMstModule().getModuleId());
				}
			}

			Page<TxnDocumentDetail> clientlist = null;
			try {
				if (mstUser.getRole().getRoleId() == 3) {
					clientlist = iFolderDAO.getFolderDetailsForReader(moduleId,
							new PageRequest(evalPage, evalPageSize));
				} else {
					clientlist = iFolderDAO.getFolderDetails(moduleId, new PageRequest(evalPage, evalPageSize));
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (mstUser.getUserId() == 3) {
				String fileRec = "disabled";
				modelAndView.addObject("location", "SHOW Details");
				modelAndView.addObject("filerec", fileRec);
			} else {
				Map<Integer, String> fileLoc = (HashMap<Integer, String>) session
						.getAttribute(CommonConstant.FULL_FILE_LOC_PATH);
				String location = null;
				if (fileLoc != null) {
					Set<Entry<Integer, String>> entries = fileLoc.entrySet();
					String fileLocPath = null;
					for (Entry<Integer, String> entry : entries) {
						fileLocPath = entry.getValue();
					}
					location = fileLocPath.replaceAll(CommonConstant.FILE_PATH, "");
				}
				
				
				
				modelAndView.addObject("location", location);
				modelAndView.addObject("navigation", "enabled");
				String fileRec = "enabled";
				modelAndView.addObject("filerec", fileRec);
			}
			modelAndView.addObject("fileDetailList", clientlist);
			


			/* call to identify is download allowed or not */
			Boolean flag = documentdetailrepository.downloadAllowed(moduleId, mstUser.getUserId());
			modelAndView.addObject("flag", flag);
			session.setAttribute("flag", flag);
			
			Boolean viewflag = documentdetailrepository.viewAllowed(moduleId, mstUser.getUserId());
			modelAndView.addObject("viewflag", viewflag);
			session.setAttribute("viewflag", viewflag);


			PagerModel pager = new PagerModel(clientlist.getTotalPages(), clientlist.getNumber(), BUTTONS_TO_SHOW);
			
			System.out.println("GetNumber:----->" + clientlist.getNumber());
			// evaluate page size
			modelAndView.addObject("selectedPageSize", evalPageSize);
			// add page sizes
			modelAndView.addObject("pageSizes", PAGE_SIZES);
			// add pager
			modelAndView.addObject("pager", pager);

			modelAndView.addObject("pageName", "open_folder_name");

		} else {

			modelAndView.setViewName("previous_files");

			int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
			int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

			MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
			String username = mstUser.getUserName();

			Page<TxnDocumentDetail> oldfiles = folderRepository.getPreviousFiles(moduleId, new PageRequest(evalPage, evalPageSize));

			modelAndView.addObject("navigation", "enabled");
			modelAndView.addObject("oldFiles", oldfiles);
			PagerModel pager = new PagerModel(oldfiles.getTotalPages(), oldfiles.getNumber(), BUTTONS_TO_SHOW);
			// evaluate page size
			modelAndView.addObject("selectedPageSize", evalPageSize);
			// add page sizes
			modelAndView.addObject("pageSizes", PAGE_SIZES);
			// add pager
			modelAndView.addObject("pager", pager);
			modelAndView.addObject("pageName", "previousFiles");

		}

		return modelAndView;

	}

	@SuppressWarnings("static-access")
	@RequestMapping(value = "viewDocument/{moduleid}/{fileName}", method = RequestMethod.GET)
	public void viewDocument(@PathVariable String fileName, @PathVariable Integer moduleid, HttpSession session,
			HttpServletResponse response, @RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page) throws Exception {

		ModelAndView model = new ModelAndView();

		// Evaluate page size. If requested parameter is null, return initial
		// page size @PathVariable Integer moduleid,
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		// Evaluate page. If requested parameter is null or less than 0 (to
		// prevent exception), return initial size. Otherwise, return value of
		// param. decreased by 1.
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Integer moduleId = 0;
		String fileLocPath = null;
		MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
		Map<Integer, String> fileLoc = new HashMap<Integer, String>();
		String docPath = new String();
		Integer module = 0;
		if (mstUser.getUserId() > 0) {
			HashMap<Integer, String> fullFileLocSession = (HashMap<Integer, String>) session
					.getAttribute(CommonConstant.FULL_FILE_LOC_PATH);
			Set<Entry<Integer, String>> entries = null;
			if (fullFileLocSession != null) {
				entries = fullFileLocSession.entrySet();
			} else {
				List<MstUserAccess> mstUserAccesses = iDocumentDetailDAO.searchHierarchy(mstUser.getUserId());
				if (mstUserAccesses.size() > 0) {
					List<Integer> moduleList = new ArrayList<Integer>();
					for (MstUserAccess mstUserAccess : mstUserAccesses) {
						moduleList.add(mstUserAccess.getMstModule().getModuleId());
					}
					Page<TxnDocumentDetail> documentDetails = iFolderDAO.getDetailsForReader(moduleList,
							new PageRequest(evalPage, evalPageSize));
					if (documentDetails.getSize() > 0) {
						for (TxnDocumentDetail txnDocumentDetail : documentDetails) {
							docPath = txnDocumentDetail.getDocPath();
							module = txnDocumentDetail.getModuleId();
						}
					}
				}
				fileLoc.put(module, docPath);
				entries = fileLoc.entrySet();
			}
			for (Entry<Integer, String> entry : entries) {
				moduleId = entry.getKey();
				fileLocPath = entry.getValue();
			}
			List<TxnDocumentDetail> documentDetails = iDocumentDetailDAO.downloadFileDocforReader(fileName, moduleId,
					fileLocPath);
			if (documentDetails.size() > 0) {
				for (TxnDocumentDetail txnDocumentDetail : documentDetails) {

					String[] fileNameDet = txnDocumentDetail.getFileName().split("\\.(?=[^\\.]+$)");

					String withoutExt = fileNameDet[0];

					if (fileName != null) {
						File newFile = new File(txnDocumentDetail.getDocPath() + "/" + txnDocumentDetail.getFileName());

						System.out.println("view File Name ::: " + newFile);
						/*Start xls*/
						
						
						
						
						/*End xls*/
						
						
					/* Start :Added By Harshil For viewing files*/
						response.setContentType( "application/pdf" );
						response.setHeader("Content-disposition","inline; filename=" + txnDocumentDetail.getFileName());
						BufferedInputStream  bis = null; 
						BufferedOutputStream bos = null;
						try {
							ServletOutputStream  outs =  response.getOutputStream ();
						    InputStream isr=new FileInputStream(newFile);
						    bis = new BufferedInputStream(isr);
						    bos = new BufferedOutputStream(outs);
						    byte[] buff = new byte[2048];
						    int bytesRead;
						    while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
						        bos.write(buff, 0, bytesRead);
						    }
						    
						} 
						catch(Exception e)
						{
						    System.out.println("Exception ----- Message ---"+e);
						} finally {
						    if (bis != null)
						        bis.close();
						    if (bos != null)
						        bos.close();
						}

						LoginHistory loginvalues = loginhistoryRepository.getDocumentDetails(mstUser.getUserId());
						String docpath = fileLocPath.substring(fileLocPath.lastIndexOf("/Files/") + 7);
						String action = loginvalues.getAction();
						if (action == null) {
							System.out.println("---------------UPDATE---------------------");
							loginhistoryRepository.saveLoginDetails(docpath, documentDetails.get(0).getFileName(), loginvalues.getLoginDate(),
									loginvalues.getLoginTime(), CommonConstant.FILE_STATUS_VIEW);

						} else {
							System.out.println("---------------INSERT---------------------");
							LoginHistory login = new LoginHistory();
							login.setAction(CommonConstant.FILE_STATUS_VIEW);
							login.setDocPath(docpath);
							login.setFileName(documentDetails.get(0).getFileName());
							login.setLoginDate(loginvalues.getLoginDate());
							login.setLoginTime(loginvalues.getLoginTime());
							login.setUserId(mstUser);
							loginhistoryRepository.save(login);

						}
						/* ENs :Added By Harshil For viewing files*/						
						
						/*Desktop desktop = Desktop.getDesktop();

						if (!desktop.isDesktopSupported()) {
							System.out.println("Desktop is not supported");
							// return;
						}

						if (newFile.exists()) {
							desktop.open(newFile);

						}*/
					}
				}
			}
		}

//		model.setViewName("redirect:/getFolderDetails/" + moduleid);
//		return "redirect:/getFolderDetails/" + moduleid;

	}

	/**
	 * @param fileName
	 * @param session
	 * @param response
	 * @param pageSize
	 * @param page
	 * @throws Exception
	 */
	/**
	 * @param fileName
	 * @param session
	 * @param response
	 * @param pageSize
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "downloadDocument/{fileName}", method = RequestMethod.GET)
	public void downloadDocument(@PathVariable String fileName, HttpSession session, HttpServletResponse response,
			@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page)
			throws Exception {

		System.out.println("Filename after downloading::::---->"+fileName);
		
		
		// Evaluate page size. If requested parameter is null, return initial
		// page size
		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		// Evaluate page. If requested parameter is null or less than 0 (to
		// prevent exception), return initial size. Otherwise, return value of
		// param. decreased by 1.
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		FileInputStream fileIn = null;
		OutputStream outStream = null;
		Integer moduleId = 0;
		String fileLocPath = null;
		MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
		Map<Integer, String> fileLoc = new HashMap<Integer, String>();
		String docPath = new String();
		Integer module = 0;
		if (mstUser.getUserId() > 0) {
			HashMap<Integer, String> fullFileLocSession = (HashMap<Integer, String>) session
					.getAttribute(CommonConstant.FULL_FILE_LOC_PATH);
			Set<Entry<Integer, String>> entries = null;
			if (fullFileLocSession != null) {
				entries = fullFileLocSession.entrySet();
			} else {
				List<MstUserAccess> mstUserAccesses = iDocumentDetailDAO.searchHierarchy(mstUser.getUserId());
				if (mstUserAccesses.size() > 0) {
					List<Integer> moduleList = new ArrayList<Integer>();
					for (MstUserAccess mstUserAccess : mstUserAccesses) {
						moduleList.add(mstUserAccess.getMstModule().getModuleId());
					}
					Page<TxnDocumentDetail> documentDetails = iFolderDAO.getDetailsForReader(moduleList,
							new PageRequest(evalPage, evalPageSize));
					if (documentDetails.getSize() > 0) {
						for (TxnDocumentDetail txnDocumentDetail : documentDetails) {
							docPath = txnDocumentDetail.getDocPath();
							module = txnDocumentDetail.getModuleId();
						}
					}
				}
				fileLoc.put(module, docPath);
				entries = fileLoc.entrySet();
			}
			for (Entry<Integer, String> entry : entries) {
				moduleId = entry.getKey();
				fileLocPath = entry.getValue();
			}
			List<TxnDocumentDetail> documentDetails = iDocumentDetailDAO.downloadFileDocforReader(fileName, moduleId,
					fileLocPath);
			//System.out.println("TXNDB 123-->" + documentDetails.get(0).getFileName());
			
			if (documentDetails.size() > 0) {
				for (TxnDocumentDetail txnDocumentDetail : documentDetails) {
					System.out.println("TXNDB-->" + txnDocumentDetail.getFileName());
					String[] fileNameDet = txnDocumentDetail.getFileName().split("\\.(?=[^\\.]+$)");
					String withoutExt = fileNameDet[0];

					if (fileName.equalsIgnoreCase(withoutExt)) {
						File newFile = new File(txnDocumentDetail.getDocPath() + "/" + txnDocumentDetail.getFileName());
						try {
							fileIn = new FileInputStream(newFile);
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
				}
			
			/* Added By Bindhu For saving downloaded details into mst_login_history(27-06-2019) */
			LoginHistory loginvalues = loginhistoryRepository.getDocumentDetails(mstUser.getUserId());

			String docpath = fileLocPath.substring(fileLocPath.lastIndexOf("/Files/") + 7);
				
				String action = loginvalues.getAction();
				if (action == null) {

					System.out.println("---------------UPDATE---------------------");
					loginhistoryRepository.saveLoginDetails(docpath, documentDetails.get(0).getFileName(), loginvalues.getLoginDate(),
							loginvalues.getLoginTime(), CommonConstant.FILE_STATUS_DOWNLOAD);

				} else {

					System.out.println("---------------INSERT---------------------");
					LoginHistory login = new LoginHistory();
					login.setAction(CommonConstant.FILE_STATUS_DOWNLOAD);
					login.setDocPath(docpath);
					login.setFileName(documentDetails.get(0).getFileName());
					login.setLoginDate(loginvalues.getLoginDate());
					login.setLoginTime(loginvalues.getLoginTime());
					login.setUserId(mstUser);
					loginhistoryRepository.save(login);

				}
		/* -----End by Bindhu------ */

			
			}
		}

	}

	public Integer checkVersionFile(String fileLocation, String fileName, Integer userId) throws Exception {

		Integer available = iDocumentDetailDAO.searchFileDetail(fileLocation, fileName, userId);
		return available;
	}

	
	@RequestMapping(path = "/redirecthome", method = RequestMethod.GET)
	public ModelAndView redirectHome(HttpSession session, RedirectAttributes redirectAttrs) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
		session.setAttribute("hiddenFlag", "false");
		if (mstUser != null) {
			redirectAttrs.addFlashAttribute("user", mstUser);
			modelAndView.setViewName("redirect:login");
		}
		return modelAndView;
	}

	@RequestMapping(path = "/backscreen", method = RequestMethod.GET)
	public ModelAndView loadBackPage(HttpSession session, RedirectAttributes redirectAttrs) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/searchFile/" + "ALL");
		return modelAndView;
	}

	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpSession session) throws Exception {
		model.addAttribute("user", new MstUser());
		session.removeAttribute(CommonConstant.SESSION_USER_VO);
		session.removeAttribute(CommonConstant.PARENT_LIST);
		session.removeAttribute(CommonConstant.CHILD_LIST);
		session.removeAttribute(CommonConstant.FULL_FILE_LOC_PATH);
		session.removeAttribute(CommonConstant.FILE_DETAILS_LIST);
		return "redirect:QMS";
	}

	/* Start Harshil Added */
	@RequestMapping(path = "/makeDocumentsinactive/{inactiveValues}", method = RequestMethod.GET)
	public ModelAndView makeDocumentsinactive(@PathVariable String inactiveValues, HttpSession session) {
		

		ModelAndView model = new ModelAndView();
		
		String[] str = inactiveValues.split(",");
		int[] files = new int[inactiveValues.length()];
		for (int i = 0; i < str.length; i++) {

			files[i] = Integer.parseInt(str[i]);
			documentdetailrepository.documentsInactive(files[i]);

		}
		Integer moduleId = (Integer) session.getAttribute(CommonConstant.MODULE_ID);
		model.setViewName("redirect:/getFolderDetails/" + moduleId);
		return model;
	}

	/* End Harshil Added */
	/* Added by vrinda start */
	@RequestMapping(path = "/updateFolderDetails/{flag}/{userId}/{glValModuleId}/{viewFlag}", method = RequestMethod.GET)
	public ModelAndView updateDetails(@PathVariable Boolean flag, @PathVariable int userId,
			@PathVariable String glValModuleId, @PathVariable Boolean viewFlag, HttpSession session, RedirectAttributes redirAttrs) {

		ModelAndView model = new ModelAndView();
		documentdetailrepository.updateFolderDetails(Integer.valueOf(userId), Integer.valueOf(glValModuleId), flag, viewFlag);
		model.setViewName("redirect:/searchuser");
		return model;
	}

	@RequestMapping(value = "/getDetailsforModule", method = RequestMethod.POST)
	@ResponseBody
	public List<MstUserAccessVO> getRegions(@RequestParam int userId, @RequestParam int glValModuleId) {

		List<MstUserAccessVO> submodules = new ArrayList<MstUserAccessVO>();
		MstUserAccessVO submodule;

		try {
			for (MstUserAccess str : moduleRepository.getParentData(userId, glValModuleId)) {
				submodule = new MstUserAccessVO();
				submodule.setFlag(str.getDownload());
				submodule.setViewflag(str.getView());
				submodules.add(submodule);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return submodules;
	}

	@RequestMapping(value = "/verifyCredentials", method = RequestMethod.POST)
	@ResponseBody
	public Boolean verifyCredentials(@RequestParam String moduleId, @RequestParam int userId) {

		try {
			List<MstUserAccess> m = moduleRepository.getParentDetails(moduleId, userId);
			if (m != null && m.size() != 0) {
				return true;
			} else
				return false;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/* Added by vrinda ===end */

	/* Start : Harshil 22/04/2019 */

	@PostMapping(path = "/addSecondChild", params = "action=create")
	public ModelAndView addSecondChild(@Valid MstModule mstModule, Model model, BindingResult result,
			HttpSession session, RedirectAttributes attr) throws Exception {

		ModelAndView modelandview = new ModelAndView();
		String[] name = mstModule.getModuleName().split(",");

		MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
		mstModule.setCreatedBy(mstUser.getUserName());
		MstModule module2 = moduleRepository.searchDirectory(name[2]);

		if (module2 != null) {
			attr.addFlashAttribute("errorMsg", "Sub-folder already available...!");
		} else {
			mstModule.setParentId(name[1]);
			mstModule.setCreatedBy(mstUser.getUserName());
			mstModule.setModuleName(name[2]);
			MstModule module = imoduleDao.addChild(mstModule);
			MstModule parentfoldername = imoduleDao.getParent(Integer.parseInt(name[0]));
			String secondChildFolderName = imoduleDao.getParent(Integer.parseInt(name[1])).getModuleName();
			System.out.println("HParentFoldername::::::::::::::::---------------" + parentfoldername.getModuleName());
			File directory = new File(
					CommonConstant.FILE_PATH_FOLDER + parentfoldername.getModuleName() + "/" + secondChildFolderName);

			System.out.println("File Path:::::::::---->" + directory);
			System.out.println("directoory there????:::::----------->" + directory.exists());

			if (directory.exists()) {
				File folder = new File(directory + "/" + name[2]);
				folder.mkdir();
			}
			attr.addFlashAttribute("Msg", "Sub-folder is successfully created");
		}

		modelandview.setViewName("redirect:/addNewFolder/secondchild");
		return modelandview;

	}

	@PostMapping(path = "/addThirdChild", params = "action=create")
	public ModelAndView addThirdChild(@Valid MstModule mstModule, Model model, BindingResult result,
			HttpSession session, RedirectAttributes attr) throws Exception {

		ModelAndView modelandview = new ModelAndView();
		String[] name = mstModule.getModuleName().split(",");

		for (int i = 0; i < name.length; i++) {
			System.out.println(name[i]);
		}

		System.out.println("HModule name:---->" + name[3]);

		MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
		mstModule.setCreatedBy(mstUser.getUserName());
		MstModule module2 = moduleRepository.searchDirectory(name[3]);

		if (module2 != null) {
			attr.addFlashAttribute("errorMsg", "Sub-folder already available...!");
		} else {
			mstModule.setParentId(name[2]);
			mstModule.setCreatedBy(mstUser.getUserName());
			mstModule.setModuleName(name[3]);
			MstModule module = imoduleDao.addChild(mstModule);
			MstModule parentfoldername = imoduleDao.getParent(Integer.parseInt(name[0]));
			String secondChildFolderName = imoduleDao.getParent(Integer.parseInt(name[1])).getModuleName();
			String thirdChildFolderName = imoduleDao.getParent(Integer.parseInt(name[2])).getModuleName();
			System.out.println("HParentFoldername::::::::::::::::---------------" + parentfoldername.getModuleName());
			File directory = new File(CommonConstant.FILE_PATH_FOLDER + parentfoldername.getModuleName() + "/"
					+ secondChildFolderName + "/" + thirdChildFolderName);

			System.out.println("File Path:::::::::---->" + directory);
			System.out.println("directoory there????:::::----------->" + directory.exists());

			if (directory.exists()) {
				File folder = new File(directory + "/" + name[3]);
				folder.mkdir();
			}
			attr.addFlashAttribute("Msg", "Sub-folder is successfully created");
		}

		modelandview.setViewName("redirect:/addNewFolder/thirdchild");
		return modelandview;

	}

	/* End : Harshil 22/04/2019 */

	/* Start : Bindhu 30/04/2019 */
	@PostMapping(path = "/removeSecondChild", params = "action=delete")
	public ModelAndView removeSecondChild(@Valid MstModule mstModule, Model model, BindingResult result,
			HttpSession session, RedirectAttributes attr) throws Exception {

		ModelAndView modelandview = new ModelAndView();
		String[] name = mstModule.getModuleName().split(",");

		/*System.out.println("Module name:---->" + name[1]);
		System.out.println("ModuleID name:---->" + name[0]);
		System.out.println("ModuleID name:---->" + name[2]);*/

		String childmoduleId = name[2];
		String parentmoduleId = name[0];

		String childmodules = moduleRepository.getAllchildModules(Integer.parseInt(childmoduleId));

		String[] str = childmodules.split(",");

		List<String> getModuleIds = new ArrayList<String>();

		for (String s : str) {
			if (s != null && !s.equalsIgnoreCase("null")) {
				getModuleIds.add(s);
			}
		}

		System.out.println("Before" + getModuleIds);

		Collections.reverse(getModuleIds);
		System.out.println("After" + getModuleIds);

		for (String getPathForFolder : getModuleIds) {

			String dirLoc = iDocumentDetailDAO.getFileLocation(Integer.parseInt(getPathForFolder));
			System.out.println("DirLoc-->" + dirLoc);

			File subdirectory = new File(CommonConstant.FILE_PATH_FOLDER + dirLoc);
			List<TxnDocumentDetail> files = documentdetailrepository.getFiles(Integer.parseInt(getPathForFolder));

			if (files.size() > 0) {
				for (TxnDocumentDetail filenames : files) {
					File filedirectory = new File(
							CommonConstant.FILE_PATH_FOLDER + dirLoc + "/" + filenames.getFileName());
					System.out.println("FilePath:::::----------------->" + filedirectory);
					System.out.println(filedirectory.exists());
					System.out.println(filedirectory.delete());
				}
			}
			subdirectory.delete();

			iuserdao.removeDependentFolderUseraccess(Integer.parseInt(getPathForFolder));
			iDocumentDetailDAO.removeDependentFiles(Integer.parseInt(getPathForFolder));

		}

		String[] str1 = childmodules.split(",");
		System.out.println("Length of strings:::::----->" + str1.length);
		int[] files = new int[childmodules.length()];
		for (int i = 0; i < str1.length; i++) {

			if (str1[i] != null && !str1[i].equalsIgnoreCase("null")) {
				files[i] = Integer.parseInt(str1[i]);
				imoduleDao.deleteSubFolders(files[i]);
			}
		}

		iuserdao.deleteuseraccess(Integer.parseInt(childmoduleId));

		File Parentdirectory = new File(
				CommonConstant.FILE_PATH_FOLDER + iDocumentDetailDAO.getFileLocation(Integer.parseInt(childmoduleId)));
		System.out.println(Parentdirectory.exists());
		System.out.println(Parentdirectory.delete());

		imoduleDao.deleteParentFolder(Integer.parseInt(childmoduleId));

		attr.addFlashAttribute("errorMsg", "Sub-folder is deleted..!!!");
		modelandview.setViewName("redirect:/addNewFolder/removesecondchild");
		return modelandview;

	}

	@PostMapping(path = "/removeThirdChild", params = "action=delete")
	public ModelAndView removeThirdChild(@Valid MstModule mstModule, Model model, BindingResult result,
			HttpSession session, RedirectAttributes attr) throws Exception {

		ModelAndView modelandview = new ModelAndView();

		String[] name = mstModule.getModuleName().split(",");

		System.out.println("Parent name:---->" + name[0]);
		System.out.println("1child name:---->" + name[1]);
		System.out.println("2child name:---->" + name[2]);
		System.out.println("Last ModuleID name:---->" + name[3]);

		int moduleid = Integer.parseInt(name[3]);
		String dirLoc = iDocumentDetailDAO.getFileLocation(moduleid);

		List<TxnDocumentDetail> files = documentdetailrepository.getFiles(moduleid);
		File subdirectory = new File(CommonConstant.FILE_PATH_FOLDER + dirLoc);

		if (files.size() > 0) {
			for (TxnDocumentDetail file : files) {

				File filedirectory = new File(CommonConstant.FILE_PATH_FOLDER + dirLoc + "/" + file.getFileName());
				System.out.println("FilePath:::::----------------->" + filedirectory);
				System.out.println(filedirectory.exists());
				System.out.println(filedirectory.delete());

			}
		}

		System.out.println("On Delete--------------->" + subdirectory.delete());

		iuserdao.removeDependentFolderUseraccess(moduleid);
		iDocumentDetailDAO.removeDependentFiles(moduleid);
		imoduleDao.deleteParentFolder(moduleid);

		attr.addFlashAttribute("errorMsg", "Sub-folder is deleted..!!!");
		modelandview.setViewName("redirect:/addNewFolder/removethirdchild");
		return modelandview;

	}

	/* End : Bindhu 30/04/2019 */

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/handleArea/{selectedArea}", method = RequestMethod.GET)
	public ModelAndView handleArea(@PathVariable String selectedArea, HttpSession httpSession, Model model)
			throws Exception {

		ModelAndView modelAndView = new ModelAndView();

		List<MstHeader> headers = iFolderDAO.getHeaders();
		if (headers.size() > 0) {
			modelAndView.addObject("headers", headers);
		}

		List<MstCategory> categories = iFolderDAO.getCategory();
		if (categories.size() > 0) {
			modelAndView.addObject("categories", categories);
		}
		List<MstSubCat> subCats = iFolderDAO.getSubCategory();
		if (subCats.size() > 0) {
			modelAndView.addObject("subcategories", subCats);
		}
		List<MstDocType> docTypes = iFolderDAO.getDocType();
		if (docTypes.size() > 0) {
			modelAndView.addObject("doctypes", docTypes);
		}
		TxnDocumentDetail documentDetail = new TxnDocumentDetail();
		Integer moduleId = (Integer) httpSession.getAttribute(CommonConstant.MODULE_ID);
		if (moduleId != null) {
			documentDetail.setModuleId(moduleId);
		}

		System.out.println("selectedArea-->" + selectedArea);
		modelAndView.addObject("documentDetails", documentDetail);
		modelAndView.addObject("selectedArea", selectedArea);
		modelAndView.setViewName("uploadform");
		return modelAndView;

	}

	@RequestMapping(value = "/getAttributes", method = RequestMethod.POST)
	@ResponseBody
	public List<AttributeVO> getAttributes(@RequestParam String header) {

		List<AttributeVO> submodules = new ArrayList<AttributeVO>();
		AttributeVO submodule;
		System.out.println("AREA :::" + header);

		try {

			List<MstAttributeConfig> mstAttributeConfig = attrributeConfigRepository.getAllAttribute(Integer.parseInt(header));
			System.out.println("List Size : " + mstAttributeConfig.size());
			for (MstAttributeConfig mstAttributeConfigValues : mstAttributeConfig) {
				submodule = new AttributeVO();
				
				submodule.setAttributeName(mstAttributeConfigValues.getMstAttribute().getAttributeName());
				submodule.setFieldId(mstAttributeConfigValues.getFieldValueId());
				submodule.setFieldName(mstAttributeConfigValues.getFieldValue());
				submodule.setHeaderName(mstAttributeConfigValues.getMstHeader().getHeaderName());

				submodules.add(submodule);

			}

			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return submodules;
	}

	@GetMapping("/paginationforReport")
	public ModelAndView paginationReport(@RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page, @RequestParam("pageName") String pageName, HttpSession session,Model model) throws Exception {
		
		
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
			} else {
				modelandview.addObject("navigation", "disable");
				
			}
			modelandview.setViewName("reports_page");
			
			return modelandview ;
			
			

		}		
	@RequestMapping(path = "/provideRights", method = RequestMethod.GET)
	public String authorization(Model model) throws Exception {

		log.info("<<<<<<<<<<<<<<<<<<<<:::::::::::::::::::::::Redirectingg of page:::::::::::::::::::::::>>>>>>>>>>>>>>>>");

		List<MstUser> usersList = iuserdao.getAllUsers();
		List<ModulesList> moduleslist = moduleRepository.getFolders();
		model.addAttribute("users", usersList);
		model.addAttribute("Folderlevels", moduleslist);
		
		return "massAuthorization";
	}
	
		
	
	@RequestMapping(value = "/editHeaderDetails", method = RequestMethod.GET)
	@ResponseBody
	public TxnDocumentDetailVO editHeaderDetails(@RequestParam String docDetailId,@RequestParam String getAreaIdForEdit) {

		
		TxnDocumentDetailVO txnDocumentDetailVO = null;
		System.out.println("docDetailId NEW :::" + docDetailId);
		System.out.println("getAreaIdForEdit NEW :::" + getAreaIdForEdit);
		
		try {

				TxnDocumentDetail editHeaderDetail = iFolderDAO.getHeaderDetailForEdit(docDetailId, getAreaIdForEdit);
			
				txnDocumentDetailVO = new TxnDocumentDetailVO();
				if(editHeaderDetail.getCategoryName() != null && !editHeaderDetail.getCategoryName().equalsIgnoreCase(null) && editHeaderDetail.getCategoryName().length() > 0)
				{
					txnDocumentDetailVO.setCategoryName(editHeaderDetail.getCategoryName().trim());
				}
				if(editHeaderDetail.getSubCategoryName() != null && !editHeaderDetail.getSubCategoryName().equalsIgnoreCase(null) && editHeaderDetail.getSubCategoryName().length() > 0)
				{
					txnDocumentDetailVO.setSubCategoryName(editHeaderDetail.getSubCategoryName().trim());
				}
				if(editHeaderDetail.getBrand() != null && !editHeaderDetail.getBrand().equalsIgnoreCase(null) && editHeaderDetail.getBrand().length() > 0)
				{
					txnDocumentDetailVO.setBrand(editHeaderDetail.getBrand().trim());
				}
				if(editHeaderDetail.getDocType() != null && !editHeaderDetail.getDocType().equalsIgnoreCase(null) && editHeaderDetail.getDocType().length() > 0)
				{
					txnDocumentDetailVO.setDocType(editHeaderDetail.getDocType());
				}
				
				if(editHeaderDetail.getArticleCode() != null && !editHeaderDetail.getArticleCode().equalsIgnoreCase(null) && editHeaderDetail.getArticleCode().length() > 0)
				{
					txnDocumentDetailVO.setArticleCode(editHeaderDetail.getArticleCode());
				}
				if(editHeaderDetail.getProductDetails() != null && !editHeaderDetail.getProductDetails().equalsIgnoreCase(null) && editHeaderDetail.getProductDetails().length() > 0)
				{
					txnDocumentDetailVO.setProductDetails(editHeaderDetail.getProductDetails());
				}
				if(editHeaderDetail.getManufacturename() != null && !editHeaderDetail.getManufacturename().equalsIgnoreCase(null) && editHeaderDetail.getManufacturename().length() > 0)
				{
					txnDocumentDetailVO.setManufacturename(editHeaderDetail.getManufacturename());
				}
				if(editHeaderDetail.getSupplierCode() != null && !editHeaderDetail.getSupplierCode().equalsIgnoreCase(null) && editHeaderDetail.getSupplierCode().length() > 0)
				{
					txnDocumentDetailVO.setSupplierCode(editHeaderDetail.getSupplierCode());
				}
				if(editHeaderDetail.getRegion() != null && !editHeaderDetail.getRegion().equalsIgnoreCase(null) && editHeaderDetail.getRegion().length() > 0)
				{
					txnDocumentDetailVO.setRegion(editHeaderDetail.getRegion());
				}
				if(editHeaderDetail.getActive() != null && !editHeaderDetail.getActive().equalsIgnoreCase(null) && editHeaderDetail.getActive().length() > 0)
				{
					txnDocumentDetailVO.setActive(editHeaderDetail.getActive());
				}
				if(editHeaderDetail.getStatus() != null && !editHeaderDetail.getStatus().equalsIgnoreCase(null) && editHeaderDetail.getStatus().length() > 0)
				{
					txnDocumentDetailVO.setStatus(editHeaderDetail.getStatus());
				}
				if(editHeaderDetail.getDistributionCenterCode() != null && !editHeaderDetail.getDistributionCenterCode().equalsIgnoreCase(null) && editHeaderDetail.getDistributionCenterCode().length() > 0)
				{
					txnDocumentDetailVO.setDistributionCenterCode(editHeaderDetail.getDistributionCenterCode());
				}
				if(editHeaderDetail.getDistributionCenterLocation() != null && !editHeaderDetail.getDistributionCenterLocation().equalsIgnoreCase(null) && editHeaderDetail.getDistributionCenterLocation().length() > 0)
				{
					txnDocumentDetailVO.setDistributionCenterLocation(editHeaderDetail.getDistributionCenterLocation());
				}
				if(editHeaderDetail.getAuditStatus() != null && !editHeaderDetail.getAuditStatus().equalsIgnoreCase(null) && editHeaderDetail.getAuditStatus().length() > 0)
				{
					txnDocumentDetailVO.setAuditStatus(editHeaderDetail.getAuditStatus());
				}
				if(editHeaderDetail.getAuditStatus() != null && !editHeaderDetail.getAuditStatus().equalsIgnoreCase(null) && editHeaderDetail.getAuditStatus().length() > 0)
				{
					txnDocumentDetailVO.setAuditStatus(editHeaderDetail.getAuditStatus());
				}
				
				System.out.println("Cat Name ::: " + txnDocumentDetailVO.getCategoryName());
				
			
			
			System.out.println("editHeaderDetail NEW :::" + editHeaderDetail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return txnDocumentDetailVO;
	}
	@SuppressWarnings("unlikely-arg-type")
	@RequestMapping(value = "/makefolderAuthorization", method = RequestMethod.POST)
	@ResponseBody
	public String makefolderAuthorization(@RequestBody AuthorizationvaluesVO Authorizationvalues, HttpSession session) {

		String value = "true";
		MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
		try {
			System.out.println("After Clicking button:::{After Successfull Ajax Call}");
			
			String[] str = Authorizationvalues.getFinalmoduleValues();
			int userId = Authorizationvalues.getUserId();
			Integer[] parentvalues = Authorizationvalues.getParentmodules();
			
			List<Integer> parental = new ArrayList<Integer>();
			System.out.println("{After Successfull Ajax Call}:::"+str.length);
			log.info("Int Length::"+parentvalues.length);
			Set<Integer> parentmodulelist = new HashSet<Integer>();
			Collections.addAll(parental, parentvalues);
			parentmodulelist.addAll(parental);
			
			log.info("Set of Prent Modules::"+parentmodulelist.size());
			
			
		/*	List<MstModule> mstModulelist = moduleRepository.getParentData("#");
			System.out.println("mstModulelist.size()---->" + mstModulelist.size());
			if (mstModulelist != null && mstModulelist.size() > 0) {
				MstUserAccess mstUserAccess;
				for (MstModule mstModule : mstModulelist) {
					mstUserAccess = new MstUserAccess();
					mstUserAccess.setUserId(saveuser.getUserId());
					mstUserAccess.setCreatedBy(mstUser.getUserName());
					mstUserAccess.setMstModule(mstModule);
					mstUserAccess.setDownload(false);
					mstUserAccess.setView(false);
					iUserDao.saveUserAccess(mstUserAccess);
				}
			} */
			
			
			

			
			List<Integer> parentlist = new ArrayList<Integer>();	
			List<Integer> moduleIdsList = new ArrayList<Integer>();
			List<Integer> parentToremove = new ArrayList<Integer>();
			
			List<MstUserAccess> accessedFolders = useraccessrepository.getAllFoldersAccessed(userId);
			if (!accessedFolders.isEmpty()) {
				for (MstUserAccess moduleId : accessedFolders) {

					moduleIdsList.add(moduleId.getMstModule().getModuleId());

				}
			}
			
			/* To give access of parent modules for doc_manager(start) */
			if (!parentmodulelist.isEmpty()) {														

				for (Integer parentTocheck : parentmodulelist) {									

					if (!moduleIdsList.contains(parentTocheck)) {
						MstUserAccess mstUserAccess;
						MstModule mstModule = new MstModule();
						mstModule.setModuleId(parentTocheck);

						mstUserAccess = new MstUserAccess();
						mstUserAccess.setUserId(userId);
						mstUserAccess.setCreatedBy(mstUser.getUserName());
						mstUserAccess.setMstModule(mstModule);
						mstUserAccess.setDownload(false);
						mstUserAccess.setView(false);
						iuserdao.saveUserAccess(mstUserAccess);

					}
					else{
						
						//log.info("Already available for User");
					}

				}

			}
			/* To give access of parent modules for doc_manager(End) */

			for(int i=0; i<str.length; i++) {
				
				String[] modulesforTest =  str[i].split("~");
					if(moduleIdsList.contains(Integer.parseInt(modulesforTest[0]))) {
						
						
						Boolean view = Boolean.parseBoolean(modulesforTest[1]);
						Boolean download =Boolean.parseBoolean(modulesforTest[2]);
						int ModuleToUpdate = Integer.parseInt(modulesforTest[0]);
						
						int isUpdated = useraccessrepository.updateViewandDownload(view,download,ModuleToUpdate,userId);
						log.info("IsUpdated::"+isUpdated);
						log.info("IsUpdated module::"+modulesforTest[0]);
						
						
					}else {
						
						log.info("IsInserted module::"+modulesforTest[0]);
						MstUserAccess useraccess = new MstUserAccess();
						MstModule modules = new MstModule();				/* Inserts module_ids  in mst_user_access of user authorization */
		
						modules.setModuleId(Integer.parseInt(modulesforTest[0]));
		
						useraccess.setUserId(userId);
						useraccess.setMstModule(modules);
						useraccess.setDownload(Boolean.parseBoolean(modulesforTest[2]));
						useraccess.setView(Boolean.parseBoolean(modulesforTest[1]));
						useraccess.setCreatedBy(mstUser.getUserName());
						useraccessrepository.save(useraccess);
						
						
					}
			
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return value;
	}
		@RequestMapping(value = "/getFolderDetailsByUser", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getFolderDetailsByUser(@RequestParam Integer UserId) throws Exception {
		
	

	//	log.info("By clicking Dropdown-------->>>>>>>>>>>>>>>>>>>>>>>>.");
		log.info(">>>>>>>"+UserId);
		
		//Added by Bindhu 22-01-2020 now() - start
				
		//Added by Bindhu 22-01-2020  now() - End

		List<MstUserAccess>   accessedFolders =   useraccessrepository.getAllFoldersAccessed(UserId);
		List<String> moduleIdsList = new ArrayList<String>();
		//StringBuilder modulerights = new StringBuilder();
		
		List<String> moduleIdsListpractise = new ArrayList<String>();
		
		for(MstUserAccess moduleId: accessedFolders) {
			StringJoiner modulerights = new StringJoiner("~");
			moduleIdsList.add(Integer.toString(moduleId.getMstModule().getModuleId()));
			modulerights.add(Integer.toString(moduleId.getMstModule().getModuleId()));	
			modulerights.add(moduleId.getDownload()!= null ? moduleId.getDownload().toString() : "false");
			modulerights.add(moduleId.getView()!= null ? moduleId.getView().toString() : "false");
			moduleIdsListpractise.add(modulerights.toString());
						
		}
		//log.info("Modules:::::>>>>"+modulerights);
		log.info("moduleIdsListpractise::::"+moduleIdsListpractise);
		
		return moduleIdsListpractise;

	} 
	
		
		@RequestMapping(path = "/removeRights", method = RequestMethod.GET)
		public String removeAuthorization(Model model) throws Exception {
		
			log.info("<<<<<<<<<<<<<<<<<<<<:::::::::::::::::::::::Redirectingg of page:::::::::::::::::::::::>>>>>>>>>>>>>>>>");
		
			List<MstUser> usersList = iuserdao.getAllUsers();
			List<ModulesList> moduleslist = moduleRepository.getFolders();
			model.addAttribute("users", usersList);
			model.addAttribute("Folderlevels", moduleslist);
			
			return "removeAuthorization";
		}
		
		
		@SuppressWarnings("unlikely-arg-type")
		@RequestMapping(value = "/removefolderAuthorization", method = RequestMethod.POST)
		@ResponseBody
		public String removefolderAuthorization(@RequestBody AuthorizationvaluesVO Authorizationvalues, HttpSession session) {
			
			
			String mesg = "Sucess";
			
			try {
				
				String[] str = Authorizationvalues.getFinalmoduleValues();
				int userId = Authorizationvalues.getUserId();
				System.out.println("{After Successfull Ajax Call}:::"+str.length);
				
				
				List<Integer> moduleIdsList = new ArrayList<Integer>();
				List<String> modulesfromUI = new ArrayList<String>();
				
				Set<String> moduleSet = new HashSet<String>();
				Collections.addAll(modulesfromUI, str);
				moduleSet.addAll(modulesfromUI);
				
				log.info("Set Size Before>>>>"+moduleSet.size());
				
				List<MstUserAccess> accessedFolders = useraccessrepository.getAllFoldersAccessed(userId);
				
				List<MstModule> parentModulelist = moduleRepository.getParentData("#");
				Set<String>  parentmodules =new HashSet<String>();
				
				if(!parentModulelist.isEmpty()) {
					for(MstModule parentmodule : parentModulelist) {
						parentmodules.add(String.valueOf(parentmodule.getModuleId()));	
					}	
				}
				
				moduleSet.removeAll(parentmodules);
				
				log.info("Set Size After>>>>"+moduleSet.size());
				
				
				
				if (!accessedFolders.isEmpty()) {
					for (MstUserAccess moduleId : accessedFolders) {

						moduleIdsList.add(moduleId.getMstModule().getModuleId());

					}
				}
				

				if(!moduleSet.isEmpty()) {
					
					log.info("Here  1");
					for(String module : moduleSet ) {
						
						log.info("Here  1>>>>>"+module);
						if(moduleIdsList.contains(Integer.parseInt(module))) {
							
							int isDeleted  = useraccessrepository.revokeAccessmass(userId, Integer.parseInt(module));
							log.info("This Module"+ module +" is revoked >>>"+ isDeleted);
	
						}				
					}
	
			    }
	
			}catch(Exception e) {
				e.printStackTrace();
				
			}
						
			return mesg;	
		}
		
}
