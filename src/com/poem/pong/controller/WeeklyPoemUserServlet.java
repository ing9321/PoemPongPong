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
@WebServlet("/weeklyPoemUser.do")
public class WeeklyPoemUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeeklyPoemUserServlet() {
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
		ArrayList<WeeklyPoemJoinMemberVO> weeklyPoemList = new ArrayList<WeeklyPoemJoinMemberVO>();
		WeeklyPoemJoinDAO weeklyPoemJoinMemberDAO = WeeklyPoemJoinDAO.getInstance();
		
		String memberPoet = request.getParameter("poet");
		
		if(memberPoet == null || memberPoet == "" || memberPoet.equals("All")){
			// 아이디 검색이 아니라면
			System.out.println(memberPoet);
			weeklyPoemList = weeklyPoemJoinMemberDAO.selectAllWeeklyPoem();
			
			//주제 검색
			SubjectDAO subjectDAO = SubjectDAO.getInstance();
			String subject_name = subjectDAO.selectSubject();
			request.setAttribute("subject_name", subject_name);
			
		}else{
			// 아이디를 검색했다면
			weeklyPoemList = weeklyPoemJoinMemberDAO.searchWeeklyPoemPoet(memberPoet);
		}
		
		HttpSession session = request.getSession();
		String loginMember = (String)session.getAttribute("loginMember");
		
		SubjectDAO subjectDAO = SubjectDAO.getInstance();
		String subject_name = subjectDAO.selectSubject();
		
		WeeklyLikeItJoinDAO weeklyLikeItJoinDAO = WeeklyLikeItJoinDAO.getInstance();
		ArrayList<WeeklyLikeItVO> weeklyLikeList = weeklyLikeItJoinDAO.selectWeeklyLikeIt(loginMember);
		
		request.setAttribute("weeklyPoemList", weeklyPoemList);
		request.setAttribute("weeklyPoemListSize", weeklyPoemList.size());
		request.setAttribute("subject_name", subject_name);
		request.setAttribute("weeklyLikeList", weeklyLikeList);
		
		RequestDispatcher rd = request.getRequestDispatcher("PoemPongPong/weeklyPoemPage.jsp");
		rd.forward(request, response);
	}

}
