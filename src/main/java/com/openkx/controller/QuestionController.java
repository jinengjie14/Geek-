package com.openkx.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.openkx.domain.Answer;
import com.openkx.domain.Comment;
import com.openkx.domain.Nav;
import com.openkx.domain.Question;
import com.openkx.domain.User;
import com.openkx.repository.AnswerDao;
import com.openkx.repository.CommentDao;
import com.openkx.repository.NavDao;
import com.openkx.repository.QuestionDao;
import com.openkx.service.QuestionService;
import com.openkx.valid.QuestionForm;




@Controller
public class QuestionController extends BaseController{
	@Resource
	private QuestionDao questiondao;
	@Resource
	private QuestionService questionService;
	@Resource
	private AnswerDao answerdao;
	@Resource
	private CommentDao commentdao;
	@Resource
	private NavDao navdao;
	
	
	@RequestMapping("/add") //发布问题入口
	public String add(Model model,HttpSession httpSession) {
		if(sessuserid!=null){
		List<Nav> list = navdao.FindAll();
		model.addAttribute("navs", list);
			
		}else{
			return "请登录";
			
		}
		
		return "/answer";
		
	}

	
	@RequestMapping(value = "/addquestion", method = RequestMethod.POST) //发布问题
	public ModelAndView addquestion(HttpSession httpSession,Model model,@Valid QuestionForm question) {


		User user = (User) httpSession.getAttribute("user");
		MultipartFile mfile = question.getFile();
		//上传源文件名字
		String Name =mfile.getOriginalFilename().replace(".jpg","");
		//文件名
		String fileName = new Date().getTime() + mfile.getOriginalFilename();
		System.out.println(fileName);
		//储存缓存文件地址
		String Path =Class.class.getClass().getResource("/").getPath()+ "static/images/";
	
		File file = new File(Path);
		//创建文件夹
		if(!file.isDirectory()){
		file.mkdir();
		}
		String filePath = Path+fileName;
		//移动文件夹到指定目录
		if(mfile.isEmpty()){
	
		}else{
		File mf = new File(filePath);
		try {
			mfile.transferTo(mf);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	String id = questionService.Addquestion(question, user,fileName);//
	return new ModelAndView("redirect:/detail/"+id);


	}
	

	
	@RequestMapping("/detail/{id}") //详情
	public String detail(Model model, @PathVariable String id,HttpSession httpSession,String answerid) {
		Question question = questiondao.findById(id);
		getSessionUser(httpSession, model);
		if(httpSession.getAttribute("user")!=null){
			User user = (User) httpSession.getAttribute("user");
			questionService.addState(user.getId(), question);
		}
		
		List<Answer> answers = answerdao.findAll_ctime(id);
		List<Comment> comment = commentdao.findAll_ctime(id);
		model.addAttribute("question", question);
		model.addAttribute("answers", answers);
		model.addAttribute("comments", comment);
		return "/init";
	}
	
	
	

	
	
}
