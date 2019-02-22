<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.poem.common.vo.MemberVO" %>
<% String memberID = (String)session.getAttribute("memberID");
	String memberPoet = (String)session.getAttribute("memberPoet");
	MemberVO myInfo = (MemberVO)request.getAttribute("myInfo"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/PoemPongPong/assets/css/myPage.css?v=201707010_1" type="text/css">
<title>PoemPongPong</title>
</head>
<body>
	<jsp:include page="navbar.jsp"/>
	<jsp:include page="loginInclude.jsp"/>
	
	<div id="bannerContainer"></div>
	
	<div id="logoGoHome" onclick="location.href = 'poemIndex.jsp'"></div>
	
	<div id="myPageContainer">
		<div id="letterMyPage">&lt; My Page &gt;</div>
	
		<div class="myInfo">
		
		<!-- 캐릭터 -->
			<div id="myProfile" style="background-image: url('${myInfo.getMemberPhoto()}')"></div>
		<!-- 정보 -->
			<div id="myInfoText">
				<div id="myPoet" class="divInfoText">
					<label class="labelInfo"> 닉네임: <%=myInfo.getMemberPoet() %> </label>
				</div>
				
				<div id="myEmail" class="divInfoText">
					<label class="labelInfo"> 이메일 : <%=myInfo.getMemberEmail() %> </label>
				</div>
				
				<div id="myEmail" class="divInfoText">
					<label class="labelInfo"><a href="weeklyPoemUser.do?poet=<%=myInfo.getMemberPoet() %>">나의 퐁퐁 보기</a></label>
				</div>
				
				<div id="myEmail" class="divInfoText">
					<label class="labelInfo"><a href="weeklyPoemLikeList.do">좋아한 퐁퐁 보기</a></label>
				</div>
			</div>
			
		</div>
		
		<!-- 정보수정 -->
		<div id="divModifyInfo">
			<input type="button" id="modifyInfoBtn" value="" onclick="location.href='modifyInfo.do';" />
		</div>
	</div>

</body>
</html>