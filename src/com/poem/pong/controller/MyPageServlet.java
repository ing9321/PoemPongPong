package com.poem.pong.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poem.common.vo.MemberVO;
import com.poem.pong.dao.MemberDAO;

/**
 * Servlet implementation class MyPageServlet
 */
@WebServlet("/myPage.do")
public class MyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("My Page : GET");
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String memberID = (String)session.getAttribute("loginMember");
		
		MemberDAO memberDao = MemberDAO.getInstance();
		// 세션에 저장된 멤버정보 불러오기
//		MemberVO myInfo = memberDao.getMemberInfo(memberID);
		MemberVO myInfo = memberDao.isMember(memberID);
		
		// 어트리뷰트를 통해 정보 넘기고 출력
		request.setAttribute("myInfo", myInfo);
		RequestDispatcher rd = request.getRequestDispatcher("PoemPongPong/myPage.jsp");
		rd.forward(request, response);
		
	}

}
