<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String selectedMemberId = (String) request.getAttribute("memberID"); %>
<% String selectedMemberPwd = (String) request.getAttribute("memberPWD"); %>
<% String selectedMemberPoet = (String) request.getAttribute("memberPoet"); %>
<% String selectedMemberPhoto = (String) request.getAttribute("memberPhoto"); %>
<% String selectedMemberEmail = (String) request.getAttribute("memberEmail"); %>
<% int selectedMemberPath = (int) request.getAttribute("memberPath"); %>
<% int selectedMemberDormancy = (int) request.getAttribute("memberDormancy"); %>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page - Member Update</title>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="ADMIN/assets/css/main.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
	$(document).ready(function () {
		switch(<%=selectedMemberPath %>) {
			case 0:
				$('#regPong').attr('checked', true);
				break;
			case 1:
				$('#regFacebook').attr('checked', true);
				break;
			case 2:
				$('#regKakao').attr('checked', true);
				break;
			default:
				// 아무것도 선택되지 않음
		}
		
		switch(<%=selectedMemberDormancy %>) {
			case 0:
				$('#run').attr('selected', 'selected');
				break;
			case 1:
				$('#rest').attr('selected', 'selected');
				break;
			case 2:
				$('#out').attr('selected', 'selected');
				break;
			default:
				// 아무것도 선택되지 않음
		}
	});
</script>
</head>
<body class="is-loading">
	<!-- Wrapper -->
	<div id="wrapper">
	
		<!-- Header -->
		<header id="header">
			<a href="adminLogin.do" class="logo">POEMPONGPONG</a>
		</header>
		
		<!-- Nav -->
		<nav id="nav">
			<ul class="links">
				<li class="active"><a href="member.do">Member</a></li>
				<li><a href="weeklyPoem.do">WeeklyPoem</a></li>
				<li><a href="weeklyPoemLikeIt.do">WeeklyLikeIt</a></li>
				<li><a href="relayPoemMain.do">RelayPoemMain</a></li>
				<li><a href="relayPoemReply.do">RelayPoemReply</a></li>
				<li><a href="relayPoemLikeIt.do">RelayLikeIt</a></li>
				<li><a href="subject.do">Subject</a></li>
			</ul>
		</nav>
		
		<!-- Main -->
		<div id="main">
			<!-- Post -->
			<section class="post">
				<header class="major">
				
					<!-- 로그아웃 버튼 -->
					<div class="buttonDiv" style="float: right;">
						<form method="post" action="adminLogout.do">
							<input type="submit" class="button" value="로그아웃" />
						</form>
					</div>
					<!--  로그아웃 버튼 -->
					
					<h1>회원 정보 수정</h1>
				</header>
				<!-- MemberUpdateServlet(memberUpdate.do) 에서 get 방식을 통해 attribute로 전달해준 수정할 회원의 정보 -->
				<section>
					<form method="post" action="memberUpdate.do">
						<div class="field">
							<label for="memberID">Member Id</label>
							<% out.print("<input type='text' name='memberID' id='memberID' value='" + selectedMemberId + "' readOnly />"); %>
						</div>
						<div class="field">
							<label for="memberPWD">Member Password</label>
							<% out.print("<input type='password' name='memberPWD' id='memberPWD' value='" + selectedMemberPwd + "' readOnly />"); %>
						</div>
						<div class="field">
							<label for="memberPoet">Member Poet</label>
							<% out.print("<input type='text' name='memberPoet' id='memberPoet' value='" + selectedMemberPoet + "' />"); %>
						</div>
						<!-- <div class="field">
							<label for="memberPhoto">Profile Photo</label>
							회원 프로필 사진 정보는 어떻게 전달받을지 몰라서 아직 못건드렸스니다,,
							<input type="file" name="memberPhoto" id="memberPhoto" />
						</div> -->
						<div class="field">
							<label for="memberEmail">Member Email</label>
							<% out.print("<input type='email' name='memberEmail' id='memberEmail' value='" + selectedMemberEmail + "' />"); %>
						</div>
						<div class="field">
							<label for="memberPath">Member Path</label>
							<input type="radio" id="regPong" name="memberPath" value="0">
                            <label for="regPong">포엠퐁퐁</label>
							<input type="radio" id="regFacebook" name="memberPath" value="1">
                            <label for="regFacebook">페이스북</label>
							<input type="radio" id="regKakao" name="memberPath" value="2">
                            <label for="regKakao">카카오톡</label>
						</div>
						<div class="field">
							<label for="memberDormancy">Member Dormancy</label>
							<% out.print("<select name='memberDormancy'>"); %>
							<% out.print("<option id='run' value='0'>활동회원</option>"); %>
							<% out.print("<option id='rest' value='1'>휴면계정</option>"); %>
							<% out.print("<option id='out' value='2'>탈퇴회원</option>"); %>
							<% out.print("</select>"); %>
						</div>
						<ul class="actions">
							<li><input type="submit" value="Update Member" /></li>
						</ul>
					</form>
				</section>
			</section>
		</div>
		
		<!-- Footer -->
		<footer id="footer">
		</footer>
		
		<!-- Copyright -->
		<div id="copyright">
			<ul>
				<li>&copy; GlobalIT ClassA</li>
				<li>Design: PoemPongPong</li>
			</ul>
		</div>
	</div>
	
	<!-- Scripts -->
	<script src="ADMIN/assets/js/jquery.min.js"></script>
	<script src="ADMIN/assets/js/jquery.scrollex.min.js"></script>
	<script src="ADMIN/assets/js/jquery.scrolly.min.js"></script>
	<script src="ADMIN/assets/js/skel.min.js"></script>
	<script src="ADMIN/assets/js/util.js"></script>
	<script src="ADMIN/assets/js/main.js"></script>
</body>

</html>