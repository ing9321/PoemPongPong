<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.poem.common.vo.RelayLikeItJoinVO"
		import="com.poem.common.vo.RelayLikeItVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.poem.common.Paging"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<% ArrayList<RelayLikeItJoinVO> relayLikeJoinList = (ArrayList<RelayLikeItJoinVO>)request.getAttribute("relayLikeJoinList"); %>

<%
   //페이징
   Paging paging = new Paging();

   if(request.getParameter("pageNo") == null || request.getParameter("pageNo") == ""){
      paging.setPageNo(1);
   } else{
      paging.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
   }
   
   paging.setTotalCount((relayLikeJoinList).size());
   paging.makePaging();
   
   request.setAttribute("paging", paging);
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page - Relay Poem Like It</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="ADMIN/assets/css/main.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<!-- relay like : 추가, 삭제, member 검색 -->

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
				<li><a href="relayPoemReply.do">RelayPoemReply</a></li>
				<li class="active"><a href="relayPoemLikeIt.do">RelayPoemLikeIt</a></li>
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
					
					<h1>릴레이퐁퐁 굿뜨 관리</h1>
				</header>
				
				<!-- 좋아요 관리 목록 -->
				
				<div class="table-wrapper">
				<!-- 은지의 출력, 용혁의 페이징의 콤비네이션 -->
		            <table class="alt">
		            	<thead>
		            		<tr>
		            			<th>Index</th>
								<th>Member ID</th>
								<th>Contents</th>
								<!-- <th></th> -->
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
		                           		<c:out value="${i+j+1 }"></c:out> <!-- 출력 인덱스 -->
		                        		<!-- hidden 타입으로 실제 weeklyPoemIndex값은 숨기되 보관하기 -->
					   					<input type="hidden" value='<c:out value="${relayLikeJoinList.get(i+j).rl.relayLikeItIndex }"></c:out>' />
		                        	</td>
					   				<td><c:out value="${relayLikeJoinList.get(i+j).rl.memberID }"></c:out></td>
					   				<td><c:out value="${relayLikeJoinList.get(i+j).rr.contents }"></c:out></td>
					   				
					   				<!-- <td>
					   					<input type='button' class='button small delete' value='삭제' />
					   				</td> -->
		                     	</tr>
		                 	</c:if>
		              	</c:forEach>
		            </table>
		            
		            <!-- 페이징 -->
					<div class="paginate">
					    <a href="relayPoemLikeIt.do?pageNo=${paging.firstPageNo}" class="first">처음 페이지</a>
					    <a href="relayPoemLikeIt.do?pageNo=${paging.prevPageNo}" class="prev">이전 페이지</a>
					    <span>
					        <c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
					            <c:choose>
					                <c:when test="${i eq paging.pageNo}"><a href="relayPoemLikeIt.do?pageNo=${i}" class="choice">${i}</a></c:when>
					                <c:otherwise><a href="relayPoemLikeIt.do?pageNo=${i}">${i}</a></c:otherwise>
					            </c:choose>
					        </c:forEach>
					    </span>
					    <a href="relayPoemLikeIt.do?pageNo=${paging.nextPageNo}" class="next">다음 페이지</a>
					    <a href="relayPoemLikeIt.do?pageNo=${paging.finalPageNo}" class="last">마지막 페이지</a>
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