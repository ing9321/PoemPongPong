<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.poem.common.vo.MemberVO" %>
<!DOCTYPE html>
<html>
<head> 
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <script src="PoemPongPong/assets/js/raphael.js" type="text/javascript" charset="utf-8"></script>
	<link rel="stylesheet" href="PoemPongPong/assets/css/poemIndex.css?v=20170704_27" type="text/css">
	<title> PoemPongPong </title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
</head>
<body>
	<jsp:include page="PoemPongPong/loginInclude.jsp"/>
	<jsp:include page="PoemPongPong/navbar.jsp"/>
	<div id="background"></div>
	
	
	
	<script>
		// bubble
	    var paper, circs, i, nowX, nowY, timer, props = {}, toggler = 0, elie, dx, dy, rad, cur, opa;
	     
	    // min, max : 크기
	    function ran(min, max){  
	        return Math.floor(Math.random() * (max - min + 1)) + min;  
	    };
	    
	    function moveIt(){
	        for(i = 0; i < circs.length; ++i){            
	              // Reset when time is at zero
	            if (! circs[i].time) {
	                circs[i].time  = ran(30, 100);
	                circs[i].deg   = ran(-179, 180);
	                circs[i].vel   = ran(1, 5);  
	                circs[i].curve = ran(0, 1);
	                circs[i].fade  = ran(0, 1);
	                circs[i].grow  = ran(-2, 2); 
	            }                
	                // Get position
	            nowX = circs[i].attr("cx");
	            nowY = circs[i].attr("cy");   
	               // Calc movement
	            dx = circs[i].vel * Math.cos(circs[i].deg * Math.PI/180);
	            dy = circs[i].vel * Math.sin(circs[i].deg * Math.PI/180);
	                // Calc new position
	            nowX += dx;
	            nowY += dy;
	                // Calc wrap around
	            if (nowX < 0) nowX = 1920 + nowX;
	            else          nowX = nowX % 1920;            
	            if (nowY < 0) nowY = 900 + nowY;
	            else          nowY = nowY % 900;
		                 // Render moved particle
	            circs[i].attr({cx: nowX, cy: nowY});
		                 // Calc growth
	            rad = circs[i].attr("r");
	            if (circs[i].grow > 0) circs[i].attr("r", Math.min(30, rad +  .1));
	            else                   circs[i].attr("r", Math.max(10,  rad -  .1));
		                 // Calc curve
	            if (circs[i].curve > 0) circs[i].deg = circs[i].deg + 2;
	            else                    circs[i].deg = circs[i].deg - 2;
		                 // Calc opacity
	            opa = circs[i].attr("fill-opacity");
	            if (circs[i].fade > 0) {
	                circs[i].attr("fill-opacity", Math.max(.3, opa -  .01));
	                circs[i].attr("stroke-opacity", Math.max(.3, opa -  .01)); }
	            else {
	                circs[i].attr("fill-opacity", Math.min(1, opa +  .01));
	                circs[i].attr("stroke-opacity", Math.min(1, opa +  .01)); }
	
	            // Progress timer for particle
	            circs[i].time = circs[i].time - 1;
		                 // Calc damping
	            if (circs[i].vel < 1) circs[i].time = 0;
	            else circs[i].vel = circs[i].vel - .05;              
		         } 
	        timer = setTimeout(moveIt, 60);
	    };
	    
	    window.onload = function () {
	   	 // 이미지 크기에 맞게
	        paper = Raphael("background", 1920, 900);
	        circs = paper.set();
	        
	        // i : 원의 갯수
	        for (i = 0; i < 20; ++i){
	            opa = ran(3,10)/10;
	            circs.push(
	                paper.circle(
	                    ran(0, 1920), ran(0,900), ran(10,30)).attr({
	                    "fill-opacity": opa, "stroke-opacity": opa}));
	        }
	        circs.attr({fill: "#FFFFFF", stroke: "#FFFFFF"});
	        moveIt();
	    };
	</script>
</body>
</html>