package com.openkx.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.openkx.domain.Question;
import com.openkx.domain.User;
import com.openkx.service.AnswerService;
import com.openkx.service.CommentService;
import com.openkx.valid.AnswerForm;
import com.openkx.valid.CommentForm;
@Controller
public class AnswerController extends BaseController{
	
	@Resource 
	private AnswerService answerService;
	
	@Resource 
	private CommentService commentService;
	
	

	@PostMapping("/answer/add")
	public ModelAndView add(AnswerForm form,HttpSession httpSession,@RequestParam String questionid){
		if(sessuserid!=null){
			answerService.save(form, (User)httpSession.getAttribute("user"),questionid);
		}else{
			return new ModelAndView("redirect:/fail/");
		}
		
		return new ModelAndView("redirect:/detail/"+questionid);
	}

	
	
	@RequestMapping("/fail") //没有登录
	public String detail(HttpSession httpSession) {
		return "请登陆注册";
	
		
	}
	
	
	@PostMapping("/comment/add")
	public ModelAndView comment(CommentForm form,HttpSession httpSession,@RequestParam String questionid,@RequestParam String answerid){
		if(sessuserid!=null){
		commentService.save(form, (User)httpSession.getAttribute("user"),questionid,answerid);
		}else
		{
			return new ModelAndView("redirect:/fail/");
		}
		return new ModelAndView("redirect:/detail/"+questionid);
	}
	
	
	
	
}
