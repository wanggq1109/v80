<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>员工信息</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
<script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.v2.0.3.js"></script>
<script type="text/javascript" src="<%=basePath%>js/uploadify/swfobject.js"></script>
<script type="text/javascript" src="<%=basePath%>js/item_selector/js/jquery.selectboxes.js"></script>
<script type="text/javascript" src="<%=basePath%>js/report_statistics_system.js"></script>

<script>
	$(function(){
        table_init();
    });

    function table_init(){
        $("#employee_table").datagrid({
            url:'myjson/eployeedataQuery.action',
            pagination:true,
            nowrap:false,
            striped: true,
            collapsible:true,
            loadMsg:"数据装载中,请稍后......",
            remoteSort:true,
			fitColumns: true,
            idField: "id",
            frozenColumns:[[
                 {field:"ck",checkbox:true}
                ]],
			columns:[[
				{field:"name",title:"姓名",width:80,align:"center"},
				{field:"position",title:"职位",width:100,align:"center"},
				{field:"intro",title:"简介",width:200,align:"center"},
				{field:"experience",title:"参与项目",width:200,align:"center"},
				{ field : "opa", title : "操作", width : 80, align : "center",
					formatter : function(value, row, index) {
						if(row.photoUrl){
							return "<a class='upload' href='javascript:add_brev(" + row.id + ")'>上传员工图片</a>";
						} else {
							return "<a class='upload' href='javascript:add_brev(" + row.id + ")'>上传员工图片</a>";
						}
						
					}
				} 
			]],
            toolbar:[{
                text:"新增",
                iconCls:"icon-add",
                handler:function(){
                    _add();
                }
            },"-",{
                text:"修改",
                iconCls:"icon-edit",
                handler:function(){
                    _edit();
                }
            },"-",{
                text:"删除",
                iconCls:"icon-remove",
                handler:function(){
                    _del();
                }
            },"-",{
                text:"刷新",
                iconCls:"icon-reload",
                handler:function(){
                    window.location.reload();
                }
            }]
        });
        showPager();
    }

    function showPager(){
        $("#employee_table").datagrid("getPager").pagination({
            pageSize: 20,
            showPageList:false,
            showRefresh:false,
            displayMsg:"当前显示从{from}到{to}条，共{total}条记录",
            beforePageText:"第",
            afterPageText:"页，共{pages}页"
        });
    }

	// 新增
	function _add(){
		dialog_add();
	}

    // dialog新增
	function dialog_add(){
        $("#div_add").show();
		$("#div_add").dialog({
			modal: true,
            iconCls:"icon-add",
			buttons:[{
				text:"保存",
				iconCls:"icon-ok",
				handler:function(){
                    $("#form_add").form("submit", {
                        url:"myjson/eployeedataAdd.action",
                        onSubmit: function(){
                            var title = $("#add_title_id").val();
                            var content = $("#add_content_id").val();
                            return $(this).form("validate");
                            
                        },
                        success:function(data){
                            if(data==1){
                                $.messager.alert("成功","保存成功!","info",function(){
                                    $("#div_add").dialog("close");
                                    $("#employee_table").datagrid("reload");
                                    $("#form_add").form("clear");
                                });
                            }else if(data==""||data==null){
                              	 $.messager.alert("成功","保存成功!","info",function(){
                                      
                                       window.location.reload();

                                   });
                              	
                              } else {
                                $.messager.alert("错误","保存失败!","error");
                                $("#div_add").dialog("close");
                                $("#form_add").form("clear");
                            }
                        }
                    });
				}
			},{
				text:"取消",
				iconCls:"icon-cancel",
				handler:function(){
					$("#div_add").dialog("close");
                    $("#form_add").form("clear");
				}
			}]
		});
	}
	
	// 修改
	function _edit(){
		var rows = $("#employee_table").datagrid("getSelected");
		var rowsLength = $("#employee_table").datagrid("getSelections");
        if(rowsLength.length>1){
            $.messager.alert("修改","只能选择一条记录","warning");
        } else if(rowsLength.length==0){
            $.messager.alert("修改","请选择要修改的记录","warning");
        } else {
            if(rows){
                $("#div_edit").show();
                $("#name_edit").val(rows.name);
                $("#position_edit").val(rows.position);
                $("#intro_edit").val(rows.intro);
                $("#experience_edit").val(rows.experience);
                id = rows.id;
                dialog_edit();
            }
        }
	}
	
	// dialog修改
	function dialog_edit(){
        $("#div_edit").show();
		$("#div_edit").dialog({
			modal: true,
            iconCls:"icon-edit",
			buttons:[{
				text:"保存",
				iconCls:"icon-ok",
				handler:function(){
					$("#form_edit").form("submit", {
                        url:"myjson/eployeedataEdit.action?id="+id,
                        onSubmit: function(){

                        },
                        success:function(data){
                            if(data==1){
                                $.messager.alert("成功","修改成功!","info",function(){
                                    $("#div_edit").dialog("close");
                                    $("#employee_table").datagrid("reload");
                                    $("#form_edit").form("clear");
                                });
                            } else {
                                $.messager.alert("错误","修改失败!","error");
                                $("#div_edit").dialog("close");
                                $("#form_edit").form("clear");
                            }
                        }
                    });
				}
			},{
				text:"取消",
				iconCls:"icon-cancel",
				handler:function(){
					$("#div_edit").dialog("close");
                    $("#form_edit").form("clear");
				}
			}]
		});
	}
	
	// 删除
	function _del(){
		var row = $("#employee_table").datagrid("getSelected");
		if (row){
			$.messager.confirm("删除", "确定删除？", function(r){
				if (r){
					var ids = [];
					var rows = $("#employee_table").datagrid("getSelections");
					for(var i=0;i<rows.length;i++){
						ids.push(rows[i].id);
					}
                    id = ids.join(",");
                    $.ajax({
                        type:"POST",
                        url:"myjson/eployeedataDelete.action",
                        data:"id="+id,
                        success:function(msg,status){
                            if("0000" == msg){
                                $.messager.alert("成功","删除成功!","info",function(){
                                    window.location.reload();
                                });
                            } else {
                                $.messager.alert("错误","删除失败!","error");
                            }
                        }
                    });
                    $("#employee_table").datagrid("clearSelections");
				}
			});
		} else {
			$.messager.alert("删除","请选择要删除的行","warning");
		}
	}
	
	function file_init(id){
	    $("#file_upload1").uploadify({
	        'uploader'       : '<%=basePath%>js/uploadify/uploadify.swf',
	        'script'         : 'myjson/eployeephotoUpload.action?id=rows.id',
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
	            if(fileObj.size>10240000){
	               $.messager.alert('错误',"上传的图片不能大于10兆，请重新选择",'error');
	               $("#btnUpload1"+id).attr("disabled","disabled");
	            }
	        },'onComplete'	 : function(event,queueId,fileObj,response,data){
	            var msg = response.substr(0,4);
	            if(msg =="0000"){
	                $.messager.alert("成功","上传成功!","info",function (){
	                     $("#url").val(response.substr(4));
	                     $("#photoworks_table").datagrid("reload");
						 $("#div_brev").dialog("close");
	                 });
	            }else{
	               $.messager.alert("错误",response,"error");
	            }
	        }
	    });
	}
	
	// 添加员工图片
	function add_brev(id) {
		file_init(id);
		$("#div_brev").show();
		$("#div_brev").dialog({
			modal : true,
			iconCls : "icon-add"
		});
	}
	
	function set_theme(){
        var rows = $("#employee_table").datagrid("getSelected");
		var rowsLength = $("#employee_table").datagrid("getSelections");
        if(rowsLength.length>1){
            $.messager.alert("主题设置","只能选择一条记录","warning");
        } else if(rowsLength.length==0){
            $.messager.alert("主题设置","请选择一条记录","warning");
        } else {
            if(rows){
                $("#div_set").show();
                $(".container").show();
                $("#employeeName").val(rows.name);
                _getlist1(rows.id);
                _getlist2(rows.id);

                _dialog(rows.id);
            }
        }
    }
    
    function _dialog(id){
        $("#div_set").dialog({
			modal: true,
            iconCls:"icon-add",
			buttons:[{
				id:"edituser_save",
				text:"保存",
				iconCls:"icon-ok",
				handler:function(){
                    var d="";
                    $("#toSelectBox option").each(function() {
                    d += $(this).val()+",";});
					$("#form_add").form("submit", {
						 url:"myjson/topicselected_edit.action?id="+id+"&topicId="+d,
                        onSubmit: function(){
                        $("#edituser_save").linkbutton("disable");
                        },
                        success:function(data){
                        $(".container").hide();
                            if(data==1){
                                $.messager.alert("成功","修改成功!","info",function(){
                                    $("#div_set").dialog("close");
                                    $("#employee_table").datagrid("reload");
                                    $("#form_set").form("clear");
                                });
                            } else {
                                $.messager.alert("错误","修改失败!","error");
                                $("#div_set").dialog("close");
                                $("#form_set").form("clear");
                            }
                        }
                    });
				}
			},{
				text:"取消",
				iconCls:"icon-cancel",
				handler:function(){
					$("#div_set").dialog("close");
                    $("#form_set").form("clear");
				}
			}]
		});
    }
    
    function _getlist1(id){
       $.ajax({
           type:"POST",
           url:"myjson/topicforselected.action",
           data:"id="+id,
           success:function(msg,status){
                var str=msg.split(',');
                $("#toSelectBox").html("");
                for(var i=0;i<str.length-1;i=i+2){
                   $("#toSelectBox").append("<option value='"+str[i+1]+"'>"+str[i]+"</option>");
                }
           }
       });
    }

    function _getlist2(id){
       $.ajax({
           type:"POST",
           url:"myjson/toselecttopic_list.action",
           data:"id="+id,
           success:function(msg,status){
                var str=msg.split(',');
                $("#fromSelectBox").html("");
                for(var i=0;i<str.length-1;i=i+2){
                   $("#fromSelectBox").append("<option value='"+str[i+1]+"'>"+str[i]+"</option>");
                }
           }
       });
    }
</script>
</head>
<body class="easyui-layout">
<div region="center" style="padding:2px 2px 2px 2px;" border="false">
	<table id="employee_table" fit="true"></table>
</div>
<div id="div_add" title="新增" style="padding:10px; width:700px; height:360px; display:none; overflow:hidden;">
    <form id="form_add" method="post">
	<table width="100%" cellpadding="5" cellspacing="5" border="0">
		<tr>
			<td align="right" width="60">姓名：</td>
			<td align="left"><input type="text" name="name" id="name" class="text" /></td>
			<td align="right" width="60">职位：</td>
			<td align="left"><input type="text" name="position" id="position" class="text" /></td>
		</tr>
		<tr>
			<td align="right">简介：</td>
			<td align="left" colspan="3">
				<textarea rows="3" cols="73" name="intro" id="intro"></textarea>
			</td>
		</tr>
		<tr>
			<td align="right">参与项目：</td>
			<td align="left" colspan="3">
				<textarea rows="3" cols="73" name="experience" id="experience"></textarea>
			</td>
		</tr>
	</table>
    </form>
</div>
<div id="div_edit" title="修改" style="padding: 10px; width:700px; height:360px; overflow: hidden; display:none;">
    <form id="form_edit" method="post">
	<table width="100%" cellpadding="5" cellspacing="5" border="0">
		<tr>
			<td align="right" width="60">姓名：</td>
			<td align="left"><input type="text" name="name" id="name_edit" class="text" /></td>
			<td align="right" width="60">职位：</td>
			<td align="left"><input type="text" name="position" id="position_edit" class="text" /></td>
		</tr>
		<tr>
			<td align="right">简介：</td>
			<td align="left" colspan="3">
				<textarea rows="3" cols="73" name="intro" id="intro_edit"></textarea>
			</td>
		</tr>
		<tr>
			<td align="right">参与项目：</td>
			<td align="left" colspan="3">
				<textarea rows="3" cols="73" name="experience" id="experience_edit"></textarea>
			</td>
		</tr>
	</table>
    </form>
</div>
<div id="div_brev" title="添加员工图片" style="padding:10px;width:620px;height:230px;display:none;overflow:hidden;">
	<form action="" method="post" name="suoluePic_form">
        <div id="uploadslp">
            <table width="100%" cellpadding="3" cellspacing="5">
                <tr>
                    <td align="right">请选择：</td>
                    <td align="left">
                        <div id="fileQueue1"></div>
                        <input type="text" id="url" name="url" value="" readonly="readonly" class="text_file" />
                        <input type="file" id="file_upload1" name="file_upload" />
            			<input type="button" id="btnUpload1" onclick="$('#file_upload1').uploadifyUpload()" value="上传">
                    </td>
                </tr>
            </table>
        </div>
	</form>
</div>
<div id="div_set" title="主题设置" style="padding:10px;width:550px;height:420px;display:none;overflow:hidden;">
    <form id="form_set" method="post" >
        <table>
	        <tr>
				<td align="right">员工名称：</td>
				<td align="left"><input type="text" name="name" id="employeeName" readonly="readonly" class="text_dis" style="padding-top:2px;"/></td>
			</tr>
        </table>
        <div class="container sys_container">
	        <div class="from_container" >
	            <table><tr><td height="20">可选主题</td></tr></table>
	            <span class="choose"> 
	            	<select id="fromSelectBox" multiple="multiple" name="selrole" style="width:200px; height: 200px;"></select>
	            </span>
	        </div>
	        <div class="buttons_container">
	            <a href = "#"><img src="<%=basePath%>js/item_selector/images/right.png" border="0" onclick="moveright()"/></a>
	            <br /><br />
	            <a href = "#"><img src="<%=basePath%>js/item_selector/images/left.png" border="0" onclick="moveleft()"/></a>
	            <br /><br />
	            <a href = "#"><img src="<%=basePath%>js/item_selector/images/gif-allright.gif" border="0" onclick="selectAll('fromSelectBox')"/></a>
	            <br /><br />
	            <a href = "#"><img src="<%=basePath%>js/item_selector/images/gif-allleft.gif" border="0" onclick="clearAll('toSelectBox')"/></a>
	        </div>
	        <div class="to_container">
	            <table><tr><td height="20">可用主题</td></tr></table>
	            <span class="choose"> 
	            	<select id="toSelectBox" multiple="multiple" style="width:200px; height: 200px;"></select>
	            </span>
	        </div>
        </div>
    </form>
</div>
</body>
</html>


