package com.poem.pong.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poem.admin.dao.RelayLikeItDAO;
import com.poem.common.vo.RelayLikeItVO;
import com.poem.common.vo.RelayPoemMainVO;
import com.poem.common.vo.RelayPoemReplyVO;
import com.poem.pong.dao.RelayPoemMainDAO;
import com.poem.admin.dao.RelayPoemReplyDAO;
import com.poem.pong.dao.RelayReplyJoinDAO;
import com.poem.pong.vo.RelayReplyJoinMemberVO;

/**
 * Servlet implementation class WeeklyPoemUserServlet
 */
@WebServlet("/relayPoemUser.do")
public class RelayPoemUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelayPoemUserServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RelayPoemMainDAO relayDao = RelayPoemMainDAO.getInstance();
		
		// 완성된 릴레이시와 진행중인 릴레이 시 메인을 포함한 전체 리스트
		// - 지난 릴레이시 보기에 사용됨
		ArrayList<RelayPoemMainVO> relayPoemMainList = relayDao.selectAllRelayPoem();
		request.setAttribute("relayPoemMainList", relayPoemMainList);
		
		// 현재 진행중인 릴레이 시 메인
		RelayPoemMainVO currentRelayPoemMain = relayDao.currentRelayPoemMain();
		request.setAttribute("currentRelayPoemMain", currentRelayPoemMain);
		
		RelayPoemReplyDAO relayReplyDao = RelayPoemReplyDAO.getInstance();
		ArrayList<RelayPoemReplyVO> relayReplyList = relayReplyDao.selectAllRelayReply();
		
		RelayReplyJoinDAO relayReplyJoinMemberDao = RelayReplyJoinDAO.getInstance();
		
		// 릴레이 시 댓글 리스트 중 좋아요 수가 많은 베스트5 리스트
		// - 멤버테이블과 조인해서 출력
		ArrayList<RelayReplyJoinMemberVO> bestReplyList = relayReplyJoinMemberDao.selectBestRelayReply();
		request.setAttribute("bestReplyList", bestReplyList);
		
		// 릴레이 시 댓글을 작성일 기준 최신순으로 출력
		ArrayList<RelayReplyJoinMemberVO> relayReplyMemberList = relayReplyJoinMemberDao.selectAllRelayReply();
		request.setAttribute("relayReplyMemberList", relayReplyMemberList);
		
		RelayLikeItDAO relayLikeDao = RelayLikeItDAO.getInstance();
		
		HttpSession session = request.getSession();
		String loginMember = (String)session.getAttribute("loginMember");
		if(loginMember == null || loginMember == ""){
			loginMember = "g";
		}
		// 릴레이 시 댓글 좋아요 리스트
		// - 로그인한 회원이 좋아요를 눌렀던 상태일 때 처리를 위해
		ArrayList<RelayLikeItVO> relayLikeList = relayLikeDao.selectRelayLikeIt(loginMember);
		request.setAttribute("loginMemberReplyLikeList", relayLikeList);
		
		RequestDispatcher rd = request.getRequestDispatcher("PoemPongPong/relayPoemPage.jsp");
		rd.forward(request, response);
	}

}
