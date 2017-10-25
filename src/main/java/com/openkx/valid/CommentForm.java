package com.openkx.valid;

import org.hibernate.validator.constraints.NotEmpty;

public class CommentForm {
		@NotEmpty(message="评论内容不能为空！")
		private String context;
		
		
		
	
		public String getContext() {
			return context;
		}
		public void setContext(String context) {
			this.context = context;
		}
		
		
			
		
	}
