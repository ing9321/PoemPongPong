<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="PoemPongPong/assets/css/sendMailPage.css?v=20170704_3" type="text/css">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="navbar.jsp"/>
	<div id = "bannerContainer"></div>
	<div id = "logoGoHome"></div>
	<div id="alertMsg">${message}</div>
	<div id="secMsg">이메일을 전송했으니 확인해 주세요!</div>
	<div id = "sendEmailImg"></div>
	
	<div id="goIndex"><input type="button" value="" onclick="location.href='poemIndex.do';"></div>

</body>
</html>