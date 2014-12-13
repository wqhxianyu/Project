<% 
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
%><%
  if((String)session.getAttribute("Enter")!="true"){
  	if((String)session.getAttribute("screen_name")=="admin"){
	     String errmsg="error2";
	     response.sendRedirect("login.jsp?errmsg="+errmsg);
	     return;
     }else{
     	response.sendRedirect("login.jsp");
     }
   }
%>
