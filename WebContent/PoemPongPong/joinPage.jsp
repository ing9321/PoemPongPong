<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="PoemPongPong/assets/css/joinPage.css?v=2017004_3" type="text/css">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
<script src="//cdn.jsdelivr.net/jquery/2.2.0/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function () {
		var passIdEquals = false;
		var passEmailEquals = false;
		
		// 실시간 json 통신으로 아이디 중복검사
		$("#joinId").keyup(function() {
			var joinId = $("#joinId").val();
	        
	        $.ajax({
	           	type: 'post',
	            url: 'PoemPongPong/jsonCheckId.jsp',
	            data: { joinId : joinId },
	            dataType: 'json',
	            success: function(data){
	                idRedundancyCheck(data);
	            }, error: function (req) {
	                alert('상태: ' + req.status + ", " + req.responseText + ", error : " + req.error);
	            }
	        });
		});
		
		// 비밀번호와 동일한지 검사를 keyup될 때마다
		$("#joinPwdChk").keyup(function () {
			var joinPwdChk = $("#joinPwdChk").val();
			var joinPwd = $("#joinPwd").val();
			
			if(joinPwd != null && joinPwd != "") {
				if(joinPwd != joinPwdChk){
					$("#checkEqualPwd").html(" * 입력한 비밀번호가 같지 않습니다.");
				} else {
					$("#checkEqualPwd").html(" * 입력한 비밀번호가 같습니다.");
				}
			}
		});
		
		// 실시간 json 통신으로 이메일 중복검사
		$("#joinEmail").keyup(function(){
	        var joinEmail = $("#joinEmail").val();
	        
	        $.ajax({
	           type: 'post',
	            url: 'PoemPongPong/jsonCheckEmail.jsp',
	            data: { joinEmail : joinEmail },
	            dataType: 'json',
	            success: function(data){
	                emailRedundancyCheck(data);
	                
	            }, error: function (req) {
	                alert('상태: ' + req.status + ", " + req.responseText + ", error : " + req.error);
	            }
	        });
	    });
	});
    
	function idRedundancyCheck(data){
        console.log(data.length);
        var joinId = $('#joinId').val().trim();
        
        // 아이디 입력란이 빈칸이 아니고
        if(joinId != null && joinId != ""){
        	// 실시간 json 검사를 통해 중복된 아이디가 아닐 경우
	        if(data.length == 0){
	        	// 아이디 유효성 검사
	    		var idcheck = /^[a-zA-Z]{1}[a-zA-Z0-9_]{5,11}$/;
	    		if(idcheck.test(joinId)){
	    			// 유효성 검사를 통과했으면
	    			$("#checkId").html(" * 사용 가능한 아이디입니다");
	    			// $('#informSubmit').prop('disabled', false);
	    			passIdEquals = true;
	    		} else {
	    			// 유효성 통과 실패
	    			$("#checkId").html(" * 조건에 맞는 아이디를 입력해주세요");
	    			// $('#informSubmit').prop('disabled', true);
	    			passIdEquals = false;
	    		}
	        	
	        }else{
	        	// 중복된 아이디
	            $("#checkId").html(" * 이미 가입된 아이디입니다");
	            // $('#informSubmit').prop('disabled', true);
	            passIdEquals = false;
	        }
        } else {
        	// 입력된 아이디가 없음
        	$("#checkId").html("");
        	// $('#informSubmit').prop('disabled', true);
        	passIdEquals = false;
        }
    }
	
	function emailRedundancyCheck(data){
        console.log(data.length);
        var joinEmail = $('#joinEmail').val().trim();
        
        // 이메일 입력란이 빈칸이 아니고
        if(joinEmail != null && joinEmail != ""){
        	// 실시간 json 검사를 통해 중복된 이메일이 아닐 경우
	        if(data.length == 0){
	        	// 이메일 유효성 검사
	    		var emailcheck = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
	            if(emailcheck.test(joinEmail)){
	            	// 유효성 검사를 통과했으면
	            	$("#checkEmail").html(" * 사용 가능한 이메일입니다");
	            	// $('#informSubmit').prop('disabled', false);
	            	passEmailEquals = true;
	        	} else {
	            	// 유효성 검사 통과 실패
	            	$("#checkEmail").html(" * 올바른 형식의 이메일이 아닙니다");
	            	// $('#informSubmit').prop('disabled', true);
	            	passEmailEquals = false;
	    		}
	        	
	        }else{
	        	// 중복된 이메일
	            $("#checkEmail").html(" * 이미 가입한 이메일입니다");
	            // $('#informSubmit').prop('disabled', true);
	            passEmailEquals = false;
	        }
        } else {
        	// 입력된 이메일이 없음
        	$("#checkEmail").html("");
        	// $('#informSubmit').prop('disabled', true);
        	passEmailEquals = false;
        }
    }
	
	function check(){
		
		var joinId = $('#joinId').val().trim();
		var joinPwd = $('#joinPwd').val().trim();
		var joinPwdChk = $('#joinPwdChk').val().trim();
		var joinEmail = $('#joinEmail').val().trim();
		
		var check = ""; // 유효성 정규식
		var checkId = false;
		var checkPwd = false;
		var checkPwd2 = false;
		var checkEmail = false;
		var error = false;
		
		// 아이디 유효성 검사
		check = /^[a-zA-Z]{1}[a-zA-Z0-9_]{5,11}$/;
		if(check.test(joinId)){
			checkId = true;
		} else {
			console.log("ID : FAIL");
			$("#checkId").html(" * 조건에 맞는 아이디를 입력해주세요");
			error = true;
		}
		
		// 비밀번호 유효성 검사
		check = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{7,19}$/;
		if(check.test(joinPwd)){
			checkPwd = true;
			// 비밀번호와 확인번호 동일한지 검사
			if(joinPwd != joinPwdChk) {
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
		
		// 이메일 유효성 검사
		check = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
        if(check.test(joinEmail))
            checkEmail = true;
        else{
        	console.log("EMAIL : FAIL");
        	$("#checkEmail").html(" * 올바른 형식의 이메일이 아닙니다");
			error = true;
		}
		
     	// 모든 유효성 검사가 통과됐으면 return true;
        if(checkId && checkPwd && checkPwd2 && checkEmail && passIdEquals && passEmailEquals) {
        	return true;
        } else {
        	return false;
        }
		
	}
</script>


<title>PoemPongPong</title>
</head>
<body>
	<jsp:include page="loginInclude.jsp"/>
	<jsp:include page="navbar.jsp"/>
	
	<div class="bannerContainer"></div>
	<div id="logoGoHome" onclick="location.href = 'poemIndex.jsp'"></div>
	
	<div class="joinContainer">
		<div class="letterJoin">&lt;회원가입&gt;</div>
		
		<form method="post" action="memberJoin.do" onsubmit="return check();">
			<input type="hidden" value="0" name="joinPath"/>
            <div class="informationList">
            	<div class="informIcon"></div>
            	<label for="joinId" class="informLabel"> Id 입력해 주세퐁 </label><br/>
            	<span class="informSpan">(Id는 6~12자 사이의 영대소문자로 시작하고, 숫자와 _를 포함할수 있습니다.)</span>
            </div>
			<div class="_blank">
				<input type ="text" name="joinId" id="joinId" class="informInputText"/>
				<label id="checkId"></label>
			</div>
			
			<br />
			
			<div class="informationList">
            	<div class="informIcon"></div>
            	<label for="joinPwd" class="informLabel"> Password 입력해 주세퐁 </label><br/>
            	<span class="informSpan">(비밀번호는 8~20자 사이의 영대소문자, 특수문자, 숫자를 포함해야 합니다.)</span>
            </div>
			<div class="_blank">
				<input type ="password" name="joinPwd" id="joinPwd" class="informInputText" />
				<label id="checkPwd"></label>
			</div>
			
			<br />
			
            <div class="informationList">
            	<div class="informIcon"></div>
            	<label for="confirmPwd" class="informLabel"> Password 한번더 입력해 주세퐁 </label><br/>
            	<span class="informSpan">(비밀번호 확인을 위해 비밀번호를 한 번 더 입력해주세요.)</span>
            </div>
			<div class="_blank">
				<input type ="password" name="confirmPwd" id="joinPwdChk" class="informInputText" />
				<label id="checkEqualPwd"></label>
			</div>
			
			<br />
			
            <div class="informationList">
            	<div class="informIcon"></div>
            	<label for="joinEmail" class="informLabel"> Email 입력해 주세퐁 </label><br/>
            	<span class="informSpan">(아이디와 비밀번호를 찾을 때 필요합니다.)</span>
           	</div>
           	<div class="_blank">
				<input type="email" name="joinEmail" id="joinEmail" class="informInputText"/>
				<label id="checkEmail"></label>
			</div>
		
			<div id="informBtn">
				<input type="reset" value="" onclick="history.go(-1);" id="informReset"/>
				<input type="submit" value="" id="informSubmit"/>
			</div>
		</form>
	</div>
</body>
</html>