$(function(){
    table_init();
});

function table_init(){
    $('#demo_defalt').datagrid({
        url:'',
        pagination:true,
        nowrap:false,
        striped: true,
        collapsible:true,
        loadMsg:'数据装载中,请稍后......',
        sortName:'id',
        sortOrder:'asc',
        remoteSort:true,
		fitColumns: true,
        idField: 'id',
        frozenColumns:[[
             {field:'ck',checkbox:true}
            ]],
		columns:[[
			{field:'id',title:'ID',width:80,align:'center'},
			{field:'name',title:'姓名',width:100,align:'center'},
			{field:'templatDate',title:'时间',width:80,align:'center'}
		]],
        toolbar:[{
            text:'新增',
            iconCls:'icon-add',
            handler:function(){
                _add();
            }
        },'-',{
            text:'修改',
            iconCls:'icon-edit',
            handler:function(){
                _edit();
            }
        },'-',{
            text:'删除',
            iconCls:'icon-remove',
            handler:function(){
                _del();
            }
        },'-',{
            text:'刷新',
            iconCls:'icon-reload',
            handler:function(){
                window.location.reload();
            }
        }]
    });
    showPager();
}

function showPager(){
    $('#demo_defalt').datagrid('getPager').pagination({
        pageSize: 10,
        showPageList:false,
        showRefresh:false,
        displayMsg:'当前显示从{from}到{to}条，共{total}条记录',
        beforePageText:'第',
        afterPageText:'页，共{pages}页'
    });
}

// dialog新增
function dialog_add(){
    $('#div_add').show();
	$('#div_add').dialog({
		modal: true,
        iconCls:'icon-add',
		buttons:[{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
                $('#form_add').form('submit', {
                    url:'',
                    onSubmit: function(){
                        var id = $('#add_id').val();
                        var name = $('#add_name_id').val();
                        var templatDate = $('#add_templatDate_id').val();
                    },
                    success:function(data){
                        if(data==1){
                            $.messager.alert('成功','保存成功!','info',function(){
                                $('#div_add').dialog('close');
                                $('#demo_defalt').datagrid('reload');
                                $('#form_add').form('clear');
                            });
                        } else {
                            $.messager.alert('错误','保存失败!','error');
                            $('#div_add').dialog('close');
                            $('#form_add').form('clear');
                        }
                    }
                });
			}
		},{
			text:'取消',
			iconCls:"icon-cancel",
			handler:function(){
				$('#div_add').dialog('close');
                $('#form_add').form('clear');
			}
		}]
	});
}

// dialog修改
function dialog_edit(){
    $('#div_edit').show();
	$('#div_edit').dialog({
		modal: true,
        iconCls:'icon-edit',
		buttons:[{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
				$('#form_edit').form('submit', {
                    url:'demo_edit.action?id='+id,
                    onSubmit: function(){

                    },
                    success:function(data){
                        if(data==1){
                            $.messager.alert('成功','修改成功!','info',function(){
                                $('#div_edit').dialog('close');
                                $('#demo_defalt').datagrid('reload');
                                $('#form_edit').form('clear');
                            });
                        } else {
                            $.messager.alert('错误','修改失败!','error');
                            $('#div_edit').dialog('close');
                            $('#form_edit').form('clear');
                        }
                    }
                });
			}
		},{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$('#div_edit').dialog('close');
                $('#form_edit').form('clear');
			}
		}]
	});
}
// 新增
function _add(){
	dialog_add();
}
// 修改
function _edit(){
	var rows = $('#demo_defalt').datagrid('getSelected');
	var rowsLength = $('#demo_defalt').datagrid('getSelections');
    if(rowsLength.length>1){
        $.messager.alert('修改','只能选择一条记录','warning');
    } else if(rowsLength.length==0){
        $.messager.alert('修改','请选择要修改的记录','warning');
    } else {
        if(rows){
            $('#div_edit').show();
            $('#edit_name_id').val(rows.name);
            $('#edit_templatDate_id').val(rows.templatDate);
            id = rows.id;
            dialog_edit();
        }
    }
}
// 删除
function _del(){
	var row = $('#demo_defalt').datagrid('getSelected');
	if (row){
		$.messager.confirm('删除', '确定删除？', function(r){
			if (r){
				var ids = [];
				var rows = $('#demo_defalt').datagrid('getSelections');
				for(var i=0;i<rows.length;i++){
					ids.push(rows[i].id);
				}
                id = ids.join(',');
                $.ajax({
                    type:"POST",
                    url:"demo_del.action",
                    data:"id="+id,
                    success:function(msg,status){
                        if("success" == status){
                            $.messager.alert('成功','删除成功!','info',function(){
                                window.location.reload();
                            });
                        } else {
                            $.messager.alert('错误','删除失败!','error');
                        }
                    }
                });
                $('#demo_defalt').datagrid('clearSelections');
			}
		});
	} else {
		$.messager.alert('删除','请选择要删除的行','warning');
	}
}
