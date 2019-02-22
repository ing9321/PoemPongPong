<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String joinId = (String)request.getAttribute("joinId");
	String joinPwd = (String)request.getAttribute("joinPwd");
	String joinEmail = (String)request.getAttribute("joinEmail");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="PoemPongPong/assets/css/joinPage.css?v=2017005_1" type="text/css">
<link rel="stylesheet" href="PoemPongPong/assets/css/joinPage2.css?v=2017005_2" type="text/css">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
<script src="//cdn.jsdelivr.net/jquery/2.2.0/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function () {
		// 실시간 json 통신으로 닉네임 중복검사
		$("#joinPoet").keyup(function(){
	        var joinPoet = $("#joinPoet").val();
	        
	        $.ajax({
	           type: 'post',
	            url: 'PoemPongPong/jsonCheckPoet.jsp',
	            data: { joinPoet : joinPoet },
	            dataType: 'json',
	            success: function(data){
	                poetRedundancyCheck(data);
	                
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
    
    function poetRedundancyCheck(data){
        console.log(data.length);
        
        var joinPoet = $('#joinPoet').val().trim();
        
        // 닉네임 입력칸이 빈칸이 아닌 경우
        if(joinPoet != null && joinPoet != ""){
        	// 닉네임이 중복되지 않을 경우
	        if(data.length == 0){
	        	var poetcheck = /^[ㄱ-ㅎ가-힣a-zA-Z\*]{3,15}$/;
	        	// 시인명 유효성 검사
	    		if(poetcheck.test(joinPoet)){
	    			$("#checkPoet").html(" * 사용 가능한 닉네임입니다");
	    			$('#informSubmit').prop('disabled', false);
	    		} else {
	    			// 유효성 검사 통과 실패
	    			$("#checkPoet").html(" * 조건에 맞는 닉네임을 설정해 주세요");
	    			$('#informSubmit').prop('disabled', true);
	    		}
	            
	        }else{
	        	// 닉네임 중복
	            $("#checkPoet").html(" * 이미 있는 닉네임입니다");
	            $('#informSubmit').prop('disabled', true);
	        }
	    } else {
	    	// 닉네임 빈칸
	    	$("#checkPoet").html("");
	    	$('#informSubmit').prop('disabled', true);
	    }
    }
    
	function check(){
		var joinPoet = $('#joinPoet').val().trim();
		
		var check = /^[ㄱ-ㅎ가-힣a-zA-Z\*]{3,15}$/;
		var checkPoet = false;
		
		// 시인명 유효성 검사
		if(check.test(joinPoet)){
			console.log("유효성 통과");
			checkPoet = true;
		} else {
			$("#checkPoet").html(" * 조건에 맞는 닉네임을 설정해 주세요");
		}
		
		if(checkPoet){
			return true;
		} else {
			return false;
		}
		
	}
	
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
			
		<form method="post"  action="memberJoin.do" onsubmit="return check();">
			<input type ="hidden" value="${joinId }" name="joinId"/><br />
			<input type ="hidden" value="${joinPwd }" name="joinPwd"/><br />
			<input type ="hidden" value="${joinEmail }" name="joinEmail"/><br />
			<input type ="hidden" value="${joinPath }" name="joinPath"/><br />
			
            <div class="informationList">
            	<div class="informIcon"></div>
				<label for="joinPoet" class="informLabel"> Poet 입력해 주세퐁 </label>
				<br/>
				<span class="informSpan">(시를 쓸 때 사용할 닉네임을 입력해주세요.)</span>
            </div>
			<div class="_blank">
				<input type="text" name="joinPoet" id="joinPoet" class="informInputText"/>
				<span id="checkPoet" class="informSpan"></span>
			</div>
			
			<div class="informationList">
				<div class="informIcon"></div>
				<label for="joinPhoto" class="informLabel"> 캐릭터를 선택해 주세퐁 </label>
				<div id="informRadio">
					<div id="informRadioPhoto">
						<div class="photoPongs" id="photoPong1"></div>
						<div class="photoPongs" id="photoPong2"></div>
						<div class="photoPongs" id="photoPong3"></div>
						<div class="photoPongs" id="photoPong4"></div>
					</div>
					<div id="informRadioText">
						<input type="radio" value ="PoemPongPong/images/pong1.png" name="joinPhoto" id="textPong1" checked />
						<input type="radio" value ="PoemPongPong/images/pong2.png" name="joinPhoto" id="textPong2" />
						<input type="radio" value ="PoemPongPong/images/pong3.png" name="joinPhoto" id="textPong3" />
						<input type="radio" value ="PoemPongPong/images/pong4.png" name="joinPhoto" id="textPong4" />
					</div>
				</div>
			</div>
			
			<div id="informBtn">	
				<input type="reset" value="" onclick="location.href='UserIndexLogin.do'" id="informReset" />
				<input type="submit" value="" id="informSubmit" />
			</div>
		</form>
	</div>
</body>
</html>