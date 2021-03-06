package com.poem.admin.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poem.admin.dao.RelayPoemMainDAO;
import com.poem.admin.dao.WeeklyPoemDAO;

/**
 * Servlet implementation class RelayPoemMainDeleteServlet
 */
@WebServlet("/relayPoemMainDelete.do")
public class RelayPoemMainDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelayPoemMainDeleteServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 주소로 접근하였을 시 처리
		HttpSession session = request.getSession();
		String sessionCheck = (String) session.getAttribute("loginAdmin");
		
		// 세션이 없다면 확인 후 주소 접근
		if(sessionCheck == null || sessionCheck.equals("")){
			RequestDispatcher rd = request.getRequestDispatcher("adminLogin.jsp");
			request.setAttribute("msg", "잘못된 접근");
			rd.forward(request, response);
		}else{
			System.out.println("RelayPoemMainDeleteServlet의 doGet 호출");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 릴레이 시 삭제
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		int relayPoemIndex = Integer.parseInt(request.getParameter("relayPoem"));
		
		RelayPoemMainDAO relayPoemMainDao = RelayPoemMainDAO.getInstance();
		int result = relayPoemMainDao.deleteRelayPoemMain(relayPoemIndex);
		if(result == 1) {
			// 삭제 성공
			System.out.println("삭제 성공");
		} else {
			// 삭제 실패 : 회원이 존재하지 않습니다?
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("ADMIN/relayPoemMainPage.jsp");
		rd.forward(request, response);
	}

}
