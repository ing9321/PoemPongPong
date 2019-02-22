<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.poem.common.vo.WeeklyPoemVO"
		 import="com.poem.common.vo.WeeklyPoemJoinVO"
		 import="com.poem.common.Paging"
		 import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% ArrayList<WeeklyPoemJoinVO> weeklyPoemJoinList = (ArrayList<WeeklyPoemJoinVO>)request.getAttribute("weeklyPoemJoinList"); %>

<%
	//페이징
	Paging paging = new Paging();
	
	if(request.getParameter("pageNo") == null || request.getParameter("pageNo") == ""){
	   paging.setPageNo(1);
	} else{
	   paging.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
	}
	
	paging.setTotalCount(weeklyPoemJoinList.size());
	paging.makePaging();
	
	request.setAttribute("paging", paging);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page - Weekly Poem</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="ADMIN/assets/css/main.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$('#searchBar').css('display', 'none');
		
		// 검색할 때 엔터키 동작 가능
		$('#searchWeeklyPoem').keypress(function() {
			if(event.which == 13) {
				searchWeeklyPoemBtn();
			}
		});
		
		// 7일 시집 수정
		$('.update').click(function () {
			// 수정버튼 클릭시 노드를 따라 index td 내에 숨겨진 hidden input 값을 얻어오기
			var item = $(event.target.parentElement.parentElement.childNodes[1].childNodes[3]);
			// console.log(item);
			// console.log(item.val()); 
			 var updateWeeklyPoemIndex = item.val(); // 선택된 시의 인덱스값
			location.href="weeklyPoemUpdate.do?weeklyPoem=" + updateWeeklyPoemIndex;
        });
		
		// 7일 시집 삭제
		$('.delete').click(function () {
			// 삭제버튼 클릭시 노드를 따라 index td 내에 숨겨진 hidden input 값을 얻어오기
			var item = $(event.target.parentElement.parentElement.childNodes[1].childNodes[3]);
			// console.log(item);
            // console.log(item.text());
            var deleteWeeklyPoemIndex = item.val(); // 선택된 시의 인덱스값
            $.post("weeklyPoemDelete.do", {weeklyPoem : deleteWeeklyPoemIndex}, function(data) { location.href="weeklyPoem.do"; });
        });
	});
	

	// 7일 시집 조회 (제목, 작성자, 주제별 검색)
	var searchDivFlag = false;
	function searchWeeklyPoemBtn() {
		if (!searchDivFlag) {
			// flag가 false일 때(검색창이 안보일 때), 보이도록 변경
			searchDivFlag = true;
			$('#searchBar').css('display', 'inline-block');
			
			// 은지 : 추가 버튼 누르면 텍스트입력창에 포커스 주기
			$('#searchWeeklyPoem').focus();
		} else {
			// flag가 true이면 검색창이 있다는 의미
			// flag 설정 변경하고 검색내용이 있으면 전달
			searchDivFlag = false;
			// 입력칸에 값이 있으면 Id값 Servlet으로 Post 전송
			var searchCategory = $('#searchCategory').val();
			var searchWeeklyPoem = $('#searchWeeklyPoem').val();
			if (searchWeeklyPoem != "" && searchWeeklyPoem != null) {
				// url에 memberId 추가하여 get 방식으로 전달
				location.href="weeklyPoem.do?category=" + searchCategory + "&weeklyPoem=" + searchWeeklyPoem;
			}
			$('#searchBar').css('display', 'none');
		}
	}
	
	function weeklyPoemAddBest(){
		$.post('weeklyPoemAddBest.do',
				function(data){
			alert("베스트 시 선정 / 주제 변경됨");
		});
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
				<li class="active"><a href="weeklyPoem.do">WeeklyPoem</a></li>
				<li><a href="weeklyPoemLikeIt.do">WeeklyLikeIt</a></li>
				<li><a href="relayPoemMain.do">RelayPoemMain</a></li>
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
					
					<h1>시인퐁퐁 관리</h1>
				</header>
				
				<!-- 데이터 관리 버튼 -->
				<div class="actions">
					<div id="buttonDiv">
						<input type="button" class="button" value="베스트 시 선정"onclick="weeklyPoemAddBest();"/>
						<input type="button" class="button" value="추가" onclick="location.href='weeklyPoemInsert.do';" />
						<input type="button" class="button" value="조회" onclick="searchWeeklyPoemBtn();" />
						<div id="searchBar">
						<select name="searchCategory" id="searchCategory" style="display:inline-block; width: 100px;">
							<option value="subject">주제</option>
							<option value="poet">작가</option>
							<option value="title">제목</option>
						</select>
						<input type="text" name="searchWeeklyPoem" id="searchWeeklyPoem" placeholder="검색어 입력" style="width: 200px; display:inline-block;" />
					</div>
					</div>
					
				</div>
				<hr />
				
				<!-- 주제별 주간 시 관리 목록 -->
				<div class="table-wrapper">
					<!-- WeeklyPoemServlet에서 request.setAttribute로 전달한 전체 WeeklyPoemList 출력 -->
					<!-- 은지의 출력, 용혁의 페이징의 콤비네이션 -->
					<table class="alt">
						<thead>
							<tr>
					    		<th>INDEX</th>
					      		<th>SUBJECTNAME</th>
					         	<th>POET</th>
					         	<th>TITLE</th>
					         	<th>CONTENTS</th>
					         	<th>VIEW</th>
					         	<th>LIKE</th>
					         	<th>DATE</th>
					         	<th>BESTPOEM</th>
					         	<!-- <th>BACKGROUND</th> -->
					         	<th></th>
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
					   					<input type="hidden" value='<c:out value="${weeklyPoemJoinList.get(i+j).w.weeklyPoemIndex }"></c:out>' />
					   				</td>
					   				<td><c:out value="${weeklyPoemJoinList.get(i+j).w.subjectName }"></c:out></td>
					   				<td><c:out value="${weeklyPoemJoinList.get(i+j).m.memberPoet }"></c:out></td>
					   				<td><c:out value="${weeklyPoemJoinList.get(i+j).w.title }"></c:out></td>
					   				<td><c:out value="${weeklyPoemJoinList.get(i+j).w.contents }" escapeXml="false"></c:out></td>
					   				<td><c:out value="${weeklyPoemJoinList.get(i+j).w.viewCount }"></c:out></td>
					   				<td><c:out value="${weeklyPoemJoinList.get(i+j).w.likeCount }"></c:out></td>
					   				<td><c:out value="${weeklyPoemJoinList.get(i+j).w.uploadDate }"></c:out></td>
					   				<td>
					   					<c:if test="${weeklyPoemJoinList.get(i+j).w.bestPoem == 0}">
					   						<c:out value="NO"></c:out>
					   					</c:if>
					   					<c:if test="${weeklyPoemJoinList.get(i+j).w.bestPoem == 1}">
					   						<c:out value="YES"></c:out>
					   					</c:if>
					   				</td>
					   				<%-- <td><c:out value="${weeklyPoemJoinList.get(i+j).w.background }"></c:out></td> --%>
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
					    <a href="weeklyPoem.do?pageNo=${paging.firstPageNo}" class="first">처음 페이지</a>
					    <a href="weeklyPoem.do?pageNo=${paging.prevPageNo}" class="prev">이전 페이지</a>
					    <span>
					        <c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
					            <c:choose>
					                <c:when test="${i eq paging.pageNo}"><a href="weeklyPoem.do?pageNo=${i}" class="choice">${i}</a></c:when>
					                <c:otherwise><a href="weeklyPoem.do?pageNo=${i}">${i}</a></c:otherwise>
					            </c:choose>
					        </c:forEach>
					    </span>
					    <a href="weeklyPoem.do?pageNo=${paging.nextPageNo}" class="next">다음 페이지</a>
					    <a href="weeklyPoem.do?pageNo=${paging.finalPageNo}" class="last">마지막 페이지</a>
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