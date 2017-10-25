$(document).ready(function () {
	/** gobal **/
	var check = 0;
	
    /** 全局消息提示 **/
    Messenger.options = {
        extraClasses: 'messenger-fixed messenger-on-top',
        theme: 'flat'
    }
	
	Dropzone.autoDiscover = false;
    $("#dropzone").dropzone({
    	url: "/upload",
    	method:"post",
    	maxFiles: 10,
    	createImageThumbnails: false,
        clickable: "#dropzone_btn",
    	addRemoveLinks: true,
    	init: function() {
            this.on("success", function(file,response) {
            	var current_date = getNowFormatDate();
            	console.dir(file);
            	console.dir(response);
            	if($(".lightbox").length == 0 || current_date != $(".ptb-title-box").first().find(":checkbox").data("date")){
            		var tpl = new Template($('#tpl2').html());
            		var s = tpl.render({
                	    name: file.name,
                	    id: response,
                	    date: current_date
                	});
                	$("#dropzone").after(s);
            	}else 
            	{
            		var tpl = new Template($('#tpl').html());
            		var s = tpl.render({
                	    name: file.name,
                	    id: response,
                	    date: current_date
                	    
                	});
                	$(".lightbox").first().prepend(s);
            	}
            	componentHandler.upgradeDom();
            });
            
            this.on("removedfile", function (file) {
            	$("[data-new='"+file.name+"']").first().find(".file_delete").click();
            });
        }
    });
    
    //修改文件名
    $(document).on('click',"[name='update_file']", function() {
    	var input = $(this).parents("[name='reset_form']").find(".name_class");
    	var id_input = $(this).parents("[name='reset_form']").find(".id_class");
    	var img_box = $(this).parents(".img-box");
    	var origin_input = img_box.find(".albumName :input");
    	var url = "/file/"+id_input.val()+"/update";
    	var name = input.val();
    	$.post(url, { "names": name,"status": "normal" },
    	   		function(data){
    				origin_input.val(name);
    				img_box.find(".albumName").show();
    				img_box.find("[name='reset_form']").hide();
    				window.location.reload();
    	 }).error(function(data){$.globalMessenger().post({message:"修改失败",type:"error",hideAfter:3});});
    });
    
    //文件删除
    $(document).on('click',".file_delete", function() {
    	var parent = $(this).parents(".img-box");
    	var url = "/file/"+parent.data("id")+"/delete";
    	$.post(url, {},
    	   		function(data){
    				parent.remove();
    	 }).error(function(data){$.globalMessenger().post({message:"删除失败",type:"error",hideAfter:3});});
    });
    
    //设置分享方式
    $("#scope_input").on("change",function(){
   		if($(this).val()=="公开"){$("[name='scope']").val("public")}
        	if($(this).val()=="保密"){$("[name='scope']").val("private")}
        	if($(this).val()=="指定好友"){$("[name='scope']").val("friends")}
    });
    
    /* 相册按日期批量选择 */
    
    $("[id^='checkbox']").click(function() {
        var checkboxid = $(this).attr("data-date") + "checkbox";
        console.log(checkboxid);
        if ($(this).prop("checked")) {
            $("[name^='" + checkboxid + "']").each(function(index, element) {
            	var labelNode = element.children[0];
                if(!labelNode.children[0].checked){
                	labelNode.children[0].click();
                }
            });
        } else {
            $("[name^='" + checkboxid + "']").each(function(index, element) {
                //element.children[0].MaterialCheckbox.uncheck();
            	var labelNode = element.children[0];
            	if(labelNode.children[0].checked){
            		labelNode.children[0].click();
                }
            });
        };
    });
    
    // 选中checkbox不消失
    $(".img-box label input").on('click', function(){
    	$(".album_operate").removeClass("close");
    	$(".album_operate").addClass("open");
    });
    
	// 指定好友
    // $("[name='friend_btn']").on('click', function(){
    //     $("[name='friend']").slideDown();
    // });
    $(document).on("click", "[name='shareway']", function(){
        if($("select[name='shareway'] option:selected").val()=="friend_btn"){
            $("[name='friend']").slideDown();   
        }
    })

	// 重命名
    $(document).on('click',"[name='reset_name']", function(){
      $(this).parents(".img-box").find(".albumName").hide();
      $(this).parents(".img-box").find("[name='reset_form']").show();
      $(this).parents(".img-box").find("[name='item_name']").select();
    });
    
    var dele_count = new Array();
	// 选中checkbox就出现头部的操作
    
    $(document).on("click", "input[type='checkbox'][name='fileids']",function(){
    	var file_id = $(this).parents(".img-box").attr("data-id");
    	var file_date = $(this).parents(".del-pic").attr("data-date");
        if(this.checked){
            $('#header').addClass('album-toggled');
            check = check + 1;
            var count = parseInt($.trim($("#photo-count").text())) + 1 ;
            $("#photo-count").text(count).trigger('change');
            dele_count.push(file_id);
            if($("[data-date="+file_date+"][class='del-pic'] input:checked").length == $("[data-date="+file_date+"][class='del-pic'] :input").length){
     			//$("[id^='checkbox'][data-date="+file_date+"]").click();
             }
        }else{
        	check = check - 1;
        	var count = parseInt($.trim($("#photo-count").text())) - 1 ;
        	$("#photo-count").text(count).trigger('change');
        	dele_count.remove(file_id);
        	if($("[data-date="+file_date+"][class='del-pic'] input:checked").length + 1 == $("[data-date="+file_date+"][class='del-pic'] :input").length){
        		$("[id^='checkbox'][data-date="+file_date+"]").parent().each(function (index,element){
        			element.checked = false;
        			//element.MaterialCheckbox.uncheck();
        		});
            }
        }
        console.log(dele_count);
        if($(check).length == 0){
        	$(".album_operate").removeClass("open");
        	$(".album_operate").addClass("close");
        }
    });
    
    //多文件删除
    $(document).on('click',"#tt2", function() {
          var FileController = "/file/delete";
			 //alert(JSON.stringify(dele_count));
			 var file_json = JSON.stringify(dele_count);
			 var self = $(this);
			 $.ajax({
				  type: "POST",
				  url: FileController,
				  data: {id:file_json},
				  dataType:"json",
				  success: function(msg){
					 for(var i=0;i<dele_count.length;i++)
					 {	
					 	 var self = $('[data-id="'+dele_count[i]+ '"]');
					 	 var title_box = self.parent().prev(".ptb-title-box");
						 self.remove();  
	   					 if(title_box.find("[id^='checkbox']").first().prop("checked")){
	   						 title_box.remove();
	   					 }
					 } 
					 
					 dele_count.splice(0,dele_count.length);
					 $("#photo-count").text(0).trigger('change');
					 if (parseInt($.trim($("#photo-count").text()))==0){
						$('#header').removeClass('album-toggled');
				   	  }
				  },
				  error: function(msg){
					  $.globalMessenger().post({message:"删除失败",type:"error",hideAfter:3});
				  }
			  });
     });
    
    // 添加到相册创建新相册出现创建相册
    $("[name='tt1_create']").on("click", function(){
        //$("[name='create_album']").slideDown();
    	$(".create-album-form").slideDown();
    });
    // 关闭创建相册
    $(".close-upload-form").on("click", function(){
        $(".create-album-form").slideUp();
    });
    // 添加到相册添加到已有相册出现弹出框
    $("[name='tt1_add']").on("click", function(){
    	/*$(".mdl-layout__content").animate({scrollTop:"0px"},500);   滚回最顶层选择相册*/   
    	$(".album_box").load("/get/album_box");
    	/*if(!(typeof(componentHandler) == 'undefined')){
    	      componentHandler.upgradeAllRegistered();
    	  }*/
    	//componentHandler.upgradeDom();
        $(".album_box").addClass("album_box_show");
    });
    $(document).on("click", "[name='btn_album_box_close']",function(){
        $(".album_box").removeClass("album_box_show");
    });
    
    // 添加图片到指定相册
    $(document).on("click", ".album_target",function(){
    	var array = new Array();
    	var album_id = $(this).data("id");
    	var addMedia_url = "/album/"+album_id+"/add/";
    	var checked = $("[class='lightbox-item'] [class='del-pic'] input:checked");
    	var len = checked.length;
    	checked.each(function(i){
    		//array.push($(this).parents(".img-box").data("id"));
    		$.post(addMedia_url+$(this).parents(".img-box").data("id"));
    		if(i == len -1 ){
    			//window.location.href="/album/"+album_id;
    		}
    	});
    });
	

    
    Array.prototype.remove = function(val) {
    	var index = this.indexOf(val);
    	if (index > -1) {
    		this.splice(index, 1);
    	}
    }; 
    
    // top chat
    $(document).on('click', '#chat_btn', function(e){
        e = e||event;
        $('#chat').toggleClass('toggled');
        $('#chat').on('click', function(e){e.stopPropagation();});
        $('body').one('click', function(e){
            $('#chat').removeClass('toggled');
            e.preventDefault();
        });
    });

    // top-menu background
    $(document).on('click', '#top-menu .mdl-navigation__link', function(e){
        e = e||event;
        $(this).toggleClass('open');
        e.stopPropagation();
        $('.dropdown-menu-lg').on('click', function(e){e.stopPropagation();});
        $('body').one('click', function(e){
            $('#top-menu .mdl-navigation__link').removeClass('open');
            $('.wrapper .mdl-menu__container').removeClass('is-visible');
            e.stopPropagation();
        });
    });
    
    $(document).on('click', ".close-upload", function() {
        $("#album_operate").hide();
    })

    $(document).on('click', ".del-pic", function() {
        $("#album_operate").slideDown();
    })
    
    function Template(tpl) {
        var
            fn,
            match,
            code = ['var r=[];\nvar _html = function (str) { return str.replace(/&/g, \'&amp;\').replace(/"/g, \'&quot;\').replace(/\'/g, \'&#39;\').replace(/</g, \'&lt;\').replace(/>/g, \'&gt;\'); };'],
            re = /\{\s*([a-zA-Z\.\_0-9()]+)(\s*\|\s*safe)?\s*\}/m,
            addLine = function (text) {
                code.push('r.push(\'' + text.replace(/\'/g, '\\\'').replace(/\n/g, '\\n').replace(/\r/g, '\\r') + '\');');
            };
        while (match = re.exec(tpl)) {
            if (match.index > 0) {
                addLine(tpl.slice(0, match.index));
            }
            if (match[2]) {
                code.push('r.push(String(this.' + match[1] + '));');
            }
            else {
                code.push('r.push(_html(String(this.' + match[1] + ')));');
            }
            tpl = tpl.substring(match.index + match[0].length);
        }
        addLine(tpl);
        code.push('return r.join(\'\');');
        fn = new Function(code.join('\n'));
        this.render = function (model) {
            return fn.apply(model);
        };
    }
    
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
        return currentdate;
    } 

});

