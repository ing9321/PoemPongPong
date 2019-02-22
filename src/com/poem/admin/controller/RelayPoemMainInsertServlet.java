package com.poem.admin.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poem.admin.dao.RelayPoemMainDAO;
import com.poem.admin.dao.SubjectDAO;
import com.poem.admin.dao.WeeklyPoemJoinDAO;
import com.poem.common.vo.SubjectVO;

/**
 * Servlet implementation class RelayPoemMainInsertServlet
 */
@WebServlet("/relayPoemMainInsert.do")
public class RelayPoemMainInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelayPoemMainInsertServlet() {
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
			SubjectDAO subjectDao = SubjectDAO.getInstance();
			ArrayList<SubjectVO> subjectList = subjectDao.selectAllSubject();
			
			request.setAttribute("subjectList", subjectList);
						
			RequestDispatcher rd = request.getRequestDispatcher("ADMIN/relayPoemMainInsertPage.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		String subject = request.getParameter("subject");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		contents = contents.replaceAll("\r\n", "<br>");
		String poets = request.getParameter("poets");
		
		RelayPoemMainDAO relayPoemMainDao = RelayPoemMainDAO.getInstance();
		
		// insertWeeklyPoemJoin() 메서드를 통해 DB WeeklyPoem 테이블에 시 추가
		int result = relayPoemMainDao.insertRelayPoemMain(subject, title, contents, poets);
		if (result == 1) {
			// 시 추가 성공
			System.out.println("작성되었습니다");
			response.sendRedirect("relayPoemMain.do");
		} else {
			// 추가 실패 : 유효성 검사 및 예외처리 필요
		}
	}

}
