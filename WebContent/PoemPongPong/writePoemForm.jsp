<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String loginMember = (String)session.getAttribute("loginMember");
	request.setAttribute("loginMember",loginMember);
%>
    
    
<!DOCTYPE html>
<html>
<head> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/PoemPongPong/assets/css/writePoemForm.css?v_20170701_77" type="text/css">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
<script src="//cdn.jsdelivr.net/jquery/2.2.0/jquery.min.js"></script>
<meta charset="UTF-8">
<title>PoemPongPong</title>
</head>
<body>
    <div id="right-bar">
        <div class="write-content">
        		
            <form action="writePoemUser.do" method="post" onsubmit="return validate();">
	        		<div class="writePoem-content">
		            	<input type="hidden" id="modifyIndex" value="" name="modifyIndex"/>
		            	<input type="hidden" id="writeMethod" value="" name="writeMethod"/> 
	                	<input type="hidden" id="writeBackground" value="${pageContext.request.contextPath}/PoemPongPong/images/background1.jpeg" name="background"/>
	                
	                	<div>
	                		<div id ="subjectType">
		                		주제 : <input type="text" id="form-subject-name" value="${subject_name}" name="subject_name" readonly/>
	                		</div>
		                	<input type="text" id="form-title" class="writePoem-title" name="title" placeholder="TITLE"/>
	                	</div>
	                	<div>
		                <textarea id="form-contents" class="writePoem-contents" name="contents" placeholder="시를 입력해주세퐁"></textarea>
	                	</div>		        		
	        		</div>
	        		<div style="width:150px; margin:auto; text-align: center;">
		        		<input type="submit" value="등 록" class="write-submitBtn"/>
	        		</div>
	        		
            </form> 
	        		
	        <div class="write-background">
		        <input type="button" class="write-background-thumbnail" onclick="backgroundChange('1')" style="background-image:url(${pageContext.request.contextPath}/PoemPongPong/images/background1.jpeg)"/>
		        <input type="button" class="write-background-thumbnail" onclick="backgroundChange('2')" style="background-image:url(${pageContext.request.contextPath}/PoemPongPong/images/background2.jpeg)"/>
		        <input type="button" class="write-background-thumbnail" onclick="backgroundChange('3')" style="background-image:url(${pageContext.request.contextPath}/PoemPongPong/images/background3.jpeg)"/> 
		        <input type="button" class="write-background-thumbnail" onclick="backgroundChange('4')" style="background-image:url(${pageContext.request.contextPath}/PoemPongPong/images/background4.jpeg)"/>        
	        </div>
        </div>
		<div class="write-needLogin">
			로그인을 해주세퐁
		</div>
		<button id="right-btn" class="sidebar-btn">OPEN</button>
    </div>
    
    <script>
        function backgroundChange(version){
       		var path = "${pageContext.request.contextPath}/PoemPongPong/images/background" + version + ".jpeg";
        		
            $(".writePoem-content").css("background-image", "url(" + path + ")");
            $("#writeBackground").val(path);
        }
        
	    $(document).ready(function() {
		    	var loginMember = "${loginMember}";
			if(loginMember == null || loginMember ==""){
				// 로그인 안했을 경우
				$(".write-content").css("display", "none");
				$(".write-needLogin").css("display", "block");
			}else{
				// 로그인 했을 경우 
				$(".write-content").css("display", "block");
				$(".write-needLogin").css("display", "none");
			}
    	
	    	
            var right_bar = $("#right-bar");
            var right_btn = $("#right-btn");
            
            right_btn.click(function(){
                var state = right_btn.text();
                
                if(state == "OPEN"){
                    right_bar_open();
                
                    // 새로 시 쓸때 
                    $("#writeMethod").val("newPoem");
                    $("#form-subject-name").val("${subject_name}");
                    $("#form-title").val("");
                    $("#form-contents").html("");
                }
                else if(state == "CLOSE")
                    right_bar_close();
            });
            
            function right_bar_open(){
                right_bar.css("right", "0px");
                right_btn.text("CLOSE");
            };

            function right_bar_close(){
                right_bar.css("right", "-800px");
                right_btn.text("OPEN");
            };
            
            $(document).mouseup(function(e){
                if(right_bar.has(e.target).length === 0){
                    right_bar_close();
                }
            });
            
            $("#form-contents").on('keydown keyup', function () {
				$(this).height(1).height( $(this).prop('scrollHeight')+12 );	
            	});
	    });
	    
	    // 유효성 검사
	    function validate(){
		    	if($('#form-title').val() == "" || $('#form-title').val() == null){
					alert("제목을 입력해주세퐁");
					$('#form-title').focus();
					return false;
				}
		    	
		    	if($('#form-title').val().length > 20){
					alert("제목이 너무 길어퐁");
					$('#form-title').focus();
					return false;
				}
		    	
		    	if($('#form-contents').val() == "" || $('#form-title').val() == null){
					alert("내용을 입력해주세퐁");
					$('#form-contents').focus();
					return false;
				}
		    	
		    	return true;
	    };
    </script>
</body>
</html>