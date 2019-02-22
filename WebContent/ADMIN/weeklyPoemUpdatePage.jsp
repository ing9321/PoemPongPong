<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Timestamp"
		import="com.poem.common.vo.WeeklyPoemJoinVO"
		import="com.poem.common.vo.SubjectVO"
		import="java.util.ArrayList" %>

<% WeeklyPoemJoinVO selectedPoem = (WeeklyPoemJoinVO)request.getAttribute("selectedPoem"); %>
<% ArrayList<SubjectVO> subjectList = (ArrayList<SubjectVO>)request.getAttribute("subjectList"); %>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page - Weekly Poem Update</title>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="ADMIN/assets/css/main.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
	$(document).ready(function () {
		var isBestPoem = <%=selectedPoem.getW().getBestPoem() %>
		if(isBestPoem != 1) {
			$('#isNotBestPoem').attr('checked', true);
		} else {
			$('#isBestPoem').attr('checked', true);
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
				<li><a href="member.do">Member</a></li>
				<li class="active"><a href="weeklyPoem.do">WeeklyPoem</a></li>
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
					
					<h1>7일 시집 수정</h1>
				</header>
				
				<section>
				
				<!-- UploadDate는 수정할 때 새로 고치던지 원래 값 남겨두던지 하려고 form에 포함시키지 않음 -->
				 
					<form method="post" action="weeklyPoemUpdate.do">
						<% out.print("<input type='hidden' name='index' id='index' value='" + selectedPoem.getW().getWeeklyPoemIndex() + "' />"); %>
						
						<div class="field">
							<label for="subject">Subject</label>
							<select name="subject">
								<!-- option태그로 subject 목록 호출.. el 못쓰게따아.. -->
								<%
									String subjectOption = "";
									for(int i = 0; i < subjectList.size(); i++) {
										subjectOption += "<option value='" + subjectList.get(i).getSubjectName() + "'>" + subjectList.get(i).getSubjectName() + "</option>";
									}
									out.print(subjectOption);
								%>
							</select>
						</div>
						<div class="field">
							<label for="poet">Poet</label>
							<% out.print("<input type='text' name='poet' id='poet' value='" + selectedPoem.getM().getMemberPoet() + "' />"); %>
						</div>
						<div class="field">
							<label for="title">Title</label>
							<% out.print("<input type='text' name='title' id='title' value='" + selectedPoem.getW().getTitle() + "' />"); %>
						</div>
						<div class="field">
							<label for="contents">Contents</label>
							<% out.print("<textarea name='contents' id='contents' rows='8'>" + selectedPoem.getW().getContents() + "</textarea>"); %>
						</div>
						<div class="field">
							<label for="viewCount">View Count</label>
							<% out.print("<input type='number' name='viewCount' id='viewCount' value='" + selectedPoem.getW().getViewCount() + "'/>"); %>
						</div>
						<div class="field">
							<label for="likeCount">Like Count</label>
							<% out.print("<input type='number' name='likeCount' id='likeCount' value='" + selectedPoem.getW().getLikeCount() + "'/>"); %>
						</div>
						<div class="field">
							<label for="bestPoem">Best Poem</label>
							<input type="radio" id="isNotBestPoem" name="bestPoem" value="0" >
                            <label for="isNotBestPoem">NO</label>
							<input type="radio" id="isBestPoem" name="bestPoem" value="1" >
                            <label for="isBestPoem">YES</label>
						</div>
						<%-- <div class="field">
							<label for="background">Background</label>
							<!-- 배경지정 방법에 따라 수정 필요 -->
							<% out.print("<input type='text' name='background' id='background' value='" + selectedPoem.getW().getBackground() + "' />"); %>
						</div> --%>
						<ul class="actions">
							<li><input type="submit" value="Update WeeklyPoem" /></li>
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