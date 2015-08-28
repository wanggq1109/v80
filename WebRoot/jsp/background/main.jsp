<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

// 系统管理
String user_ManageInit = (String)request.getAttribute("user_ManageInit");//用户管理
String sysAuthority_Manage = (String)request.getAttribute("sysAuthority_Manage");//权限管理
String role_ManageInit = (String)request.getAttribute("role_ManageInit");//角色管理
String resources_Manage = (String)request.getAttribute("resources_Manage");//资源管理

String photoworkType = (String)request.getAttribute("photoworkType");
String dressType = (String)request.getAttribute("dressType");
String slideType = (String)request.getAttribute("slideType");
String studioType = (String)request.getAttribute("studioType");
String employeephotoType = (String)request.getAttribute("employeephotoType");
String hotspots = (String)request.getAttribute("hotspots");

%>
<!DOCTYPE HTML>
<html>
<head>
	<title>八零视觉</title>
    <link href="<%=basePath%>favicon.ico" rel="icon" type="image/ico">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/layout.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jquery-easyui/jquery.easyui.min.js"></script>
	<script>
	$(document).ready(function() {
        tabClose();
        tabCloseEven();
    });
	// 打开工作区
    function openMainFrame(title, href) {
        var tt = $("#workspace");
        if (tt.tabs("exists", title)) {
            tt.tabs("select", title);
        } else {
            if (href) {
                var content = '<iframe scrolling="auto" frameborder="0" src="' + href + '" style="width:100%;height:99%;background:#EEE;"></iframe>';
            } else {
                var content = "系统正在开发中...";
            }
            tt.tabs("add", {
                title:title,
                closable:true,
                content:content
            });
        }
        tabClose();
    }
    function tabClose(){
        /*双击关闭TAB选项卡*/
        $(".tabs-inner").dblclick(function(){
            var subtitle = $(this).children("span").text();
            if(subtitle!="首页")
            $("#workspace").tabs("close",subtitle);
        })

        $(".tabs-inner").bind("contextmenu",function(e){
            $("#mm").menu("show", {
                left: e.pageX,
                top: e.pageY
            });
            var subtitle =$(this).children("span").text();
            if(subtitle!="首页")
            $("#mm").data("currtab",subtitle);
            return false;
        });
    }
    function tabCloseEven(){
        //关闭当前
        $("#mm-tabclose").click(function(){
            var currtab_title = $("#mm").data("currtab");
            $("#workspace").tabs("close",currtab_title);
        })
        //全部关闭
        $("#mm-tabcloseall").click(function(){
            $(".tabs-inner span").each(function(i,n){
                var t = $(n).text();
                if(t!="首页")
                $("#workspace").tabs("close",t);
            });
        });
        //关闭除当前之外的TAB
        $("#mm-tabcloseother").click(function(){
            var currtab_title = $("#mm").data("currtab");
            $(".tabs-inner span").each(function(i,n){
                var t = $(n).text();
                if(t!="首页")
                if(t!=currtab_title)
                    $("#workspace").tabs("close",t);
            });
        });
        //关闭当前右侧的TAB
        $("#mm-tabcloseright").click(function(){
            var nextall = $(".tabs-selected").nextAll();
            if(nextall.length==0){
                return false;
            }
            nextall.each(function(i,n){
                var t=$("a:eq(0) span",$(n)).text();
                $("#workspace").tabs("close",t);
            });
            return false;
        });
        //关闭当前左侧的TAB
        $("#mm-tabcloseleft").click(function(){
            var prevall = $(".tabs-selected").prevAll();
            if(prevall.length==0){
                return false;
            }
            prevall.each(function(i,n){
                var t=$("a:eq(0) span",$(n)).text();
                if(t!="首页")
                $("#workspace").tabs("close",t);
            });
            return false;
        });
    }
    
    // 刷新tab页
    function reload_tabs(title, href) {
        close_tabs(title);
        openMainFrame(title, href);
    }

    // 关闭tab页
    function close_tabs(title) {
        var tt = $("#workspace");
        tt.tabs("close", title);
    }

    function remove_title(title){
        $("#acc").accordion("remove",title);
    }
	</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:90px;background:#1A1F1F;">
		<div class="top-logo"></div>
		<div class="top-user"><span></span>欢迎登录，admin</div>
		<div class="top-set">
			<span class="set ico1"></span>
			<span class="set ico2"></span>
			<span class="set ico3"></span>
		</div>
	</div>
	<div data-options="region:'west'" style="width:219px;">
		<div class="easyui-accordion" fit="true" border="false" animate="true" id="acc">
			<div title="网站模块" data-options="iconCls:'left-ico1'" selected="true" class="left-menu">
				<ul>
					<li onclick="openMainFrame('作品赏析','<%=basePath%>photoworktopic.action?code=<%=photoworkType %>')">作品赏析</li>
					<li onclick="openMainFrame('礼服赏析','<%=basePath%>photoworktopic.action?code=<%=dressType %>')">礼服赏析</li>
					<li onclick="openMainFrame('客片管理','<%=basePath%>customerInfoshow.action')">客片管理</li>
					<li onclick="openMainFrame('热门景点','<%=basePath%>photoworktopic.action?code=<%=hotspots %>')">热门景点</li>
					<li onclick="openMainFrame('微电影','<%=basePath%>video_init.action')">微电影</li>
					<li onclick="openMainFrame('幻灯片','<%=basePath%>slideshow_init.action?code=<%=slideType %>')">幻灯片</li>
					<li onclick="openMainFrame('新闻','<%=basePath%>news_init.action')">新闻</li>
					
				</ul>
			</div>
			<div title="工作室管理" data-options="iconCls:'left-ico2'" selected="true" class="left-menu">
				<ul>
					<li onclick="openMainFrame('工作室简介','<%=basePath%>studio_init.action?code=<%=studioType %>')">工作室简介</li>
					<li onclick="openMainFrame('工作室服务','<%=basePath%>studio_service_init.action')">工作室服务</li>
					<li onclick="openMainFrame('员工信息','<%=basePath%>employee_init.action')">员工信息</li>
					<li onclick="openMainFrame('招聘信息','<%=basePath%>zhaopin_init.action')">招聘信息</li>
					<%--<li onclick="openMainFrame('主题作品','<%=basePath%>emphototopic_init.action?code=<%=employeephotoType %>')">主题作品</li>
				--%></ul>
			</div>
			<div title="团购管理" data-options="iconCls:'left-ico4'" selected="true" class="left-menu">
				<ul>
					<li onclick="openMainFrame('团购订单','<%=basePath%>orderGroup_init.action')">团购订单</li>
					<li onclick="openMainFrame('团购管理','<%=basePath%>orderManager_init.action')">团购管理</li>
				</ul>
			</div>
			<div title="系统管理" data-options="iconCls:'left-ico3'" class="left-menu">
				<ul>
					<li onclick="openMainFrame('用户管理','<%=basePath%>user_ManageInit.action')">用户管理</li>
					<li onclick="openMainFrame('角色管理','<%=basePath%>role_ManageInit.action')">角色管理</li>
					<li onclick="openMainFrame('权限管理','<%=basePath%>sysAuthority_Manage.action')">权限管理</li>
					<li onclick="openMainFrame('资源管理','<%=basePath%>resources_Manage.action')">资源管理</li>
					<li onclick="openMainFrame('类型管理','<%=basePath%>phototype.action')">类型管理</li>
				</ul>
			</div>
		</div>
	</div>
	<div data-options="region:'center'" style="background: red; border: 0px; overflow:hidden">
		<div class="easyui-tabs" fit="true" border="false" id="workspace">
			<div title="首页" style="text-align: center;margin-top: 15px;"></div>
		</div>
	</div>
	<div id="mm" class="easyui-menu" style="width:150px;">
	    <div id="mm-tabclose">关闭</div>
	    <div id="mm-tabcloseall">全部关闭</div>
	    <div id="mm-tabcloseother">除此之外全部关闭</div>
	    <div class="menu-sep"></div>
	    <div id="mm-tabcloseright">当前页右侧全部关闭</div>
	    <div id="mm-tabcloseleft">当前页左侧全部关闭</div>
	</div>
</body>
</html>