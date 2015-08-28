<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻管理</title>

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
<script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.v2.0.3.js"></script>
<script type="text/javascript" src="<%=basePath%>js/uploadify/swfobject.js"></script>
<script>
	$(function(){
        table_init();
    });
	
	function file_init(id){
	    $("#file_upload1").uploadify({
	        'uploader'       : '<%=basePath%>js/uploadify/uploadify.swf',
	        'script'         : 'myjson/newssuoluepic_upload.action',
	        'scriptData'     : {'id':id},
	        'cancelImg'      : '<%=basePath%>js/uploadify/cancel.png',
	        'folder'         : 'suolue_pic',
	        'queueID'        : 'fileQueue1',
	        'fileDataName'   : 'file_upload',
	        'auto'           : false,
	        'multi'          : false,
	        'simUploadLimit' : 1,
	        'buttonText'     : '浏览',
	        'height'         : 22,
	        'width'          : 70,
	        'buttonImg'      : '<%=basePath%>js/uploadify/browse.png',
	        'fileDesc'       : '请选择文件',
	        'fileExt'        : '*.jpg;*.png;*.gif;*.jpeg',
	        'sizeLimit'      : 0,
	        'sizeMax'        : 10240000,
	        'onSelect'       : function(e,queueId,fileObj){
	            //if(fileObj.size>10240000){
	            //   $.messager.alert('错误',"上传的图片不能大于10兆，请重新选择",'error');
	            //   $("#btnUpload"+id).attr('disabled','disabled');
	            //}
	        },'onComplete'	 : function(event,queueId,fileObj,response,data){
	            var msg = response.substr(0,4);
	            if(msg =="0000"){
	                $.messager.alert("成功","上传成功!","info",function (){
						 window.location.reload();
	                 });
	            }else{
	               $.messager.alert("错误",response,"error");
	            }
	        }
	    });
	}

    function table_init(){
        var postUrl ="myjson/newQuery.action";
        $("#news_table").datagrid({
            url:postUrl,
            pagination:true,
            nowrap:false,
            striped: true,
            collapsible:true,
            loadMsg:"数据装载中,请稍后......",
            sortName:"id",
            sortOrder:"asc",
            remoteSort:true,
            fitColumns: true,
            idField: "id",
            frozenColumns:[[{field:"ck",checkbox:true}]],
            columns:[[
                {field:"title",title:"标题",width:200,align:"center",
                	formatter : function(value, row, index) {
						if(value.length > 18){
							return value.substr(0, 18) + "...";
						} else {
							return value;
						}
					}},
                {field:"descr",title:"描述",width:200,align:"center",
                	formatter : function(value, row, index) {
               			if(value.length > 18){
							return value.substr(0, 18) + "...";
						} else {
							return value;
						}
					}},
                {field:"releaseTime",title:"时间",width:100,align:"center"},
                {field:"source",title:"来源",width:80,align:"center"},
                {field:"author",title:"作者",width:80,align:"center"},
                {field:"status",title:"发布状态",width:50,align:"center",
                	formatter : function(value, row, index) {
               			if(value == 1){
							return "<span style='color:#75B468;'>已发布</span>";
						} else {
							return "<span style='color:red;'>未发布</span>";
						}
					}},
				{field:"opa",title:"操作",width:80,align:"center",
					formatter : function(value, row, index) {
						if(row.url){
							return "<a class='add' href='javascript:add_brev(" + row.id + ")'>添加缩略图</a>";
						} else {
							return "<a class='add' href='javascript:add_brev(" + row.id + ")'>添加缩略图</a>";
						}
						
					}
				}
            ]],
            toolbar:[{
                text:"新增",iconCls:"icon-add",handler:function(){ _add(); }
            },"-",{
                text:"修改",iconCls:"icon-edit",handler:function(){ _edit(); }
            },"-",{
                text:"删除",iconCls:"icon-remove",handler:function(){ _del(); }
            },"-",{
                text:"发布",iconCls:"icon-reload", handler:function(){ _all_release(); }
            },"-",{
                text:"刷新",iconCls:"icon-reload", handler:function(){ window.location.reload(); }
            }]
        });
        showPager();
    }

    function showPager(){
        $("#news_table").datagrid("getPager").pagination({
            pageSize: 10,
            showPageList:false,
            showRefresh:false,
            displayMsg:"当前显示从{from}到{to}条，共{total}条记录",
            beforePageText:"第",
            afterPageText:"页，共{pages}页"
        });
    }
    
 // 发布
	function _all_release() {
		var row = $("#news_table").datagrid("getSelected");
		if (row) {
			$.messager.confirm("发布", "确定需要发布吗？", function(r) {
				if (r) {
					var ids = [];
					var rows = $("#news_table").datagrid("getSelections");
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					id = ids.join(",");
					$.ajax({
						type : "POST",
						url : "myjson/news_release.action",
						data : "id=" + id,
						success : function(msg, status) {
							if ("001" == msg) {
								$.messager.alert("成功", "发布成功!", "info", function() {
									window.location.reload();
								});
							} else {
								$.messager.alert("错误", "发布失败!", "error");
							}
						}
					});
					$("#photoworks_table").datagrid("clearSelections");
				}
			});
		} else {
			$.messager.alert("发布", "请选择要发布的行", "warning");
		}
	}
    
	// 添加缩略图
	function add_brev(id) {
		file_init(id);
		$("#div_brev").show();
		$("#div_brev").dialog({
			modal : true,
			iconCls : "icon-add"
		});
	}

	// 新增
	function _add(){
		window.location.href = "<%=basePath%>newsInfo_add.action";
	}
	// 修改
	function _edit(){
        parent.close_tabs("修改新闻");
		var rows = $("#news_table").datagrid("getSelected");
		var rowsLength = $("#news_table").datagrid("getSelections");
        if(rowsLength.length>1){
            $.messager.alert("修改","只能选择一条记录","warning");
        } else if(rowsLength.length==0){
            $.messager.alert("修改","请选择要修改的记录","warning");
        } else {
            if(rows){
                id = rows.id;
                window.location.href = "<%=basePath%>newsInfo_eidt.action?id="+id;
            }
        }
	}

     function _edit2(){
         var id = $("#news_table").datagrid("getSelected").id;
        $.ajax({
            type:"POST",
            url:"artVideo_release.action",
            data:"id="+id,
            success:function(msg,status){
               $("#news_table").datagrid("reload");
                $("#news_table").datagrid("clearSelections");
            }
        });
	}
	// 删除
	function _del(){
		var row = $("#news_table").datagrid("getSelected");
		if (row){
			$.messager.confirm("删除", "确定删除？", function(r){
				if (r){
					var ids = [];
                    var ids1 = [];
					var rows = $("#news_table").datagrid("getSelections");
					for(var i=0;i<rows.length;i++){
						ids.push(rows[i].id);
                        ids1.push(rows[i].url);
					}
                    id = ids.join(",");
                    var url = ids1.join(",");
                    $.ajax({
                        type:"POST",
                        url:"myjson/newdelete.action",
                        data:"id="+id+"&url="+url,
                        success:function(msg,status){
                            if(msg == "0000"){
                               $.messager.alert("成功","删除成功!","info",function(){
                                    window.location.reload();
                                });
                            }else{
                               $.messager.alert("错误",msg,"error");
                            }
                        }
                    });
                    $("#news_table").datagrid("clearSelections");
				}
			});
		} else {
			$.messager.alert("删除","请选择要删除的行","warning");
		}
	}

</script>
</head>
<body class="easyui-layout">
	<div region="center" style="padding:2px 2px 2px 2px;" border="false">
		<table id="news_table" fit="true"></table>
	</div>
	<div id="div_brev" title="添加缩略图" style="padding:10px;width:650px;height:300px;display:none;overflow:hidden;">
		<form action="" method="post" name="suoluePic_form">
	        <div id="uploadslp">
	            <table width="100%" cellpadding="3" cellspacing="5">
	                <tr>
	                    <td align="right">请选择缩略图：</td>
	                    <td align="left">
	                        <div id="fileQueue1"></div>
	                        <input type="text" id="url" name="url" value="" readonly="readonly" class="text_file" />
	                        <input type="file" id="file_upload1" name="file_upload" />
	                    </td>
	                    <td><input type="button" id="btnUpload1" onclick="$('#file_upload1').uploadifyUpload()" value="上传"></td>
	                </tr>
	            </table>
	        </div>
		</form>
	</div>
</body>
</html>


