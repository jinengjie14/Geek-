$(function(){
    
  //创建课程的显示隐藏
//    $(".newcourse_form").click(function(){
//        $(".course_load").load("/u/course/input",function(){
//          $(".course_load").slideDown();
//          try {componentHandler.upgradeDom();}catch(e){};
//        });
//    });
    $(document).on("click", ".btnCancel", function() {
        $(".course_add").slideUp();
    });
    $(document).on("click", ".coufo_close", function() {
        $(".course_add").slideUp();
    });
  
  $(document).on("click", "[data-imgdel]", function(){
    $(this).parents("[data-img]").remove();
  });
  
//通用加载
  //******************
  $(document).on("click", "[data-load]", function() {
      $("[data-loadbox]").load($(this).data("load"), function(response){
          try {
              var json = JSON.parse(response);
              console.log(json);
              $.globalMessenger().post({message:json.message,type:"error",hideAfter:3});
              return;
          }catch(e){};
          $(this).show("fast");
      });
  });
//course提交
	$(document).on("click", "[name='submit_course']", function(e) {
      var url = $(this).parents("form").attr("action");
      var data = $(this).parents("form").serialize();
      $("[data-error]").html("");
      $.post(url, data, function(json) {
          var result = 1;
          $.each(json, function(field, message) {
          	console.log(json);
              if (field != "tourl") {
                  result = 2;
                  var name = "[data-error='" + field + "']";
                  if ($(name).length > 0) {
                      $(name).html(message);
                  } else {
                      Messenger().post({
                          message: message,
                          type: 'error',
                          hideAfter: 2,
                          showCloseButton: true
                      });
                  }
              }
          });
          console.log(json.tourl);
          if (result == 1 && json.tourl != "")
              window.location.href = json.tourl;
          
      }, "json");
      setTimeout(function() {
      	location.reload(true);
      }, 1000);
  });

	
	    //course删除
	    $("body").on("click", ".delete", function(event) {
	        event.preventDefault(); //使a自带的方法失效，即无法跳转到href中的URL(http://www.baidu.com)
	        $.post($(this).attr("href"), function(json) {

	            if (json.result == "success") {
	                $.globalMessenger().post({
	                    message: "操作成功",
	                    type: "error",
	                    hideAfter: 2
	                });
	            } else if (json.result == "error") {
	                $.globalMessenger().post({
	                    message: json.message,
	                    type: "error",
	                    hideAfter: 3
	                });
	            }
	            setTimeout(function() {
	            	location.reload(true);
	            }, 1000);
	        }, "json");
	    });
	    
	  //修改章节内容提交点击事件
	    $(document).on("click", "[name='lesson_edit']", function() {
	        var form = $(this).parents("form");
	        var courseid = $("[name='courseid']").data("courseid");
	        $.post(form.attr("action"), form.serialize(), function(json) {
	            if (json.result == "success") {
	            	console.log(json);
	                $.globalMessenger().post({
	                    message: json.result,
	                    type: "error",
	                    hideAfter: 2
	                });
	                setTimeout(function(){
	                	window.location.reload();
	               },1000);
	            }else if(json.result == "error"){
					$.globalMessenger().post({message:json.message,type:"error",hideAfter:3});
				}
	        }, "json");
	    });
	
	  //修改课程的提交点击事件
	    $(document).on("click", "[name='lessons_edit']", function() {
	        var form = $(this).parents("[name='form1']");
	        $.post(form.attr("action"), form.serialize(), function(json) {
	            if (json.result == "success") {
	                $.globalMessenger().post({
	                    message: json.result,
	                    type: "error",
	                    hideAfter: 2
	                });
	                setTimeout(function(){
	    	        	window.location.reload();
	    	       },1000);
	            } else if(json.result == "error"){
					$.globalMessenger().post({message:json.message,type:"error",hideAfter:3});
				}
	            setTimeout(function(){
		        	window.location.reload();
		       },1000);
	        }, "json");
	    });
	    
		//章节列表编辑模式按钮的点击
		$(document).on("click","[name='edit']",function(event){
			event.preventDefault();
			$(".back").load($(this).attr("data-url"), function(){ 
				$(".back").slideDown();
				if(!(typeof(componentHandler) == 'undefined')){
				      componentHandler.upgradeAllRegistered();
				  }
			}); 
			try {componentHandler.upgradeDom();}catch(e){};
		});
		
	    //章节列表退出编辑模式点击事件
	    $(document).on("click",".exit-edit",function(){
	    	window.location.reload();
		});
	    
	  //点击修改章节内容的详细内容
		$(".couvi-new").on("click",function(event){
			event.preventDefault();
			if($(this).attr("data-id")!=null){
				$(".page-wrapper").load($(this).attr("data-url"), function(){  
					$(".page-wrapper").slideDown();			
				});
			}else{
				$.globalMessenger().post({
	                message: "没有内容进行修改",
	                type: "error",
	                hideAfter: 2
	            });
			} 
		});
		
	  //章节列表删除按钮点击
		$("body").on("click",".del",function (event) {
			event.preventDefault();//使a自带的方法失效，即无法调整到href中的URL(http://www.baidu.com)
			$.post($(this).attr("href"),  function(json) {
		        if (json.result == "success") {
		        	console.log(json);
		        	$.globalMessenger().post({
		                message: "操作成功",
		                type: "error",
		                hideAfter: 2
		            });
		        }else if(json.result == "error"){
					$.globalMessenger().post({message:json.message,type:"error",hideAfter:3});
				}
		        var durl=$(".del").attr("data-id");
		        console.log(durl);
		        setTimeout(function(){
		        	window.location.href=durl;
		       },1000);
		        
		    }, "json");
		});
		
		/** 异步表单提交课程修改 **/
	    $(document).on("click", "[name='submit']", function(e) {
			var form = $(this).parents("form");
	        var url = form.attr("action");
	        var data = form.serialize();
	        $("[data-error]").html("");
	        post(form, url, data);
	    });
})