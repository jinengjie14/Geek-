package com.openkx.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.openkx.repository.UserDao;
import com.openkx.service.LoginService;
import com.openkx.valid.LoginForm;






@Controller
public class UserController {
	
	@Resource
	private UserDao userdao;
	


	@RequestMapping({"/login", "/"}) //登陆入口
	public String login() {
		return "login";
	}

	
	
	
	@ResponseBody//登陆入口
	@RequestMapping(value = "/login/check", method = RequestMethod.POST)
	public ModelAndView logincheck(@Valid LoginForm user,BindingResult Result,HttpSession httpSession,LoginService loginservice){
		ModelAndView mav= new ModelAndView("redirect:/login");
		loginservice.init(Result, user, httpSession,userdao);
		if(loginservice.getCount()>0){
			mav.addObject("error", loginservice.getErrorInfo()); //向前台输入错误信息
			return mav;
		}else{
			mav.setViewName("redirect:/");
			return mav; 
			
		}

	}
	
	
	@RequestMapping("/userindex") //用户中心入口
	public String userindex() {
		return "user/index";
	}
	


	
	
	
}
