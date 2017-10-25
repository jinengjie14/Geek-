package com.openkx.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.openkx.domain.Question;
import com.openkx.domain.User;
import com.openkx.repository.NavDao;
import com.openkx.repository.QuestionDao;
import com.openkx.valid.QuestionForm;

@Component
public class QuestionService {

	
	@Resource
	private QuestionDao questionDao;
	
	/***
	 * 记录浏览问题的用户
	 * @param userid
	 * @param question
	 */
	public void addState(String userid,Question question){
		List<String> list = question.getState();
		if(!list.contains(userid)){
			list.add(userid);
			question.setState(list);
		}
		questionDao.addState(question);
	}
	
	
	
	public String Addquestion(QuestionForm questionForm,User user,String file) {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		Question question = new Question();
		BeanUtils.copyProperties(questionForm, question);
		question.setId(uuid);//问题的id
		question.setUserid(user.getId());//用户id
		question.setCtime(new Timestamp(System.currentTimeMillis()));//创建时间
		question.setUname(user.getUname());
		question.setImage(file);
		questionDao.save(question);
		return uuid;
	}
	
}
