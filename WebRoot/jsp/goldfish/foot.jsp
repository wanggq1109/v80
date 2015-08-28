<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<div class="service">
 <div class="service_box">
   <div class="about mr40">
     <h5>关于</h5>
     <p>丽江市古城区八零视觉婚纱摄影工作室成立于08年，是丽江婚纱摄影行业规模最大的摄影店，也是丽江摄影协会唯一的商家单位，工作室总店位于市区民主路锦天国际商11号，总面积达1500多平米，建有豪华3D实景影棚，拥有服装上千款，并配备尼康FX专业相机及世界顶级卡尔蔡司镜头，是一家拥有超强实力的正规商。[ <a href="<%=basePath%>studioInfo.action" target="_blank">更多...</a> ]</p>
   </div>
   <div class="team mr40">
     <h5>团队</h5>
     <ul>
      <li><a href="<%=basePath%>studioemployeePhoto.action" target="_blank"><img src="goldfish/images/t1.jpg"/></a></li>
      <li><a href="<%=basePath%>studioemployeePhoto.action" target="_blank"><img src="goldfish/images/t2.jpg"/></a></li>
      <li><a href="<%=basePath%>studioemployeePhoto.action" target="_blank"><img src="goldfish/images/t3.jpg"/></a></li>
      <li><a href="<%=basePath%>studioemployeePhoto.action" target="_blank"><img src="goldfish/images/t4.jpg"/></a></li>
      <li><a href="<%=basePath%>studioemployeePhoto.action" target="_blank"><img src="goldfish/images/t5.jpg"/></a></li>
      <li><a href="<%=basePath%>studioemployeePhoto.action" target="_blank"><img src="goldfish/images/t6.jpg"/></a></li>
     </ul>
   </div>
   <div class="contact mr40">
     <h5>联系我们</h5>
     <ul>
       <li class="tel">0888-8889680</li><li class="ph">186 0888 5999</li><li class="mail">diza_0872@163.com</li><li class="qq">800004280</li><li class="add">总店：丽江市民主路锦天国际花苑11号商铺<br/>分店：丽江市七星街中段锦江之星一楼</li>
     </ul>
   </div>
   <div class="ewm">
     <h5>其他</h5>
     <ul>
       <li class="mr20"><img src="goldfish/images/ewm_wx.png" width="121" height="121"/><span>微信服务号</span></li>
       <li><img src="goldfish/images/ewm_tb.png" width="121" height="121"/><span>手机淘宝</span></li>
     </ul>
   </div>
 </div>
</div>
<div id="footer"><div id="footer_main"><span><a class="yj" href="http://www.yn.cyberpolice.cn" target="_blank"></a><a class="ba" href="http://www.miitbeian.gov.cn" target="_blank">滇ICP备11003080号-1</a></span>Copyright © 2009-2012 版权所有丽江市古城区八零视觉婚纱摄影工作室</div></div>
<script src="http://kefu.qycn.com/vclient/state.php?webid=99374" language="javascript" type="text/javascript"></script>