var prefix = "";
$(function() {
	prefix = root+"/manager/core/CoreDeptController";
	
	var actionType = $("#actionType").val();
	if(actionType == "view"){
		$('#coreDeptForm').find('input,textarea').attr('disabled',true);
	}else{
		$("#coreDeptBtn").on('click',function(){$("#coreDeptForm").submit();});
		validateRule();	
	}
});

$.validator.setDefaults({
    submitHandler: function () {
    	coreDeptFormSave();
    }
});

function coreDeptFormSave() {
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/saveCoreDeptForm",
		data : $('#coreDeptForm').serialize(),
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
    $("#coreDeptForm").validate({
        rules: {
        	deptName: {
                required: true
            },
            deptCode: {
                required: true
            }
        },
        messages: {
        	deptName: {
                required: icon + "请输入部门名称",
            },
            deptCode: {
                required: icon + "请输入部门代码",
            }
        }
    })
}