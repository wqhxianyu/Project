<%@page import="weibo4j.http.AccessToken"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="weibo4j.*" %>
<%@ page import="weibo4j.Users"%>
<%@ page import="weibo4j.model.User"%>
<%@ page import="weibo4j.model.WeiboException"%>
<%@ page import="org.apache.log4j.Logger"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:wb="http://open.weibo.com/wb">
  <head>
   <script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=301111228" type="text/javascript" charset="utf-8">
  </script>
    <base href="<%=basePath%>">
    
    <title>微博登陆并设置username</title>
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
     <wb:login-button type="3,2" onlogin="login" onlogout="logout">微博登陆</wb:login-button>
  
    <%
    	String code = request.getParameter("code");
    	out.println(code); 
    	Oauth oauth = new Oauth();
    	AccessToken fuck = oauth.getAccessTokenByCode(code);
    	session.setAttribute("accessToken", fuck.getAccessToken());
    	session.setAttribute("uid", fuck.getid());
    	String chk="true";
	    session.setAttribute("Enter",chk);
	    session.setAttribute("userName",fuck.getid());
	    //response.sendRedirect("../index.jsp");
    	response.sendRedirect("weibo.jsp"); 
     %>
  </body>
</html>
