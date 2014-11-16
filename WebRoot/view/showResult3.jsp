<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="check.jsp"%>
<%@page import="com.vote.service.*"%>
<%@page import="com.vote.bean.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="org.jfree.data.general.DefaultPieDataset"%>
<%@page import="java.io.PrintWriter"%>
<%
	//从请求当中获取到ID，根据ID查询出题目和内容
	String id = request.getParameter("oid");
	System.out.println("oid=" + id);
	int oid = Integer.parseInt(id);
	List<Replay> replay = new ArrayList<Replay>();
	replay = ReplayService.sort(oid);
	
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
		//返回
		function back() {
			window.location.href="./wjList.jsp";
		}
		</script>
</head>
		
<body>

<h2 align="center">《排行榜》</h2>
<div>
	<a href="showResult2.jsp?oid=<%=oid%>">切换饼图显示</a>
</div>
<div>
	<a href="showResult.jsp?oid=<%=oid%>">切换柱状图显示</a>
</div>
<center>
<table width=600 align=center border="0" cellpadding="0" cellspacing="1">
	<tr bgColor= #dedede>
		<th width=200 align="center"> 排名 </th>
		<th width=200 align="center"> Id </th>
		<th width=200 align="center"> 分数 </th>
	</tr>
<%
	for(int i=0;i<replay.size();i++){
		Replay r = replay.get(i);
		if(i%2==1){
			out.print("<tr bgColor= #CDE2FD align="+"center"+">");
		}else{
			out.print("<tr bgColor= #ffffff align="+"center"+">");
		}
		out.println("<td>"+(i+1)+"</td>");
		out.println("<td>"+r.getReplayId()+"</td>");
		out.println("<td>"+r.getRemark()+"</td>");
		out.print("</tr>");
	} 

%>
</table>
<input type="button" name="back" value=" 返  回   "  onclick="back();" class="btn"/>
</center>
</body>
</html>