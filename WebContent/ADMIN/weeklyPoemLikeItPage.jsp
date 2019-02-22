<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.poem.common.vo.WeeklyLikeItJoinVO"
		import="java.util.ArrayList"
		import="com.poem.common.Paging"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<% ArrayList<WeeklyLikeItJoinVO> weeklyLikeJoinList = (ArrayList<WeeklyLikeItJoinVO>)request.getAttribute("weeklyLikeJoinList"); %>

<%
   //페이징
   Paging paging = new Paging();

   if(request.getParameter("pageNo") == null || request.getParameter("pageNo") == ""){
      paging.setPageNo(1);
   } else{
      paging.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
   }
   
   paging.setTotalCount(((ArrayList<WeeklyLikeItJoinVO>)request.getAttribute("weeklyLikeJoinList")).size());
   paging.makePaging();
   
   request.setAttribute("paging", paging);
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page - Weekly Like It</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="ADMIN/assets/css/main.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$('#searchWeeklyLike').css('display', 'none');
		
		// 검색할 때 엔터키 동작 가능
		$('#searchWeeklyLike').keypress(function() {
			if(event.which == 13) {
				searchWeeklyLikeBtn();
			}
		});
		
		// 삭제
		$('.delete').click(function () {
			// 삭제버튼 클릭시 노드를 따라 index td 내에 숨겨진 hidden input 값을 얻어오기
			var item = $(event.target.parentElement.parentElement.childNodes[1].childNodes[3]);
			// console.log(item);
            // console.log(item.text());
            var deleteWeeklyLikeItIndex = item.val(); // 선택된 인덱스값
            $.post("weeklyLikeItDelete.do", {weeklyLikeIt : deleteWeeklyLikeItIndex}, function(data) { location.href="weeklyPoem.do"; });
        });
	});
	
	var searchDivFlag = false;
	function searchWeeklyLikeBtn() {
		if (!searchDivFlag) {
			// flag가 false일 때(검색창이 안보일 때), 보이도록 변경
			searchDivFlag = true;
			$('#searchWeeklyLike').css('display', 'inline-block');
			
			// 추가 버튼 누르면 텍스트입력창에 포커스 주기
			$('#searchWeeklyLike').focus();
		} else {
			// flag가 true이면 검색창이 있다는 의미
			// flag 설정 변경하고 검색내용이 있으면 전달
			searchDivFlag = false;
			// 입력칸에 값이 있으면 Id값 Servlet으로 Post 전송
			var searchWeeklyLike = $('#searchWeeklyLike').val();
			if (searchWeeklyLike != "" && searchWeeklyLike != null) {
				// url에 memberId 추가하여 get 방식으로 전달
				location.href="member.do?weeklyLike=" + searchWeeklyLike;
			}
			$('#searchWeeklyLike').css('display', 'none');
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
				<li class="active"><a href="weeklyPoemLikeIt.do">WeeklyLikeIt</a></li>
				<li><a href="relayPoemMain.do">RelayPoemMain</a></li>
				<li><a href="relayPoemReply.do">RelayPoemReply</a></li>
				<li><a href="relayPoemLikeIt.do">RelayPoemLikeIt</a></li>
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
					
					<h1>시인퐁퐁 굿뜨 관리</h1>
				</header>
				
				<!-- 데이터 관리 버튼 -->
				<div class="actions">
					<!-- <div id="buttonDiv">
						<input type="button" class="button" value="추가" onclick="location.href='weeklyLikeInsert.do';" />
						<input type="button" class="button" value="조회" onclick="searchWeeklyLikeBtn();" />
						<input type="text" name="searchWeeklyLike" id="searchWeeklyLike" placeholder="검색어 입력" style="width: 200px;" />
					</div> -->
				</div>
				<hr />
				
				<!-- 은지의 출력, 용혁의 페이징의 콤비네이션 -->
	            <table class="alt">
	               <thead>
	                  <tr>
						<th>Index</th>
						<th>Title</th>
						<th>Contents</th>
						<th>likeCount</th>
						<th>Author</th>
						<th></th>
	                  </tr>
	               </thead>
	               <c:set var="j" value="${(paging.pageNo-1)*paging.pageSize}"></c:set>
	               <c:set var="loop" value="true"></c:set>
	               <c:forEach var="i" begin="0" end="${paging.pageSize }" step="1">
	                  <c:if test="${paging.totalCount <= i+j }">
	                     <c:set var="loop" value="false"></c:set>
	                  </c:if>   
	                  <c:if test="${loop != false}">                                    
	                     <tr>
	                        <td>
	                           <c:out value="${i+j+1 }"></c:out>
	                           <!-- hidden 타입으로 실제 weeklyLikeItIndex값은 숨기되 보관하기 -->
			   					<input type="hidden" value='<c:out value="${weeklyLikeJoinList.get(i+j).wl.weeklyLikeItIndex }"></c:out>' />
			   				</td>
			   				<td><c:out value="${weeklyLikeJoinList.get(i+j).wp.title }"></c:out></td>
			   				<td><c:out value="${weeklyLikeJoinList.get(i+j).wp.contents }" escapeXml="false"></c:out></td>
			   				<td><c:out value="${weeklyLikeJoinList.get(i+j).wp.likeCount }"></c:out></td>
			   				<td><c:out value="${weeklyLikeJoinList.get(i+j).wp.memberID }"></c:out></td>
			   				
	                        <!-- <td>
			   					<input type='button' class='button small delete' value='삭제' />
			   				</td> -->
	                     </tr>
	                  </c:if>
	               </c:forEach>
	            </table>
	            
	            <!-- 페이징 -->
				<div class="paginate">
				    <a href="weeklyPoemLikeIt.do?pageNo=${paging.firstPageNo}" class="first">처음 페이지</a>
				    <a href="weeklyPoemLikeIt.do?pageNo=${paging.prevPageNo}" class="prev">이전 페이지</a>
				    <span>
				        <c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
				            <c:choose>
				                <c:when test="${i eq paging.pageNo}"><a href="weeklyPoemLikeIt.do?pageNo=${i}" class="choice">${i}</a></c:when>
				                <c:otherwise><a href="weeklyPoemLikeIt.do?pageNo=${i}">${i}</a></c:otherwise>
				            </c:choose>
				        </c:forEach>
				    </span>
				    <a href="weeklyPoemLikeIt.do?pageNo=${paging.nextPageNo}" class="next">다음 페이지</a>
				    <a href="weeklyPoemLikeIt.do?pageNo=${paging.finalPageNo}" class="last">마지막 페이지</a>
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