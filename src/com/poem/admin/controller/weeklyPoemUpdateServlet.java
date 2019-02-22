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

import com.poem.admin.dao.MemberDAO;
import com.poem.admin.dao.SubjectDAO;
import com.poem.admin.dao.WeeklyPoemDAO;
import com.poem.admin.dao.WeeklyPoemJoinDAO;
import com.poem.common.vo.MemberVO;
import com.poem.common.vo.SubjectVO;
import com.poem.common.vo.WeeklyPoemJoinVO;
import com.poem.common.vo.WeeklyPoemVO;

/**
 * Servlet implementation class weeklyPoemUpdateServlet
 */
@WebServlet("/weeklyPoemUpdate.do")
public class weeklyPoemUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public weeklyPoemUpdateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		// 주소로 접근하였을 시 처리
		HttpSession session = request.getSession();
		String sessionCheck = (String) session.getAttribute("loginAdmin");
		
		// 세션이 없다면 확인 후 주소 접근
		if(sessionCheck == null || sessionCheck.equals("")){
			RequestDispatcher rd = request.getRequestDispatcher("adminLogin.jsp");
			request.setAttribute("msg", "잘못된 접근");
			rd.forward(request, response);
		}else{
			
			// doGet으로 받았을 때 url을 통해 수정할 시의 index 얻기
			// parameter는 String 형태라서 int형으로 변경 필요
			int weeklyPoemIndex = Integer.parseInt(request.getParameter("weeklyPoem"));
			
			WeeklyPoemJoinDAO weeklyPoemJoinDao = WeeklyPoemJoinDAO.getInstance();
			// selectWeeklyPoem() 메서드를 통해 정보를 수정할 선택된 시 정보 반환
			WeeklyPoemJoinVO selectedPoem = weeklyPoemJoinDao.selectWeeklyPoemJoin(weeklyPoemIndex);
			// 정보를 수정하기 위해 선택된 시의 기존 정보 전달
			request.setAttribute("selectedPoem", selectedPoem);
			
			SubjectDAO subjectDao = SubjectDAO.getInstance();
			ArrayList<SubjectVO> subjectList = subjectDao.selectAllSubject();
			request.setAttribute("subjectList", subjectList);
			
			RequestDispatcher rd = request.getRequestDispatcher("ADMIN/weeklyPoemUpdatePage.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// weeklyPoemUpdatePage.jsp 에서 수정될 시 정보를 form 태그의 submit으로 전달
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		// form 태그로 넘겨받은 값을 호출
		int index = Integer.parseInt(request.getParameter("index"));
		String subject = request.getParameter("subject");
		String poet = request.getParameter("poet");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		int viewCount = Integer.parseInt(request.getParameter("viewCount"));
		int likeCount = Integer.parseInt(request.getParameter("likeCount"));
		int bestPoem = Integer.parseInt(request.getParameter("bestPoem"));
		String background = request.getParameter("background");
		
		WeeklyPoemJoinDAO weeklyPoemJoinDao = WeeklyPoemJoinDAO.getInstance();
		
		int result = weeklyPoemJoinDao.updateWeeklyPoemJoin(index, subject, poet, title, contents, viewCount, likeCount, bestPoem, background);
		if (result == 1) { // update가 성공했으면 return 값이 1
			// update 성공한 경우
			System.out.println("수정 성공");
			// 성공했을 때 WeeklyPoemSevlet으로 넘겨서 페이지 새로고침
			response.sendRedirect("weeklyPoem.do");
		} else {
			// 	실패
		}
	}

}
