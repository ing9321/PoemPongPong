<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		 
<!DOCTYPE html>
<html>
<head> 
<meta charset="UTF-8">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="stylesheet" href="PoemPongPong/assets/css/findUserInformation.css?v=20170704_7" type="text/css">
<title>PoemPongPong</title>
</head>
<body>
	<jsp:include page="navbar.jsp"/>
	<div id="logoGoHome" onclick="location.href = 'poemIndex.jsp'"></div>
	<div id="bannerContainer"></div>
	<div id="allContainer">
		<div id="findIdDiv">
				<form method="post" action="findId.do">
					
					<div id="findIdText">아이디 찾고 시퐁</div>
					<div class="explainId"><label for="emailFindId">가입 당시 입력한 이메일을 입력해 주세요</label></div>
					<div class="textBox"><input type="text" name="emailFindId" class="textBox2" /></div>
					
					<div class="btnDiv">
						<span><input type="reset" value="" class="informReset" onclick="history.go(-1);" /></span>
						<span><input type="submit" value="" class="informSubmit"/></span>
					</div>
				</form>
		</div>
	 
	 	<div id="findPwdDiv">
	 			<form method="post" action="findPwd.do">
	 				
	 				<div id="findPwdText">비밀번호 찾고 시퐁</div>
	 				<div class="explainId"><label for="idFindPwd">아이디를 입력해 주세요</label></div>
	 				<div class="textBox"><input type="text" name="idFindPwd" class="textBox2"/></div>
	 				<br/>
	 				<div class="explainId" style="margin-top:0"><label for="emailFindPwd">가입 당시 입력한 이메일을 입력해 주세요</label></div>
	 				<br/>
	 				<div class="textBox" style="margin-top:0"><input type="text" name="emailFindPwd" class="textBox2" /></div>
	 				
	 				<div class="btnDiv">
	 					<span><input type="reset" value="" class="informReset" onclick="history.go(-1);" /></span>
	 					<span><input type="submit" value="" class="informSubmit"/></span>
	 				</div>
	 			</form>
	 	</div>
	 </div>
  	
</body>
</html>