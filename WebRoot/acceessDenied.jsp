<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="taglibs.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Access Denied</title>
    <style type="text/css">
div.error {
    width: 260px;
    border: 2px solid red;
    background-color: yellow;
    text-align: center;
}
    </style>
  </head>
  <body>
    <h1>Access Denied
</h1>
    <hr>
    <div class="error">
      对不起，您没有权限，访问被拒绝！<br>
      ${requestScope['SPRING_SECURITY_403_EXCEPTION'].message}
    </div>
    <hr>
  </body>
</html>

