package com.openkx.domain;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
public class Answer {
	@Id
	private String id;
	private String content;
	private String question_id;
	@OneToOne
	@JoinColumn(name="userid")
	private User user;
	private Integer useful;
	private Integer un_useful;
	private Timestamp ctime;
	
	public Answer(){
		this.id = UUID.randomUUID().toString().replace("-", "");
		this.ctime = new Timestamp(System.currentTimeMillis());
		this.un_useful = 0;
		this.useful = 0;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(String question_id) {
		this.question_id = question_id;
	}
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getUseful() {
		return useful;
	}
	public void setUseful(Integer useful) {
		this.useful = useful;
	}
	public Integer getUn_useful() {
		return un_useful;
	}
	public void setUn_useful(Integer un_useful) {
		this.un_useful = un_useful;
	}
	public Timestamp getCtime() {
		return ctime;
	}
	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}
	
	
	
	
}
