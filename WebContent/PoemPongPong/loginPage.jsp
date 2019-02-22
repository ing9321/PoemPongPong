<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.poem.common.vo.MemberVO" %>
 <% String loginResult = (String)request.getAttribute("loginResult");
 	if(loginResult == null || loginResult == "") {
 		loginResult = "john";
 	}%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="//cdn.jsdelivr.net/jquery/2.2.0/jquery.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/PoemPongPong/assets/css/loginPage.css?v=20170624_38" type="text/css">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<title>PoemPongPong</title>
</head>
<script type='text/javascript'>
    // SNS *******************************************************

        // kakao
        // 사용할 앱의 JavaScript 키를 설정해 주세요.
        Kakao.init('f78dfe2494ae0f1148adc31cc590711b');
        function loginWithKakao() {
          // 로그인 창을 띄웁니다.
            Kakao.Auth.login({
            success: function(userInfo) {
                Kakao.API.request({
                    url: '/v1/user/me',
                    success: function(res){
                        console.log(res);
                        infomation(res);
                    },
                    fail: function(error){
                        alert(JSON.stringify(error));
                    }
                })
            },
            fail: function(err) {
                alert(JSON.stringify(err));
                }
            });
        };        

        // 로그인 성공 후 정보를 받아오는 메서드
        function infomation(userInfo){            
            var form = document.createElement("form");
            form.setAttribute("charset", "UTF-8");
            form.setAttribute("method", "Post");
            form.setAttribute("action", "memberJoin.do");
            
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", "joinId");
            hiddenField.setAttribute("value", userInfo.id);
            form.appendChild(hiddenField);
            
            hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", "joinPwd");
            hiddenField.setAttribute("value", userInfo.id);
            form.appendChild(hiddenField);
            
            hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", "joinEmail");
            hiddenField.setAttribute("value", "poem@pong.pong");
            form.appendChild(hiddenField);
            
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", "joinPath");
            hiddenField.setAttribute("value", 2);		// 카카오톡 2
            form.appendChild(hiddenField);
            
            document.body.appendChild(form);
            
            form.submit();
        };

</script>
<script>
    //페이스북 로그인
    user = "";
    function statusChangeCallback(response) {
        console.log('statusChangeCallback');
        if (response.status === 'connected') {
            user = response;
            loginComplete();
        } else {
            // document.getElementById('status').innerHTML = 'Please log ' + 'into this app.';
        }
    }

    function checkLoginState() {
        FB.getLoginStatus(function(response) {
        statusChangeCallback(response);
        });
    }

    window.fbAsyncInit = function() {
        FB.init({
            appId      : '{952556204886430}',
            cookie     : true,  // enable cookies to allow the server to access 
                    // the session
            xfbml      : true,  // parse social plugins on this page
            version    : 'v2.8' // use graph api version 2.8

        });

        FB.getLoginStatus(function(response) {
            statusChangeCallback(response);
        });

        FB.Event.subscribe('auth.login', function(response) {
            document.location.reload();
        });
    };


    (function(d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) return;
        js = d.createElement(s); js.id = id;
        js.src = "//connect.facebook.net/ko_KR/sdk.js#xfbml=1&version=v2.8&appId=952556204886430";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));


    function loginComplete() {
    	// 로그인 성공 후 정보를 받아오는 메서드
        var form = document.createElement("form");
        form.setAttribute("charset", "UTF-8");
        form.setAttribute("method", "Post");
        form.setAttribute("action", "memberJoin.do");

        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "joinId");
        hiddenField.setAttribute("value", user.authResponse.userID);
        form.appendChild(hiddenField);

        hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "joinPwd");
        hiddenField.setAttribute("value", user.authResponse.userID);
        form.appendChild(hiddenField);

        hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "joinEmail");
        hiddenField.setAttribute("value", "poem@pong.pong");
        form.appendChild(hiddenField);

        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "joinPath");
        hiddenField.setAttribute("value", 1);		// 페이스북 1
        form.appendChild(hiddenField);
        
        document.body.appendChild(form);
        
        FB.logout();
        
        form.submit();
    }
    
</script>
<script type="text/javascript">
	
	$(document).ready(function() {

		var loginResult = "<%=loginResult %>";
		console.log(loginResult);
		if(loginResult != "john"){
			console.log("!");
			alert(loginResult);
		}
	});
</script>
<body>
	<jsp:include page="navbar.jsp"/>
	<div id="logoGoHome" onclick="location.href = 'poemIndex.jsp'"></div>
	<div id="bannerContainer"></div>
	
	<div id ="caseOfLogin" >
			<div id="letterLogin">Login</div>
				<form method="post" action="UserIndexLogin.do">
					<div id="divID">
						<input type="hidden" value="login" name="loginBtn"/>
					    <div id="loginId">Id</div>
					    <div id = "insertFormId" ><input type="text" class="textForm" tabindex="1" name="userId"/></div>
					    <div class="btnDiv"><input type="submit" id ="loginBtn" tabindex="3" value=""/></div>
					</div>
					<div id="divPwd">
					    <div id="loginPwd">Password</div>
					    <div id ="insertFormPwd" ><input type="password" class="textForm" tabindex="2" name="userPwd"/></div>
					    <div class="btnDiv"><input type="button"  id="joinBtn" value="" onclick="location.href ='memberJoin.do';"/></div>
					</div>
				</form>
			
			<div id="snsLogo">
                <input type="button" id ="facebook" name="facebook" value="" onclick="FB.login();"/>
                <a id="custom-login-btn" href="javascript:loginWithKakao()">
				    <input type="button" id ="kakao" name="kakao" value=""/>
                </a>
			</div>
		    <div id = "findIdNpwdDiv"><input type="button" id="findIdNpwd" value="" onclick="location.href='findUserInfo.do';" /></div>
		
	</div>
</body>
</html>