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
 * Servlet implementation class FindPwdServlet
 */
@WebServlet("/findPwd.do")
public class FindPwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindPwdServlet() {
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
		
		String idFindPwd = (String)request.getParameter("idFindPwd");
		String emailFindPwd = (String)request.getParameter("emailFindPwd");

		System.out.println(idFindPwd);
		System.out.println(emailFindPwd);
		
		MemberDAO memberDao = MemberDAO.getInstance();
		//이메일로 비밀번호찾기
		String foundPwd = memberDao.findPWD(idFindPwd, emailFindPwd);
		
		// 일치하는 이메일과 아이디로 비밀번호를 찾았을 경우
		// (사실 새로 생성한 랜덤값)
		// 메일전송
		FindInfoMail mailing = new FindInfoMail();
		try {
			mailing.sendMail(1, emailFindPwd, foundPwd);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("message", "비밀번호 찾고 시퐁");
		
		// 메일 전송 완료 페이지로 이동
		RequestDispatcher rd = request.getRequestDispatcher("PoemPongPong/sendMailPage.jsp");
		rd.forward(request, response);
	}

}
