package com.poem.pong.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poem.pong.dao.RelayPoemReplyDAO;
import com.poem.pong.dao.RelayLikeItDAO;

/**
 * Servlet implementation class RelayLikeUserServlet
 */
@WebServlet("/uRelayLike.do")
public class RelayLikeUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelayLikeUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.print("System : 릴레이 댓글에 좋아요를 눌러버렸다..! 두겟으로..!");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		int relayIndex = Integer.parseInt(request.getParameter("index"));
		String memberID = request.getParameter("member");
		System.out.println(relayIndex + ":" + memberID);
		
		// 좋아요 테이블에서 좋아요 여부 확인
		RelayLikeItDAO relayLikeDao = RelayLikeItDAO.getInstance();
		// result : true(좋아요가 있어서 삭제) / false(좋아요없어서 추가)
		boolean result = relayLikeDao.checkedLikeIt(relayIndex, memberID);
		
		RelayPoemReplyDAO relayReplyDao = RelayPoemReplyDAO.getInstance();
		
		if(!result) {
			// 릴레이 댓글 테이블에서 해당 댓글 좋아요 count++
			relayReplyDao.increaseLikeCount(relayIndex);
		} else {
			// 릴레이 댓글 테이블에서 해당 댓글 좋아요 count--
			relayReplyDao.decreaseLikeCount(relayIndex);
		}
		
		
	}

}
