<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String code = request.getParameter("code");
String id = request.getParameter("id");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'fileUpload.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--请不要忘记将jquery导进来,所有组建都是在这个的基础上的-->
	<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
    <!-- 必不可少的样式的两个样式-->
	<link rel="stylesheet" href="css/jquery.fileupload-ui.css" type="text/css"></link> 
	<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"></link>
  </head>
<body>
<div class="">
    <!-- The file upload form used as target for the file upload widget -->
    <!--        action执行的当前的表单要提交的位置                       enctype 不对字符编码,在使用包含文件上传控件的表单时，必须使用该值            -->
    <form id="fileupload" action="UploadServlet" method="post" enctype="multipart/form-data">
        <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
        <div class="row fileupload-buttonbar">
            <div class="span7">
                <!-- The fileinput-button span is used to style the file input field as button -->
                <span class="btn btn-success fileinput-button">
                    <span>添加图片</span>
                    <input type="file" name="files[]" multiple>
                </span>
                <button type="submit" class="btn btn-primary start">
                    <span>开始上传</span>
                </button>
                <button type="reset" class="btn btn-warning cancel">
                    <span>取消上传</span>
                </button>
            </div>
            <!-- The global progress information -->
            <div class="span5 fileupload-progress fade">
                <!-- The global progress bar -->
                <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
                    <div class="bar" style="width:0%;"></div>
                </div>
                <!-- The extended global progress information -->
                <div class="progress-extended">&nbsp;</div>
            </div>
        </div>
        <!-- The loading indicator is shown during file processing -->
        <div class="fileupload-loading"></div>
        <br>
        <input type="hidden" name="code" value="<%=code%>" />
         <input type="hidden" name="id" value="<%=id%>" />
        <!-- The table listing the files available for upload/download -->
        <table role="presentation" class="table table-striped">
        	<thead>
        		<tr>
        			<td>图片</td>
        			<td>进度条</td>
        			<td colspan="2">操作</td>
        		</tr>
        	</thead>
        	<tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery">
        		<tr>
        			<td></td>
        			<td></td>
        			<td></td>
        			<td></td>
        		</tr>
        	</tbody>
        </table>
    </form>
    <br>
</DIV>	
</body>
</html>

<!--
	 <td class="name"><span id="filename">{%=file.name%}</span></td>
	下面中上所示的代码本来span的id是没有的,这一个部分是动态生成,为了获取上传图片的名字,所以加了一个id
 -->
<script id="template-upload" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
        <tr class="template-upload fade">
            <td class="preview"><span class="fade"></span></td>
            {% if (file.error) { %}
            <td class="error" colspan="2"><span class="label label-important">Error</span> {%=file.error%}</td>
            {% } else if (o.files.valid && !i) { %}
            <td>
                <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="bar" style="width:0%;"></div></div>
            </td>
            <td class="start">{% if (!o.options.autoUpload) { %}
                <button class="btn btn-primary">
                    <span>开始</span>
                </button>
                {% } %}</td>
            {% } else { %}
            <td colspan="2"></td>
            {% } %}
            <td class="cancel">{% if (!i) { %}
                <button class="btn btn-warning">
                    <span>取消</span>
                </button>
                {% } %}</td>
        </tr>
        {% } %}
    </script>
    <!-- The template to display files available for download -->
    <script id="template-download" type="text/x-tmpl">
        {% for (var i=0, file; file=o.files[i]; i++) { %}
        <tr class="template-download fade">
            {% if (file.error) { %}
            <td></td>
            <td class="error" colspan="2"><span class="label label-important">Error</span> {%=file.error%}</td>
            {% } else { %}
            <td class="preview">{% if (file.thumbnail_url) { %}
                <a href="{%=file.url%}" title="{%=file.name%}" rel="gallery" download="{%=file.name%}"><img src="{%=file.thumbnail_url%}"></a>
                {% } %}</td>
            <td><div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="bar" style="width:100%;">100%</div></div></td>
            {% } %}
            <td class="delete">
                <button class="btn btn-danger" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}"{% if (file.delete_with_credentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <span>删除</span>
                </button>
            </td>
        </tr>
        {% } %}
    </script>
    
    <!-- 这里原作者用了这么多的js知道哪个有用没用全给导进来算了,因为作者的原工程不知道是用什么IDE的,所有就姑且这样用着吧 -->
    <script src="js/vendor/jquery.ui.widget.js"></script>
    <script src="js/tmpl.min.js"></script>
    <script src="js/load-image.min.js"></script>
    <script src="js/canvas-to-blob.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-image-gallery.min.js"></script>
    <script src="js/jquery.iframe-transport.js"></script>
    <script src="js/jquery.fileupload.js"></script>
    <script src="js/jquery.fileupload-fp.js"></script>
    <script src="js/jquery.fileupload-ui.js"></script>
    <script src="js/locale.js"></script>
    <script src="js/main.js"></script>
 
