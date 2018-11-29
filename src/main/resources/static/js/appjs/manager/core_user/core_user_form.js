var prefix = ""
$(function() {
	prefix = root+"/manager/core/CoreUserController";	
	
	$("#birthday").datetimepicker({
		format: 'yyyy-mm-dd',
		language: 'zh-CN',
		weekStart: 1,
		todayBtn: 1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2, 
		clearBtn:true,
		forceParse: 0
	});
	
	var actionType = $("#actionType").val();
	if(actionType == "view"){
		$('#coreUserForm').find('input,textarea').attr('disabled',true);
	}else{
		$("#coreUserBtn").on('click',function(){$("#coreUserForm").submit();});
		validateRule();
	}
});

$.validator.setDefaults({
    submitHandler: function () {
    	coreUserFormSave();
    }
});

function getCheckedRoles() {
	var adIds = "";
	$("input:checkbox[name=coreRole]:checked").each(function(i) {
		if (0 == i) {
			adIds = $(this).val();
		} else {
			adIds += ("," + $(this).val());
		}
	});
	return adIds;
}

function coreUserFormSave() {
	$("#coreRoleIds").val(getCheckedRoles());
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/saveCoreUserForm",
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
        	realName: {
                required: true
            },
            userName: {
                required: true
            },
            password: {
                required: true
            },     
            comfirmPassword: {
            	equalTo:"#password"
            },
            sex: {
                required: true
            },
            mobile: {
                required: true
            },
            email: {
                required: true,
                email:true
            },
            deptName: {
                required: true
            },
            status: {
                required: true
            }             
        },
        messages: {
        	realName: {
                required: icon + "请输入姓名"
            },
            userName: {
                required: icon + "请输入用户名"
            },
            password: {
                required: icon + "请输入密码"
            },
            comfirmPassword: {
                equalTo: icon + "两次密码不一至，请核对！"
            },
            sex: {
                required: icon + "性别必选"
            },
            mobile: {
                required: icon + "请输入手机"
            },
            email: {
                required: icon + "请输入email",
                email: icon + "请确认email格式"
            },
            deptName: {
                required: icon + "部门必选"
            },
            status: {
                required: icon + "状态必选"
            }              
        }
    });
}

function openDept(){
	layer.open({
		type:2,
		title:"选择部门",
		area : [ '300px', '450px' ],
		content:root+"/manager/core/CoreDeptController/coreDeptTree"
	})
}

function loadDept(deptId,deptName){
	//if(deptCode.length == 6){
		$("#coreDeptId").val(deptId);
		$("#deptName").val(deptName);
	//}else{
	//	alert("请选择区县");
	//	$("#coreDeptId").val('');
	//	$("#deptName").val('');		
	//}
}