	$(function(){
	    $('.easyui-validatebox').validatebox({
			required:true,
			missingMessage:'必填项'
		});
	});	
	// 分页
    function showPager(_page){
        $('#datagrid').datagrid('getPager').pagination({
            pageSize: _page,
            showPageList:false,
            showRefresh:false,
            displayMsg:'当前显示从{from}到{to}条，共{total}条记录',
            beforePageText:'第',
            afterPageText:'页，共{pages}页'
        });
    }
    
    // 显示新增修改div
	function open_div(div_id,icon,type){
        $('#'+div_id).show();
        $('#'+div_id).dialog({
            modal: true,
            iconCls:icon,
            buttons:[{
                text:'保存',
	            iconCls:'icon-ok',
	            handler:function(){
            		if(type=="add"){
            			add_save();
            		} else {
            			edit_save();
            		}
	            }
	        },{
	            text:'取消',
	            iconCls:"icon-cancel",
	            handler:function(){
	                $('#'+div_id).dialog('close');
	            }
            }]
        });
    }
	
	// 显示查看div
	function open_view(div_id){
		$('#'+div_id).show();
		$('#'+div_id).dialog({
			modal: true,
			iconCls:"icon-view",
			buttons:[{
				text:'关闭',
				iconCls:"icon-cancel",
				handler:function(){
					$('#'+div_id).dialog('close');
				}
			}]
		});
	}
	
	// 保存
	function ajax_submit(_form_id,_url){
		$('#'+_form_id).form('submit', {
           	url:_url,
           	success:function(data){
               	if(data==1){
                   	$.messager.alert('成功','操作成功!','info',function(){
                       	window.location.reload();
                   	});
               	} else {
                   	$.messager.alert('错误','操作失败!'+data,'error');
               	}
           	}
       	});
	}
	
	// 选中一条记录
	function select_one(){
		var rows = $('#datagrid').datagrid('getSelected');
	    var rowsLength = $('#datagrid').datagrid('getSelections');
	    if(rowsLength.length>1){
	        $.messager.alert('提示','只能选择一条记录','warning');
	    } else if(rowsLength.length==0){
	        $.messager.alert('提示','请至少选择一条记录','warning');
	    } else {
	        if(rows){
	            return "one";
	        }
	    }
	}
	
	// 删除
	function _del(_url){
		var row = $('#datagrid').datagrid('getSelected');
		if (row){
			$.messager.confirm('删除', '确定删除？', function(r){
				if (r){
					var ids = [];
					var rows = $('#datagrid').datagrid('getSelections');
					for(var i=0;i<rows.length;i++){
						ids.push(rows[i].id);
					}
                    id = ids.join(',');
                    $.ajax({
                        type:"POST",
                        url:_url,
                        data:"id="+id,
                        success:function(msg,status){
                            if("success" == status){
                            	if(msg==1){
                                   	$.messager.alert('成功','删除成功!','info',function(){
                                       	window.location.reload();
                                   	});
                               	} else {
                                   	$.messager.alert('错误','删除失败!'+msg,'error');
                               	}
                            } else {
                                $.messager.alert('错误','删除失败!'+msg,'error');
                            }
                        }
                    });
                    $('#datagrid').datagrid('clearSelections');
				}
			});
		} else {
			$.messager.alert('提示','请至少选择一条记录','warning');
		}
	}