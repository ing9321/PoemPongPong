package com.poem.pong.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poem.admin.dao.RelayPoemReplyDAO;

/**
 * Servlet implementation class RelayReplyUserDeleteServlet
 */
@WebServlet("/uReplyDelete.do")
public class RelayReplyUserDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelayReplyUserDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("System: 릴레이시 댓글을 지우는 서블릿의 두겟을 호출하였다");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		int deleteReplyIndex = Integer.parseInt(request.getParameter("index"));
		System.out.println("삭제할 댓글 번호 : " + deleteReplyIndex);
		
		RelayPoemReplyDAO relayReplyDao = RelayPoemReplyDAO.getInstance();
		int result = relayReplyDao.deleteRelayReply(deleteReplyIndex);
		if(result == 1) {
			// 삭제 성공
			System.out.println("댓글 삭제");
		} else {
			// 삭제 실패
		}
	}

}
