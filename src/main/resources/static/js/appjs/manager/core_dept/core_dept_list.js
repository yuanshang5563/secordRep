var prefix ="";
$(document).ready(function () {
	prefix = root+"/manager/core/CoreDeptController";
    load('');
});
var load = function (deptName) {
    $('#deptTable').bootstrapTreeTable({
        id: 'coreDeptId',
        code: 'coreDeptId',
        parentCode: 'parentCoreDeptId',
        type: "GET", // 请求数据的ajax类型
        url: prefix + '/coreDeptListJsonDataNoPage', // 请求数据的ajax的url
        ajaxParams: {deptName:deptName}, // 请求数据的ajax的data属性
        expandColumn: '1',// 在哪一列上面显示展开按钮
        striped: true, // 是否各行渐变色
        bordered: true, // 是否显示边框
        expandAll: false, // 是否全部展开
        // toolbar : '#exampleToolbar',
        columns: [{
                title: '编号',
                field: 'coreDeptId',
                visible: false,
                align: 'center',
                valign: 'center',
                width: '5%'
            },{
                title: '部门名称',
                valign: 'center',
                field: 'deptName'
            },{
                title: '部门代码',
                field: 'deptCode',
                align: 'center',
                valign: 'center'
            },{
                title: '排序号',
                field: 'orderNum',
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
                        + item.coreDeptId
                        + '\')"><i class="fa fa-edit"></i></a> ';
                    var p = '<a class="btn btn-primary btn-sm '
                        + s_add_h
                        + '" href="#" mce_href="#" title="添加下级" onclick="add(\''
                        + item.coreDeptId
                        + '\',\'addSub\')"><i class="fa fa-plus"></i></a> ';
                    var d = '<a class="btn btn-warning btn-sm '
                        + s_remove_h
                        + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
                        + item.coreDeptId
                        + '\')"><i class="fa fa-remove"></i></a> ';
                    var s = '<a class="btn btn-primary btn-sm '
                        + s_view_h
                        + '" href="#" mce_href="#" title="查看" onclick="view(\''
                        + item.coreDeptId
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
	var deptName = $("#deptName").val();
    load(deptName);
}

function add(coreDeptId,actionType) {
    layer.open({
        type: 2,
        title: '增加部门',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/coreDeptForm?coreDeptId=' + coreDeptId+"&actionType="+actionType // iframe的url
    });
}

function edit(coreDeptId) {
    layer.open({
        type: 2,
        title: '部门修改',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/coreDeptForm?coreDeptId=' + coreDeptId+"&actionType=edit" // iframe的url
    });
}

function view(coreDeptId) {
    layer.open({
        type: 2,
        title: '部门查看',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/coreDeptForm?coreDeptId=' + coreDeptId+"&actionType=view" // iframe的url
    });
}

function remove(coreDeptId) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/deleteCoreDept?coreDeptId="+coreDeptId,
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
