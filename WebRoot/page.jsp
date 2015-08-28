<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
<!--
function nextPage(index){
  var url = "template_page?pageIndex=";
  window.location.href = url + index;
}
$(function(){
  $('#pageGo').click(function(){
    var pageIndex = $('#pageIndex').val();
    if(null != pageIndex && "" != pageIndex && 0 < parseInt(pageIndex)){
      var url = "template_page?pageIndex=";
      window.location.href = url + $('#pageIndex').val();
    }
  });
});
//-->
</script>


共${page.pageCount}页 当前第${page.pageIndex}页 共${page.totalRecordCount}条记录
<c:if test="${page.pageCount!=1}">
  <c:if test="${page.pageIndex==1}">
    <font color="gray">首页 上一页</font>
    <a href="template_page?pageIndex=${page.pageIndex + 1}">下一页</a>
    <a href="template_page?pageIndex=${page.pageCount}">尾页</a>
  </c:if>
  <c:if test="${page.pageIndex==page.pageCount}">
    <a href="template_page?pageIndex=1">首页</a>
    <a href="template_page?pageIndex=${page.pageIndex - 1}">上一页</a>
    <font color="gray">下一页 尾页</font>
  </c:if>
  <c:if test="${page.pageIndex!=1 and page.pageIndex!=page.pageCount}">
    <a href="template_page?pageIndex=1">首页</a>
    <a href="template_page?pageIndex=${page.pageIndex - 1}">上一页</a>
    <a href="template_page?pageIndex=${page.pageIndex + 1}">下一页</a>
    <a href="template_page?pageIndex=${page.pageCount}">尾页</a>
  </c:if>
  <input id="pageIndex" type="text"/>
  <input id="pageGo" type="button" value="GO"/>
</c:if>

<hr/>

共${page.pageCount}页 当前第${page.pageIndex}页 共${page.totalRecordCount}条记录
<c:if test="${page.pageCount!=1}">
  <c:if test="${page.pageIndex==1}">
    <img src="images/page-first-disabled.gif"/>
    <img src="images/page-prev-disabled.gif"/>
    <img src="images/page-next.gif" onclick="nextPage(${page.pageIndex + 1})"/>
    <img src="images/page-last.gif" onclick="nextPage(${page.pageCount})"/>
  </c:if>
  <c:if test="${page.pageIndex==page.pageCount}">
    <img src="images/page-first.gif" onclick="nextPage(1)"/>
    <img src="images/page-prev.gif" onclick="nextPage(${page.pageIndex - 1})"/>
    <img src="images/page-next-disabled.gif"/>
    <img src="images/page-last-disabled.gif"/>
  </c:if>
  <c:if test="${page.pageIndex!=1 and page.pageIndex!=page.pageCount}">
    <img src="images/page-first.gif" onclick="nextPage(1)"/>
    <img src="images/page-prev.gif" onclick="nextPage(${page.pageIndex - 1})"/>
    <img src="images/page-next.gif" onclick="nextPage(${page.pageIndex + 1})"/>
    <img src="images/page-last.gif" onclick="nextPage(${page.pageCount})"/>
  </c:if>
  <input id="pageIndex" type="text"/>
  <input id="pageGo" type="button" value="GO""/>
</c:if>