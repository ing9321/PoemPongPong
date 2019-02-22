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
 * Servlet implementation class MemberServlet
 */
@WebServlet("/member.do")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberServlet() {
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
			// doGet으로 받았을 때 url 조회
			String searchMember = request.getParameter("searchMember");
			
			// url에 memberId가 없으면 member 전체 조회 = doPost로 전달
			if(searchMember == null) {
				doPost(request, response);
				
			} else {
				// url에 memberId가 있으면 해당 아이디를 가진 회원 선택 조회
				MemberDAO memDao = MemberDAO.getInstance();
				ArrayList<MemberVO> findMemList = memDao.searchMemberID(searchMember);
				
				// memberId 조회해서 결과값이 있으면 해당 member 정보 출력
				if(findMemList != null && findMemList.size() > 0) {
					// 조회 성공
					request.setAttribute("memberList", findMemList);
					RequestDispatcher rd = request.getRequestDispatcher("ADMIN/memberPage.jsp");
					rd.forward(request, response);
				} else {
					// 조회 실패 : 회원이 존재하지 않습니다.
					// 조회값없이 출력
					ArrayList<MemberVO> memList = null;
					request.setAttribute("memberList", memList);
					RequestDispatcher rd = request.getRequestDispatcher("ADMIN/memberPage.jsp");
					rd.forward(request, response);
					
					//혹은 doPost로 전체출력
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 은지 : 전체 회원 조회
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		MemberDAO memDao = MemberDAO.getInstance();
		ArrayList<MemberVO> memList = memDao.selectAllMember();
		
		request.setAttribute("memberList", memList);
		RequestDispatcher rd = request.getRequestDispatcher("ADMIN/memberPage.jsp");
		rd.forward(request, response);
	}

}
