package com.openkx.service;

import javax.servlet.http.HttpSession;

import org.springframework.validation.BindingResult;

import com.openkx.domain.User;
import com.openkx.repository.UserDao;
import com.openkx.valid.LoginForm;



public class LoginService {
	private int count = 0;
	private String Errorinfo;
	UserDao userDao;
	public void init(BindingResult bindingResult, LoginForm loginform, HttpSession httpSession,UserDao userDao) {
		this.userDao=userDao;
		if(bindingResult.hasErrors()){
			setErrorInfo(bindingResult.getAllErrors().get(0).getDefaultMessage());
			this.plus();
			return;
		}
		checkUserName(loginform.getUname());
		if(this.count==0){
			User user = new User();
			user=userDao.checkPasswd(loginform.getUname(),loginform.getPasswd());
			if(user.getId()==null){
				this.plus();
				setErrorInfo("用户名或密码错误,请重试!");
			}else{
				//httpSession.setAttribute("userid", user.getId());
				httpSession.setAttribute("user", user);
			}
		}
		
	}
	
	public void checkUserName(String username) {
		if (!userDao.findByUserNameExist(username)) {
			setErrorInfo("该用户名还未注册!");
			this.plus();
		}
	}
	
	
	public User Login(LoginForm loginform) { //登录。
		User user = new User();
		user=userDao.checkPasswd(loginform.getUname(),loginform.getPasswd());
		return user;
	}
	
	public void plus() {
		this.count++;
	}
		
	public int getCount() {
		return this.count;
	}
	public String getErrorInfo(){
		return this.Errorinfo;
	}
	
	public void setErrorInfo(String error){
		this.Errorinfo=error;
	}

}
