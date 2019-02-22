package com.poem.pong.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poem.admin.dao.RelayPoemReplyDAO;

/**
 * Servlet implementation class RelayReplyUserUpdate
 */
@WebServlet("/uReplyUpdate.do")
public class RelayReplyUserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelayReplyUserUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("System: 릴레이 시 댓글을 수정하려고 접근하였당");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int replyIndex = Integer.parseInt(request.getParameter("index"));
		String oldReplyContents = request.getParameter("before");
		String newReplyContents = request.getParameter("after");
		// 혹시 모를 앞 뒤 공백 제거
		newReplyContents = newReplyContents.trim();
		
		System.out.println("수정할 댓글 번호 : " + replyIndex);
		System.out.println("구댓 : " + oldReplyContents);
		System.out.println("신댓 : " + newReplyContents);
		
		RelayPoemReplyDAO relayReplyDao = RelayPoemReplyDAO.getInstance();
		int result = relayReplyDao.updateRelayReply(replyIndex, oldReplyContents, newReplyContents);
		if(result == 1) {
			System.out.println("댓글 수정");
			
		} else {
			// 실패
		}
	}

}
