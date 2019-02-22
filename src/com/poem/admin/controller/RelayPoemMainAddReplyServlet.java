package com.poem.admin.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poem.admin.dao.MemberDAO;
import com.poem.admin.dao.RelayLikeItDAO;
import com.poem.admin.dao.RelayPoemMainDAO;
import com.poem.admin.dao.RelayPoemReplyDAO;
import com.poem.admin.dao.SubjectDAO;
import com.poem.common.vo.RelayPoemReplyVO;

/**
 * Servlet implementation class RelayPoemMainAddReply
 */
@WebServlet("/relayPoemAddReply.do")
public class RelayPoemMainAddReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * 
     * @see HttpServlet#HttpServlet()
     */
    public RelayPoemMainAddReplyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		// 주소로 접근하였을 시 처리
		HttpSession session = request.getSession();
		String sessionCheck = (String) session.getAttribute("loginAdmin");
		
		// 세션이 없다면 확인 후 주소 접근
		if(sessionCheck == null || sessionCheck.equals("")){
			RequestDispatcher rd = request.getRequestDispatcher("adminLogin.jsp");
			request.setAttribute("msg", "잘못된 접근");
			rd.forward(request, response);
		}else{
			doPost(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");

		// 릴레이 댓글 중에서 좋아요가 가장 많은 댓글 선택
		RelayPoemReplyDAO relayReplyDao = RelayPoemReplyDAO.getInstance();
		RelayPoemReplyVO bestReply = relayReplyDao.selectBestReply();
		String contents = bestReply.getContents(); // 내용 or 제목
		
		// 작가명
		MemberDAO memberDao = MemberDAO.getInstance();
		String memberPoet = memberDao.searchMemberPoet(bestReply.getMemberID());
		
		RelayPoemMainDAO relayMainDao = RelayPoemMainDAO.getInstance();
		
		// <br>태그 구분으로 몇행이 완성됐나 확인할 수 있음
		String lastRelayContents = relayMainDao.selectLastRelayPoem().getContents();
		
		String[] splitArray = {};
		
		if(lastRelayContents != null && lastRelayContents != ""){
			int splitarraylength = lastRelayContents.split("<br>").length;
			splitArray = new String[splitarraylength];
			splitArray = lastRelayContents.split("<br>");
		}
		
		int result = 0;
		if(splitArray.length > 7){
			// 릴레이 메인 8줄(이상)인 경우
			// 릴레이 댓글은 제목으로 설정
			 result = relayMainDao.setRelayMainTitle(contents);
			
			// 새로운 주제 선택
			SubjectDAO subjectDao = SubjectDAO.getInstance();
			subjectDao.selectAllSubject();
			String newSubject = subjectDao.chooseRandomSubject();
			
			// 선택된 주제로 새로운 릴레이 시작
			relayMainDao.startNewRelay(newSubject);
		} else {
			// 가장 최신 릴레이 메인에 내용 추가
			// 매개변수 contents, poet
			result = relayMainDao.addReplyToMain(contents, memberPoet);
		}
		
		// 릴레이 내용 추가 혹은 제목설정이 성공했을 경우
		if(result == 1) {
			// 릴레이좋아요 테이블 리셋
			RelayLikeItDAO relayLikeDao = RelayLikeItDAO.getInstance();
			relayLikeDao.clearLikeTable();
			
			// 릴레이댓글 테이블 리셋
			relayReplyDao.clearReplyTable();
		} else {
			System.out.println("뭔가가 실패했다 수박");
		}
		
		response.sendRedirect("relayPoemMain.do");
	}

}
