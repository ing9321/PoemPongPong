<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.poem.common.vo.SubjectVO"
		 import="java.util.ArrayList" %>
<% ArrayList<SubjectVO> subjectList = (ArrayList<SubjectVO>)request.getAttribute("subjectList"); %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page - Relay Poem Insert</title>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="ADMIN/assets/css/main.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
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
					
					<h1>릴레이 시 추가</h1>
				</header>
				
				<section>
					<form method="post" action="relayPoemMainInsert.do">
						
						<div class="field">
							<label for="subject">Subject</label>
							<select name="subject">
								<!-- option태그로 subject 목록 호출.. el 못쓰게따아.. -->
								<%
									String subjectOption = "";
									for(int i = 0; i < subjectList.size(); i++) {
										subjectOption += "<option value='" + subjectList.get(i).getSubjectName() + "'>";
										subjectOption += subjectList.get(i).getSubjectName() + "</option>";
									}
									out.print(subjectOption);
								%>
							</select>
						</div>
						<div class="field">
							<label for="title">Title</label>
							<input type="text" name="title" id="title" />
						</div>
						<div class="field">
							<label for="contents">Contents</label>
							<textarea name="contents" id="contents" rows="8"></textarea>
						</div>
						<div class="field">
							<label for="poets">Poets</label>
							<input type="text" name="poets" id="poets" />
						</div>
						<ul class="actions">
							<li><input type="submit" value="Insert Relay Poem" /></li>
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