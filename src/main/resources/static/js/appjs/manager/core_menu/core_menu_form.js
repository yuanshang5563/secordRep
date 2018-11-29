var prefix = "";
$(function() {
	prefix = root+"/manager/core/CoreMenuController";
	
	var actionType = $("#actionType").val();
	if(actionType == "view"){
		$('#coreMenuForm').find('input,textarea').attr('disabled',true);
	}else{
		$("#coreMenuBtn").on('click',function(){$("#coreMenuForm").submit();});
		validateRule();			
	}
});

$.validator.setDefaults({
    submitHandler: function () {
    	coreMenuFormSave();
    }
});

function coreMenuFormSave() {
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix + "/saveCoreMenuForm",
		data : $('#coreMenuForm').serialize(),
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
    $("#coreMenuForm").validate({
        rules: {
        	menuName: {
                required: true
            },
            menuType: {
                required: true
            }
        },
        messages: {
        	menuName: {
                required: icon + "请输入菜单名称",
            },
            menuType: {
                required: icon + "菜单类型必选",
            }
        }
    })
}

function openIco(){
    layer.open({
        type: 2,
		title:'图标列表',
        content: root+'/LoginController/fontIcoList',
        area: ['480px', '90%'],
        success: function(layero, index){
            //var body = layer.getChildFrame('.ico-list', index);
            //console.log(layero, index);
        }
    });
}