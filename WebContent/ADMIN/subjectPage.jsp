<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.poem.common.vo.SubjectVO"
		 import="com.poem.common.Paging"
		 import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% ArrayList<SubjectVO> subjectList = (ArrayList<SubjectVO>)request.getAttribute("SubjectList");
	
	//페이징
	Paging paging = new Paging();
	
	if(request.getParameter("pageNo") == null || request.getParameter("pageNo") == ""){
	   paging.setPageNo(1);
	} else{
	   paging.setPageNo(Integer.parseInt(request.getParameter("pageNo")));
	}
	
	paging.setTotalCount(subjectList.size());
	paging.makePaging();
	
	request.setAttribute("paging", paging);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Page - Subject</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="ADMIN/assets/css/main.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$('#newSubject').css('display', 'none');
		
		// 텍스트입력창에서 엔터키 눌렀을 때도 동작하도록 설정
		$('#newSubject').keypress(function() {
			if(event.which == 13) {
				addSubjectBtn();
			}
		});
		
		// 수정버튼 toggle키로 구현
		var subjectEditFlag = false;
		var oldSubjectName = ""; // 수정 전 주제명 
		var newSubjectName = ""; // 수정 후 주제명
		// 주제 수정
		$('.updateSubject').click(function () {
			alert("주제는 수정할 수 업스니다.. 다른 테이블에 FK로 참조되고 있어서여..ㅎ");
			/* if(!subjectEditFlag) {
				// 수정버튼을 눌렀을 때
				subjectEditFlag = true;
				
				// 수정버튼이 선택된 row의 주제명 얻기
				var nameTag = $(event.target.parentElement.parentElement.childNodes[3]);
				oldSubjectName = nameTag.text(); // 태그에 들어있는 내용(주제명)
				
				// 수정버튼이 선택된 row의 주제명 td를 input type=text로 수정
				$(nameTag).html("<input type='text' value='" + oldSubjectName + "'/>");
				
				// 수정버튼 내용을 저장으로 변경
				event.target.value = "저장";
				
			} else {
				// 다시 수정버튼(저장)을 눌렀을 때
				subjectEditFlag = false;
				
				// 주제명 input type text 선택
				var item = $(event.target.parentElement.parentElement.childNodes[3].childNodes);
               	newSubjectName = item.val(); // input type text 내용(변경할 주제명)
                $.post("subjectUpdate.do", {oldSubjectName : oldSubjectName, newSubjectName : newSubjectName}, function(result) {location.reload();});
                event.target.value = "수정";
                
			} */
        });
		
		// 주제 삭제
		$('.deleteSubject').click(function () {
            var item = $(event.target.parentElement.parentElement.childNodes[3]);
            var deleteSubject = item.text();
            $.post("subjectDelete.do", {DeleteSubject : deleteSubject}, function(result) { location.reload(); });
        });
	});

	// 주제 추가 버튼 flag 동작
	var subjectFlag = false;
	function addSubjectBtn() {
		if (!subjectFlag) {
			subjectFlag = true;
			$('#newSubject').css('display', 'inline-block');
			
			// 추가 버튼 누르면 텍스트입력창에 포커스 주기
			$('#newSubject').focus();
		} else {
			subjectFlag = false;
			
			// 주제입력칸에 값이 있을 경우 전달
			var subject = $('#newSubject').val();
			if(subject != null && subject != "") {
				// 새로운 주제를 추가한 후 페이지 새로고침
				$.post("subjectInsert.do", {subject : subject}, function(result) {location.reload();});
			}
			$('#newSubject').css('display', 'none');
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
				<li><a href="member.do">Member</a></li>
				<li><a href="weeklyPoem.do">WeeklyPoem</a></li>
				<li><a href="weeklyPoemLikeIt.do">WeeklyLikeIt</a></li>
				<li><a href="relayPoemMain.do">RelayPoemMain</a></li>
				<li><a href="relayPoemReply.do">RelayPoemReply</a></li>
				<li><a href="relayPoemLikeIt.do">RelayLikeIt</a></li>
				<li class="active"><a href="subject.do">Subject</a></li>
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
					
					<h1>주제 관리</h1>
				</header>
				
				<!-- 관리자 회원 관리 버튼 -->
				<div class="actions">
					<input type="button" class="button" value="추가" onclick="addSubjectBtn();" />
					<input type="text" name="newSubject" id="newSubject" placeholder="추가할 주제" style="width: 200px;" />
				</div>
				<hr />
				<!-- 주제 관리 목록 -->
				<div class="table-wrapper">
					<table class="alt">
						<thead>
							<tr>
								<th style="width: 100px;">Index</th>
								<th>Subject</th>
								<th style="width: 300px;"></th>
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
					   				<td><c:out value="${SubjectList.get(i+j).subjectName }"></c:out></td>
									<td>
					   					<input type='button' class='button small updateSubject' value='수정' />
					   					<input type='button' class='button small deleteSubject' value='삭제' />
					   				</td>
					   			</tr>
							</c:if>
						</c:forEach>	
					</table>
					
					<!-- 페이징 -->
					<div class="paginate">
					    <a href="subject.do?pageNo=${paging.firstPageNo}" class="first">처음 페이지</a>
					    <a href="subject.do?pageNo=${paging.prevPageNo}" class="prev">이전 페이지</a>
					    <span>
					        <c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
					            <c:choose>
					                <c:when test="${i eq paging.pageNo}"><a href="subject.do?pageNo=${i}" class="choice">${i}</a></c:when>
					                <c:otherwise><a href="subject.do?pageNo=${i}">${i}</a></c:otherwise>
					            </c:choose>
					        </c:forEach>
					    </span>
					    <a href="subject.do?pageNo=${paging.nextPageNo}" class="next">다음 페이지</a>
					    <a href="subject.do?pageNo=${paging.finalPageNo}" class="last">마지막 페이지</a>
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