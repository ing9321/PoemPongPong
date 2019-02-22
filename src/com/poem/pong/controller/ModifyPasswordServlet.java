package com.poem.pong.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poem.common.EncryptUtil;
import com.poem.common.vo.MemberVO;
import com.poem.pong.dao.MemberDAO;

/**
 * Servlet implementation class ChangePasswordServlet
 */
@WebServlet("/modifyPwd.do")
public class ModifyPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyPasswordServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		RequestDispatcher rd = request.getRequestDispatcher("PoemPongPong/modifyPasswordPage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String memberID = (String)session.getAttribute("loginMember");
		
		String myOldPwd = (String)request.getParameter("myOldPwd");

		String memberPwd = (String)request.getParameter("myNewPwd");
		
		MemberDAO memberDao = MemberDAO.getInstance();
		
		// 세션에 로그인된 멤버가 비밀번호 수정을 시도했을 때
		// 현재 비밀번호가 맞는지 확인 (isMember메서드를 재사용해서 값이 있으면 통과)
		// DB에 암호화된 비밀번호가 저장되어 있으므로 비밀번호 암호화 후 비교
		String encryptOldPwd = EncryptUtil.getEncryptSHA256(myOldPwd);
		MemberVO checkMember = memberDao.isMember(memberID, encryptOldPwd);

		if(checkMember != null) {
			// 비밀번호 암호화
			String encryptNewPwd = EncryptUtil.getEncryptSHA256(memberPwd);

			// 리턴값이 있을 경우 비밀번호 수정
			int result = memberDao.updateMemberPassword(memberID, encryptNewPwd);
			
			if(result == 1){
				response.sendRedirect("myPage.do");
				
			}
		} else {
			// 입력한 현재 비밀번호가 일치하지 않아 넘겨받은 memberVO가 없을 경우
			request.setAttribute("curPwdEqual", "incorrect");
			RequestDispatcher rd = request.getRequestDispatcher("PoemPongPong/modifyPasswordPage.jsp");
			rd.forward(request,response);
		}
		
	}

}
