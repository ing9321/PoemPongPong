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
import com.poem.admin.dao.RelayLikeItDAO;
import com.poem.admin.dao.RelayLikeItJoinDAO;
import com.poem.common.vo.MemberVO;
import com.poem.common.vo.RelayLikeItJoinVO;
import com.poem.common.vo.RelayLikeItVO;

/**
 * Servlet implementation class LikeItServlet
 */
@WebServlet("/relayPoemLikeIt.do")
public class RelayPoemLikeItServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelayPoemLikeItServlet() {
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
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");

//		RelayLikeItDAO relayLikeDao = RelayLikeItDAO.getInstance();
//		ArrayList<RelayLikeItVO> relayLikeList = relayLikeDao.selectAllRelayLikeIt();
		
		RelayLikeItJoinDAO relayLikeJoinDao = RelayLikeItJoinDAO.getInstance();
		ArrayList<RelayLikeItJoinVO> relayLikeJoinList = relayLikeJoinDao.selectAllRelayLikeItJoin();
		
		System.out.println(relayLikeJoinList);
		
		request.setAttribute("relayLikeJoinList", relayLikeJoinList);
		
		RequestDispatcher rd = request.getRequestDispatcher("ADMIN/relayPoemLikeItPage.jsp");
		rd.forward(request, response);
	}

}
