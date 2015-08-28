<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>摄影作品类型管理</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css">
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<script>
	$(function() {
		tree_init();
		table_init("null");
	});

	function table_init(id) {
		var lastIndex;
		$('#type_table').datagrid({
			url : 'myjson/getphtotoTypeData.action?id=' + id,
			singleSelect : true,
			fitColumns : true,
			rownumbers : true,
			columns : [ [
				{
					field : 'code',
					title : 'CODE',
					width : 150,
					align : 'center',
					editor : 'text'
				},
				{
					field : 'name',
					title : '名称',
					width : 300,
					align : 'center',
					editor : 'text'
				},
				{
					field : 'opa',
					title : '操作',
					width : 150,
					align : 'center',
					formatter : function(value, row, index) {
						if (row.editing) {
							$('#type_table').datagrid('acceptChanges');
							var s = '<a class="edit" href="javascript:_save(' + index + ')">保存</a> ';
							var c = '<a class="del" href="javascript:_cancel(' + index + ')">取消</a>';
							return s + c;
						} else {
							var e = '<a class="edit" href="javascript:_edit(' + index + ')">编辑</a> ';
							var d = '<a class="del" href="javascript:_del(' + index + ')">删除</a>';
							return e + d;
						}
					}
				} ] ],
			toolbar : [
				{
					text : '新建类型',
					iconCls : 'icon-add',
					handler : function() {
						var node = $('#type_tree').tree('getSelected');
						$('#type_table').datagrid('endEdit', lastIndex);
						$('#type_table').datagrid('appendRow', {
							code : '',
							name : ''
						});
						var lastIndex = $('#type_table').datagrid('getRows').length - 1;
						$('#type_table').datagrid('beginEdit',lastIndex);
						$('#type_table').datagrid('selectRow',lastIndex);
					}
				}, '-', {
					text : '刷新',
					iconCls : 'icon-reload',
					handler : function() {
						window.location.reload();
					}
				} ],
			onBeforeEdit : function(index, row) {
				row.editing = true;
				updateActions();
			},
			onAfterEdit : function(index, row) {
				row.editing = false;
				updateActions();
			},
			onCancelEdit : function(index, row) {
				row.editing = false;
				updateActions();
			}
		});
	}

	function updateActions() {
		var rowcount = $('#type_table').datagrid('getRows').length;
		for ( var i = 0; i < rowcount; i++) {
			$('#type_table').datagrid('updateRow', {
				index : i,
				row : {
					opa : ''
				}
			});
		}
	}

	function _cancel(index) {
		$('#type_table').datagrid('cancelEdit', index);
	}

	function _edit(index) {
		$('#type_table').datagrid('beginEdit', index);
	}

	function _save() {
		$('#type_table').datagrid('acceptChanges');
		var id = $('#type_table').datagrid('getSelected').id;
		var name = $('#type_table').datagrid('getSelected').name;
		var code = $('#type_table').datagrid('getSelected').code;
		if (name == "") {
			var lastIndex = $('#type_table').datagrid('getRows').length - 1;
			$.messager.alert('错误', '类型不能为空', 'error');
			$('#type_table').datagrid('beginEdit', lastIndex);
		} else {
			if (id == "" || id == undefined) {
				var parent_id = 'null';
				if ($('#type_tree').tree('getSelected') != null) {
					parent_id = $('#type_tree').tree('getSelected').id;
				}
				$.ajax({
					type : "POST",
					url : "myjson/photoTypeadd.action",
					data : "parent_id=" + parent_id + "&name=" + name + "&code=" + code,
					success : function(msg, status) {
						if (msg == "1") {
							$.messager.alert('成功', '类型保存成功!', 'info', function() {
								$('#type_tree').tree('reload');
								$('#type_table').datagrid('reload');
							});
						} else if (msg == '2') {
							$.messager.alert('错误', '类型已存在', 'error');
							$('#type_table').datagrid('reload');
						} else if(msg == '3'){
							
							$.messager.alert('错误', 'code格式错误', 'error');
							$('#type_table').datagrid('reload');
							
						}else {
							$.messager.alert('错误', '保存失败', 'error');
							$('#type_table').datagrid('reload');
						}
						
						
					}
				});
			} else {
				parent_id = $('#type_table').datagrid('getSelected').parent_id;
				$.ajax({
					type : "POST",
					url : "myjson/photoTypeUpdate.action",
					data : "parent_id=" + parent_id + "&id=" + id + "&name=" + name + "&code=" + code,
					success : function(msg, status) {
						if (status == "success") {
							if (msg == '1') {
								$.messager.alert('成功', '类型保存成功!', 'info', function() {
									$('#type_tree').tree('reload');
									$('#type_table').datagrid('reload');
								});
							} else if (msg == '2') {
								$.messager.alert('错误', '类型已存在', 'error');
								$('#type_table').datagrid('reload');
							} else {
								$.messager.alert('错误', '保存失败', 'error');
								$('#type_table').datagrid('reload');
							}
						}
					}
				});
			}
		}
	}

	// 删除类型
	function del_type() {
		var node = $('#type_tree').tree('getSelected');
		if (node) {
			$.messager.confirm('删除', '确定删除？', function(r) {
				if (r) {
					var id = node.id;
					$.ajax({
						type : "POST",
						url : "myjson/photoTypeDelete.action",
						data : "id=" + id,
						success : function(msg, status) {
							if (msg == '0000') {
								$.messager.alert('成功', '类型删除成功!', 'info', function() {
									$('#type_tree').tree('reload');
									$('#type_table').datagrid('reload');
								});
							} else if (msg == '0001') {
								$.messager.alert('错误', '类型删除失败,请先删除子类型!', 'error');
							} else if (msg == '0002') {
								$.messager.alert('错误', '类型删除失败，请先删除与该类型相关的信息!', 'error');
							} else {
								$.messager.alert('错误', msg, 'error');
							}
						}
					});
				}
			});
		}
	}

	// 删除
	function _del() {
		var pid = $('#type_table').datagrid('getSelected');
		if (pid == 0) {
			$.messager.alert('提示', '请先删除该目录下面的子目录!', 'error');
		} else {
			$.messager.confirm('删除', '确定删除？', function(r) {
				if (r) {
					var id = $('#type_table').datagrid('getSelected').code;
					$.ajax({
						type : "POST",
						url : "myjson/photoTypeDelete.action",
						data : "id=" + id,
						success : function(msg, status) {
							if (msg == '0000') {
								$.messager.alert('成功', '类型删除成功!', 'info', function() {
									$('#type_tree').tree('reload');
									$('#type_table').datagrid('reload');
								});
							} else if (msg == '0001') {
								$.messager.alert('错误', '类型删除失败,请先删除子类型!', 'error');
							} else if (msg == '0002') {
								$.messager.alert('错误', '类型删除失败，请先删除与该类型相关的信息!', 'error');
							} else {
								$.messager.alert('错误', msg, 'error');
							}
						}
					});
				}
			});
		}
	}

	// 初始化树
	function tree_init() {
		$('#type_tree').tree({
			url : 'myjson/getphtotoTypeTree.action',
			onClick : function(node) {
				$(this).tree('toggle', node.target);
				var id = node.id;
				table_init(id);
			}
		});
	}
	// 数的刷新
	function reload() {
		var node = $('#type_tree').tree('getSelected');
		// 如果选中，刷新当前，否则，刷新整个树
		if (node) {
			$('#type_tree').tree('reload', node.target);
		} else {
			$('#type_tree').tree('reload');
		}
	}
</script>
</head>
<body class="easyui-layout">
	<div region="west" split="true"
		style="width:180px; overflow:hidden;">
		<div id="type_tree" fit="true"></div>
	</div>
	<div region="center" style="padding:2px;" border="true">
		<table id="type_table" idField="id" fit="true"></table>
	</div>
</body>
</html>