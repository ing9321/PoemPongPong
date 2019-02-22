package com.poem.pong.controller;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poem.common.FindInfoMail;
import com.poem.pong.dao.MemberDAO;

/**
 * Servlet implementation class FindIdServlet
 */
@WebServlet("/findId.do")
public class FindIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindIdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		String emailFindId = (String)request.getParameter("emailFindId");
		System.out.println(emailFindId);
		
		MemberDAO memberDao = MemberDAO.getInstance();
		//이메일로 아이디찾기
		String foundId = memberDao.findID(emailFindId);
		
		// 메일로 전달하기 : 차후 추가
		FindInfoMail mailing = new FindInfoMail();
		try {
			mailing.sendMail(0, emailFindId, foundId);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("message", "아이디 찾고 시퐁");
		
		// 메일 전송 완료 페이지로 이동
		RequestDispatcher rd = request.getRequestDispatcher("PoemPongPong/sendMailPage.jsp");
		rd.forward(request, response);
		
	}

}
