package com.openkx.service;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.openkx.domain.Answer;
import com.openkx.domain.User;
import com.openkx.repository.AnswerDao;
import com.openkx.repository.QuestionDao;
import com.openkx.valid.AnswerForm;

@Component
public class AnswerService {
	
	@Resource
	private AnswerDao answerDao;
	@Resource
	private QuestionDao questionDao;
	
	
	/**
	 * 
	 * @param form
	 * @param user
	 * @param questionid
	 */
	public void save(AnswerForm form ,User user,String questionid){
		Answer answer = new Answer();
		answer.setUser(user);
		answer.setQuestion_id(questionid);
		BeanUtils.copyProperties(form,answer, Answer.class);
		answerDao.save(answer);
	}
}
