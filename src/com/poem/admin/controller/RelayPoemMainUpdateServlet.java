package com.poem.admin.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poem.admin.dao.RelayPoemMainDAO;
import com.poem.admin.dao.WeeklyPoemJoinDAO;
import com.poem.common.vo.RelayPoemMainVO;
import com.poem.common.vo.WeeklyPoemJoinVO;

/**
 * Servlet implementation class RelayPoemMainUpdateServlet
 */
@WebServlet("/relayPoemMainUpdate.do")
public class RelayPoemMainUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelayPoemMainUpdateServlet() {
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
			int relayPoemMainIndex = Integer.parseInt(request.getParameter("relayPoem"));
			
			RelayPoemMainDAO relayPoemMainDao = RelayPoemMainDAO.getInstance();
			RelayPoemMainVO selectedPoem = relayPoemMainDao.selectRelayPoemMain(relayPoemMainIndex);
			
			// 정보를 수정하기 위해 선택된 시의 기존 정보 전달
			request.setAttribute("index", selectedPoem.getRelayMainIndex());
			request.setAttribute("subject", selectedPoem.getSubjectName());
			request.setAttribute("title", selectedPoem.getTitle());
			request.setAttribute("contents", selectedPoem.getContents());
			request.setAttribute("poets", selectedPoem.getPoets());
			
			RequestDispatcher rd = request.getRequestDispatcher("ADMIN/relayPoemMainUpdatePage.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		// form 태그로 넘겨받은 값을 호출
		int relayPoemMainIndex = Integer.parseInt(request.getParameter("index"));
		String relayPoemMainSubject = request.getParameter("subject");
		String relayPoemMainTitle = request.getParameter("title");
		String relayPoemMainContents = request.getParameter("contents");
		relayPoemMainContents = relayPoemMainContents.replaceAll("\r\n", "<br>");
		String relayPoemMainPoets = request.getParameter("poets");
		
		RelayPoemMainDAO relayPoemMainDao = RelayPoemMainDAO.getInstance();
		
		int result = relayPoemMainDao.updateRelayPoemMain(relayPoemMainIndex, relayPoemMainSubject, relayPoemMainTitle, relayPoemMainContents, relayPoemMainPoets);
		if (result == 1) { // update가 성공했으면 return 값이 1
			// update 성공한 경우
			System.out.println("수정 성공");
			// 성공했을 때 WeeklyPoemSevlet으로 넘겨서 페이지 새로고침
			response.sendRedirect("relayPoemMain.do");
		} else {
			// 	실패
		}
	}

}
