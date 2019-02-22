<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.poem.common.vo.MemberVO"
		 import="com.poem.common.Paging"
		 import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% ArrayList<MemberVO> memberList = (ArrayList<MemberVO>)request.getAttribute("memberList");

	//페이징
	Paging paging = new Paging();
	
	if(request.getParameter("pageNo") == null || request.getParameter("pageNo") == ""){
	   paging.setPageNo(1);
	} else{
	   paging.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
	}

	paging.setTotalCount(memberList.size());
	paging.makePaging();
	
	request.setAttribute("paging", paging);
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page - Member</title>
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="ADMIN/assets/css/main.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$('#searchMember').css('display', 'none');
		
		// 회원 조회 텍스트창에서 엔터키 눌렀을 때도 동작하도록 설정
		$('#searchMember').keypress(function() {
			if(event.which == 13) {
				searchMemberBtn();
			}
		});
		
		// 회원 정보 수정
		$('.update').click(function () {
			var item = $(event.target.parentElement.parentElement.childNodes[3]);
			var memberId = item.text();
			location.href="memberUpdate.do?member=" + memberId;
        });
		
		// 회원 탈퇴 (삭제)
		$('.delete').click(function () {
            var item = $(event.target.parentElement.parentElement.childNodes[3]);
            var memberId = item.text();
            $.post("memberDelete.do", {member : memberId}, function(data) { location.href="member.do"; });
        });
	});

	// 회원 ID 조회
	var searchDivFlag = false;
	// 회원 조회 버튼 클릭시 이벤트
	function searchMemberBtn() {
		if (!searchDivFlag) {
			// flag가 false일 때(검색창이 안보일 때), 보이도록 변경
			searchDivFlag = true;
			$('#searchMember').css('display', 'inline-block');

			// 추가 버튼 누르면 텍스트입력창에 포커스 주기
			$('#searchMember').focus();
		} else {
			// flag가 true이면 검색창이 있다는 의미
			// flag 설정 변경하고 검색내용이 있으면 전달
			searchDivFlag = false;
			// 입력칸에 값이 있으면 Id값 Servlet으로 Post 전송
			var searchMember = $('#searchMember').val();
			if (searchMember != "" && searchMember != null) {
				// url에 memberId 추가하여 get 방식으로 전달
				location.href="member.do?searchMember=" + searchMember;				
			}
			$('#searchMember').css('display', 'none');
		}
	}
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
				<li class="active"><a href="member.do">Member</a></li>
				<li><a href="weeklyPoem.do">WeeklyPoem</a></li>
				<li><a href="weeklyPoemLikeIt.do">WeeklyPoemLikeIt</a></li>
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
					
					<h1>회원 관리</h1>
				</header>
				
				<!-- 관리자 회원 관리 버튼 -->
				<div class="actions">
					<div id="buttonDiv">
						<input type="button" class="button" value="추가" onclick="location.href='memberInsert.do';" />
						<input type="button" class="button" value="조회" onclick="searchMemberBtn();" />
						<input type="text" name="searchMember" id="searchMember" placeholder="조회할 회원" style="width: 200px;" />
					</div>
				</div>
				<hr />
				
				<!-- 회원 출력 테이블 -->
				
				<div class="table-wrapper" id="tableCanvas">
				
					<!-- 으 토나와.. -->
					<!-- MemberServlet에서 request.setAttribute로 전달한 전체 MemberList 출력 -->
					<!-- 은지의 출력, 용혁의 페이징의 콤비네이션 -->
					<table class="alt">
						<thead>
							<tr>
								<th>INDEX</th>
					    		<th>ID</th>
					        	<!-- <th>PW</th> -->
					      		<th>닉네임</th>
					         	<!-- <th>프로필 사진</th> -->
					         	<th>Email</th>
					        	<th>가입경로</th>
					        	<th>활동상태</th>
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
					   				<td><c:out value="${i+j+1 }"></c:out></td>
					   				<td><c:out value="${memberList.get(i+j).memberID }"></c:out></td>
					   				<%-- <td><c:out value="${memberList.get(i+j).memberPWD }"></c:out></td> --%>
					   				<td><c:out value="${memberList.get(i+j).memberPoet }"></c:out></td>
					   				<%-- <td><c:out value="${memberList.get(i+j).memberPhoto }"></c:out></td> --%>
					   				<td><c:out value="${memberList.get(i+j).memberEmail }"></c:out></td>
					   				<td><c:out value="${memberList.get(i+j).memberPath }"></c:out></td>
					   				<td><c:out value="${memberList.get(i+j).memberDormancy }"></c:out></td>
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
					    <a href="member.do?pageNo=${paging.firstPageNo}" class="first">처음 페이지</a>
					    <a href="member.do?pageNo=${paging.prevPageNo}" class="prev">이전 페이지</a>
					    <span>
					        <c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
					            <c:choose>
					                <c:when test="${i eq paging.pageNo}"><a href="member.do?pageNo=${i}" class="choice">${i}</a></c:when>
					                <c:otherwise><a href="member.do?pageNo=${i}">${i}</a></c:otherwise>
					            </c:choose>
					        </c:forEach>
					    </span>
					    <a href="member.do?pageNo=${paging.nextPageNo}" class="next">다음 페이지</a>
					    <a href="member.do?pageNo=${paging.finalPageNo}" class="last">마지막 페이지</a>
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