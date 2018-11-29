var prefix = ""
$(function() {
	prefix = root+"/manager/core/CoreUserController";	
	
	$("#coreUserBtn").on('click',function(){$("#coreUserForm").submit();});
	validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
    	saveResetPwd();
    }
});

function saveResetPwd() {
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/saveResetPwd",
		data : $('#coreUserForm').serialize(),
		async : false,
		error : function(request) {
			laryer.alert("连接错误");
		},
		success : function(data) {
			if (data.success == 1) {
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				layer.alert(data.msg)
			}
		}
	});
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#coreUserForm").validate({
        rules: {
            password: {
                required: true
            },     
            comfirmPassword: {
            	equalTo:"#password"
            }           
        },
        messages: {
            password: {
                required: icon + "请输入密码"
            },
            comfirmPassword: {
                equalTo: icon + "两次密码不一至，请核对！"
            }              
        }
    });
}