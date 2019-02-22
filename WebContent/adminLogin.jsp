<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Poem PongPong</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
		<link rel="stylesheet" href="ADMIN/assets/css/index.css?ver=2" />
		<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
		<!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
	</head>
	<body class="loading">
		<div id="wrapper">
			<div id="bg"></div>
			<div id="overlay"></div>
			<div id="main">

				<!-- Header -->
					<header id="header">
						<h1>Poem PongPong</h1>
						<p>${msg}</p>
						<form action="adminLogin.do" method="post">
							<div id="loginForm">
								<input type="text" class="boxColor" name="id" placeholder="ID"/><br/>
								<input type="password" class="boxColor" name="password" placeholder="PASSWORD"/><br/>
								<input type="submit" class="boxColor" value="LOGIN"/>
							</div>
						</form>
						<nav id="navForm">
							<ul>
								<li><a href="member.do" class="icon fa-users"><span class="label">Member</span></a></li>
								<li><a href="weeklyPoem.do" class="icon fa-dribbble"><span class="label">Weekly<br/>Poem</span></a></li>
								<li><a href="weeklyPoemLikeIt.do" class="icon fa-thumbs-up"><span class="label">WeeklyPoem<br/>LikeIt</span></a></li>
								<li><a href="relayPoemMain.do" class="icon fa-plane"><span class="label">RelayPoem<br/>Main</span></a></li>
								<li><a href="relayPoemReply.do" class="icon fa-ship"><span class="label">RelayPoem<br/>Reply</span></a></li>
								<li><a href="relayPoemLikeIt.do" class="icon fa-thumbs-up"><span class="label">RelayPoem<br/>LikeIt</span></a></li>
								<li><a href="subject.do" class="icon fa-dribbble"><span class="label">Subject</span></a></li>
							</ul>
						</nav>
						<br/>
						<br/>
						<form action="adminLogout.do" method="post">
							<div id="logoutForm">
								<input type="submit" class="boxColor" value="LOGOUT"/>
							</div>
						</form>
					</header>

				<!-- Footer -->
					<footer id="footer">
						<span class="copyright">&copy; Untitled. Design: <a href="#">Poem PongPong</a>.</span>
					</footer>

			</div>
		</div>
		<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->

		<script>
			window.onload = function() { document.body.className = ''; }
			window.ontouchmove = function() { return false; }
			window.onorientationchange = function() { document.body.scrollTop = 0; }
		</script>
		
		<%
			if(session.getAttribute("loginAdmin") == null){
				// 로그인 안된 상황
		%>
				<script>
					document.getElementById("loginForm").style.display = "block";
					document.getElementById("navForm").style.display = "none";
					document.getElementById("logoutForm").style.display = "none";
				</script>
		<%
			} else {
				// 로그인 된 상황
		%>
				<script>
					document.getElementById("loginForm").style.display = "none";
					document.getElementById("navForm").style.display = "block";
					document.getElementById("logoutForm").style.display = "block";
				</script>
		<%
			}
		%>
	</body>
</html>