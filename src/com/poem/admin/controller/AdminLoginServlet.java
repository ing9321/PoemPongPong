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

import com.poem.admin.dao.AdminDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/adminLogin.do")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminLoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("adminLogin.jsp");
		request.setAttribute("msg", "잘못된 접근");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String msg = "잘못된 접근";
		
		AdminDAO adminDao = AdminDAO.getInstance();
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		// DB 접근 가능할 때
		if(adminDao.isAdmin(id, password)){
			HttpSession session = request.getSession();
			session.setAttribute("loginAdmin", id);
			msg = id + "님 환영합니다";
		}
		
		// DB 사용 못할 때
//		if(id.equals("dragon") && password.equals("27559627")){
//			HttpSession session = request.getSession();
//			session.setAttribute("loginAdmin", id);
//			msg = id + "님 환영합니다";
//		}
		
		String url = "adminLogin.jsp";
		request.setAttribute("msg", msg);
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}
}
