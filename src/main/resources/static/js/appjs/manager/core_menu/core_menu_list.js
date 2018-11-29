var prefix ="";
$(document).ready(function () {
	prefix = root+"/manager/core/CoreMenuController";
    load();
});
var load = function () {
    $('#menuTable').bootstrapTreeTable({
        id: 'coreMenuId',
        code: 'coreMenuId',
        parentCode: 'parentCoreMenuId',
        type: "GET", // 请求数据的ajax类型
        url: prefix + '/coreMenuListJsonDataNoPage', // 请求数据的ajax的url
        ajaxParams: {sort:'orderNum'}, // 请求数据的ajax的data属性
        expandColumn: '1',// 在哪一列上面显示展开按钮
        striped: true, // 是否各行渐变色
        bordered: true, // 是否显示边框
        expandAll: false, // 是否全部展开
        // toolbar : '#exampleToolbar',
        columns: [{
                title: '编号',
                field: 'coreMenuId',
                visible: false,
                align: 'center',
                valign: 'center',
                width: '5%'
            },{
                title: '名称',
                valign: 'center',
                field: 'menuName'
            },{
                title: '图标',
                field: 'icon',
                align: 'center',
                valign: 'center',
                width : '5%',
                formatter: function (item, index) {
                    return item.icon == null ? '' : '<i class="' + item.icon + ' fa-lg"></i>';
                }
            },{
                title: '类型',
                field: 'menuType',
                align: 'center',
                valign: 'center',
                width : '10%',
                formatter: function (item, index) {
                    if (item.menuType === '0') {
                        return '<span class="label label-primary">目录</span>';
                    }
                    if (item.menuType === '1') {
                        return '<span class="label label-success">菜单</span>';
                    }
                    if (item.menuType === '2') {
                        return '<span class="label label-warning">权限</span>';
                    }
                }
            },{
                title: 'url路径',
                valign: 'center',
                field: 'menuUrl'
            },{
                title: '权限标识',
                valign: 'center',
                field: 'permission'
            },{
                title: '操作',
                field: 'id',
                align: 'center',
                valign: 'center',
                formatter: function (item, index) {
                    var e = '<a class="btn btn-primary btn-sm '
                        + s_edit_h
                        + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                        + item.coreMenuId
                        + '\')"><i class="fa fa-edit"></i></a> ';
                    var p = '<a class="btn btn-primary btn-sm '
                        + s_add_h
                        + '" href="#" mce_href="#" title="添加下级" onclick="add(\''
                        + item.coreMenuId
                        + '\',\'addSub\')"><i class="fa fa-plus"></i></a> ';
                    var d = '<a class="btn btn-warning btn-sm '
                        + s_remove_h
                        + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                        + item.coreMenuId
                        + '\')"><i class="fa fa-remove"></i></a> ';
                    var s = '<a class="btn btn-primary btn-sm '
                        + s_view_h
                        + '" href="#" mce_href="#" title="查看" onclick="view(\''
                        + item.coreMenuId
                        + '\')"><i class="fa fa-search"></i></a> ';                   
                    return e + d + p + s;
                }
            }]
    });
}

function reLoad() {
    load();
}

function add(coreMenuId,actionType) {
    layer.open({
        type: 2,
        title: '增加菜单',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/coreMenuForm?coreMenuId=' + coreMenuId+"&actionType="+actionType // iframe的url
    });
}

function edit(coreMenuId) {
    layer.open({
        type: 2,
        title: '菜单修改',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/coreMenuForm?coreMenuId=' + coreMenuId+"&actionType=edit" // iframe的url
    });
}

function view(coreMenuId) {
    layer.open({
        type: 2,
        title: '菜单查看',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/coreMenuForm?coreMenuId=' + coreMenuId+"&actionType=view" // iframe的url
    });
}

function remove(coreMenuId) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/deleteCoreMenu?coreMenuId="+coreMenuId,
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

function reloadPermissions() {
    layer.confirm('确定要加载权限吗？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: root + "/manager/core/CoreManagerController/reLoadPermissions",
            type: "post",
            success: function (data) {
                if (data.success == 1) {
                    layer.msg(data.msg);
                } else {
                    layer.msg(data.msg);
                }
            }
        });
    })
}
