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
import com.poem.common.vo.WeeklyLikeItVO;
import com.poem.pong.dao.SubjectDAO;
import com.poem.pong.dao.WeeklyPoemJoinDAO;
import com.poem.pong.vo.WeeklyPoemJoinMemberVO;

/**
 * Servlet implementation class WeeklyPoemUserServlet
 */
@WebServlet("/weeklyPoemLikeList.do")
public class WeeklyPoemLikeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeeklyPoemLikeListServlet() {
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
		String url = "poemIndex.jsp";
		
		HttpSession session = request.getSession();
		String loginMember = (String)session.getAttribute("loginMember");
		
		if(loginMember != null && loginMember != ""){
			url = "PoemPongPong/weeklyPoemLikeListPage.jsp";
			
			WeeklyLikeItJoinDAO weeklyLikeItJoinDAO = WeeklyLikeItJoinDAO.getInstance();
			// 좋아요 리스트
			ArrayList<WeeklyLikeItVO> weeklyLikeList = weeklyLikeItJoinDAO.selectWeeklyLikeIt(loginMember);
			
			// 좋아요한 시 리스트
			ArrayList<WeeklyPoemJoinMemberVO> weeklyPoemList = new ArrayList<WeeklyPoemJoinMemberVO>();
			WeeklyPoemJoinDAO weeklyPoemJoinMemberDAO = WeeklyPoemJoinDAO.getInstance();
			weeklyPoemList = weeklyPoemJoinMemberDAO.selectLikeWeeklyPoem(loginMember);
			
			//주제 검색
			SubjectDAO subjectDAO = SubjectDAO.getInstance();
			String subject_name = subjectDAO.selectSubject();
			request.setAttribute("subject_name", subject_name);

			request.setAttribute("weeklyPoemList", weeklyPoemList);
			request.setAttribute("weeklyPoemListSize", weeklyPoemList.size());
			request.setAttribute("subject_name", subject_name);
			request.setAttribute("weeklyLikeList", weeklyLikeList);
			
			System.out.println(weeklyPoemList);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

}
