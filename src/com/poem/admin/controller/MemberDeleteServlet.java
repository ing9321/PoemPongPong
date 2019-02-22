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
import com.poem.common.vo.MemberVO;

/**
 * Servlet implementation class MemberDeleteServlet
 */
@WebServlet("/memberDelete.do")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDeleteServlet() {
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
			System.out.println("MemberDeleteSerclet의 doGet 호출");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// System.out.println("삭제할 회원 ID : " + request.getParameter("member"));
		
		// 회원 삭제
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		String deleteMemberId = request.getParameter("member");
		
		MemberDAO memDao = MemberDAO.getInstance();
		int result = memDao.deleteMember(deleteMemberId);
		if(result == 1) {
			// 삭제 성공
			System.out.println("삭제 성공");
		} else {
			// 삭제 실패 : 회원이 존재하지 않습니다?
		}
		
		// 회원테이블에서 회원을 삭제하고 전체 회원 리스트 출력 페이지 새로고침
		ArrayList<MemberVO> memList = memDao.selectAllMember();
		request.setAttribute("memberList", memList);
		RequestDispatcher rd = request.getRequestDispatcher("ADMIN/memberPage.jsp");
		rd.forward(request, response);
		
	}

}
