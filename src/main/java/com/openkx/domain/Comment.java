package com.openkx.domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Comment {

	@Id
	private String id;
	private String name;
	private Timestamp ctime;
	private String context;
	private String question_id;
	private String answerid;
	
	
	@OneToOne
	@JoinColumn(name="userid")
	private User user;
	
	public Comment(){
		this.id = UUID.randomUUID().toString().replace("-", "");
		this.ctime = new Timestamp(System.currentTimeMillis());
	}
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getCtime() {
		return ctime;
	}
	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getQuestionid() {
		return question_id;
	}
	public void setQuestionid(String questionid) {
		this.question_id = questionid;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAnswerid() {
		return answerid;
	}
	public void setAnswerid(String answerid) {
		this.answerid = answerid;
	}
	
	
	
	
	
	
}
