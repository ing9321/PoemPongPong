package com.poem.pong.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poem.pong.dao.WeeklyPoemDAO;

/**
 * Servlet implementation class WeeklyPoemDeleteServlet
 */
@WebServlet("/weeklyPoemDeleteUser.do")
public class WeeklyPoemDeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeeklyPoemDeleteUserServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 7일 시집 삭제
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		int weeklyPoemIndex = Integer.parseInt(request.getParameter("weeklyPoemIndex"));
		
		WeeklyPoemDAO weeklyPoemDAO = WeeklyPoemDAO.getInstance();
		weeklyPoemDAO.deleteWeeklyPoem(weeklyPoemIndex);
		
		RequestDispatcher rd = request.getRequestDispatcher("weeklyPoemUser.do");
		rd.forward(request, response);
	}
}
