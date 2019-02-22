<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.poem.common.vo.MemberVO" %>
<% String loginMember = (String)session.getAttribute("loginMember");
	MemberVO myInfo = (MemberVO)request.getAttribute("myInfo"); 
	String memberID = (String)request.getAttribute("memberID");
	String memberPoet = (String)request.getAttribute("memberPoet");
	String memberEmail = (String)request.getAttribute("memberEmail");
	String memberPhoto = (String)request.getAttribute("memberPhoto");
	int memberPath = (int)request.getAttribute("memberPath");%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="//cdn.jsdelivr.net/jquery/2.2.0/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function () {
		// SNS로 가입했을 경우 비밀번호 수정버튼 비활성화
		var memberPath = "<%=memberPath %>";
		if(memberPath != 0){
			$("#btnChangePwd").prop("disabled", true);
			$("#myEmail").prop("readOnly", true);
		}
		
		// 캐릭터 라디오 버튼에 현재 활성화 버튼 설정
		var myPhotoPath = "<%=memberPhoto %>";
		console.log(myPhotoPath); // 얘가 전체 경로야..
		// 스크링에서 /로 나누고 3번째 애를 .으로 나눠서 비교
		var myPhoto = myPhotoPath.substring(20, 25);
		console.log(myPhoto);
		
		switch(myPhoto){
			case 'pong1':
				pong1clicked();
				$('#textPong1').attr('checked', true);
				break;
			case 'pong2':
				pong2clicked();
				$('#textPong2').attr('checked', true);
				break;
			case 'pong3':
				pong3clicked();
				$('#textPong3').attr('checked', true);
				break;
			case 'pong4':
				pong4clicked();
				$('#textPong4').attr('checked', true);
				break;
		}
		
		// 실시간 json 통신으로 닉네임 중복검사
		$("#myPoet").keyup(function(){
	        var myPoet = $("#myPoet").val();
	        var memberPoet = "<%=memberPoet %>";
	        $.ajax({
	           type: 'post',
	            url: 'PoemPongPong/jsonCheckPoet.jsp',
	            data: { joinPoet : myPoet, memberPoet : memberPoet },
	            dataType: 'json',
	            success: function(data){
	                poetRedundancyCheck(data);
	                
	            }, error: function (req) {
	                alert('상태: ' + req.status + ", " + req.responseText + ", error : " + req.error);
	            }
	        });
	    });
		
		// 실시간 json 통신으로 이메일 중복검사
		$("#myEmail").keyup(function(){
	        var myEmail = $("#myEmail").val();
	        var memberEmail = "<%=memberEmail %>";
	        $.ajax({
	           type: 'post',
	            url: 'PoemPongPong/jsonCheckEmail.jsp',
	            data: { joinEmail : myEmail, memberEmail : memberEmail },
	            dataType: 'json',
	            success: function(data){
	                emailRedundancyCheck(data);
	                
	            }, error: function (req) {
	                alert('상태: ' + req.status + ", " + req.responseText + ", error : " + req.error);
	            }
	        });
	    });

		// 캐릭터 라디오 버튼 눌렀을 때 이미지 변경 및 라디오 버튼 체크속성
		$('#photoPong1').click(function() {
			pong1clicked();
			// 라디오 버튼 체크
			$('#textPong1').prop('checked', true);
		});
		$('#photoPong2').click(function() {
			pong2clicked();
			// 라디오 버튼 체크
			$('#textPong2').prop('checked', true);
		});
		$('#photoPong3').click(function() {
			pong3clicked();
			// 라디오 버튼 체크
			$('#textPong3').prop('checked', true);
		});
		$('#photoPong4').click(function() {
			pong4clicked();
			// 라디오 버튼 체크
			$('#textPong4').prop('checked', true);
		});
		
		// 라디오 버튼 선택했을 때 이미지 변경
		$('input[name=joinPhoto]').change(function () {
			switch($(this).val()){
				case 'pong1':
					pong1clicked();
					break;
				case 'pong2':
					pong2clicked();
					break;
				case 'pong3':
					pong3clicked();
					break;
				case 'pong4':
					pong4clicked();
					break;
			}
		});
		
	});
	
	// 라디오버튼이나 캐릭터 클릭으로 바뀔 css
	function pong1clicked() {
		$('#photoPong1').css('background-image', 'url("PoemPongPong/images/pong1clicked.png")');
		$('#photoPong2').css('background-image', 'url("PoemPongPong/images/pong2.png")');
		$('#photoPong3').css('background-image', 'url("PoemPongPong/images/pong3.png")');
		$('#photoPong4').css('background-image', 'url("PoemPongPong/images/pong4.png")');
	}
	function pong2clicked() {
		$('#photoPong1').css('background-image', 'url("PoemPongPong/images/pong1.png")');
		$('#photoPong2').css('background-image', 'url("PoemPongPong/images/pong2clicked.png")');
		$('#photoPong3').css('background-image', 'url("PoemPongPong/images/pong3.png")');
		$('#photoPong4').css('background-image', 'url("PoemPongPong/images/pong4.png")');
	}
	function pong3clicked() {
		$('#photoPong1').css('background-image', 'url("PoemPongPong/images/pong1.png")');
		$('#photoPong2').css('background-image', 'url("PoemPongPong/images/pong2.png")');
		$('#photoPong3').css('background-image', 'url("PoemPongPong/images/pong3clicked.png")');
		$('#photoPong4').css('background-image', 'url("PoemPongPong/images/pong4.png")');
	}
	function pong4clicked() {
		$('#photoPong1').css('background-image', 'url("PoemPongPong/images/pong1.png")');
		$('#photoPong2').css('background-image', 'url("PoemPongPong/images/pong2.png")');
		$('#photoPong3').css('background-image', 'url("PoemPongPong/images/pong3.png")');
		$('#photoPong4').css('background-image', 'url("PoemPongPong/images/pong4clicked.png")');
	}


    function poetRedundancyCheck(data){
        console.log(data.length);
        
        var myPoet = $('#myPoet').val().trim();
        // 닉네임 입력칸이 빈칸이 아닌 경우
        if(myPoet != null && myPoet != ""){
        	// 닉네임이 중복되지 않을 경우
	        if(data.length == 0){
	        	var poetcheck = /^[ㄱ-ㅎ가-힣a-zA-Z\*]{3,15}$/;
	        	// 시인명 유효성 검사
	    		if(poetcheck.test(myPoet)){
	    			$("#checkPoet").html(" * 사용 가능한 닉네임입니다");
	    			$('#modifySubmit').prop('disabled', false);
	    		} else {
	    			// 유효성 검사 통과 실패
	    			$("#checkPoet").html(" * 3~15자 내의 한글 혹은 영문자로 이루어진 닉네임을 설정해 주세요");
	    			$('#modifySubmit').prop('disabled', true);
	    		}
	            
	        }else{
	        	// 닉네임 중복
	            $("#checkPoet").html(" * 이미 있는 닉네임입니다");
	            $('#modifySubmit').prop('disabled', true);
	        }
	    } else {
	    	// 닉네임 빈칸
	    	$("#checkPoet").html("");
	    	$('#modifySubmit').prop('disabled', true);
	    }
        return result;
    }
    
	// 이메일 중복검사
	function emailRedundancyCheck(data){
        console.log(data.length);
        var myEmail = $('#myEmail').val().trim();
        
        // 이메일 입력란이 빈칸이 아니고
        if(myEmail != null && myEmail != ""){
        	// 실시간 json 검사를 통해 중복된 이메일이 아닐 경우
	        if(data.length == 0){
	        	// 이메일 유효성 검사
	    		var emailcheck = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
	            if(emailcheck.test(myEmail)){
	            	// 유효성 검사를 통과했으면
	            	$("#checkEmail").html(" * 사용 가능한 이메일입니다");
	            	$('#modifySubmit').prop('disabled', false);
	            } else{
	            	// 유효성 검사 통과 실패
	            	$("#checkEmail").html(" * 올바른 형식의 이메일이 아닙니다");
	            	$('#modifySubmit').prop('disabled', true);
	    		}
	        	
	        }else{
	        	// 중복된 이메일
	            $("#checkEmail").html(" * 이미 가입한 이메일입니다");
	            $('#modifySubmit').prop('disabled', true);
	        }
        } else {
        	// 입력된 이메일이 없음
        	$("#checkEmail").html("");
        	$('#modifySubmit').prop('disabled', true);
        }
    }
	
	// 유효성검사
	function check(){
		
		var myPoet = $('#myPoet').val().trim();
		var myEmail = $('#myEmail').val().trim();
		console.log(myPoet);
		console.log(myEmail);
		
		var check = "";
		var checkPoet = false;
		var checkEmail = false;
		var error = false;
		
		// 시인명 유효성 검사
		check = /^[ㄱ-ㅎ가-힣a-zA-Z\*]{3,15}$/;
		if(check.test(myPoet)){
			console.log("유효성 통과");
			checkPoet = true;
		} else {
			$("#checkPoet").html(" * 조건에 맞는 닉네임을 설정해 주세요");
		}
		
		// 이메일 유효성 검사
		check = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
        if(check.test(myEmail))
            checkEmail = true;
        else{
        	console.log("EMAIL : FAIL");
        	$("#checkEmail").html(" * 올바른 형식의 이메일이 아닙니다");
			error = true;
		}
        
		if(checkPoet && checkEmail){
			return true;
		} else {
			return false;
		}

	}
</script>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/PoemPongPong/assets/css/modifyInfoPage.css?v=20170707_7" type="text/css">
<title>PoemPongPong</title>
</head>
<body>

	<jsp:include page="navbar.jsp"/>
	<jsp:include page="loginInclude.jsp"/>
	
	<div id="bannerContainer"></div>
	
	<div id="logoGoHome" onclick="location.href = 'poemIndex.jsp'"></div>
	
	<div id="modifyContainer">
		<div class="letterModify">&lt; 정보수정 &gt;</div>
	
		<form method="post" action="modifyInfo.do" onsubmit="return check();">
	
			<div class="informationList">
				<div class="informIcon"></div>
				<label for="myID" class="informLabel"> 아이디 </label>
			</div>
			<div class="_blank">
				<input type="text" id="myID" class="informInputText" value="<%=memberID %>" readOnly />
				<input type="hidden" name="myID" value="<%=loginMember %>" />
				<span class="informSpan"> * 아이디는 변경할 수 없습니다.</span>
			</div>
			
			<div class="informationList">
				<div class="informIcon"></div>
				<label class="informLabel"> 비밀번호 </label>
			</div>
			<div class="_blank">
				<input type="button" id="btnChangePwd" value="" onclick="location.href='modifyPwd.do'" />
				<span class="informSpan"> * 비밀번호를 변경하시려면 눌러주세요.</span>
			</div>
			
			<div class="informationList">
				<div class="informIcon"></div>
				<label for="myPoet" class="informLabel"> 시인명 </label>
			</div>
			<div class="_blank">
				<input type="text" name="myPoet" id="myPoet" class="informInputText" value="<%=memberPoet %>" />
				<span id="checkPoet" class="informSpan"></span>
			</div>
			
			<div class="informationList">
				<div class="informIcon"></div>
				<label for="myEmail" class="informLabel"> 이메일 </label>
			</div>
			<div class="_blank">
				<input type="text" name="myEmail" id="myEmail" class="informInputText" value="<%=memberEmail %>" />
				<span id="checkEmail" class="informSpan"></span>
			</div>
			
			<div class="informationList">
				<div class="informIcon"></div>
				<label for="myPhoto" class="informLabel"> 캐릭터 </label>
				<br/>
				<div id="informRadio">
					<div id="informRadioPhoto">
						<div class="photoPongs" id="photoPong1"></div>
						<div class="photoPongs" id="photoPong2"></div>
						<div class="photoPongs" id="photoPong3"></div>
						<div class="photoPongs" id="photoPong4"></div>
					</div>
					<div id="informRadioText">
						<input type="radio" value ="PoemPongPong/images/pong1.png" name="myPhoto" id="textPong1" />
						<input type="radio" value ="PoemPongPong/images/pong2.png" name="myPhoto" id="textPong2" />
						<input type="radio" value ="PoemPongPong/images/pong3.png" name="myPhoto" id="textPong3" />
						<input type="radio" value ="PoemPongPong/images/pong4.png" name="myPhoto" id="textPong4" />
					</div>
				</div>
			</div>
				
			<div id="informBtn">
				<input type="button" value="" onclick="history.go(-1);" id="modifyCancelBtn"/>
				<input type="submit" value="" id="modifySubmit" />
			</div>
		</form>
	
	</div>
	
	<div id="divFooter"></div>

</body>
</html>