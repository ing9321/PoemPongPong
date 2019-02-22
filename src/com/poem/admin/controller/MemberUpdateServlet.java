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
import com.poem.common.vo.MemberVO;

/**
 * Servlet implementation class MemberUpdateServlet
 */
@WebServlet("/memberUpdate.do")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateServlet() {
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
			// doGet으로 받았을 때 url을 통해 수정할 memberId 얻기
			String memberID = request.getParameter("member");
			
			MemberDAO memDao = MemberDAO.getInstance();
			// selectMember() 메서드를 통해 정보를 수정할 선택된 회원 반환
			MemberVO selectMember = memDao.selectMember(memberID);
			
			// 정보를 수정하기 위해 선택된 회원의 기존 정보 전달
			request.setAttribute("memberID", selectMember.getMemberID());
			request.setAttribute("memberPWD", selectMember.getMemberPWD());
			request.setAttribute("memberPoet", selectMember.getMemberPoet());
			request.setAttribute("memberPhoto", selectMember.getMemberPhoto());
			request.setAttribute("memberEmail", selectMember.getMemberEmail());
			request.setAttribute("memberPath", selectMember.getMemberPath());
			request.setAttribute("memberDormancy", selectMember.getMemberDormancy());
			
			RequestDispatcher rd = request.getRequestDispatcher("ADMIN/memberUpdatePage.jsp");
			rd.forward(request, response);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// memberUpdatePage.jsp 에서 수정될 회원 정보를 form 태그의 submit으로 전달
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		// form 태그로 넘겨받은 값을 호출
		String memberID = request.getParameter("memberID");
		String memberPWD = request.getParameter("memberPWD");
		String memberPoet = request.getParameter("memberPoet");
		String memberPhoto = request.getParameter("memberPhoto");
		String memberEmail = request.getParameter("memberEmail");int memberPath = Integer.parseInt(request.getParameter("memberPath"));
		int memberDormancy = Integer.parseInt(request.getParameter("memberDormancy"));
		
		MemberDAO memDao = MemberDAO.getInstance();
		// updateMember() 메서드를 통해 회원 정보를 수정
		int result = memDao.updateMember(memberID, memberPWD, memberPoet, "profile", memberEmail, memberPath, memberDormancy);
		if (result == 1) { // update가 성공했으면 return 값이 1
			// update 성공한 경우
			System.out.println("수정 성공");
			// 성공했을 때 memberSevlet으로 넘겨서 페이지 새로고침
			response.sendRedirect("member.do");
		} else {
			// 	실패
			System.out.println("수정 실패");
		}
	}

}
