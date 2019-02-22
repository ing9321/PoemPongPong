<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.poem.common.vo.MemberVO"
		 import="com.poem.common.Paging"%>

<%
	String loginMember = (String)session.getAttribute("loginMember");
    int weeklyPoemListSize = (int)request.getAttribute("weeklyPoemListSize");
   
   	// 페이징
	Paging paging = new Paging();
	
	if(request.getParameter("pageNo") == null || request.getParameter("pageNo") == ""){
	   paging.setPageNo(1);
	} else{
	   paging.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
	}

	paging.setTotalCount(weeklyPoemListSize);
    paging.setPageSize(9);
	paging.makePaging();
	
	request.setAttribute("paging", paging);
		
	// 로그인하지 않고 접근해서 session 값이 없을 경우 임의로 g라고 값을 줌(guest, NullPointerException 날까봐..)
	if(loginMember == null || loginMember == ""){
		loginMember = "empty";
	}
%>

<!DOCTYPE html>
<html>
<head> 
<meta charset="UTF-8">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
<link rel="stylesheet" href="PoemPongPong/assets/css/weeklyPoemPage.css?v=20170707_10" type="text/css">
<title>PoemPongPong</title>
<script src="//cdn.jsdelivr.net/jquery/2.2.0/jquery.min.js"></script>
    <script>
    		//시 리스트 저장 
	    var weeklyPoemList = new Array();
        
        <c:forEach var="weeklyPoem" items="${weeklyPoemList}" varStatus="status">
            var weeklyPoem = {
            	weeklyPoemIndex: '',
                subjectName: '',
                title: '',
                contents: '',
                viewCount: '',
                likeCount: '',
                background: '',
                uploadDate: '',
                memberPoet: ''
            };
        
            weeklyPoem.weeklyPoemIndex = "<c:out value='${weeklyPoem.w.weeklyPoemIndex}'/>";
            weeklyPoem.subjectName = "<c:out value='${weeklyPoem.w.subjectName}'/>";
            weeklyPoem.title = "<c:out value='${weeklyPoem.w.title}'/>";
            weeklyPoem.contents = "<c:out value='${weeklyPoem.w.contents}' escapeXml='false'/>";
            weeklyPoem.viewCount = "<c:out value='${weeklyPoem.w.viewCount}'/>";
            weeklyPoem.likeCount = "<c:out value='${weeklyPoem.w.likeCount}'/>";
            weeklyPoem.background = "<c:out value='${weeklyPoem.w.background}'/>";
            weeklyPoem.uploadDate = "<c:out value='${weeklyPoem.w.uploadDate}'/>";
            weeklyPoem.memberPoet = "<c:out value='${weeklyPoem.m.memberPoet}'/>";
        
            weeklyPoemList.push(weeklyPoem);
        </c:forEach>
        
        var likeList = new Array();
        
        <c:forEach var="weeklyLike" items="${weeklyLikeList}" varStatus="status">
        		var weeklyLike = "<c:out value='${weeklyLike.weeklyPoemIndex}'/>";
        		
        		likeList.push(weeklyLike);
        </c:forEach>
        
        console.log(likeList);
    </script>
    <style>
    		input:-ms-input-placeholder { color: #ED8080; } 
  		input::-webkit-input-placeholder { color: #ED8080; }
   		input::-moz-placeholder { color: #ED8080; } 
   		input::-moz-placeholder { color: #ED8080; }
    </style>
</head>
<body>
	<jsp:include page="banner.jsp"></jsp:include>
	<jsp:include page="loginInclude.jsp"/>
	<jsp:include page="navbar.jsp"/>
	<jsp:include page="writePoemForm.jsp" flush="true"/>
	<div id="container">
        <div class="main-title">
            시인 퐁퐁
        </div>
        <!-- 검색 버튼   -->
        <div class="divSearch">
            <input type="text" placeholder="닉네임을 입력해퐁" class="textSearch"/>
            <input type="button" value="" class="btnSearch"/>
        </div>
        
        <!-- 시 내용 출력 -->
        <div class="weeklyPoem-container">
	        <c:if test="${weeklyPoemList.size() ne 0 }">
	            <c:set var="j" value="${(paging.pageNo-1)*paging.pageSize}"></c:set>
	            <c:set var="loop" value="true"></c:set>
	
	            <c:forEach var="i" begin="0" end="${paging.pageSize - 1 }" step="1">
	                <c:if test="${paging.totalCount <= i+j }">
	                    <c:set var="loop" value="false"></c:set>
	                </c:if>
	                <c:if test="${loop != false}">   
	                    <div class="thumbnail-poem">
	                        <div class="thumbnail-wrap modal-popup" style="background-image: url('${weeklyPoemList.get(i+j).w.background}')">
	                            <input type="hidden" value="${i+j }"/>
	                            <div class="thumbnail-title">
	                                ${weeklyPoemList.get(i+j).w.title }
	                            </div>
	                            <div class="thumbnail-contents">
	                           		<c:out value="${weeklyPoemList.get(i+j).w.contents }" escapeXml="false"></c:out>
	                            </div>
	                        </div>
	                        <div class="thumbnail-info">
	                            <div class="thumbnail-left">
	                                <div>
	                                    ${weeklyPoemList.get(i+j).m.memberPoet } 퐁님
	                                </div>
	                                <div>
	                                    굿뜨♥ ${weeklyPoemList.get(i+j).w.likeCount }
	                                </div>
	                                
	                                <!-- 로그인한 사용자가 작성한 댓글일 경우 수정/삭제 버튼 생성 -->
		                            <c:set var="loginMemberID" value="<%=loginMember %>"></c:set>
		                            <c:if test="${loginMemberID eq weeklyPoemList.get(i+j).m.memberID }">
		                            		<div style="float:left;">
			                                <input type='button' class='thumbnail-modify weeklyPoem-btn' value='수정' id="btnModify${i+j}"/>
			                                <form action="weeklyPoemDeleteUser.do" method="post" onsubmit="return checkDelete();">
			                                    <input type="hidden" value="${weeklyPoemList.get(i+j).w.weeklyPoemIndex}" name="weeklyPoemIndex"/>
			                                    <input type='submit' class='btnDelete weeklyPoem-btn' value='삭제'/>
			                                </form>
		                            		</div>
		                            </c:if>
	                            </div>
	                            <div class="thumbnail-right" style="background-image:url('${weeklyPoemList.get(i+j).m.memberPhoto }')">
	                            </div>
	                        </div>
	                    </div>
	                </c:if>
	            </c:forEach>
	        </c:if>		
        </div>

    </div>
    
	<!-- 페이징 -->
	<div class="paginate">
	    <input type="button" class="lastPrevBtn" onclick="location.href='weeklyPoemUser.do?pageNo=${paging.firstPageNo}&poet=${poet }'"/>&nbsp;
	    <input type="button" class="prevBtn" onclick="location.href='weeklyPoemUser.do?pageNo=${paging.prevPageNo}&poet=${poet }'"/> &nbsp;
	    <span>
	        <c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
	            <c:choose>
	                <c:when test="${i eq paging.pageNo}"><a href="weeklyPoemUser.do?pageNo=${i}&poet=${poet }" class="choice">${i}</a></c:when>
	                <c:otherwise><a href="weeklyPoemUser.do?pageNo=${i}&poet=${poet }">${i}</a></c:otherwise>
	            </c:choose>
	        </c:forEach>
	    </span>
	    &nbsp;<input type="button" class="nextBtn" onclick="location.href='weeklyPoemUser.do?pageNo=${paging.nextPageNo}&poet=${poet }'"/>&nbsp;
	    <input type="button" class="lastNextBtn" onclick="location.href='weeklyPoemUser.do?pageNo=${paging.finalPageNo}&poet=${poet }'"/>
	</div>
        
    <!--    모달 -->
    <div class="modal">
        <div class="modal-content">
            <span class="modal-close">&times;</span>
            <input type="hidden" class="poem-weeklyPoemIndex"/>
            
            <div class="poem-uploadDate"></div> 
            <div class="poem-info">
	            <div class="subDiv">주제:&nbsp;<span class="poem-subjectName"></span></div>
	            <div class="poem-title divCenter"></div>
	            <div class="poem-memberPoet divRight"></div>
	            <div class="poem-contents"></div>
            </div>
            <div class="countDiv">조회수:<span class="poem-viewCount"></span></div><br /> 
	        <div class="divRight" style="margin-right: 20px">
            	<div style="display: inline-block;">
	            	<input type="button" value="" class="poem-likeBtn"/>
            	</div>
            	<span class="poem-likeCount"></span>
            </div>   
        </div>
    </div>
        
    <script>
        $(document).ready(function() {
            // 모달
            var modal = $(".modal");
            var modal_close = $(".modal-close");
            var modal_popup = $(".modal-popup");

            modal_popup.click(function(){
                var position = $(this)[0].childNodes[1].value;
				$(".poem-weeklyPoemIndex").val(weeklyPoemList[position].weeklyPoemIndex);
                $(".poem-subjectName").text(weeklyPoemList[position].subjectName);
                $(".poem-title").text(weeklyPoemList[position].title);
                $(".poem-contents").html(weeklyPoemList[position].contents);
                $(".poem-viewCount").text(weeklyPoemList[position].viewCount);
                $(".poem-likeCount").text(weeklyPoemList[position].likeCount);
                $(".poem-uploadDate").text(weeklyPoemList[position].uploadDate);
                $(".poem-memberPoet").text(weeklyPoemList[position].memberPoet);
                $(".poem-info").css("background-image", 'url(' + weeklyPoemList[position].background + ')');
                
                var isLike = false;

                for(var i=0; i<likeList.length; i++){
                		if(likeList[i] == weeklyPoemList[position].weeklyPoemIndex){
                			isLike = true;
                		}
                }
                
                if(isLike){
	               $(".poem-likeBtn").css("background-image", "url('${pageContext.request.contextPath}/PoemPongPong/images/likeIt.png')");
                }else{
               	 	$(".poem-likeBtn").css("background-image", "url('${pageContext.request.contextPath}/PoemPongPong/images/dislikeIt.png')");
                }
                
                $.post("weeklyPoemViewcount.do", 
            			{weeklyPoemIndex : weeklyPoemList[position].weeklyPoemIndex},
            			function(data){
            				// console.log(data);
            			});
                modal.css("display", "block");
            });
            
            modal_close.click(function(){
                modal.css("display", "none");
            });
            
            $(document).mouseup(function(e){
                if(modal.has(e.target).length === 0){
                    modal.css("display", "none");
                }
            });
            // -------------------------------- 모달 ------------------------
            
            // 검색
            $(".btnSearch").click(function(){
                var textSearch = $(".textSearch").val();
                location.href = "weeklyPoemUser.do?poet=" + textSearch;
            });
            
            $(".textSearch").keyup(function(event){
            		if(event.keyCode == 13){
            			$(".btnSearch").click();
            		}
            });
            
            // 수정
            $(".thumbnail-modify").click(function(){
                var id = $(this)[0].id;
                var position = id.substring(9);
				  
                $("#modifyIndex").val(weeklyPoemList[position].weeklyPoemIndex);
                $("#writeMethod").val("modify");
                $("#form-subject-name").val(weeklyPoemList[position].subjectName);
                $("#form-title").val(weeklyPoemList[position].title);
                
                // textarea br 처리
                var regex = /<br\s*[\/]?>/gi;
                
                $("#form-contents").html(weeklyPoemList[position].contents.replace(regex, "\n"));
                
                $("#right-bar").css("right", "0px");
                $("#right-btn").text("CLOSE");
            });
            
            $(".poem-likeBtn").click(function(){
	            	var memberID = "<%=loginMember %>";
	    			console.log(memberID);
	    			
	    			// 로그인하지 않은 상태이면 좋아요 기능 수행하지 않음
	    			if(memberID != "empty"){
	    				var weeklyPoemIndex = $(".poem-weeklyPoemIndex").val();
	    				
	    				$.post("weeklyPoemLikeItProcess.do", 
		            			{weeklyPoemIndex : weeklyPoemIndex},
		            			function(data){
		            				location.reload();
		            			});
	    			} else {
	    				alert("로그인하면 굿뜨할 수 있지퐁!");
	    			}
            });
            
	    });
        
        // 삭제
        function checkDelete(){
            if(confirm("정말 삭제할꺼야퐁???◐ㅁ◐;;;;  ◑ㅁ◑;;;;") == true){
                return true;
            }else{
                return false;
            }
        };
    </script>
</body>
</html>