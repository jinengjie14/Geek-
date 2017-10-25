$(function(){
	// 选中checkbox不消失,并出现头部的操作
    $('input').on('ifChecked', function(event){
    	$(this).parents(".del-pic").css("opacity", "1");
    	$(".album_operate").addClass("open");
    });
    //取消复选框
	$('input').on('ifUnchecked', function(event){
		$(this).parents(".del-pic").css("opacity", "0");
	});
  //收起头部操作，取消所有复选框
  $(document).on("click", ".close-upload i", function(){
    $(".album_operate").removeClass("open");
    $('input').iCheck('uncheck');
  })
	// 重命名照片
    $(document).on('click',"[name='reset_name']", function(){
      $(this).parents(".img-box").find(".albumName").hide();
      $(this).parents(".img-box").find("[name='reset_form']").show();
      $(this).parents(".img-box").find("[name='item_name']").focus();
    });

})