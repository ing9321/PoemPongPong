package com.poem.pong.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poem.pong.dao.WeeklyPoemJoinDAO;
import com.poem.pong.vo.WeeklyPoemJoinMemberVO;

/**
 * Servlet implementation class WeeklyPoemUserServlet
 */
@WebServlet("/weeklyBestPoemUser.do")
public class WeeklyBestPoemUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeeklyBestPoemUserServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		ArrayList<WeeklyPoemJoinMemberVO> weeklyBestPoemList = new ArrayList<WeeklyPoemJoinMemberVO>();
		
		WeeklyPoemJoinDAO weeklyPoemJoinMemberDAO = WeeklyPoemJoinDAO.getInstance();
		weeklyBestPoemList = weeklyPoemJoinMemberDAO.selectAllWeeklyBestPoem();
		
		request.setAttribute("weeklyBestPoemList", weeklyBestPoemList);
		RequestDispatcher rd = request.getRequestDispatcher("PoemPongPong/weeklyBestPoemPage.jsp");
		rd.forward(request, response);
	}

}
