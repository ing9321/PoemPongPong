<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.poem.common.vo.RelayPoemMainVO"
		 import="com.poem.pong.vo.RelayReplyJoinMemberVO"
		 import="com.poem.common.vo.RelayLikeItVO"
		 import="com.poem.common.vo.MemberVO"
		 import="com.poem.common.Paging"
		 import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%	// 릴레이 시 메인 리스트
	ArrayList<RelayPoemMainVO> relayPoemMainList = (ArrayList<RelayPoemMainVO>)request.getAttribute("relayPoemMainList");
	// 현재 작성중인 릴레이 시
	RelayPoemMainVO currentRelayPoemMain = (RelayPoemMainVO)request.getAttribute("currentRelayPoemMain");
	// 현재 작성중인 릴레이 시의 댓글 중 좋아요 수가 가장 많은 5개의 댓글
	ArrayList<RelayReplyJoinMemberVO> bestReplyList = (ArrayList<RelayReplyJoinMemberVO>)request.getAttribute("bestReplyList");
	// 현재 작성중인 릴레이 시의 댓글(회원테이블과 조인)
	ArrayList<RelayReplyJoinMemberVO> relayReplyMemberList = (ArrayList<RelayReplyJoinMemberVO>)request.getAttribute("relayReplyMemberList");
	// 현재 로그인 중인 회원이 누른 좋아요 댓글 리스트
	ArrayList<RelayLikeItVO> loginMemberReplyLikeList = (ArrayList<RelayLikeItVO>)request.getAttribute("loginMemberReplyLikeList");
	// 현재 로그인 중인 회원 ID 얻기
	String loginMember = (String)session.getAttribute("loginMember");
	// 로그인하지 않고 접근해서 session 값이 없을 경우 임의로 g라고 값을 줌(guest, NullPointerException 날까봐..)
	if(loginMember == null || loginMember == ""){
		loginMember = "g";
	}
	%>
   
<%
	// 페이징
	// 은지 : 마지막 페이지부터 출력하도록 순서를 변경했으니 다른 페이지에서는 다른 페이징을 복붙해주세용
	Paging paging = new Paging();
	
	paging.setTotalCount(relayPoemMainList.size());
	paging.setPageSize(3);	
	paging.makePaging();
		
	if(request.getParameter("pageNo") == null || request.getParameter("pageNo") == ""){
		paging.setPageNo(paging.getEndPageNo());
	} else{
	   paging.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
	}	
	
	request.setAttribute("paging", paging);
	
	// 댓글 페이지
	Paging replyPaging = new Paging();

	replyPaging.setTotalCount(relayReplyMemberList.size());
	replyPaging.setPageSize(20);
	replyPaging.makePaging();
	
	if(request.getParameter("rPageNo") == null || request.getParameter("rPageNo") == ""){
		replyPaging.setPageNo(1);
	} else{
		replyPaging.setPageNo(Integer.parseInt(request.getParameter("rPageNo")));
	}	

	request.setAttribute("replyPaging", replyPaging);

%>

<!DOCTYPE html>
<html>
<head> 
<meta charset="UTF-8">
<title>PoemPongPong</title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
	<link rel="icon" href="${pageContext.request.contextPath}/PoemPongPong/images/favicon.png" />
<link href="PoemPongPong/assets/css/relayPoemPage.css?v=20170624_39" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<!-- java로 호출한 릴레이시 배열을 javascript 배열로 저장하는 script -->
<script>
	var jspRelayPoemList = new Array();
	
	<c:forEach var="relayPoemMain" items="${relayPoemMainList}" varStatus="status">
	    var relayPoem = {
	        subjectName: '',
	        title: '',
	        contents: '',
	        poets: ''
	    };
	
	    relayPoem.subjectName = "<c:out value='${relayPoemMain.subjectName}'/>";
	    relayPoem.title = "<c:out value='${relayPoemMain.title}'/>";
	    relayPoem.contents = "<c:out value='${relayPoemMain.contents }' escapeXml='false'/>";
	    relayPoem.poets = "<c:out value='${relayPoemMain.poets}'/>";
	
	    jspRelayPoemList.push(relayPoem);
	</c:forEach>
</script>
<script>
	
	$(document).ready(function() {
		
		// 수정버튼 toggle키로 구현
		var replyEditFlag = false;
		var oldReplyContents = ""; // 수정 전 댓글 
		var newReplyContents = ""; // 수정 후 댓글
		// 댓글 수정
		$('.update').click(function () {
			// index 옆에 hidden으로 숨겨놓은 relayreply index
			var item = $(event.target.parentElement.parentElement.childNodes[1].childNodes[3]);
			var replyIndex = item.val();
			console.log(item);
			console.log(replyIndex);
			
			if(!replyEditFlag) {
				replyEditFlag = true;
				
				var contentsTag = $(event.target.parentElement.parentElement.childNodes[5]);
				console.log(contentsTag);
				oldReplyContents = contentsTag.text();
				console.log(oldReplyContents);
				
				$(contentsTag).html("<input type='text' value='" + oldReplyContents + "' class='editTextReply'/>");

				event.target.value = "저장";
			} else {
				replyEditFlag = false;
				
				var editTag = $(event.target.parentElement.parentElement.childNodes[5].childNodes);
				console.log(editTag);
				newReplyContents = editTag.val();
				console.log(newReplyContents);
				
				// 댓글 길이 유효성 검사 후 post전송
				if(newReplyContents.length < 20) {
					$.post("uReplyUpdate.do", {index : replyIndex, before : oldReplyContents, after : newReplyContents}, function(result) {location.reload();});
					event.target.value = "수정";
				} else {
					alert("댓글이 너무 길어퐁ㅜ.ㅜ");
					location.reload();
				}
			}
			
			
		});
		
		// 댓글 삭제
		$('.delete').click(function () {
			// index 옆에 hidden으로 숨겨놓은 relayreply index
			var item = $(event.target.parentElement.parentElement.childNodes[1].childNodes[3]);
			var replyIndex = item.val();
			$.post("uReplyDelete.do", {index : replyIndex}, function(data) { location.href="relayPoemUser.do"; });
		});
		
		// 좋아요
		$('.btnLike').click(function () {
			var item = $(event.target.parentElement.parentElement.childNodes[1].childNodes[3]);
			var replyIndex = item.val();			
			var memberID = "<%=loginMember %>";
			
			// 로그인하지 않은 상태이면 좋아요 기능 수행하지 않음
			if(memberID != "g"){
				$.post("uRelayLike.do", {index : replyIndex, member : memberID}, function(data) { location.href="relayPoemUser.do"; });
			} else {
				alert("로그인하면 굿뜨할 수 있지퐁!");
			}

		});
		//------------------------------------------------------

		/* 완성된 릴레이 시 클릭 시 화면에 출력될 모달창 구현 */
        var modal = $(".modal");
        var modal_close = $(".modal-close");
        var modal_popup = $(".pastRelayPoem");    
        
        modal_popup.click(function(){
        	var pastPoemTag = $(this.childNodes[1]);
			var relayPoemIndex = pastPoemTag.text();
            
            $(".pastRelaySubject").text(jspRelayPoemList[relayPoemIndex-1].subjectName);
            $(".pastRelayTitle").text(jspRelayPoemList[relayPoemIndex-1].title);
            // 은지 : contents는 줄바꿈 태그를 위해 text가 아닌 html로 처리
            $(".pastRelayContents").html(jspRelayPoemList[relayPoemIndex-1].contents);
            $(".pastRelayPoets").text(jspRelayPoemList[relayPoemIndex-1].poets);
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
	});
	
	// 새로 입력하는 댓글창 유효성
	function validate() {
		// 로그인하지 않은 상태 처리
		var isLogin = "<%=loginMember %>";
		if(isLogin != "g"){
			var newReply = $.trim($('#newReply').val());
			
			if(newReply != "" && newReply != null) {
				
				// 댓글의 길이가 sql로 지정해준 길이보다 길 경우
				if($('#newReply').val().length > 20){
					alert("댓글이 너무 길어퐁ㅜ.ㅜ");
					$('#newReply').focus();
					return false;
					
				} else {
					return true;
				}
			} else {
				alert("추가할 댓글 내용이 어퐁!");
				$('#newReply').focus();
				return false;
			}
		} else {
			alert("로그인 해야 댓글을 쓸 수 있지퐁'-')..");
			return false;
		}
	}

</script>
<style>
    	input:-ms-input-placeholder { color: #ED8080; } 
  		input::-webkit-input-placeholder { color: #ED8080; }
   		input::-moz-placeholder { color: #ED8080; } 
   		input::-moz-placeholder { color: #ED8080; }
</style>
</head>
<body>
	<jsp:include page="banner.jsp"/>
	<jsp:include page="navbar.jsp"/>
	<jsp:include page="loginInclude.jsp"/>
  	
  	<div id="container">
  	
        <!-- 우선 출력을 해보자 -->
        <div id="outputDiv">
        	<!-- 릴레이 시 메인 div -->
	        <div id="relayPoemMainDiv">
	            <div id="titlePong">릴레이 퐁퐁</div>
				<div id="indexPong">No.
					<c:out value="${currentRelayPoemMain.relayMainIndex }"></c:out>
				</div>
				
				<!-- 작성중인 릴레이 시 -->
	            <div id="relayPoemMain">
	            	
	                <div id="subjectPong">주제 : <c:out value="${currentRelayPoemMain.subjectName }"></c:out></div>
	                
	                <div id="mainPong">
	                	<c:out value="${currentRelayPoemMain.contents }" escapeXml="false"></c:out>
	                </div>
	            </div>
	            
           	</div>
           	
           	<!-- 릴레이 시 댓글 중에 좋아요가 많은 다섯개만 보여쥼 -->
           	<div id="relayBestReplyDiv">
	           	
	           	<!-- 베댓 타이틀 추가해야해 -->
	           	<div class="bestReplyTitle">
	           		<div id="bestReplyTitle"></div>
	           	</div>
           	
           	
				<table id="replyBestTable" class="replyTable">
					<c:if test="${bestReplyList.size() > 0 }">
					
						<c:forEach var="x" begin="0" end="${bestReplyList.size()-1 }" step="1">
								<tr>
					   				<td class="tdFirst">
					   					BEST <c:out value="${x+1 }"></c:out>
					   					<!-- hidden 타입으로 실제 Index값은 숨기되 보관하기 -->
	 				   					<input type='hidden' value='<c:out value="${bestReplyList.get(x).r.relayReplyIndex }"></c:out>' />
					   				</td>
					   				<td class="tdSecond"><c:out value="${bestReplyList.get(x).m.memberPoet }"></c:out></td>
					   				<td class="tdThird"><c:out value="${bestReplyList.get(x).r.contents }"></c:out></td>
					   				<td class="tdForth">굿뜨
					   					<c:choose>
						   					<c:when test="${loginMemberReplyLikeList.size() > 0 }">
						   						<c:set var="blikeLoof" value="false" />
						   						<c:forEach var="y" begin="0" end="${loginMemberReplyLikeList.size() -1 }" step="1">
						   							<c:if test="${not blikeLoof }">
							   							<c:if test="${bestReplyList.get(x).r.relayReplyIndex eq loginMemberReplyLikeList.get(y).relayReplyIndex }">
								   							<input type="button" class='btnLike' style="background-image:url('PoemPongPong/images/likeIt.png');"/>
								   							<c:set var="blikeLoof" value="true"></c:set>
								   						</c:if>
						   							</c:if>
		 				   						</c:forEach>
		 				   						<c:if test="${not blikeLoof }">
						   								<input type="button" class="btnLike" />
						   						</c:if>
						   					</c:when>
						   					<c:otherwise>
						   						<input type="button" class="btnLike" />
						   					</c:otherwise>				   					
					   					</c:choose>
					   				</td>
					   				<td class="tdFifth">
					   					x<c:out value="${bestReplyList.get(x).r.likeCount }"></c:out>
					   				</td>
					   				
					   				<td class="tdWriterVer">
					   					<!-- 로그인한 사용자가 작성한 댓글일 경우 수정/삭제 버튼 생성 -->
					   					<c:set var="loginMemberID" value="<%=loginMember %>"></c:set>
					   					<c:if test="${loginMemberID eq bestReplyList.get(x).m.memberID }"  >
					   						<input type='button' class='update btncss' value='수정' />
						   					<input type='button' class='delete btncss' value='삭제' />
					   					</c:if>
					   				</td>
								</tr>
						</c:forEach>
					</c:if>
				</table>           	
           	</div>
           	
           	
            <!-- 릴레이 시 댓글 목록 -->
            <div id="relayPoemReplyDiv" >
            
	            <!-- 릴레이 댓글 타이틀 추가해야해 -->
	           	<div class="basicReplyTitle">
	           		<div id="basicReplyTitle"></div>
	           	</div>
	            
                <table id="replyRecentTable" class="replyTable">
                	<c:set var="n" value="${(replyPaging.pageNo-1)*replyPaging.pageSize}"></c:set>
					<c:set var="rloop" value="true"></c:set>
					<c:forEach var="m" begin="0" end="${replyPaging.pageSize - 1 }" step="1">
						<c:if test="${replyPaging.totalCount <= m+n }">
							<c:set var="rloop" value="false"></c:set>
						</c:if>
						<c:if test="${rloop != false}">
			                <tr>
				   				<td class="tdFirst">
				   					<c:out value="${m+n+1 }"></c:out>
				   					<!-- hidden 타입으로 실제 Index값은 숨기되 보관하기 -->
 				   					<input type='hidden' value='<c:out value="${relayReplyMemberList.get(m+n).r.relayReplyIndex }"></c:out>' />
				   				</td>
				   				<td class="tdSecond"><c:out value="${relayReplyMemberList.get(m+n).m.memberPoet }"></c:out></td>
				   				<td class="tdThird"><c:out value="${relayReplyMemberList.get(m+n).r.contents }"></c:out></td>
				   				<td class="tdForth">굿뜨
 				   					<c:choose>
					   					<c:when test="${loginMemberReplyLikeList.size() > 0 }">
					   						<c:set var="likeLoof" value="false" />
					   						<c:forEach var="l" begin="0" end="${loginMemberReplyLikeList.size()-1 }" step="1">
					   							<c:if test="${not likeLoof }">
								   						<c:if test="${relayReplyMemberList.get(m+n).r.relayReplyIndex eq loginMemberReplyLikeList.get(l).relayReplyIndex }">
								   							<input type="button" class='btnLike' style="background-image:url('PoemPongPong/images/likeIt.png');"/>
								   							<c:set var="likeLoof" value="true"></c:set>
								   						</c:if>
					   							</c:if>
	 				   						</c:forEach>
	 				   						<c:if test="${not likeLoof }">
					   								<input type="button" class="btnLike" />
					   						</c:if>
					   					</c:when>
					   					<c:otherwise>
					   						<input type="button" class="btnLike" />
					   					</c:otherwise>				   					
				   					</c:choose>
				   				</td>
				   				<td class="tdFifth">
				   					x<c:out value="${relayReplyMemberList.get(m+n).r.likeCount }"></c:out>
				   				</td>
				   				
				   				<td class="tdWriterVer">
				   					<!-- 로그인한 사용자가 작성한 댓글일 경우 수정/삭제 버튼 생성 -->
				   					<c:set var="loginMemberID" value="<%=loginMember %>"></c:set>
				   					<c:if test="${loginMemberID eq relayReplyMemberList.get(m+n).m.memberID }"  >
				   						<input type='button' class='update btncss' value='수정' />
					   					<input type='button' class='delete btncss' value='삭제' />
				   					</c:if>
				   				</td>
							</tr>
						</c:if>
					</c:forEach>
                </table>
        	</div>
        	
        	<!-- 댓글 페이징 -->
			<div class="replyPaginate">
			    <a href="relayPoemUser.do?rPageNo=${replyPaging.firstPageNo}" class="first atagcss"><img class="btnPage" alt="처음 페이지" src="PoemPongPong/images/lastPrevBtn.png" /></a>
			    <a href="relayPoemUser.do?rPageNo=${replyPaging.prevPageNo}" class="prev atagcss"><img class="btnPage" alt="이전 페이지" src="PoemPongPong/images/prevBtn.png" /></a>
			    <span>
			        <c:forEach var="i" begin="${replyPaging.startPageNo}" end="${replyPaging.endPageNo}" step="1">
			            <c:choose>
			                <c:when test="${i eq replyPaging.pageNo}"><a href="relayPoemUser.do?rPageNo=${i}" class="choice atagcss">${i}</a></c:when>
			                <c:otherwise><a href="relayPoemUser.do?rPageNo=${i}">${i}</a></c:otherwise>
			            </c:choose>
			        </c:forEach>
			    </span>
			    <a href="relayPoemUser.do?rPageNo=${replyPaging.nextPageNo}" class="next atagcss"><img class="btnPage" alt="다음 페이지" src="PoemPongPong/images/nextBtn.png" /></a>
			    <a href="relayPoemUser.do?rPageNo=${replyPaging.finalPageNo}" class="last atagcss"><img class="btnPage" alt="마지막 페이지" src="PoemPongPong/images/lastNextBtn.png" /></a>
			</div>
        	
        	<!-- 새로 작성할 댓글 창 -->
        	<div id="writeReplyDiv">
                <form method="post" action="uRelayReplyWrite.do" name="newReplyForm" onsubmit="return validate();">
                	<input type="text" placeholder="다음 행을 입력해 주세퐁" name="newReply" id="newReply"/>
                	<input type="submit" value="" id="btnNewReply" />
                </form>
            </div>
            
            <!-- 완성된 릴레이 시 리스트 -->
            <div id="relayListDiv">
            
            	<!-- 썸네일 세개씩 출력 -->
                <div id="thumbnailBox">
                    <c:set var="j" value="${(paging.pageNo-1)*paging.pageSize}"></c:set>
					<c:set var="loop" value="true"></c:set>
					<c:forEach var="i" begin="0" end="${paging.pageSize - 1 }" step="1">
						<c:if test="${paging.totalCount <= i+j }">
							<c:set var="loop" value="false"></c:set>
						</c:if>   
						<c:if test="${loop != false}">
			                <div class="pastRelayPoem">
		                		<div class="thumIndex"><c:out value="${i+j+1 }"></c:out></div>
		                		<div>
		                			<input type='hidden' value='<c:out value="${relayPoemMainList.get(i+j).relayMainIndex }"></c:out>' />
		                		</div>
		                		<%-- <div style="text-align: right;"><c:out value="${relayPoemMainList.get(i+j).subjectName }"></c:out></div> --%>
		                		<div class="thumTitle">
		                			<c:if test='${relayPoemMainList.get(i+j).title.trim() != null && relayPoemMainList.get(i+j).title.trim() != "" }'>
		                				&lt;<c:out value="${relayPoemMainList.get(i+j).title }"></c:out>&gt;
		                			</c:if>
		                		</div>
		                		<br />
		                		<div class="thumContents"><c:out value="${relayPoemMainList.get(i+j).contents }" escapeXml="false"></c:out></div>
		                		<%-- <div><c:out value="${relayPoemMainList.get(i+j).poets }"></c:out></div> --%>
			                </div>
						</c:if>
					</c:forEach>
            	</div>
            </div>
            
           
               <!-- 페이징 -->
			<div class="paginate">
			    <a href="relayPoemUser.do?pageNo=${paging.firstPageNo}" class="first atagcss"><img class="btnPage" alt="처음 페이지" src="PoemPongPong/images/lastPrevBtn.png" /></a>
			    <a href="relayPoemUser.do?pageNo=${paging.prevPageNo}" class="prev atagcss"><img class="btnPage" alt="이전 페이지" src="PoemPongPong/images/prevBtn.png" /></a>
			    <span>
			        <c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
			            <c:choose>
			                <c:when test="${i eq paging.pageNo}"><a href="relayPoemUser.do?pageNo=${i}" class="choice atagcss">${i}</a></c:when>
			                <c:otherwise><a href="relayPoemUser.do?pageNo=${i}">${i}</a></c:otherwise>
			            </c:choose>
			        </c:forEach>
			    </span>
			    <a href="relayPoemUser.do?pageNo=${paging.nextPageNo}" class="next atagcss"><img class="btnPage" alt="다음 페이지" src="PoemPongPong/images/nextBtn.png" /></a>
			    <a href="relayPoemUser.do?pageNo=${paging.finalPageNo}" class="last atagcss"><img class="btnPage" alt="마지막 페이지" src="PoemPongPong/images/lastNextBtn.png" /></a>
			</div>
               
        </div>
        
        <!-- output div 끝 -->
        
		<div id="divFooter"></div>
    </div>
	<!-- container 끝 -->
	
	<div class="modal">
      	<div class="modal-content">
      		<span class="modal-close">&times;</span>
      		<div class="InnerContent">
	          	<div class ="subDiv">주제:<span class="pastRelaySubject"></span></div>
	          	<p class="pastRelayTitle"></p>
	          	<div class="pastRelayPoets"></div><br />
	          	<div class="pastRelayContents"></div>
	        </div>
      	</div>
  	</div>
  	
  	
	<div id="divFooter"></div>
</body>
</html>