package com.fg.tree.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.fg.tree.dao.IUserDAO;
import com.fg.tree.model.ErrorMessageVO;
import com.fg.tree.model.MstModule;
import com.fg.tree.model.MstRole;
import com.fg.tree.model.MstUser;
import com.fg.tree.model.MstUserAccess;
import com.fg.tree.repository.LoginHistoryRepository;
import com.fg.tree.repository.LoginRepository;
import com.fg.tree.repository.ModuleRepository;
import com.fg.tree.repository.UserAccessRepository;

@Controller
public class UserLoginController {

	@Autowired
	LoginRepository loginRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	IUserDAO iUserDao;

	@Autowired
	UserAccessRepository userAccessRepository;

	@Autowired
	LoginHistoryRepository loginhistoryRepository;

	@RequestMapping(path = "/userMaster", method = RequestMethod.GET)
	public String checkDocument(Model model) throws Exception {
		System.out.println("checkDocument");
		model.addAttribute("createUser", new MstUser());
		List<MstRole> roles = iUserDao.getRole();

		if (roles.size() > 0) {
			model.addAttribute("roles", roles);

		}

		return "user_management";

	}

	/*
	 * @RequestMapping(path="/user_management", method=RequestMethod.GET) public
	 * ModelAndView user_management(@ModelAttribute MstUser user){
	 * System.out.println("UserLoginController.user_management()"); ModelAndView
	 * modelAndView=new ModelAndView(); try{ //modelAndView.addObject("createUser",
	 * new MstUser()); List<MstRole> roles = iUserDao.getRole(); if (roles.size() >
	 * 0) { modelAndView.addObject("roles", roles); }
	 * modelAndView.setViewName("user_management"); }catch(Exception e){
	 * e.printStackTrace(); }
	 * 
	 * return modelAndView; }
	 */

	@PostMapping(value = "/save", params = "action=create")
	public String createUser(@Valid MstUser user, Model model, BindingResult result, HttpSession session) {

		List<ErrorMessageVO> errorMessageVOs = new ArrayList<ErrorMessageVO>();
		String returnVal = null;
		try {
			if (iUserDao.isUserExists(user.getUserName())) {
				ErrorMessageVO errorMessageVO = new ErrorMessageVO();
				errorMessageVO.setErrorCode(CommonConstant.USER_ALREADY_EXIST_CODE);
				errorMessageVO.setErrorMessage(CommonConstant.USER_ALREADY_EXIST);
				errorMessageVOs.add(errorMessageVO);
				model.addAttribute("errorMsg", errorMessageVO);
				model.addAttribute("createUser", new MstUser());
				List<MstRole> roles = iUserDao.getRole();
				if (roles.size() > 0) {
					model.addAttribute("roles", roles);
				}
				returnVal = "user_management";
			} else {
				if (user.getRole().getRoleId() == 1
						&& iUserDao.getUderDataFromrole(user.getRole().getRoleId()) != null) {
					ErrorMessageVO errorMessageVO = new ErrorMessageVO();
					errorMessageVO.setErrorCode(CommonConstant.UNIQUE_SYSADMIN_CHECK_CODE);
					errorMessageVO.setErrorMessage(CommonConstant.UNIQUE_SYSADMIN_CHECK);
					errorMessageVOs.add(errorMessageVO);
					model.addAttribute("errorMsg", errorMessageVO);
					model.addAttribute("createUser", new MstUser());
					List<MstRole> roles = iUserDao.getRole();
					if (roles.size() > 0) {
						model.addAttribute("roles", roles);
					}
					returnVal = "user_management";
				} else if (loginRepository.isEmailIdExists(user.getEmailId()) != null) {
					ErrorMessageVO errorMessageVO = new ErrorMessageVO();
					errorMessageVO.setErrorMessage(CommonConstant.USER_EMAIL_EXIST_MESSAGE);
					errorMessageVOs.add(errorMessageVO);
					model.addAttribute("errorMsg", errorMessageVO);
					model.addAttribute("createUser", user);
					List<MstRole> roles = iUserDao.getRole();
					if (roles.size() > 0) {
						model.addAttribute("roles", roles);
					}
					returnVal = "user_management";
				} else {
					MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
					user.setCreatedBy(mstUser.getUserName());
					loginRepository.save(user);
					MstUser saveuser = loginRepository.isUserExists(user.getUserName());
					System.out.println(saveuser.getUserId());
					List<MstModule> mstModulelist = moduleRepository.getParentData("#");
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
					}

					model.addAttribute("userDetails", loginRepository.findAll());
					returnVal = "searchuser";
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnVal;
	}

	@RequestMapping(path = "/userSearch", method = RequestMethod.GET)
	public String userSearch(Model model) throws Exception {
		return "searchuser";
	}

	@RequestMapping(path = "/searchuser", method = RequestMethod.GET)
	public ModelAndView searchuser(@ModelAttribute MstUser user) {
		System.out.println("UserLoginController.searchuser()");
		ModelAndView modelAndView = new ModelAndView();
		try {
			List<MstUser> users = iUserDao.getAllUsers();
			System.out.println("users.size()--->" + users.size());
			modelAndView.addObject("userDetails", users);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return modelAndView;
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") int userId, Model model) {
		try {
			MstUser user = loginRepository.findById(userId);
			model.addAttribute("user", user);
			List<MstRole> roles = iUserDao.getRole();
			if (roles.size() > 0) {
				model.addAttribute("roles", roles);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "update-user";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") int id, @Valid MstUser user, BindingResult result, Model model) {

		String returnVal = null;
		List<ErrorMessageVO> errorMessageVOs = new ArrayList<ErrorMessageVO>();

		try {
			if (result.hasErrors()) {
				user.setUserId(id);
				returnVal = "update-user";
			}
			if (user.getRole().getRoleId() == 1 && iUserDao.getUderDataFromrole(user.getRole().getRoleId()) != null) {
				System.out.println("On sys admin check");
				ErrorMessageVO errorMessageVO = new ErrorMessageVO();
				errorMessageVO.setErrorCode(CommonConstant.UNIQUE_SYSADMIN_CHECK_CODE);
				errorMessageVO.setErrorMessage(CommonConstant.UNIQUE_SYSADMIN_CHECK);
				errorMessageVOs.add(errorMessageVO);
				model.addAttribute("errorMsg", errorMessageVO);
				user.setUserId(id);
				model.addAttribute("user", user);
				List<MstRole> roles = iUserDao.getRole();
				if (roles.size() > 0) {
					model.addAttribute("roles", roles);
				}
				returnVal = "update-user";
			} else if (loginRepository.isEmailIdExistsWithUser(user.getEmailId(), user.getUserName()) != null) {
				ErrorMessageVO errorMessageVO = new ErrorMessageVO();
				errorMessageVO.setErrorMessage(CommonConstant.USER_EMAIL_EXIST_MESSAGE);
				errorMessageVOs.add(errorMessageVO);
				model.addAttribute("errorMsg", errorMessageVO);
				user.setUserId(id);
				model.addAttribute("user", user);
				List<MstRole> roles = iUserDao.getRole();
				if (roles.size() > 0) {
					model.addAttribute("roles", roles);
				}
				returnVal = "update-user";
			} else {

				iUserDao.updateUserDetail(user.getFirstName(), user.getLastName(), user.getContact(), user.getEmailId(),
						user.getRole().getRoleId(), user.getUserId());
				System.out.println("after update...");
				model.addAttribute("userDetails", loginRepository.findAll());
				returnVal = "searchuser";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnVal;
	}

	@GetMapping("/view/{id}")
	public String showUserForm(@PathVariable("id") int userId, Model model) {

		try {
			MstUser user = loginRepository.findById(userId);
			model.addAttribute("user", user);
			List<MstRole> roles = iUserDao.getRole();
			if (roles.size() > 0) {
				model.addAttribute("roles", roles);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "view-user";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/folder/{id}")
	public String showFolderForm(@PathVariable("id") Integer userId, Model model,
			RedirectAttributes redirectAttributes) {

		try {
			//System.out.println("HARSHIL userId1-->" + userId);
			//System.out.println("Bindhu Testingg......!!!!!");
			List<MstUserAccess> modules = userAccessRepository.searchHierarchy(userId);
			List<MstUserAccess> moduleList = null;
			MstUserAccess useraccess = new MstUserAccess();
			if (model != null && modules.size() > 0) {
				moduleList = new ArrayList<>();
				MstUserAccess modulein;
				MstModule mstModule;
				for (MstUserAccess module : modules) {
					modulein = new MstUserAccess();
					mstModule = new MstModule();
					mstModule.setModuleId(module.getMstModule().getModuleId());

					if (module.getMstModule().getParentId() != null
							&& module.getMstModule().getParentId().equalsIgnoreCase("#")) {
						mstModule.setParentId(module.getMstModule().getModuleName());

					} else {
						mstModule.setParentId(moduleRepository
								.findbyParent(Integer.parseInt(module.getMstModule().getParentId())).getModuleName());
						mstModule.setModuleName(module.getMstModule().getModuleName());
					}
					modulein.setMstModule(mstModule);
					moduleList.add(modulein);
				}
			}

			useraccess.setUserId(userId);

			List<ErrorMessageVO> messageVOs = (ArrayList<ErrorMessageVO>) model.asMap().get("errorMsg");
			if (messageVOs != null && messageVOs.size() > 0) {
				model.addAttribute("errorMsg", messageVOs);
			}
			model.addAttribute("folderDetail", moduleRepository.getParentData("#"));
			model.addAttribute("userAccess", useraccess);
			model.addAttribute("moduleDetail", moduleList);

			model.addAttribute("userId", userId);
		//	System.out.println("HARSHIL userId2-->" + userId);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "folder-view";
	}

	@RequestMapping(value = "/subfolder", method = RequestMethod.POST)
	@ResponseBody
	public List<ErrorMessageVO> getRegions(@RequestParam String country) {

		List<ErrorMessageVO> submodules = new ArrayList<ErrorMessageVO>();
		ErrorMessageVO submodule;
		System.out.println("NEW :::" + country);
		try {
			for (MstModule str : moduleRepository.getParentData(country)) {
				submodule = new ErrorMessageVO();
				submodule.setErrorCode(str.getModuleId());
				submodule.setErrorMessage(str.getModuleName());
				submodules.add(submodule);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return submodules;
	}

	@PostMapping(value = "/saveUserAccess")
	public ModelAndView createUserAccess(@Valid MstUserAccess user, Model model, BindingResult result,
			HttpSession session, RedirectAttributes redirectAttrs) {

		List<ErrorMessageVO> errorMessageVOs = new ArrayList<ErrorMessageVO>();
		ModelAndView modelAndView = new ModelAndView();
		try {
			MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
			if (iUserDao.isUserAccessExists(user.getUserId(), user.getMstModule().getModuleId())) {
				if (iUserDao.isUserAccessExists(user.getUserId(), Integer.parseInt(user.getCreatedBy()))) {
					ErrorMessageVO errorMessageVO = new ErrorMessageVO();
					errorMessageVO.setErrorCode(CommonConstant.UNIQUE_CREATE_USER_RIGHTS_CODE);
					errorMessageVO.setErrorMessage(CommonConstant.UNIQUE_CREATE_USER_RIGHTS);
					errorMessageVOs.add(errorMessageVO);
					redirectAttrs.addFlashAttribute("errorMsg", errorMessageVOs);
					// redirectAttrs.addFlashAttribute("id",
					// mstUser.getUserId());
					modelAndView.setViewName("redirect:/folder/" + user.getUserId());
				} else {
					System.out.println("user Id--->" + user.getUserId());
					System.out.println("created by --->" + user.getCreatedBy());
					MstModule mstModule = new MstModule();

					mstModule.setModuleId(Integer.parseInt(user.getCreatedBy()));
					user.setMstModule(mstModule);
					user.setCreatedBy(mstUser.getUserName());
					iUserDao.saveUserAccess(user);
					model.addAttribute("userDetails", loginRepository.findAll());
					modelAndView.setViewName("searchuser");
				}
			} else {
				System.out.println("In else...");
				int submoduleId = Integer.parseInt(user.getCreatedBy());

				user.setCreatedBy(mstUser.getUserName());
				iUserDao.saveUserAccess(user);

				System.out.println("user Id--->" + user.getUserId());
				System.out.println("submoduleId --->" + submoduleId);
				MstUserAccess mstUserAccess = new MstUserAccess();
				MstModule mstModule = new MstModule();

				mstModule.setModuleId(submoduleId);
				mstUserAccess.setUserId(user.getUserId());
				mstUserAccess.setCreatedBy(mstUser.getUserName());
				mstUserAccess.setMstModule(mstModule);
				iUserDao.saveUserAccess(mstUserAccess);
				model.addAttribute("userDetails", loginRepository.findAll());
				modelAndView.setViewName("searchuser");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modelAndView;
	}

	/* Start : Harshil On : 23-04-2019 */
	@PostMapping(value = "/saveUserAccessNew")
	public ModelAndView createUserAccessNew(@Valid MstUserAccess user, Model model, BindingResult result,
			HttpSession session, RedirectAttributes redirectAttrs) {

		System.out.println("ModuleId:----->" + user.getMstModule().getModuleId());

		List<ErrorMessageVO> errorMessageVOs = new ArrayList<ErrorMessageVO>();
		ModelAndView modelAndView = new ModelAndView();
		boolean folderMapped = false;
		try {
			MstUser mstUser = (MstUser) session.getAttribute(CommonConstant.SESSION_USER_VO);
			System.out.println("Modules-->" + user.getCreatedBy());
			System.out.println("user Id--->" + user.getUserId());

			List<String> subFolders = Arrays.asList(user.getCreatedBy().split("\\s*,\\s*"));
			System.out.println("subfolders....>" + subFolders);

			if (subFolders.contains("-1")) {
				System.out.println("Contains");
				subFolders = subFolders.subList(0, subFolders.size() - 1);
			}

			System.out.println("items-->" + subFolders);

			if (iUserDao.isUserAccessExists(user.getUserId(), user.getMstModule().getModuleId())) {
				if (!(subFolders.size() > 0)) {
					folderMapped = true;
				}
				for (String folderId : subFolders) {
					if (iUserDao.isUserAccessExists(user.getUserId(), Integer.parseInt(folderId))) {
						/*
						 * ErrorMessageVO errorMessageVO = new ErrorMessageVO();
						 * errorMessageVO.setErrorCode(CommonConstant.UNIQUE_CREATE_USER_RIGHTS_CODE);
						 * errorMessageVO.setErrorMessage(CommonConstant.UNIQUE_CREATE_USER_RIGHTS);
						 * errorMessageVOs.add(errorMessageVO);
						 * redirectAttrs.addFlashAttribute("errorMsg", errorMessageVOs);
						 * modelAndView.setViewName("redirect:/folder/" + user.getUserId()); break;
						 */
						folderMapped = true;
					} else {
						System.out.println("folderId--->" + folderId);
						MstModule mstModule = new MstModule();
						mstModule.setModuleId(Integer.parseInt(folderId));
						MstUserAccess mstUserAccessNew = new MstUserAccess();
						mstUserAccessNew.setUserId(user.getUserId());
						mstUserAccessNew.setMstModule(mstModule);
						mstUserAccessNew.setCreatedBy(mstUser.getUserName());
						mstUserAccessNew.setDownload(false);
						iUserDao.saveUserAccess(mstUserAccessNew);
						System.out.println("mstUserAccess--->" + mstUserAccessNew);
						model.addAttribute("userDetails", loginRepository.findAll());
						modelAndView.setViewName("searchuser");
						folderMapped = false;
					}
				}

				if (folderMapped) {
					ErrorMessageVO errorMessageVO = new ErrorMessageVO();
					errorMessageVO.setErrorCode(CommonConstant.UNIQUE_CREATE_USER_RIGHTS_CODE);
					errorMessageVO.setErrorMessage(CommonConstant.UNIQUE_CREATE_USER_RIGHTS);
					errorMessageVOs.add(errorMessageVO);
					redirectAttrs.addFlashAttribute("errorMsg", errorMessageVOs);
					modelAndView.setViewName("redirect:/folder/" + user.getUserId());
				}

			}

			else {
				System.out.println("In else..." + subFolders);
				if (subFolders.size() > 0) {
					for (String list : subFolders) {

						user.setCreatedBy(mstUser.getUserName());
						iUserDao.saveUserAccess(user);
						MstUserAccess mstUserAccess = new MstUserAccess();
						MstModule mstModule = new MstModule();

						mstModule.setModuleId(Integer.parseInt(list));
						mstUserAccess.setUserId(user.getUserId());
						mstUserAccess.setCreatedBy(mstUser.getUserName());
						mstUserAccess.setMstModule(mstModule);
						mstUserAccess.setDownload(false);
						iUserDao.saveUserAccess(mstUserAccess);
						model.addAttribute("userDetails", loginRepository.findAll());
						modelAndView.setViewName("searchuser");

					}
				} else {
					System.out.println("ModuleId-->" + user.getMstModule().getModuleId());
					MstModule mstModule = new MstModule();
					mstModule.setModuleId(user.getMstModule().getModuleId());
					MstUserAccess mstUserAccessNew = new MstUserAccess();
					mstUserAccessNew.setUserId(user.getUserId());
					mstUserAccessNew.setMstModule(mstModule);
					mstUserAccessNew.setCreatedBy(mstUser.getUserName());
					mstUserAccessNew.setDownload(false);
					iUserDao.saveUserAccess(mstUserAccessNew);
					System.out.println("mstUserAccess--->" + mstUserAccessNew);
					model.addAttribute("userDetails", loginRepository.findAll());
					modelAndView.setViewName("searchuser");

				}
				/*
				 * for(String list : subFolders){
				 * 
				 * user.setCreatedBy(mstUser.getUserName()); iUserDao.saveUserAccess(user);
				 * MstUserAccess mstUserAccess = new MstUserAccess(); MstModule mstModule = new
				 * MstModule();
				 * 
				 * mstModule.setModuleId(Integer.parseInt(list));
				 * mstUserAccess.setUserId(user.getUserId());
				 * mstUserAccess.setCreatedBy(mstUser.getUserName());
				 * mstUserAccess.setMstModule(mstModule); mstUserAccess.setDownload(false);
				 * iUserDao.saveUserAccess(mstUserAccess); model.addAttribute("userDetails",
				 * loginRepository.findAll()); modelAndView.setViewName("searchuser");
				 * 
				 * }
				 */
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return modelAndView;
	}

	/* End : Harshil On 23-04-2019 */

	/* Start Harshil Added */
	@RequestMapping(path = "/revokeFolderAccess/{glValUserId}/{revokeValues}", method = RequestMethod.GET)
	public ModelAndView revokeFolderAccess(@PathVariable int glValUserId, @PathVariable String revokeValues,
			HttpSession session, RedirectAttributes redirAttrs) {

		ModelAndView model = new ModelAndView();
		System.out.println("UI values:::>>>"+revokeValues);

		String childmodules = moduleRepository.getAllchildModules(Integer.parseInt(revokeValues));
		System.out.println("child modules:--->" + childmodules);

		String[] str = childmodules.split(",");

		List<Integer> revokeArray = new ArrayList<Integer>();
		revokeArray.add(Integer.parseInt(revokeValues));
		for (int i = 0; i < str.length; i++) {
			if (str[i] != null && !str[i].equalsIgnoreCase("null")) {
				revokeArray.add(Integer.parseInt(str[i]));
			}
		}

		System.out.println("Modules to be deleted:---->" + revokeArray);
		userAccessRepository.revokeAccess(glValUserId, revokeArray);
		redirAttrs.addFlashAttribute("message", "Access Successfully Revoked");
		model.setViewName("redirect:/folder/" + glValUserId);
		// model.setViewName("redirect:/search user");

		return model;
	}

	/* End Harshil Added */
	// @SuppressWarnings("unchecked")
	@GetMapping("/deleteUser/{id}")
	public ModelAndView deleteUser(@PathVariable("id") Integer userId, Model model,
			RedirectAttributes redirectAttributes) {
		ModelAndView modelandView = new ModelAndView();
		try {
			System.out.println("Delete userId -->" + userId);
			userAccessRepository.removeUserAccess(userId);
			loginhistoryRepository.deletefromLoginHistory(userId);
			userAccessRepository.removeUser(userId);

			redirectAttributes.addFlashAttribute("userDeleteMessage", "User Deleted Successfully");
			modelandView.setViewName("redirect:/searchuser");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelandView;

	}
}
