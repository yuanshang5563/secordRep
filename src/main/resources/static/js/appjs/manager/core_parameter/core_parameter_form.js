var prefix = ""
$(function() {
	prefix = root + "/manager/core/CoreParameterController";
	
	var actionType = $("#actionType").val();
	if(actionType == "view"){
		$('#coreParameterForm').find('input,textarea').attr('disabled',true);
	}else{
		$("#coreParameterBtn").on('click',function(){$("#coreParameterForm").submit();});
		validateRule();	
	}
});

$.validator.setDefaults({
    submitHandler: function () {
    	saveCoreParameterForm();
    }
});

function saveCoreParameterForm() {
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/saveCoreParameterForm",
		data : $('#coreParameterForm').serialize(),
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
    $("#coreParameterForm").validate({
        rules: {
        	paramName: {
                required: true
            },
            paramType: {
                required: true
            },
            paramCode: {
                required: true
            }
        },
        messages: {
        	paramName: {
                required: icon + "请输入参数名",
            },
            paramType: {
                required: icon + "请选择参数类型",
            },
            paramCode: {
                required: icon + "请输入参数代码",
            }
        }
    })
}