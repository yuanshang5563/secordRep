var prefix = ""
$(function() {
	prefix = root + "/manager/core/CoreUserController";
	getTreeData();
	load('');
	$('#jstree').on("changed.jstree", function(e, data) {
		if (data.selected == -1) {
			var opt = {
				query : {
					coreDeptId : '',
				}
			}
			$('#coreUserTable').bootstrapTable('refresh', opt);
		} else {
			var opt = {
				query : {
					coreDeptId : data.selected[0],
				}
			}
			$('#coreUserTable').bootstrapTable('refresh',opt);
		}

	});	
});

function load(coreDeptId) {
	var queryUrl = prefix + "/coreUserListJsonData?rnd=" + Math.random();
	$('#coreUserTable').bootstrapTable({
		method : 'GET', // 服务器数据的请求方式 get or post
		url : queryUrl, // 服务器数据的加载地址
		//showRefresh : true,
		// showToggle : true,
		// showColumns : true,
		//iconSize : 'outline',
		//toolbar : '#coreUserToolbar',
		striped : true, // 设置为true会有隔行变色效果
		dataType : "json", // 服务器返回的数据类型
		pagination : true, // 设置为true会在底部显示分页条
		// queryParamsType : "limit",
		// //设置为limit则会发送符合RESTFull格式的参数
		singleSelect : true, // 设置为true将禁止多选
		// contentType : "application/x-www-form-urlencoded",//发送到服务器的数据编码类型
		pageSize : 10, // 如果设置了分页，每页数据条数
		pageNumber : 1, // 如果设置了分布，首页页码
		// search : true, // 是否显示搜索框
		showColumns : false, // 是否显示内容下拉框（选择显示的列）
		sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者"server"
		pageList:[5,10],
		//height: 510,
		queryParams : function(params) {
			return {
				// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
				limit : params.limit,
				start : (params.offset / params.limit) + 1,
				userName : $('#userName').val(),
				coreDeptId : coreDeptId
			};
		},
		// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
		// queryParamsType = 'limit' ,返回参数必须包含
		// limit, offset, search, sort, order 否则, 需要包含:
		// pageSize, pageNumber, searchText, sortName,
		// sortOrder.
		// 返回false将会终止请求
		columns : [
			{
				checkbox : true
			},{
				field : 'coreUserId', // 列字段名
				title : '序号' // 列标题
			},{
				field : 'realName',
				title : '姓名'
			},{
				field : 'userName',
				title : '用户名'
			},{
				field : 'sex',
				title : '性别'				
			},{
				field : 'birthday',
				title : '生日'
			},{
				field : 'mobile',
				title : '手机'
			},{
				field : 'email',
				title : '邮箱'
			},
//			{
//				field : 'createdTime',
//				title : '创建时间'
//			},{
//				field : 'modifiedTime',
//				title : '修改时间'
//			},
			{
				field : 'status',
				title : '状态',
				align : 'center',
				formatter : function(value, row, index) {
					if (value == '0') {
						return '<span class="label label-danger">禁用</span>';
					} else if (value == '1') {
						return '<span class="label label-primary">正常</span>';
					}
				}
			},{
				title : '操作',
				field : 'id',
				align : 'center',
				formatter : function(value, row, index) {
					var e = '<a  class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
						+ row.coreUserId
						+ '\')"><i class="fa fa-edit "></i></a> ';
					var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
						+ row.coreUserId
						+ '\')"><i class="fa fa-remove"></i></a> ';
					var f = '<a class="btn btn-success btn-sm ' + s_resetPwd_h + '" href="#" title="重置密码"  mce_href="#" onclick="resetPwd(\''
						+ row.coreUserId
						+ '\')"><i class="fa fa-key"></i></a> ';
					var g = '<a class="btn btn-primary btn-sm ' + s_view_h + '" href="#" title="查看"  mce_href="#" onclick="view(\''
						+ row.coreUserId
						+ '\')"><i class="fa fa-search"></i></a> ';					
					return e + d + f + g;
				}
			} ]
	});

}
function reLoad() {
	$('#coreUserTable').bootstrapTable('refresh');
}
function add() {
	// iframe层
	layer.open({
		type : 2,
		title : '增加用户',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/coreUserForm?actionType=add'
	});
}

function edit(coreUserId) {
	layer.open({
		type : 2,
		title : '用户修改',
		maxmin : true,
		shadeClose : false,
		area : [ '800px', '520px' ],
		content : prefix + '/coreUserForm?coreUserId=' + coreUserId+"&actionType=edit" // iframe的url
	});
}

function view(coreUserId) {
	layer.open({
		type : 2,
		title : '用户查看',
		maxmin : true,
		shadeClose : false,
		area : [ '800px', '520px' ],
		content : prefix + '/coreUserForm?coreUserId=' + coreUserId+"&actionType=view" // iframe的url
	});
}

function remove(coreUserId) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/deleteCoreUser",
			type : "post",
			data : {
				'coreUserId' : coreUserId
			},
			success : function(res) {
				if (res.success == 1) {
					layer.msg(res.msg);
					reLoad();
				} else {
					layer.msg(res.msg);
				}
			}
		});
	})
}
function resetPwd(coreUserId) {
	layer.open({
		type : 2,
		title : '重置密码',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '400px', '360px' ],
		content : prefix + '/resetPwd?coreUserId=' + coreUserId // iframe的url
	});
}

function getTreeData() {
	$.ajax({
		type : "GET",
		url : root+"/manager/core/CoreDeptController/coreDeptTreeJson",
		success : function(tree) {
			loadTree(tree);
		}
	});
}
function loadTree(tree) {
	$('#jstree').jstree({
		'core' : {
			'data' : tree
		},
		"plugins" : [ "search" ]
	});
	$('#jstree').jstree().open_all();
}
