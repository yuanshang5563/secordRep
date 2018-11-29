var prefix = "";
$(function() {
	prefix = root+"/manager/core/CoreDictionariesGroupController";
	
	var actionType = $("#actionType").val();
	if(actionType == "view"){
		$('#groupForm').find('input,textarea').attr('disabled',true);
	}else{
		$("#groupBtn").on('click',function(){$("#groupForm").submit();});
		validateRule();	
	}
});

$.validator.setDefaults({
    submitHandler: function () {
    	saveCoreDictionariesGroupForm();
    }
});

function saveCoreDictionariesGroupForm() {
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/saveCoreDictionariesGroupForm",
		data : $('#groupForm').serialize(),
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
    $("#groupForm").validate({
        rules: {
        	dictGroupName: {
                required: true
            },
            dictGroupCode: {
                required: true
            }
        },
        messages: {
        	dictGroupName: {
                required: icon + "请输入字典组名称",
            },
            dictGroupCode: {
                required: icon + "请输入字典组代码",
            }
        }
    })
}