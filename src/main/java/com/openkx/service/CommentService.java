package com.openkx.service;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.openkx.domain.Answer;
import com.openkx.domain.Comment;
import com.openkx.domain.User;
import com.openkx.repository.AnswerDao;
import com.openkx.repository.CommentDao;
import com.openkx.repository.QuestionDao;
import com.openkx.valid.AnswerForm;
import com.openkx.valid.CommentForm;

@Component
public class CommentService {
	
	@Resource
	private CommentDao commentDao;
	
	public void save(CommentForm form ,User user,String questionid,String answerid){
		Comment comment = new Comment();
		comment.setUser(user);
		comment.setQuestionid(questionid);
		comment.setAnswerid(answerid);
		BeanUtils.copyProperties(form,comment, Comment.class);
		
		commentDao.save(comment);
	}
}
