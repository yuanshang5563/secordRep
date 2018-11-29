var prefix = ""
$(function() {
	prefix = root + "/manager/core/CoreParameterController";
	load();
});

function load() {
	$('#coreParameterTable').bootstrapTable({
		method : 'get', // 服务器数据的请求方式 get or post
		url : prefix + "/coreParameterListJsonData", // 服务器数据的加载地址
		striped : true, // 设置为true会有隔行变色效果
		dataType : "json", // 服务器返回的数据类型
		pagination : true, // 设置为true会在底部显示分页条
		// queryParamsType : "limit",
		// //设置为limit则会发送符合RESTFull格式的参数
		singleSelect : true, // 设置为true将禁止多选
		//iconSize : 'outline',
		//toolbar : '#coreRoleTableToolbar',
		// contentType : "application/x-www-form-urlencoded",
		// //发送到服务器的数据编码类型
		pageSize : 10, // 如果设置了分页，每页数据条数
		pageNumber : 1, // 如果设置了分布，首页页码
		search : false, // 是否显示搜索框
		pageList:[5,10],
		showColumns : false, // 是否显示内容下拉框（选择显示的列）
		sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
		// "server"
		// queryParams : queryParams,
		// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
		// queryParamsType = 'limit' ,返回参数必须包含
		// limit, offset, search, sort, order 否则, 需要包含:
		// pageSize, pageNumber, searchText, sortName,
		// sortOrder.
		// 返回false将会终止请求
		//height: 510,
		queryParams : function(params) {
			return {
				// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
				limit : params.limit,
				start : (params.offset / params.limit) + 1,
				paramName : $('#paramName').val()
			};
		},		
		columns : [
			{ // 列配置项
				// 数据类型，详细参数配置参见文档http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/
				checkbox : true
			},{
				field : 'coreParamId', // 列字段名
				title : '序号' // 列标题
			},{
				field : 'paramName',
				title : '参数名'
			},{
				field : 'paramType',
				title : '参数类型'
			},{
				field : 'paramCode',
				title : '参数代码'
			},{
				field : 'paramValue',
				title : '参数值'
			},{
				title : '操作',
				field : 'id',
				align : 'center',
				formatter : function(value, row, index) {
					var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
							+ row.coreParamId
							+ '\')"><i class="fa fa-edit"></i></a> ';
					var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
							+ row.coreParamId
							+ '\')"><i class="fa fa-remove"></i></a> ';
					var f = '<a class="btn btn-primary btn-sm '+s_view_h+'" href="#" mce_href="#" title="查看" onclick="view(\''
							+ row.coreParamId
							+ '\')"><i class="fa fa-search"></i></a> ';					
					return e + d + f;
				}
			} ]
	});
}

function reLoad() {
	$('#coreParameterTable').bootstrapTable('refresh');
}

function add() {
	// iframe层
	layer.open({
		type : 2,
		title : '添加参数',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '560px' ],
		content : prefix + '/coreParameterForm?actionType=add' // iframe的url
	});
}

function edit(coreParamId) {
	layer.open({
		type : 2,
		title : '参数修改',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '560px' ],
		content : prefix + '/coreParameterForm?coreParamId=' + coreParamId+"&actionType=edit" // iframe的url
	});
}

function view(coreParamId) {
	layer.open({
		type : 2,
		title : '参数查看',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '560px' ],
		content : prefix + '/coreParameterForm?coreParamId=' + coreParamId+"&actionType=view" // iframe的url
	});
}

function remove(coreParamId) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/deleteCoreParameter",
			type : "post",
			data : {
				'coreParamId' : coreParamId
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
