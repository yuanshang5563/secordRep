	/*<![CDATA[*/
		root = /*[[@{/}]]*/ '';
	/*]]>*/
var prefix ="";
$(document).ready(function () {
	prefix = root+"/userController";
});

function add() {
	// iframe层
	layer.open({
		type : 2,
		title : '增加用户',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/userForm?actionType=add'
	});
}