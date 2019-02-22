<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head> 
<meta charset="UTF-8">
<title>PoemPongPong</title>
<link rel="stylesheet" href="PoemPongPong/assets/css/contactUs.css?v=20170705_21" type="text/css">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
<script src="//cdn.jsdelivr.net/jquery/2.2.0/jquery.min.js"></script>
</head>
<body>
	<jsp:include page="banner.jsp"></jsp:include>
	<jsp:include page="loginInclude.jsp"/>
	<jsp:include page="navbar.jsp"/>
	<div id="container">
		<form action="contactUs.do" method="post">
			<div class="main-title">
	            Contact Us
	        </div>
			<div class="information">이메일</div>
			<div>
				<input type="text" value="${memberEmail }" name="memberEmail" class="information-text"/>
			</div>
			<div class="information">제목</div>
			<div>
				<input type="text" name="subject" class="information-text"/>
			</div>
			<div class="information">내용</div>
			<div>
				<textarea name="contents" class="information-text contents"></textarea>
			</div>
			<div>
				<input type="submit" value="" class="btnSend"><br/>
			</div>
			
			<input type="hidden" name="to" value="hotsnickers@naver.com"/>
			<input type="hidden" name="from" value="hotsnickers@naver.com"/>
		</form>
	</div>
	
	<script>
		$(".contents").on('keydown keyup', function () {
			$(this).height(1).height( $(this).prop('scrollHeight')+12 );	
    	});
	</script>
</body>
</html>