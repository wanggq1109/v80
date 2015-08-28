<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
  </head>
  <body>
    <table>
      <tr>
        <th style="width: 30%">主键</th>
        <th style="width: 30%">名字</th>
        <th style="width: 30%">时间</th>
      </tr>
      <c:forEach var="template" items="${page.record}">
      <tr>
        <td><c:out value="${template.id}"/></td>
        <td><c:out value="${template.name}"/></td>
        <td><fmt:formatDate value="${template.templatDate}" pattern="yyyy-MM-dd" /></td>
      </tr>
      </c:forEach>
    </table>
    
    <%@ include file="page.jsp" %>
  </body>
</html>