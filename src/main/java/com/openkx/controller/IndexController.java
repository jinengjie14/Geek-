package com.openkx.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.openkx.domain.Question;
import com.openkx.repository.NavDao;
import com.openkx.repository.QuestionDao;
import com.openkx.utils.MyPage;




@Controller
public class IndexController extends BaseController{
	
	
	@Resource
	private QuestionDao questiondao;
	
	
	@Resource
	private NavDao navdao;
	

	@GetMapping("/index") //主页入口
	public String next(Model model,@RequestParam(name="page", defaultValue="1")int page, @RequestParam(defaultValue="") String keyword) {
		model.addAttribute("question", questiondao.FindPage(page, keyword)); //分页 既  搜索  模糊查询
		model.addAttribute("nav", navdao.FindAll());//栏目
		return "index";
	}
	

	@RequestMapping("/logout") //登出
	public ModelAndView logout(HttpSession httpSession) {
		httpSession.removeAttribute("user");  
		return new ModelAndView("redirect:/");
	}
	
	
	
	@RequestMapping("/navdetail/{navid}") //栏目详情
	public String detail(Model model, @PathVariable String navid,HttpSession httpSession,@RequestParam(name="page", defaultValue="1")int page) {
		MyPage<Question> questions = questiondao.findByNavId(navid, page);
		model.addAttribute("question", questions);
		model.addAttribute("nav", navdao.FindAll());
		return "index";
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
