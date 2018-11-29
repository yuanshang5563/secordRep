var prefix = ""
$(function() {
	prefix = root+"/manager/core/CoreDictionariesController";	
	
	var actionType = $("#actionType").val();
	if(actionType == "view"){
		$('#coreDictionariesForm').find('input,textarea').attr('disabled',true);
	}else{
		$("#coreDictionariesBtn").on('click',function(){$("#coreDictionariesForm").submit();});
		validateRule();
	}
});

$.validator.setDefaults({
    submitHandler: function () {
    	saveCoreDictionariesForm();
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

function saveCoreDictionariesForm() {
	$("#coreRoleIds").val(getCheckedRoles());
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/saveCoreDictionariesForm",
		data : $('#coreDictionariesForm').serialize(),
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
    $("#coreDictionariesForm").validate({
        rules: {
        	realName: {
                required: true
            },
            userName: {
                required: true
            },
            dictGroupName: {
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
            dictGroupName: {
                required: icon + "请选择字典组"
            }           
        }
    });
}

function openGroup(){
	layer.open({
		type:2,
		title:"选择字典组",
		area : [ '300px', '450px' ],
		content:root+"/manager/core/CoreDictionariesGroupController/coreDictionariesGroupTree"
	})
}

function loadGroup(coreDictGroupId,dictGroupName){
	$("#coreDictGroupId").val(coreDictGroupId);
	$("#dictGroupName").val(dictGroupName);
}