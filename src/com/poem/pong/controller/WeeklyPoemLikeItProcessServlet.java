package com.poem.pong.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poem.pong.dao.WeeklyLikeItJoinDAO;
import com.poem.common.vo.MemberVO;
import com.poem.common.vo.WeeklyLikeItJoinVO;



/**
 * Servlet implementation class LikeItServlet
 */
@WebServlet("/weeklyPoemLikeItProcess.do")
public class WeeklyPoemLikeItProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeeklyPoemLikeItProcessServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String loginMember = (String)session.getAttribute("loginMember");
		int weeklyPoemIndex = Integer.parseInt(request.getParameter("weeklyPoemIndex"));
		
		WeeklyLikeItJoinDAO weeklyLikeItJoinDAO = WeeklyLikeItJoinDAO.getInstance();
		weeklyLikeItJoinDAO.processWeeklyPoemLikeIt(weeklyPoemIndex, loginMember);
	}
}
