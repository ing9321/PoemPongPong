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

import com.poem.admin.dao.WeeklyPoemDAO;
import com.poem.admin.dao.WeeklyPoemJoinDAO;
import com.poem.common.vo.WeeklyPoemJoinVO;
import com.poem.common.vo.WeeklyPoemVO;

/**
 * Servlet implementation class WeeklyPoemServlet
 */
@WebServlet("/weeklyPoem.do")
public class WeeklyPoemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeeklyPoemServlet() {
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
			// location.href로 호출 : 검색기능
			String category = request.getParameter("category");
			String searchWeeklyPoem = request.getParameter("weeklyPoem");
			
			if(category == null || searchWeeklyPoem == null){
				doPost(request, response);
			} else {
				System.out.println("doGET 실행");
				// 파라메터 값이 있으면 검색 결과 받아오기
				WeeklyPoemJoinDAO weeklyPoemJoinDao = WeeklyPoemJoinDAO.getInstance();
				ArrayList<WeeklyPoemJoinVO> searchResultList = null;
				
				switch(category){
				case "subject":
					searchResultList = weeklyPoemJoinDao.searchSubjectWeeklyPoem(searchWeeklyPoem);
					break;
				case "poet":
					searchResultList = weeklyPoemJoinDao.searchPoetWeeklyPoem(searchWeeklyPoem);
					break;
				case "title":
					searchResultList = weeklyPoemJoinDao.searchTitleWeeklyPoem(searchWeeklyPoem);
					break;
				}
				
				if(searchResultList != null) {
					// 조회성공
					request.setAttribute("weeklyPoemJoinList", searchResultList);
					
					RequestDispatcher rd = request.getRequestDispatcher("ADMIN/weeklyPoemPage.jsp");
					rd.forward(request, response);
				} else {
					// 조회 실패
				}
				
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		WeeklyPoemDAO weeklyPoemDao = WeeklyPoemDAO.getInstance();
		weeklyPoemDao.selectAllWeeklyPoem();
		
		// WeeklyPoem 테이블에서 FK로 참조하는 MemberID를 통해 Member 테이블과 JOIN된 List 전달
		WeeklyPoemJoinDAO weeklyPoemJoinDao = WeeklyPoemJoinDAO.getInstance();
		ArrayList<WeeklyPoemJoinVO> weeklyPoemJoinList = weeklyPoemJoinDao.selectAllWeeklyPoemJoin();
		request.setAttribute("weeklyPoemJoinList", weeklyPoemJoinList);
		
		RequestDispatcher rd = request.getRequestDispatcher("ADMIN/weeklyPoemPage.jsp");
		rd.forward(request, response);
	}

}
