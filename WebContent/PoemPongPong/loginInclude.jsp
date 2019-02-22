<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.poem.common.vo.MemberVO" %>

<%
	String loginMember = (String)session.getAttribute("loginMember");
	request.setAttribute("loginMember",loginMember);
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PoemPongPong</title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/PoemPongPong/assets/css/loginInclude.css?v=20170624_8" type="text/css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<script>
	$(document).ready(function(){
		var loginMember = "${loginMember}";
		if(loginMember == null || loginMember ==""){
			$("#caseOfLogin").css("display","block");
			$("#caseOfLogout").css("display","none");
		}else{
			$("#caseOfLogin").css("display","none");
			$("#caseOfLogout").css("display","block");
		}
	});
</script>
</head>
<body>

	<form method="post" action="UserIndexLogin.do">
		<div id="caseOfLogin">
			<input type="hidden" value = "loginBtn" name = "loginBtn"/>
			<input type="image" name="submit" src ="PoemPongPong/images/loginButton.png" value=""/>
		</div>
	</form>
	<span id= "caseOfLogout">    
		<div id="logoutBtnDiv">
			<input type="submit" id="logoutBtn" value="" onclick="location.href='memberLogout.do';"/>
		</div>
		<div id="myPageDiv" >
			<input type="button" id="myPage" value="" onclick="location.href='myPage.do';"/>
		</div>
		<div id="contactUsDiv">
			<input type="button" id="contactUs" value="" onclick="location.href='contactUs.do';"/>
		</div>
	</span>
	
</body>
</html>