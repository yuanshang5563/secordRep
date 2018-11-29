var prefix ="";
$(document).ready(function () {
	prefix = root+"/manager/core/CoreDictionariesGroupController";
    load('');
});
var load = function (dictGroupName) {
    $('#groupTable').bootstrapTreeTable({
        id: 'coreDictGroupId',
        code: 'coreDictGroupId',
        parentCode: 'parentCoreDictGroupId',
        type: "GET", // 请求数据的ajax类型
        url: prefix + '/coreDictionariesGroupListJsonDataNoPage', // 请求数据的ajax的url
        ajaxParams: {dictGroupName:dictGroupName}, // 请求数据的ajax的data属性
        expandColumn: '1',// 在哪一列上面显示展开按钮
        striped: true, // 是否各行渐变色
        bordered: true, // 是否显示边框
        expandAll: false, // 是否全部展开
        // toolbar : '#exampleToolbar',
        columns: [{
                title: '编号',
                field: 'coreDictGroupId',
                visible: false,
                align: 'center',
                valign: 'center',
                width: '5%'
            },{
                title: '字典组名称',
                valign: 'center',
                field: 'dictGroupName'
            },{
                title: '字典组代码',
                field: 'dictGroupCode',
                align: 'center',
                valign: 'center'
            },{
                title: '操作',
                field: 'id',
                align: 'center',
                valign: 'center',
                formatter: function (item, index) {
                    var e = '<a class="btn btn-primary btn-sm '
                        + s_edit_h
                        + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                        + item.coreDictGroupId
                        + '\')"><i class="fa fa-edit"></i></a> ';
                    var p = '<a class="btn btn-primary btn-sm '
                        + s_add_h
                        + '" href="#" mce_href="#" title="添加下级" onclick="add(\''
                        + item.coreDictGroupId
                        + '\',\'addSub\')"><i class="fa fa-plus"></i></a> ';
                    var d = '<a class="btn btn-warning btn-sm '
                        + s_remove_h
                        + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                        + item.coreDictGroupId
                        + '\')"><i class="fa fa-remove"></i></a> ';
                    var s = '<a class="btn btn-primary btn-sm '
                        + s_view_h
                        + '" href="#" mce_href="#" title="查看" onclick="view(\''
                        + item.coreDictGroupId
                        + '\')"><i class="fa fa-search"></i></a> ';                    
                    return e + d + p + s;
                }
            }]
    });
}

function reLoad() {
    load('');
}

function queryTable() {
	var dictGroupName = $("#dictGroupName").val();
    load(dictGroupName);
}

function add(coreDictGroupId,actionType) {
    layer.open({
        type: 2,
        title: '增加字典组',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/coreDictionariesGroupForm?coreDictGroupId=' + coreDictGroupId+"&actionType="+actionType // iframe的url
    });
}

function edit(coreDictGroupId) {
    layer.open({
        type: 2,
        title: '字典组修改',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/coreDictionariesGroupForm?coreDictGroupId=' + coreDictGroupId+"&actionType=edit" // iframe的url
    });
}

function view(coreDictGroupId) {
    layer.open({
        type: 2,
        title: '字典组查看',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/coreDictionariesGroupForm?coreDictGroupId=' + coreDictGroupId+"&actionType=view" // iframe的url
    });
}

function remove(coreDictGroupId) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/deleteCoreDictionariesGroup?coreDictGroupId="+coreDictGroupId,
            type: "post",
            success: function (data) {
                if (data.success == 1) {
                    layer.msg(data.msg);
                    reLoad();
                } else {
                    layer.msg(data.msg);
                }
            }
        });
    })
}
