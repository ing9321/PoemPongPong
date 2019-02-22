<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/PoemPongPong/assets/css/navbar.css?v=20170702_v37" type="text/css">
<script src="//cdn.jsdelivr.net/jquery/2.2.0/jquery.min.js"></script>
<meta charset="UTF-8">

<title>PoemPongPong</title>
</head>
<body>
    <div id="left-bar">
    		<div class="menu-content">
            <ul>
                <li><a href="allAboutPong.do"><span class="spanFontL">포엠퐁퐁</span><span class="spanFontS">이란?</span></a></li>
                <li><span class="spanFontL">퐁퐁</span><span class="spanFontS">의</span> <span class="spanFontL">시</span>
                		<ul class="subMenu">
                			<li><a href="weeklyBestPoemUser.do"><span class="spanFontS">베스트</span> <span class="spanFontM">퐁퐁</span></a></li>
                			<li><a href="weeklyPoemUser.do?poet=All"><span class="spanFontS">시인</span> <span class="spanFontM">퐁퐁</span></a></li>
                		</ul>
                </li>
                <li><a href="relayPoemUser.do"><span class="spanFontS">릴레이</span> <span class="spanFontM">퐁퐁</span></a></li>
                <li><a href="contactUs.do"><span class="spanFontL">Contact Us</span></a></li>
            </ul>
    		</div>
            <div class="menu-pong"></div>
 	   <button id="left-btn" class="sidebar-btn">OPEN</button>
    </div>
    
	<script>
	    $(document).ready(function() {
            var left_bar = $("#left-bar");
            var left_btn = $("#left-btn");
            
            $("#left-bar").click(function(){
                var state = left_btn.text();
                
                if(state == "OPEN")
                    left_bar_open();
                else if(state == "CLOSE")
                    left_bar_close();
            });
            
            function left_bar_open(){
                left_bar.css("left", "0px");
                left_btn.text("CLOSE");
            };

            function left_bar_close(){
                left_bar.css("left", "-400px");
                left_btn.text("OPEN");
            };
            
            $(document).mouseup(function(e){
                if(left_bar.has(e.target).length === 0){
                    left_bar_close();
                }
            });
	    });
        
	</script>
</body>
</html>