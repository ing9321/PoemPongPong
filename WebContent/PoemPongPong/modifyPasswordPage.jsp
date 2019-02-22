<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String memberID = (String)session.getAttribute("loginMember");
	String curPwdEqual = (String)request.getAttribute("curPwdEqual");%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
<script src="//cdn.jsdelivr.net/jquery/2.2.0/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function () {

		var curPwdEqual = "<%=curPwdEqual %>";
		if(curPwdEqual == "incorrect"){
			alert("현재 비밀번호가 일치하지 않습니다!");
		}
		
		// 비밀번호와 동일한지 검사를 keyup될 때마다
		$("#myNewPwdChk").keyup(function () {
			var myNewPwdChk = $("#myNewPwdChk").val();
			var myNewPwd = $("#myNewPwd").val();
			
			if(myNewPwd != null && myNewPwd != "") {
				if(myNewPwd != myNewPwdChk){
					$("#checkEqualPwd").html(" * 입력한 비밀번호가 같지 않습니다.");
				} else {
					$("#checkEqualPwd").html(" * 입력한 비밀번호가 같습니다.");
				}
			}
		});
	});
	
	function check(){
		
		var myNewPwd = $('#myNewPwd').val().trim();
		var myNewPwdChk = $('#myNewPwdChk').val().trim();
		
		var check = "";
		var checkPwd = false;
		var checkPwd2 = false;
		var error = false;
		
		// 비밀번호 유효성 검사
		check = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{7,19}$/;
		if(check.test(myNewPwd)){
			checkPwd = true;
			// 비밀번호와 확인번호 동일한지 검사
			if(myNewPwd != myNewPwdChk) {
				console.log("PW : NOT EQUAL");
				$("#checkEqualPwd").html(" * 비밀번호가 일치하지 않습니다.");
				error = true;
			} else {
				// 유효성도 통과하고 비밀번호도 동일한 경우
				checkPwd2 = true;
			}
		} else {
			console.log("PW : FAIL");
			$("#checkPwd").html(" * 조건에 맞는 비밀번호를 입력해주세요.");
			error = true;
		}
		
     	// 모든 유효성 검사가 통과됐으면 return true;
        if(checkPwd && checkPwd2) {
        	return true;
        } else {
        	return false;
        }
	}
</script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/PoemPongPong/assets/css/modifyPasswordPage.css?v=20170707_3" type="text/css">
<title>PoemPongPong</title>
</head>
<body>

	<jsp:include page="navbar.jsp"/>
	<jsp:include page="loginInclude.jsp"/>
	
	<div id="bannerContainer"></div>
	
	<div id="logoGoHome" onclick="location.href = 'poemIndex.jsp'"></div>
	
	<div id="modifyContainer">
	
		<div class="letterModify">&lt; 비밀번호 수정 &gt;</div>
		
		<form method="post" action="modifyPwd.do" onsubmit="return check();">
	
			<div class="informationList">
				<div class="informIcon"></div>
				<label for="myOldPwd" class="informLabel"> 현재 비밀번호 </label><br/>
			</div>
			<div class="_blank">
				<input type="password" name="myOldPwd" id="myOldPwd" class="informInputText" />
				<span id="checkMyOldPwd" class="informSpan"></span>
			</div>
			
			<div class="informationList">
				<div class="informIcon"></div>
				<label for="myNewPwd" class="informLabel"> 새로운 비밀번호 </label><br/>
				<span class="informSpan">(비밀번호는 8~20자 사이의 영대소문자, 특수문자, 숫자를 포함해야 합니다.)</span>
			</div>
			<div class="_blank">
				<input type="password" name="myNewPwd" id="myNewPwd" class="informInputText" />
				<span id="checkPwd" class="informSpan"></span>
			</div>
			
			<div class="informationList">
				<div class="informIcon"></div>
				<label for="myNewPwdChk" class="informLabel"> 새로운 비밀번호 확인 </label><br/>
				<span class="informSpan">(비밀번호 확인을 위해 비밀번호를 한 번 더 입력해주세요.)</span>
			</div>
			<div class="_blank">
				<input type="password" name="myNewPwdChk" id="myNewPwdChk" class="informInputText" />
				<span id="checkEqualPwd" class="informSpan"></span>
			</div>

			<div id="informBtn">
				<input type="button" value="" onclick="history.go(-1);" id="modifyCancelBtn" />
				<input type="submit" value="" id="modifySubmit" />
			</div>
		</form>
	
	</div>

	<div id="divFooter"></div>
</body>
</html>