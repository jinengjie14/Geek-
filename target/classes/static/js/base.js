function post(form, url, data) {
		$.post(url, data, function(json) {
			console.log(json);
			if(json.code == 200) {
				window.location.href = "/index";
			} else if(json.code == 412) {
				if(json.error != null) {
					$.each(json.error, function(field, message) {
						switch(message) {
						case "user.account.format.error":
							message = "没有此账号";
							break;
						case "user.passwd.isnull":
							message = "密码不能为空";
							break;
						case "user.passwd.length.error":
							message = "密码长度过短";
							break;
						case "user.account.isnull":
							message = "账号不能为空";
							break;
						}
						var name = form.find("[data-error='" + field + "']");
	                    name.html(message);
					});
				} else {
					var error = form.find("[data-error='password']");
					error.html("账号或密码错误");
				}
			} else {
				var error = form.find("[data-error='password']");
				error.html("账号或密码错误");
			}
        }, "json");
	}

$(function(){

    /** 异步表单提交 **/
    $(document).on("click", "[name='submit']", function(e) {
    	console.log(11111);
		var form = $(this).parents("form");
        var url = form.attr("action");
        var data = form.serialize();
        $("[data-error]").html("");
        post(form, url, data);
    });
});
//左侧菜单滑动
$(document).on("click", ".drawer_button", function(){//控制左侧滑的菜单
    // console.log("点击了");
    $(".layout_drawer").toggleClass("is-visible");
    $(".layout_obfuscator").toggleClass("is-visible");
    $(".main").toggleClass("pl0");
})
$(document).on("click", ".layout_obfuscator", function(){//点击蒙板隐藏侧滑和蒙板
    // console.log("点击了");
    $(".layout_drawer").toggleClass("is-visible");
    $(".layout_obfuscator").toggleClass("is-visible");
    $(".main").toggleClass("pl0");
})
$(document).on('click', '#top-search', function(e){//header搜索   
    e.preventDefault();
    $('.header').addClass('search-toggled');
    $('#top-search-wrap input').focus();
    $('#top-search-wrap input').on('click', function(e){e.stopPropagation();});
    $('html').one('click', function(){
        $('.header').removeClass('search-toggled');
        e.preventDefault();
    });
});
$('body').on('click', '.profile-menu > a', function(e){//小屏幕左侧滑用户banner展开收起
    e.preventDefault();
    $(this).parent().toggleClass('toggled');
    $(this).next().slideToggle(200);
});
