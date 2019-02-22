<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% int selectedRelayPoemMainIndex = (int) request.getAttribute("index"); %>
<% String selectedRelayPoemMainSubject = (String) request.getAttribute("subject"); %>
<% String selectedRelayPoemMainTitle = (String) request.getAttribute("title"); %>
<% String selectedRelayPoemMainContents = (String) request.getAttribute("contents"); %>
<% String selectedRelayPoemMainPoets = (String) request.getAttribute("poets"); %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page - Relay Poem Main Update</title>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="ADMIN/assets/css/main.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
	$(document).ready(function () {
		
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
				<li><a href="member.do">Member</a></li>
				<li><a href="weeklyPoem.do">WeeklyPoem</a></li>
				<li><a href="weeklyPoemLikeIt.do">WeeklyLikeIt</a></li>
				<li class="active"><a href="relayPoemMain.do">RelayPoemMain</a></li>
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
					
					<h1>릴레이 시 수정</h1>
				</header>
				
				<section>
				
					<form method="post" action="relayPoemMainUpdate.do">
						<% out.print("<input type='hidden' name='index' id='index' value='" + selectedRelayPoemMainIndex + "' readOnly />"); %>
						
						<div class="field">
							<label for="subject">Subject</label>
							<% out.print("<input type='text' name='subject' id='subject' value='" + selectedRelayPoemMainSubject + "' />"); %>
						</div>
						<div class="field">
							<label for="title">Title</label>
							<% out.print("<input type='text' name='title' id='title' value='" + selectedRelayPoemMainTitle + "' />"); %>
						</div>
						<div class="field">
							<label for="contents">Contents</label>
							<% out.print("<textarea name='contents' id='contents' rows='8'>" + selectedRelayPoemMainContents + "</textarea>"); %>
						</div>
						<div class="field">
							<label for="poets">Poets</label>
							<% out.print("<input type='text' name='poets' id='poets' value='" + selectedRelayPoemMainPoets + "' />"); %>
						</div>
						
						<ul class="actions">
							<li><input type="submit" value="Update Relay Poem" /></li>
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