     $(function(){
		 //增加必填项
				$('.easyui-validatebox').validatebox({
					disabled:false,
					required:true,
					missingMessage:'必填项'
					});
				$('.easyui-combotree').combotree({
					required:true,
 					missingMessage:'必填项'					
 					});
 				$('.easyui-numberbox').numberbox({
 					required:true,
 					missingMessage:'必填项',
 					min:0
					});
		//中文下的确定和取消			
 			    $.messager.defaults={ok:"确定",cancel:"取消"};
			});	
     	//显示页数
     			function showPager(){
     				$('#demo_defalt').datagrid('getPager').pagination({
     					pageSize: 15,
     					showPageList:false,
     					showRefresh:false,
     					displayMsg:'当前显示从{from}到{to}条，共{total}条记录',
     					beforePageText:'第',
     					afterPageText:'页，共{pages}页'
     				});
     			}
     			function showPager2(){
     				$('#demo_data').datagrid('getPager').pagination({
     					pageSize: 10,
     					showPageList:false,
     					showRefresh:false,
     					displayMsg:'当前显示从{from}到{to}条，共{total}条记录',
     					beforePageText:'第',
     					afterPageText:'页，共{pages}页'
     				});
     			}
     			
     			function showPager3(){
     				$('#goodsinfo_dis').datagrid('getPager').pagination({
     					pageSize: 10,
     					showPageList:false,
     					showRefresh:false,
     					displayMsg:'当前显示从{from}到{to}条，共{total}条记录',
     					beforePageText:'第',
     					afterPageText:'页，共{pages}页'
     				});
     			}
     	
