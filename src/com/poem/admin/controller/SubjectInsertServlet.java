package com.poem.admin.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poem.admin.dao.SubjectDAO;

/**
 * Servlet implementation class SubjectInsertServlet
 */
@WebServlet("/subjectInsert.do")
public class SubjectInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubjectInsertServlet() {
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
			doPost(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String subjectName = request.getParameter("subject");
		System.out.println(subjectName);
		
		SubjectDAO subjectDao = SubjectDAO.getInstance();
		
		int result = subjectDao.insertSubject(subjectName);
		if(result == 1) {
			System.out.println("주제 추가");
			response.sendRedirect("subject.do");
		} else {
			// 실패 처리
		}
		
	}

}
