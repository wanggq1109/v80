			 $(function(){
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
  			}//selectbox单个选择，所有选择的左移，右移
			 function moveright()
  		    {

  		        $('#fromSelectBox option:selected').remove().appendTo('#toSelectBox');
  		    }

  		    function moveleft()
  		    {
  		         $('#toSelectBox option:selected').remove().appendTo('#fromSelectBox');
  		    }
  		    
  		    function noOptions(thisObj){
  		        if(!$(thisObj+" option").length){
  		 //           alert("There are no options to select/move");
  		            return 0;
  		        }
  		        return 1;
  		    }
  		    function selectAll(thisObj){
  		        if(!noOptions("#"+thisObj)){return;}
  		        $('#'+thisObj+' option').each(function(){$(this).attr("selected","selected");});
  		         $('#fromSelectBox option:selected').remove().appendTo('#toSelectBox');     
  		    }
  		       
  		     function clearAll(thisObj){
  		 //       $('#'+thisObj).each(function(){$(this).find('option:selected').removeAttr("selected");});
  		 		if(!noOptions("#"+thisObj)){return;}
  		        	$('#'+thisObj+' option').each(function(){$(this).attr("selected","selected");});
  		        	$('#toSelectBox option:selected').remove().appendTo('#fromSelectBox');
  		    }
  		    // 报表    dialog查询
  		    function dialog_query(){
  		        $('#div_query').show();  		        
  		        $('#div_query').dialog({
  		            modal: true,
  		            iconCls:'icon-search'
  		        });
  		    }
  		// 报表    查询窗口的“取消”按钮的关闭窗口事件
  		    function query_cancel(){
  		       $('#div_query').dialog('close');
  		     }
  		    //统计分析   dialog查询
  		    function dialog_query_statistics(){
  		        $('#div_query_statistics').show();       
  		        $('#div_query_statistics').dialog({
  		            modal: true,
  		            iconCls:'icon-search',
  		             onClose:function(){
  		     		  $('#tt').show();
  		    		}
  		        });
  		    }
  		//统计分析    查询窗口的“取消”按钮的关闭窗口事件
  		   function query_cancel_statistics(){
  		       $('#div_query_statistics').dialog('close');
  		       $('#tt').show();
  		    }
  			