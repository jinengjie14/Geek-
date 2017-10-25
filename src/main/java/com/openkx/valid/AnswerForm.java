package com.openkx.valid;

import org.hibernate.validator.constraints.NotEmpty;

public class AnswerForm {
		@NotEmpty(message="评论内容不能为空！")
		private String content;
		
		
		
	
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		
		
			
		
	}
