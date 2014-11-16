<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="check.jsp"%>
<%@page import="com.vote.bean.*"%>
<%@page import="com.vote.service.*"%>
<%@ page import= "java.sql.Timestamp" %>
<%
	ObjectBeanService obs=new ObjectBeanService();
	FileUtil fu=new FileUtil();
	ObjectBean ob = new ObjectBean();
	String title = request.getParameter("title");
	String discribe = request.getParameter("discribe");
	String anonymousFlag = request.getParameter("anonymousFlag");
	String remark = session.getAttribute("userName").toString();
	String name = session.getAttribute("screen_name").toString();
	Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    //out.print(currentTime);
    ob.setCreateTime(currentTime);
	ob.setTitle(title);
	ob.setDiscribe(discribe);
	ob.setAnonymousFlag(anonymousFlag);
	ob.setRemark(remark);
	ob.setName(name);
	
	int id = obs.intsertObjectBean(ob);
	if(id >0){
		response.sendRedirect("wjList.jsp");
	}else{
%>
	<script>
		alert("创建失败");
	</script>
		<%response.sendRedirect("wjNew.jsp");
	}
%>