	// 触发菜单的效果
    function click_menu(){
    	$("p").hover(function() {
            $(this).removeClass("out");
            $(this).addClass("over");
        }, function() {
            $(this).removeClass("over");
            $(this).addClass("out");
        });
    }
   
    // 新建tab页
    function open_tabs(title, href) {
        var tt = $('#workspace');
        if (tt.tabs('exists', title)) {
            tt.tabs('select', title);
        } else {
            if (href) {
                var content = '<iframe scrolling="auto" frameborder="0" src="' + href + '" style="width:100%;height:100%;"></iframe>';
            } else {
                var content = '系统正在开发中...';
            }
            tt.tabs('add', {
                title:title,
                closable:true,
                content:content
            });
        }
        tabClose();
    }

    // 刷新tab页
    function reload_tabs(title, href) {
        close_tabs(title);
        open_tabs(title, href);
    }

    // 关闭tab页
    function close_tabs(title) {
        var tt = $('#workspace');
        tt.tabs('close', title);
    }

	// 双击关闭tab页
    function tabClose(){
        /*双击关闭TAB选项卡*/
        $(".tabs-inner").dblclick(function(){
            var subtitle = $(this).children("span").text();
            if(subtitle!="欢迎界面")
            $('#workspace').tabs('close',subtitle);
        })

        $(".tabs-inner").bind('contextmenu',function(e){
            $('#mm').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
            var subtitle =$(this).children("span").text();
            if(subtitle!="欢迎界面")
            $('#mm').data("currtab",subtitle);
            return false;
        });
    }

	// 关闭全部tab页
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

	// 移除标题
    function remove_title(title){
        $('#acc').accordion('remove',title);
    }