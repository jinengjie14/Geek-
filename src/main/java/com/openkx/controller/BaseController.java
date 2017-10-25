package com.openkx.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.openkx.domain.User;
import com.openkx.repository.UserDao;


public abstract class BaseController {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	protected String sessuserid = "";
	@Autowired
	private UserDao userdao;
	

	@ModelAttribute
	public void getSessionUser(HttpSession session, Model model) {
		sessuserid = (String) session.getAttribute("userid");
		//TODO测试完删除
		sessuserid = "0042dd84ff4d4246a0e3d06095392a86";
		System.out.println("==============" + sessuserid);
		User user = userdao.findById(sessuserid);
		session.setAttribute("user", user);
		if (StringUtils.isNotBlank(sessuserid)) {
			log.debug("BaseController", sessuserid);
			System.out.println("==========e3333====" + sessuserid);
	
		} else {
		
		}

		log.debug("BaseController", "running...");
	}

	
}