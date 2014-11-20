<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="check.jsp"%>
<%@page import="java.sql.*"%>
<%@page import="com.vote.service.*"%>
<%@page import="com.vote.bean.*"%>
	<%
	String id = request.getParameter("oid");
	System.out.println("id="+id);
	int oid = Integer.parseInt(id);
	QuestionService qs = new QuestionService();
	SelecterService ss = new SelecterService();
	int count = ObjectBeanService.getCount(oid);	//取得数据库中问卷问题的个数
	int seq = 1;
	String content = request.getParameter("content");
	if(content!=null&&content.trim().length()>0){
		//content=new String(content.getBytes("iso8859-1"),"UTF-8");
		}
	String selCount = request.getParameter("listCnt");
	int listCnt = Integer.parseInt(selCount);
	String type=request.getParameter("qtype");
	int qtype = Integer.parseInt(type);
	//问卷中没有问题时直接插入问题
	if (count == 0) {
		//往题目表中插入题目
		int insertQues = qs.addQues(oid, content, qtype, seq);
		if (insertQues > 0) {
			//插入选项数据
			for (int i = 1; i <= listCnt; i++) {
				//设置name属性并取得选项内容和分数
				String name = String.valueOf("txt" + i);
				String value = request.getParameter(name);
				String score = String.valueOf("score" + i);
				String valuescore = request.getParameter(score);
				ss.syscore(valuescore);
				ss.addSelecter(oid, seq, value, i,valuescore);
			}
		}
	} else {
		//往题目表中插入题目
		int addQues = qs.addQues(oid, content, qtype, (count + 1));
		if (addQues > 0) {
			//插入选项数据
			for (int i = 1; i <= listCnt; i++) {
					//设置name属性并取得选项内容和分数
					String name = String.valueOf("txt" + i);
					String value = request.getParameter(name);
					String score = String.valueOf("score" + i);
					String valuescore = request.getParameter(score);
					ss.syscore(valuescore);
					ss.addSelecter(oid, (count + 1), value, i,valuescore);
			}
		}else if(addQues==-1){
			response.sendRedirect("newQues1.jsp?oid=" + oid);
		}
	}
	response.sendRedirect("quesList.jsp?oid=" + oid);
%>
