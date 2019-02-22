package com.poem.admin.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poem.admin.dao.MemberDAO;

/**
 * Servlet implementation class MemberInsertServlet
 */
@WebServlet("/memberInsert.do")
public class MemberInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 관리자 로그인을 통한 상세 페이지 접근이 가능하도록 처리

		// 주소로 접근하였을 시 처리
		HttpSession session = request.getSession();
		String sessionCheck = (String) session.getAttribute("loginAdmin");
		
		// 세션이 없다면 확인 후 주소 접근
		if(sessionCheck == null || sessionCheck.equals("")){
			RequestDispatcher rd = request.getRequestDispatcher("adminLogin.jsp");
			request.setAttribute("msg", "잘못된 접근");
			rd.forward(request, response);
		}else{
			// 은지 :
			// memberPage에서 location.href로 넘기니까 doGet이 호출..
			// 그래서 doGet에서는 memberInsertPage.jsp 페이지를 출력하는 역할을 시켰습니당..
			RequestDispatcher rd = request.getRequestDispatcher("ADMIN/memberInsertPage.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 은지 :
		// memberInsertPage에서 form 태그 - post 액션을 사용해서 넘겨줌으로써 doPost 호출
		// form 태그에서 가입시킬 회원 정보를 넘겨줬으니까 Member 테이블에 데이터 추가 역할하고 회원조회 페이지로 넘겨줌
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		String memberID = request.getParameter("memberID");
		String memberPWD = request.getParameter("memberPWD");
		String memberPoet = request.getParameter("memberPoet");
		String memberPhoto = request.getParameter("memberPhoto");
		String memberEmail = request.getParameter("memberEmail");
		
		MemberDAO memDao = MemberDAO.getInstance();
		
		// insertMember() 메서드를 통해 DB Member테이블에 멤버 추가
		int result = memDao.insertMember(memberID, memberPWD, memberPoet, memberPhoto, memberEmail);
		if (result == 1) {
			// 회원 추가 성공
			System.out.println("가입 성공");
			// 성공했을 때 memberSevlet으로 넘겨서 페이지 새로고침
			response.sendRedirect("member.do");
		} else {
			// 회원 추가 실패 : 유효성 검사 및 예외처리 필요
		}
	}

}
