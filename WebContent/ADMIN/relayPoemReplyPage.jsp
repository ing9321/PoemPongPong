<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.poem.common.vo.RelayPoemReplyVO"
		 import="com.poem.common.vo.RelayPoemReplyJoinVO"
		 import="com.poem.common.Paging"
		 import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% ArrayList<RelayPoemReplyJoinVO> relayReplyJoinList = (ArrayList<RelayPoemReplyJoinVO>)request.getAttribute("relayReplyJoinList"); %>

<%
	//페이징
	Paging paging = new Paging();
	
	if(request.getParameter("pageNo") == null || request.getParameter("pageNo") == ""){
	   paging.setPageNo(1);
	} else{
	   paging.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
	}
	
	paging.setTotalCount(relayReplyJoinList.size());
	paging.makePaging();
	
	request.setAttribute("paging", paging);
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page - Relay Poem Reply</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="ADMIN/assets/css/main.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$('#searchRelayReply').css('display', 'none');
		
		// 검색할 때 엔터키 동작 가능
		$('#searchRelayReply').keypress(function() {
			if(event.which == 13) {
				relayReplySearchBtn();
			}
		});
		
		// 수정
		$('.update').click(function () {
			// 수정버튼 클릭시 노드를 따라 index td 내에 숨겨진 hidden input 값을 얻어오기
			var item = $(event.target.parentElement.parentElement.childNodes[1].childNodes[3]);
			// console.log(item);
			// console.log(item.val()); 
			 var relayReplyIndex = item.val(); // 선택된 시의 인덱스값
			location.href="relayPoemReply.do?relayPoem=" + relayReplyIndex;
        });
		
		// 삭제
		$('.delete').click(function () {
			// 삭제버튼 클릭시 노드를 따라 index td 내에 숨겨진 hidden input 값을 얻어오기
			var item = $(event.target.parentElement.parentElement.childNodes[1].childNodes[3]);
			// console.log(item);
            // console.log(item.text());
            var relayReplyIndex = item.val(); // 선택된 시의 인덱스값
            $.post("relayReplyDelete.do", {index : relayReplyIndex}, function(data) { location.href="relayPoemReply.do"; });
        });
		
	});
</script>
</head>

<body class="is-loading">

	<!-- Wrapper -->
	<div id="wrapper">
	
		<!-- Header -->
		<header id="header">
			<a href="adminLogin.do" class="logo">POEMPONGPONG</a>
		</header>
		
		<!-- Nav -->
		<nav id="nav">
			<ul class="links">
				<li><a href="member.do">Member</a></li>
				<li><a href="weeklyPoem.do">WeeklyPoem</a></li>
				<li><a href="weeklyPoemLikeIt.do">WeeklyLikeIt</a></li>
				<li><a href="relayPoemMain.do">RelayPoemMain</a></li>
				<li class="active"><a href="relayPoemReply.do">RelayPoemReply</a></li>
				<li><a href="relayPoemLikeIt.do">RelayLikeIt</a></li>
				<li><a href="subject.do">Subject</a></li>
			</ul>
		</nav>
		
		<!-- Main -->
		<div id="main">
		
			<!-- Post -->
			<section class="post">
				<header class="major">
				
					<!-- 로그아웃 버튼 -->
					<div class="buttonDiv" style="float: right;">
						<form method="post" action="adminLogout.do">
							<input type="submit" class="button" value="로그아웃" />
						</form>
					</div>
					<!--  로그아웃 버튼 -->
					
					<h1>릴레이퐁퐁 댓글 관리</h1>
				</header>
			
				<!-- 데이터 관리 버튼 -->
				<div class="actions">
					<!-- <div id="buttonDiv">
						<input type="button" class="button" value="추가" onclick="location.href='relayReplyInsert.do';" />
						<input type="button" class="button" value="조회" onclick="relayReplyBtn();" />
						<input type="text" name="searchRelayReply" id="searchRelayReply" placeholder="검색어 입력" style="width: 200px;" />
					</div> -->
				</div>
				<hr />
				
				<!-- 릴레이 시 댓글 관리 목록 -->
				<div class="table-wrapper">
					<table class="alt">
						<thead>
							<tr>
								<th>Index</th>
								<th>Contents</th>
								<th>Poet</th>
								<th>Like</th>
								<th>Date</th>
								<th></th>
							</tr>
						</thead>
						
						<c:set var="j" value="${(paging.pageNo-1)*paging.pageSize}"></c:set>
						<c:set var="loop" value="true"></c:set>
						
						<c:if test="${paging.pageSize > 0 }" >
							<c:forEach var="i" begin="0" end="${paging.pageSize - 1 }" step="1">
								<c:if test="${paging.totalCount <= i+j }">
									<c:set var="loop" value="false"></c:set>
								</c:if>   
								<c:if test="${loop != false}">                                    
									<tr>
						   				<td>
						   					<c:out value="${i+j+1 }"></c:out>
						   					<!-- hidden 타입으로 실제 weeklyPoemIndex값은 숨기되 보관하기 -->
						   					<input type="hidden" value='<c:out value="${relayReplyJoinList.get(i+j).r.relayReplyIndex }"></c:out>' />
						   				</td>
						   				<td><c:out value="${relayReplyJoinList.get(i+j).r.contents }"></c:out></td>
						   				<td><c:out value="${relayReplyJoinList.get(i+j).m.memberPoet }"></c:out></td>
						   				<td><c:out value="${relayReplyJoinList.get(i+j).r.likeCount }"></c:out></td>
						   				<td><c:out value="${relayReplyJoinList.get(i+j).r.uploadDate }"></c:out></td>
						   				<td>
						   					<input type='button' class='button small update' value='수정' />
						   					<input type='button' class='button small delete' value='삭제' />
						   				</td>
									</tr>
								</c:if>
							</c:forEach>
						</c:if>
					</table>
					
					<!-- 페이징 -->
					<div class="paginate">
					    <a href="relayPoemReply.do?pageNo=${paging.firstPageNo}" class="first">처음 페이지</a>
					    <a href="relayPoemReply.do?pageNo=${paging.prevPageNo}" class="prev">이전 페이지</a>
					    <span>
					        <c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
					            <c:choose>
					                <c:when test="${i eq paging.pageNo}"><a href="relayPoemReply.do?pageNo=${i}" class="choice">${i}</a></c:when>
					                <c:otherwise><a href="relayPoemReply.do?pageNo=${i}">${i}</a></c:otherwise>
					            </c:choose>
					        </c:forEach>
					    </span>
					    <a href="relayPoemReply.do?pageNo=${paging.nextPageNo}" class="next">다음 페이지</a>
					    <a href="relayPoemReply.do?pageNo=${paging.finalPageNo}" class="last">마지막 페이지</a>
					</div>
				</div>
			</section>
		</div>
		
		<!-- Footer -->
		<footer id="footer">
		</footer>
		
		<!-- Copyright -->
		<div id="copyright">
			<ul>
				<li>&copy; GlobalIT ClassA</li>
				<li>Design: PoemPongPong</li>
			</ul>
		</div>
	</div>
	
	<!-- Scripts -->
	<script src="ADMIN/assets/js/jquery.min.js"></script>
	<script src="ADMIN/assets/js/jquery.scrollex.min.js"></script>
	<script src="ADMIN/assets/js/jquery.scrolly.min.js"></script>
	<script src="ADMIN/assets/js/skel.min.js"></script>
	<script src="ADMIN/assets/js/util.js"></script>
	<script src="ADMIN/assets/js/main.js"></script>
</body>
</html>