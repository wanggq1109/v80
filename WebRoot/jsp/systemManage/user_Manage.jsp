<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String adminName = (String)request.getAttribute("authName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css"/>
<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/item_selector/js/jquery.selectboxes.js"></script>
<script type="text/javascript" src="<%=basePath%>js/simple.js"></script>
<script type="text/javascript" src="<%=basePath%>js/report_statistics_system.js"></script>
<script>
	$(function(){
        table_init();
    });

    function table_init(){
        var postUrl ="user_getDefaltJsonList.action";
        $("#demo_defalt").datagrid({
            url:postUrl,
            height:300,
            pagination:true,
            nowrap:false,
            striped: true,
            collapsible:true,
            loadMsg:"数据装载中,请稍后......",
           	sortName: "userAccount",
            sortOrder:"asc",
            remoteSort: false,
            fitColumns: true,
            idField: "id",
            frozenColumns:[[{field:"ck",checkbox:true}]],
            columns:[[
                {field:"userAccount",title:"用户账号",width:100,sortable:true,align:"center",
				formatter:function(value,row,index){
				return '<div style="text-align:left;">'+row.userAccount+'</div>';
				}},
                {field:"userName",title:"用户名称",width:100,sortable:true,align:"center",
				formatter:function(value,row,index){
				return '<div style="text-align:left;">'+row.userName+'</div>';
				}},
            ]],
            toolbar:[{
                text:"新增",iconCls:"icon-add",handler:function(){ _add();}
            },"-",{
                text:"删除",iconCls:"icon-remove",handler:function(){ _del();}
            },"-",{
                text:"修改",iconCls:"icon-edit",handler:function(){_editInfo();}
            },"-",{
                text:"密码修改",iconCls:"icon-edit",handler:function(){_edit();}
            },"-",{
                text:"角色设置",iconCls:"icon-add", handler:function(){_adduser();}
            },"-",{
                text:"刷新",iconCls:"icon-reload", handler:function(){ window.location.reload(); }
            }],
            onClickRow:function(rowIndex,row){
				$("#demo_defalt").datagrid("clearSelections");
				$("#demo_defalt").datagrid("selectRecord",row.id);
			}	
        });
        //}
        showPager();
    }

     //公司
    function chaicompany_init(){
      $("#userDept").combotree({
        url:"myjson/user_getJsonList.action",
        width:151,
        onClick:function(node){
            if(node.attributes!=undefined){
            }
             document.getElementById("userDept1").value=node.id;
        }
      });
   }
   
	// 新增
	function _add(){
	    chaicompany_init();
        $("#jbxx").show();       
        $("#jbxx").dialog({
			modal: true,
            iconCls:"icon-add",
			buttons:[{
				id:"add_save",
				text:"保存",
				iconCls:"icon-ok",				
				handler:function(){
                     if ($("#userAccount").validatebox("isValid") &&$("#userName").validatebox("isValid") &&
                            $("#userPassword").validatebox("isValid") && $("#userPassword1").validatebox("isValid")
                          
                            ) 
                            if($("#userPassword1").val()!=$("#userPassword").val()){
                               $("#userDesc").hide();
                               $.messager.alert("警告", "密码不一致，请重新输入", "warning",
                                function alert1(){
                                  $("#userDesc").show();
       						      $("#userDesc option").attr("selected",true);
                                }
                               );
                               return false;
                            }
                            else
                            {           
                               $("#form_jbxx").form("submit", {
                                    url:"user_Add.action",
                                    onSubmit: function(){                                  
                                    $("#add_save").linkbutton("disable");
                                    },
                                    success:function(msg,status){
                                       $("#userDesc").hide();
                                        if(msg==0000){
                                            $.messager.alert("成功","新增成功!","info",function(){ 
                                                $("#jbxx").dialog("close");
                                                $("#demo_defalt").datagrid("reload");
                                                $("#form_jbxx").form("clear");
                                            });
                                        } else {
                                             $.messager.alert("错误","用户已存在!","error");
 //                                            $("#add_save").linkbutton("enable");  
                                             $("#jbxx").dialog("close");
                                             $("#form_jbxx").form("clear");
                                        }
                                    }
                                });  

                      }
                     /*
                      else {
                          $.messager.alert("警告", "有必填项未填或者填写错误", "error");
                            }
                           */

				}
			},{
				text:"取消",
				iconCls:"icon-cancel",
				handler:function(){
					$("#jbxx").dialog("close");
                    $("#form_jbxx").form("clear");
				}
			}]
		});
    }
    
      //公司
    function chaicompany_init1(){
      $("#userDept_edit").combotree({
        url:"myjson/user_getJsonList.action",
        width:151,
        onClick:function(node){
            if(node.attributes!=undefined){
            }
             document.getElementById("userDept1_edit").value=node.id;
        }
      });
   }
    
    // 信息修改
    function _editInfo(){
        var rows = $("#demo_defalt").datagrid("getSelected");
		var rowsLength = $("#demo_defalt").datagrid("getSelections");
        if(rowsLength.length>1){
            $.messager.alert("修改","只能选择一条记录","warning");
        } else if(rowsLength.length==0){
            $.messager.alert("修改","请选择一条记录","warning");
        } else {
            if(rows){
                var id = rows.id;
                $("#userAccount_edit").val(rows.userAccount);
                $("#userName_edit").val(rows.userName);
                $("#userDesc_edit").val(rows.userDesc);
                
                
                Info_edit(id);
            }
        }
    
    }
		
	// 密码修改
	function _edit(){
		var rows = $("#demo_defalt").datagrid("getSelected");
		var rowsLength = $("#demo_defalt").datagrid("getSelections");
        if(rowsLength.length>1){
            $.messager.alert("修改","只能选择一条记录","warning");
        } else if(rowsLength.length==0){
            $.messager.alert("修改","请选择一条记录","warning");
        } else {
            if(rows){
                var id = rows.id;
                $("#edit_userAccount").val(rows.userAccount);
                $("#edit_userName").val(rows.userName);
                pwd_edit(id);
            }
        }
	}
	
	function Info_edit(id){
	    $("#yhxx").show();
	  $("#userDesc_edit").show();
       // $("#eidt_issys option").attr("selected",true);
       
        $("#yhxx").dialog({
			modal: true,
            iconCls:"icon-edit",
			buttons:[{
				id:"edit_save1",
				text:"保存",
				iconCls:"icon-ok",
				handler:function(){
                     if ($("#userName_edit").validatebox("isValid") 
                            ) {								
                               $("#form_yhxx").form("submit", {
                                    url:"user_UptInfo.action?id="+id,
                                    onSubmit: function(){
                                      $("#edit_save1").linkbutton("disable");
                                    },
                                    success:function(msg,status){        		                
                                        if(msg==1000){
                                        	$("#userDesc_edit").hide();   
                                            $.messager.alert("成功","修改成功!","info",function(){
                                                $("#yhxx").dialog("close");
                                                $("#demo_defalt").datagrid("reload");
                                                $("#form_yhxx").form("clear");
                                            });
                                        } else {
                                            $.messager.alert("错误","修改失败!","error");
//                                             $("#edit_save1").linkbutton("enable");  
                                            $("#yhxx").dialog("close");
                                            $("#form_yhxx").form("clear");
                                        }
                                    }
                                });
                            }
				  }
			},{
				text:"取消",
				iconCls:"icon-cancel",
				handler:function(){
					$("#yhxx").dialog("close");
                    $("#form_yhxx").form("clear");
				}
			}]
		});
	}

    function pwd_edit(id)
    {
       $("#jbxx_edit").show();
       $("#jbxx_edit").dialog({
			modal: true,
            iconCls:"icon-edit",
			buttons:[{
				id:"edit_save",
				text:"保存",
				iconCls:"icon-ok",
				handler:function(){
                     if ($("#edit_userPassword").validatebox("isValid") && $("#edit_userPassword1").validatebox("isValid")
                            ) {

                            if($("#edit_userPassword1").val()!=$("#edit_userPassword").val()){
                               $.messager.alert("警告", "密码不一致，请重新输入", "error");
                               return false;
                            } else
                            {
                               $("#form_jbxx_edit").form("submit", {
                                    url:"user_Upt.action?id="+id,
                                    onSubmit: function(){
                                    $("#edit_save").linkbutton("disable");
                                    },
                                    success:function(msg,status){
                                        if(msg==1000){
                                            $.messager.alert("成功","修改成功!","info",function(){
                                                $("#jbxx_edit").dialog("close");
                                                $("#demo_defalt").datagrid("reload");
                                                $("#form_jbxx_edit").form("clear");
                                            });
                                        } else {
                                            $.messager.alert("错误","修改失败!","error");
 //                                            $("#edit_save").linkbutton("enable");  
                                            $("#jbxx_edit").dialog("close");
                                            $("#form_jbxx_edit").form("clear");
                                        }
                                    }
                                });
                            }

                      }
                      /* else {
                          $.messager.alert("警告", "有必填项未填或者填写错误", "error");
                            }*/

				}
			},{
				text:"取消",
				iconCls:"icon-cancel",
				handler:function(){
					$("#jbxx_edit").dialog("close");
                    $("#form_jbxx_edit").form("clear");
				}
			}]
		});
    }


	// 删除
	function _del(){
		var row = $("#demo_defalt").datagrid("getSelected");
		if (row){
			$.messager.confirm("删除", "确定删除？", function(r){
				if (r){
					var ids = [];
					var rows = $("#demo_defalt").datagrid("getSelections");
					for(var i=0;i<rows.length;i++){
						ids.push(rows[i].id);
					}
                    var id = ids.join(",");
                    $.ajax({
                        type:"POST",
                        url:"user_Del.action",
                        data:"id="+id,
                        success:function(msg,status){
                            if(msg == "0000"){
                               $.messager.alert("成功","删除成功!","info",function(){
                                    //window.location.reload();
                                    $("#demo_defalt").datagrid("reload");
                                });
                            }else{
                               $.messager.alert("错误",msg,"error");
                            }
                        }
                    });
                    $("#demo_defalt").datagrid("clearSelections");
				}
			});
		} else {
			$.messager.alert("删除","请选择要删除的行","warning");
		}
	}
//角色设置
    function _adduser(){
        var rows = $("#demo_defalt").datagrid("getSelected");
		var rowsLength = $("#demo_defalt").datagrid("getSelections");
        if(rowsLength.length>1){
            $.messager.alert("角色设置","只能选择一条记录","warning");
        } else if(rowsLength.length==0){
            $.messager.alert("角色设置","请选择一条记录","warning");
        } else {
            if(rows){
                $("#div_add").show();
                $(".container").show();
                $("#name").val(rows.userName);
                _getlist(rows.id);
                _getlist2(rows.id);

                edituser_dialog(rows.id);
            }
        }
    }

    function edituser_dialog(id){
        $("#div_add").dialog({
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
                        url:"user_role_edit.action?id="+id+"&role="+d,
                        onSubmit: function(){
                        $("#edituser_save").linkbutton("disable");
                        },
                        success:function(data){
                        $(".container").hide();
                            if(data==1){
                                $.messager.alert("成功","修改成功!","info",function(){
                                    $("#div_add").dialog("close");
                                    $("#demo_defalt").datagrid("reload");
                                    $("#form_add").form("clear");
                                });
                            } else {
                                $.messager.alert("错误","修改失败!","error");
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

    function _getlist(id){
       $.ajax({
           type:"POST",
           url:"getrolelist.action",
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
           url:"getrolelist2.action",
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
	<table id="demo_defalt" fit="true"></table>
</div>
<div id="div_add" title="角色设置" style="padding:10px;padding-left:18px;padding-top:18px;width:550px;height:420px;display:none;overflow:hidden;">
    <form id="form_add" method="post" >
        <table>
	        <tr>
				<td align="right">用户名称：</td>
				<td align="left"><input type="text" name="name" id="name" readonly="readonly" class="text_dis" style="padding-top:2px;"/></td>
			</tr>
        </table>
        <div class="container sys_container">
	        <div class="from_container" >
	            <table><tr><td height="30">可选角色</td></tr></table>
	            <span class="choose"> 
	            	<select id="fromSelectBox" multiple="multiple" name="selrole"></select>
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
	            <table><tr><td height="30">可用角色</td></tr></table>
	            <span class="choose"> 
	            	<select id="toSelectBox" multiple="multiple"></select>
	            </span>
	        </div>
        </div>
    </form>
</div>
<div id="jbxx" title="新增用户" style="padding:20px;width:570px;height:350px;display:none;overflow:hidden;">
    <form id="form_jbxx" method="post" >
      <table width="100%" align="center" cellpadding="5" cellspacing="5" >
          <tr>
              <td align="right">用户账号：</td>
              <td align="left" ><input type="text" id="userAccount" name="userAccount" value="${k_user.userAccount}" class="easyui-validatebox " /></td>
          </tr>
          <tr>
              <td align="right">用户名称：</td>
              <td align="left"><input type="text" id="userName" name="userName" value="${k_user.userName}" class="easyui-validatebox " /></td>
          </tr>
          <tr>
              <td align="right">密码：</td>
              <td align="left"><input type="password" id="userPassword" name="userPassword" maxlength="8" value="${k_user.userPassword}" class="easyui-validatebox " /></td>
          </tr>
          <tr>
              <td align="right">确认密码：</td>
              <td align="left"><input type="password" id="userPassword1" name="userPassword1" maxlength="8" value="${k_user.userPassword}" class="easyui-validatebox " /></td>
          </tr>
      </table>
   </form>
   </div>
        
   <div id="yhxx" title="修改用户" style="padding:20px;width:570px;height:250px;display:none;overflow:hidden;">
    	<form id="form_yhxx" method="post" >
            <table width="100%" align="center" cellpadding="5" cellspacing="5" >
                <tr>
                    <td align="right">用户账号：</td>
                    <td align="left" ><input type="text" id="userAccount_edit" name="userAccount_edit" class="text_dis" readonly="readonly"  /></td>
                </tr>
                <tr>
                    <td align="right">用户名称：</td>
                    <td align="left"><input type="text" id="userName_edit" name="userName_edit"  class="text_dis easyui-validatebox " /></td>
                </tr><%--
                <tr>                                                         
                    <td align="right">用户类型：</td>
                    <td align="left"><span class="s_border"><s:select id="userDesc_edit" name="userDesc_edit" list="#{'系统用户':'系统用户','商铺用户':'商铺用户'}" listKey="key" listValue="value" cssClass="select s_choose" theme="simple" ></s:select></span></td>
                </tr>
                <tr>  
                    <td align="right">所属公司：</td>
                    <td align="left" ><input class="easyui-combotree text" id="userDept_edit" name="userDept_edit" /><input type="hidden" id="userDept1_edit" name="userDept1_edit" class="easyui-validatebox" /></td>
                </tr>
            --%></table>
         </form>
    </div>     
   
        
<div id="jbxx_edit" title="密码修改" style="padding:20px;width:560px;height:350px;display:none;overflow:hidden;">
    <form id="form_jbxx_edit" method="post" >
       <table cellpadding="5" cellspacing="5" width="100%" align="center" >
           <tr>
               <td align="right">用户账号：</td>
               <td align="left" ><input type="text" id="edit_userAccount" name="userAccount" value="${k_user.userAccount}" class="text_dis" readonly="readonly" /></td>
           </tr>
           <tr>
               <td align="right">用户名称：</td>
               <td align="left"><input type="text" id="edit_userName" name="userName" value="${k_user.userName}" class="text_dis" readonly="readonly"/></td>
           </tr>
           <tr>
               <td align="right">密码：</td>
               <td align="left"><input type="password" id="edit_userPassword" name="userPassword" maxlength="8" value="${k_user.userPassword}" class="easyui-validatebox validatebox-text" /></td>
           </tr>
           <tr>
               <td align="right">确认密码：</td>
               <td align="left"><input type="password" id="edit_userPassword1" name="userPassword1" maxlength="8" value="${k_user.userPassword}" class="easyui-validatebox " /></td>
           </tr>
       </table>
    </form>
</div>
</body>
</html>


