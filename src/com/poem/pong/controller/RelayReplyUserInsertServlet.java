package com.poem.pong.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poem.admin.dao.RelayPoemReplyDAO;
import com.poem.common.vo.MemberVO;

/**
 * Servlet implementation class RelayReplyUserInsertServlet
 */
@WebServlet("/uRelayReplyWrite.do")
public class RelayReplyUserInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelayReplyUserInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("System : 새로운 댓글을 다는데 두겟으로 접근해따아");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		String newReply = request.getParameter("newReply");
		// 혹시 모를 앞 뒤 공백 제거
		newReply = newReply.trim();
		
		HttpSession session = request.getSession();
		String loginMember = (String) session.getAttribute("loginMember");
		
		RelayPoemReplyDAO relayReplyDao = RelayPoemReplyDAO.getInstance();
		
		int result = relayReplyDao.insertRelayReply(loginMember, newReply);
		if(result == 1){
			System.out.println("댓글 작성 성공");
			response.sendRedirect("relayPoemUser.do");
		} else {
			// 실패 처리
		}
	}

}
