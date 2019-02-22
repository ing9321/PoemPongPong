<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.poem.common.vo.RelayPoemMainVO"
		 import="com.poem.common.Paging"
		 import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% ArrayList<RelayPoemMainVO> relayPoemMainList = (ArrayList<RelayPoemMainVO>)request.getAttribute("relayPoemMainList");
	
	//페이징
	Paging paging = new Paging();
	
	if(request.getParameter("pageNo") == null || request.getParameter("pageNo") == ""){
	   paging.setPageNo(1);
	} else{
	   paging.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
	}
	
	paging.setTotalCount(relayPoemMainList.size());
	paging.makePaging();
	
	request.setAttribute("paging", paging);
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page - Relay Poem Main</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="ADMIN/assets/css/main.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$('#searchRealyPoem').css('display', 'none');
		
		// 릴레이 시 조회 텍스트창에서 엔터키 눌렀을 때도 동작하도록 설정
		$('#searchRealyPoem').keypress(function() {
			if(event.which == 13) {
				searchRelayPoemBtn();
			}
		});
		
		// 릴레이 시 수정
		$('.update').click(function () {
			var item = $(event.target.parentElement.parentElement.childNodes[1].childNodes[3]);
			var relayPoemMainIndex = item.val();
			location.href="relayPoemMainUpdate.do?relayPoem=" + relayPoemMainIndex;
        });
		
		// 릴레이 시 삭제
		$('.delete').click(function () {
            var item = $(event.target.parentElement.parentElement.childNodes[1].childNodes[3]);
            var relayPoemMainIndex = item.val();
            $.post("relayPoemMainDelete.do", {relayPoem : relayPoemMainIndex}, function(data) { location.href="relayPoemMain.do"; });
        });
	});

	// 릴레이 시 조회
	var searchDivFlag = false;
	// 릴레이 시 조회 버튼 클릭시 이벤트
	function searchRelayPoemBtn() {
		if (!searchDivFlag) {
			// flag가 false일 때(검색창이 안보일 때), 보이도록 변경
			searchDivFlag = true;
			$('#searchRealyPoem').css('display', 'inline-block');
			
			// 추가 버튼 누르면 텍스트입력창에 포커스 주기
			$('#searchRealyPoem').focus();
		} else {
			// flag가 true이면 검색창이 있다는 의미
			// flag 설정 변경하고 검색내용이 있으면 전달
			searchDivFlag = false;
			// 입력칸에 값이 있으면 검색어를 Servlet으로 전송
			var searchRelayPoem = $('#searchRealyPoem').val();
			if (searchRelayPoem != "" && searchRelayPoem != null) {
				// url에 searchRelayPoem Index 추가하여 get 방식으로 전달
				location.href="relayPoemMain.do?relayPoem=" + searchRelayPoem;
			}
			$('#searchRealyPoem').css('display', 'none');
		}
	}
	// ---------------------------------------------------------------

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
				<li class="active"><a href="relayPoemMain.do">RelayPoemMain</a></li>
				<li><a href="relayPoemReply.do">RelayPoemReply</a></li>
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
					
					<h1>릴레이퐁퐁 메인 관리</h1>
				</header>
				
				<!-- 관리자 회원 관리 버튼 -->
				<div class="actions">
					<div id="buttonDiv">
						<input type="button" class="button" value="추가" onclick="location.href='relayPoemMainInsert.do';" />
						<input type="button" class="button" value="조회" onclick="searchRelayPoemBtn();" />
						<input type="text" name="searchRealyPoem" id="searchRealyPoem" placeholder="조회할 릴레이 시" style="width: 200px;" />
					</div>
					<div>
						<br/>
						<input type="button" class="button" value="릴레이 업뎃" onclick="location.href='relayPoemAddReply.do';" />
					</div>
				</div>
				<hr />
				
				<!-- 완성된 릴레이 시 관리 목록 -->
				<div class="table-wrapper">
				
				<!-- Relay Poem Main Servlet에서 request.setAttribute로 전달한 전체 Relay Poem Main List 출력 -->
				<!-- 은지의 출력, 용혁의 페이징의 콤비네이션 -->
					<table class="alt">
						<thead>
							<tr>
								<th style="width: 60px;">Index</th>
								<th style="width: 100px;">Subject</th>
								<th style="width: 150px;">Title</th>
								<th>Contents</th>
								<th style="width: 150px;">Poet</th>
								<th style="width: 100px;"></th>
							</tr>
						</thead>
						<c:set var="j" value="${(paging.pageNo-1)*paging.pageSize}"></c:set>
						<c:set var="loop" value="true"></c:set>
						
						<c:forEach var="i" begin="0" end="${paging.pageSize - 1 }" step="1">
							<c:if test="${paging.totalCount <= i+j }">
								<c:set var="loop" value="false"></c:set>
							</c:if>   
							<c:if test="${loop != false}">
								<tr>
					   				<td>
					   					<c:out value="${i+j+1 }"></c:out>
					   					<!-- hidden 타입으로 실제 weeklyPoemIndex값은 숨기되 보관하기 -->
					   					<input type='hidden' value='<c:out value="${relayPoemMainList.get(i+j).relayMainIndex }"></c:out>' />
					   				</td>
					   				<td><c:out value="${relayPoemMainList.get(i+j).subjectName }"></c:out></td>
					   				<td><c:out value="${relayPoemMainList.get(i+j).title }"></c:out></td>
					   				<td><c:out value="${relayPoemMainList.get(i+j).contents }" escapeXml="false"></c:out></td>
					   				<td><c:out value="${relayPoemMainList.get(i+j).poets }"></c:out></td>
					   				<td>
					   					<input type='button' class='button small update' value='수정' />
					   					<input type='button' class='button small delete' value='삭제' />
					   				</td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
					
					<!-- 페이징 -->
					<div class="paginate">
					    <a href="relayPoemMain.do?pageNo=${paging.firstPageNo}" class="first">처음 페이지</a>
					    <a href="relayPoemMain.do?pageNo=${paging.prevPageNo}" class="prev">이전 페이지</a>
					    <span>
					        <c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
					            <c:choose>
					                <c:when test="${i eq paging.pageNo}"><a href="relayPoemMain.do?pageNo=${i}" class="choice">${i}</a></c:when>
					                <c:otherwise><a href="relayPoemMain.do?pageNo=${i}">${i}</a></c:otherwise>
					            </c:choose>
					        </c:forEach>
					    </span>
					    <a href="relayPoemMain.do?pageNo=${paging.nextPageNo}" class="next">다음 페이지</a>
					    <a href="relayPoemMain.do?pageNo=${paging.finalPageNo}" class="last">마지막 페이지</a>
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