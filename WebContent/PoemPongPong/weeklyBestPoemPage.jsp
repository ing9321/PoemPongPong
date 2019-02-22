<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="com.poem.pong.vo.WeeklyPoemJoinMemberVO"
		 import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
<head> 
<meta charset="UTF-8">
<title>PoemPongPong</title>
<link rel="stylesheet" href="PoemPongPong/assets/css/weeklyBestPoemPage.css?v=20170703_23" type="text/css">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
</head>
<body>
	<jsp:include page="banner.jsp"/>
	<jsp:include page="loginInclude.jsp"/>
	<jsp:include page="navbar.jsp"/>
	<div id="container">
		<div class="bestPoem-addon">
				<div class="main-title">베스트 퐁퐁</div>
       		<input type="button" class="prevBtn" onclick="changePoem(1);"/>
	        <span id="bestPoem-uploadDate">${weeklyBestPoemList[0].w.uploadDate }</span>
        		<input type="button" class="nextBtn" onclick="changePoem(-1);"/>
		</div>
		<div class="bestPoem-info" style="background-image: url('${weeklyBestPoemList[0].w.background}')">
			<input type="hidden" id="bestPoem-index" value="${weeklyBestPoemList[0].w.weeklyPoemIndex }"/>
            <div class="divCenter">
	            주제:&nbsp;<span id="bestPoem-subject_Name">${weeklyBestPoemList[0].w.subjectName }</span>
            </div>
            <div class="divCenter">
                <span id="bestPoem-title" class="spanTitle">${weeklyBestPoemList[0].w.title }</span>
            </div>
            
            <div style="text-align:right; float:right;margin-top:20px;">
	            <div class="bestPoem-profile">
					<div class="thumbnail-left">
					    <div>
					        <span id="bestPoem-memberPoet">${weeklyBestPoemList[0].m.memberPoet }</span> 퐁님
						</div>
						<div>
						    굿뜨♥ ${weeklyBestPoemList[0].w.likeCount }
						</div>
					</div>
					<div id="bestPoem-memberPhoto" style="background-image:url('${weeklyBestPoemList[0].m.memberPhoto }')">
				    </div>
				</div>
			</div>
            
            <div id="bestPoem-contents">
                ${weeklyBestPoemList[0].w.contents }
            </div>
            
		</div>
    </div>

    <script>
    	var poemIndex = 0;
	    var weeklyBestPoemList = new Array();
	    
	    <c:forEach var="weeklyBestPoem" items="${weeklyBestPoemList}" varStatus="status">
            var weeklyBestPoem = {
                weeklyPoemIndex: '',
                subjectName: '',
                title: '',
                contents: '',
                uploadDate: '',
                memberPoet: '',
                memberPhoto: '',
                background: ''
            };
            
		    	weeklyBestPoem.weeklyPoemIndex = "<c:out value='${weeklyBestPoem.w.weeklyPoemIndex}'/>";
		    	weeklyBestPoem.subjectName = "<c:out value='${weeklyBestPoem.w.subjectName}'/>";
		    	weeklyBestPoem.title = "<c:out value='${weeklyBestPoem.w.title}'/>";
		    	weeklyBestPoem.contents = "<c:out value='${weeklyBestPoem.w.contents}' escapeXml='false'/>";
		    	weeklyBestPoem.uploadDate = "<c:out value='${weeklyBestPoem.w.uploadDate}'/>";
		    	weeklyBestPoem.memberPoet = "<c:out value='${weeklyBestPoem.m.memberPoet}'/>";
		    	weeklyBestPoem.memberPhoto = "<c:out value='${weeklyBestPoem.m.memberPhoto}'/>";
		    	weeklyBestPoem.background = "<c:out value='${weeklyBestPoem.w.background}'/>";
		    	
		    	weeklyBestPoemList.push(weeklyBestPoem);
	    </c:forEach>
	    
        function changePoem(changeIndex){            
        	poemIndex += changeIndex;
        	
        	if(poemIndex == -1){
        		poemIndex = 0;
        	}else if(poemIndex == weeklyBestPoemList.length){
        		poemIndex = weeklyBestPoemList.length - 1;
        	}

            $("#bestPoem-index").val(weeklyBestPoemList[poemIndex].weeklyPoemIndex);
            $("#bestPoem-subject_Name").text(weeklyBestPoemList[poemIndex].subjectName);
            $("#bestPoem-title").text(weeklyBestPoemList[poemIndex].title);
            $("#bestPoem-contents").html(weeklyBestPoemList[poemIndex].contents);
            $("#bestPoem-uploadDate").text(weeklyBestPoemList[poemIndex].uploadDate);
            $("#bestPoem-memberPoet").text(weeklyBestPoemList[poemIndex].memberPoet);
            $("#bestPoem-memberPhoto").css("background-image", 'url(' + weeklyBestPoemList[poemIndex].memberPhoto + ')');
            $(".bestPoem-info").css("background-image", 'url(' + weeklyBestPoemList[poemIndex].background +')');
        };
    </script>
</body>
</html>