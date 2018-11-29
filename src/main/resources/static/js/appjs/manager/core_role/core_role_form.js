var prefix = ""
var coreMenuIds;
$(function() {
	prefix = root + "/manager/core/CoreRoleController";
	getMenuTreeData();
	
	var actionType = $("#actionType").val();
	if(actionType == "view"){
		$('#coreRoleForm').find('input,textarea').attr('disabled',true);
	}else{
		$("#coreRoleBtn").on('click',function(){$("#coreRoleForm").submit();});
		validateRule();	
	}
});

$.validator.setDefaults({
    submitHandler: function () {
    	coreRoleFormSave();
    }
});

function getAllSelectNodes() {
	var ref = $('#coreMenuTree').jstree(true); // 获得整个树

	coreMenuIds = ref.get_selected(); // 获得所有选中节点的，返回值为数组

	$("#coreMenuTree").find(".jstree-undetermined").each(function(i, element) {
		coreMenuIds.push($(element).closest('.jstree-node').attr("id"));
	});
}

function getMenuTreeData() {
	var actionType = $("#actionType").val();
	var queryUrl = root+"/manager/core/CoreMenuController/coreMenuTreeJson";
	if(actionType == 'edit' || actionType == 'view'){
		var coreRoleId = $("#coreRoleId").val();
		queryUrl = root+"/manager/core/CoreMenuController/coreMenuTreeJsonByRoleId?coreRoleId="+coreRoleId;
	}
	$.ajax({
		type : "GET",
		url : queryUrl,
		success : function(menuTree) {
			loadMenuTree(menuTree);
		}
	});
}

function loadMenuTree(menuTree) {
	$('#coreMenuTree').jstree({
		'core' : {
			'data' : menuTree
		},
		"checkbox" : {
			"three_state" : true,
		},
		"plugins" : [ "wholerow", "checkbox" ]
	});
	$('#coreMenuTree').jstree("open_all");

}

function coreRoleFormSave() {
	getAllSelectNodes();
	$('#coreMenuIds').val(coreMenuIds);
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/saveCoreRoleForm",
		data : $('#coreRoleForm').serialize(),
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
    $("#coreRoleForm").validate({
        rules: {
        	roleName: {
                required: true
            },
            role: {
                required: true
            }
        },
        messages: {
        	roleName: {
                required: icon + "请输入角色名称",
            },
            role: {
                required: icon + "请输入角色",
            }
        }
    })
}