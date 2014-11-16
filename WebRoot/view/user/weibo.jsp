<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="weibo4j.ShowUser"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:wb="http://open.weibo.com/wb">
  <head>
	<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=301111228" type="text/javascript" charset="utf-8"></script>	
	<script charset="UTF-8" src="https://api.weibo.com/2/oauth2/query?source=301111228&callback=STK_901415773992254"></script>
    <script type="text/javascript">
    	
WB2.anyWhere(function(W){

    W.widget.publish({

        'id' : 'wb_publish',

        'default_text' : '预置方案　& \r\n我可以换行.',

        'callback' : function(o) {

            //do something...

        }

    });

});
    </script>
    <base href="<%=basePath%>">
    <title>My JSP 'weibo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
	  <wb:login-button type="3,2" onlogin="login" onlogout="logout">登录按钮</wb:login-button> <br> 
	  <wb:publish action="pubilish" type="web" language="zh_cn" button_type="red" button_size="middle" refer="y" appkey="u4F52" default_text="<%=basePath %>"></wb:publish><br>  
	  
	  <%
    	//get the access_token
    	String access_token = session.getAttribute("accessToken").toString();
    	String uid = session.getAttribute("uid").toString();
    	ShowUser user = new ShowUser();
    	String screen_name = user.getscreen_name(access_token, uid);
    	session.setAttribute("screen_name", screen_name);
    	out.print(screen_name);
     %>
	     
	     欢迎 <a href="//weibo.com/u/<%=uid %>">

    <img alt="<%=screen_name %>" src="http://tp1.sinaimg.cn/<%=uid %>/30/5650501886/1"></img></a>
	     <a href="http://weibo.com/u/<%=uid %>"><%=screen_name %></a>登陆问卷管理系统<br>
	     用户的Id ： <%=uid %><br>
	    <%=basePath%> 
	   <%  response.sendRedirect("../index.jsp");%> 
    <a href="/vote/view/index.jsp">管理问卷</a> <br>
    <wb:publish></wb:publish>
    <wb:publish default_text="<%=basePath %>>" default_image="http://ww1.sinaimg.cn/square/3ec33750jw1e5fksj1fdvj2028028web.jpg"></wb:publish>
  </body>
</html>
