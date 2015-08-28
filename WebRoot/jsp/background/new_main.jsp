<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

// 系统管理
String user_ManageInit = (String)request.getAttribute("user_ManageInit");//用户管理
String sysAuthority_Manage = (String)request.getAttribute("sysAuthority_Manage");//权限管理
String role_ManageInit = (String)request.getAttribute("role_ManageInit");//角色管理
String resources_Manage = (String)request.getAttribute("resources_Manage");//资源管理
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>八零视觉</title>
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
        var tt = $('#workspace');
        if (tt.tabs('exists', title)) {
            tt.tabs('select', title);
        } else {
            if (href) {
                var content = '<iframe scrolling="auto" frameborder="0" src="' + href + '" style="width:100%;height:99%;"></iframe>';
            } else {
                var content = '系统正在开发中...';
            }
            tt.tabs('add', {
                title:title,
                closable:true,
                content:content
            });
        }
        //tabClose();
    }
    function tabClose(){
        /*双击关闭TAB选项卡*/
        $(".tabs-inner").dblclick(function(){
            var subtitle = $(this).children("span").text();
            if(subtitle!="首页")
            $('#workspace').tabs('close',subtitle);
        })

        $(".tabs-inner").bind('contextmenu',function(e){
            $('#mm').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
            var subtitle =$(this).children("span").text();
            if(subtitle!="首页")
            $('#mm').data("currtab",subtitle);
            return false;
        });
    }
    function tabCloseEven(){
        //关闭当前
        $('#mm-tabclose').click(function(){
            var currtab_title = $('#mm').data("currtab");
            $('#workspace').tabs('close',currtab_title);
        })
        //全部关闭
        $('#mm-tabcloseall').click(function(){
            $('.tabs-inner span').each(function(i,n){
                var t = $(n).text();
                if(t!="首页")
                $('#workspace').tabs('close',t);
            });
        });
        //关闭除当前之外的TAB
        $('#mm-tabcloseother').click(function(){
            var currtab_title = $('#mm').data("currtab");
            $('.tabs-inner span').each(function(i,n){
                var t = $(n).text();
                if(t!="首页")
                if(t!=currtab_title)
                    $('#workspace').tabs('close',t);
            });
        });
        //关闭当前右侧的TAB
        $('#mm-tabcloseright').click(function(){
            var nextall = $('.tabs-selected').nextAll();
            if(nextall.length==0){
                //msgShow('系统提示','后边没有啦~~','error');
                //alert('后边没有啦~~');
                return false;
            }
            nextall.each(function(i,n){
                var t=$('a:eq(0) span',$(n)).text();
                $('#workspace').tabs('close',t);
            });
            return false;
        });
        //关闭当前左侧的TAB
        $('#mm-tabcloseleft').click(function(){
            var prevall = $('.tabs-selected').prevAll();
            if(prevall.length==0){
                //alert('到头了，前边没有啦~~');
                return false;
            }
            prevall.each(function(i,n){
                var t=$('a:eq(0) span',$(n)).text();
                if(t!="首页")
                $('#workspace').tabs('close',t);
            });
            return false;
        });
    }

    function remove_title(title){
        $('#acc').accordion('remove',title);
    }
	</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:90px;background:#414042;">
		<div class="top-logo"></div>
		<div class="top-user"><span></span>欢迎登录，admin</div>
		<div class="top-set">
			<span class="set ico1"></span>
			<span class="set ico2"></span>
			<span class="set ico3"></span>
		</div>
		<div class="top-search"><input type="text" value="搜索" /></div>
	</div>
	<div data-options="region:'west'" style="width:249px;background:#4D4D4F;border: 1px solid #4D4D4F;">
		<div class="left-menu">
			<span>
				<div class="left-ico1"></div>
				<p>后台首页</p>
			</span>
		</div>
		<div class="left-menu">
			<span>
				<div class="left-ico2"></div>
				<p>网站模块</p>
			</span>
			<ul>
				<li onclick="openMainFrame('呆毛','<%=basePath%>jsp/background/new_demo.jsp')">呆毛</li>
				<li onclick="openMainFrame('一周推荐','<%=basePath%>weekRecommend.action')">一周推荐</li>
				<li onclick="openMainFrame('作品赏析','')">作品赏析</li>
				<li onclick="openMainFrame('礼服赏析','')">礼服赏析</li>
				<li onclick="openMainFrame('微电影','')">微电影</li>
				<li onclick="openMainFrame('活动团购','')">活动团购</li>
				<li onclick="openMainFrame('工作室简介','')">工作室简介</li>
				<li onclick="openMainFrame('幻灯片','')">幻灯片</li>
				<li onclick="openMainFrame('客户评价','')">客户评价</li>
				<li onclick="openMainFrame('新闻','')">新闻</li>
			</ul>
		</div>
		<div class="left-menu">
			<span>
				<div class="left-ico3"></div>
				<p>系统管理</p>
			</span>
			<ul>
				<li onclick="openMainFrame('用户管理','<%=basePath%>user_ManageInit.action')">用户管理</li>
				<li onclick="openMainFrame('角色管理','<%=basePath%>role_ManageInit.action')">角色管理</li>
				<li onclick="openMainFrame('权限管理','<%=basePath%>sysAuthority_Manage.action')">权限管理</li>
				<li onclick="openMainFrame('资源管理','<%=basePath%>resources_Manage.action')">资源管理</li>
			</ul>
		</div>
	</div>
	<div data-options="region:'center'" title="工作区" style="background: #F1F2F2; border: 0px; overflow:hidden">
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