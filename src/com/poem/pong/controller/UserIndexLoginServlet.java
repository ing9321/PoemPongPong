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
 * Servlet implementation class UserIndexLoginServlet
 */
@WebServlet("/UserIndexLogin.do")
public class UserIndexLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserIndexLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("PoemPongPong/loginPage.jsp");
		rd.forward(request,response);	
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String url = "";
		String loginMethod = (String)request.getParameter("loginBtn");
		System.out.println(loginMethod);
		if(loginMethod.equals("loginBtn")){
			url = "PoemPongPong/loginPage.jsp";
			
		}else if(loginMethod.equals("login")){
			String userId = request.getParameter("userId");
			String userPwd = request.getParameter("userPwd");
			
			System.out.println(userId);
			System.out.println(userPwd);
			
			MemberDAO memberDAO = MemberDAO.getInstance();
			
			// 아이디 존재여부 확인
			MemberVO checkID = memberDAO.isMember(userId);
			if(checkID != null){
				// 아이디 비교해서 회원이 존재할 경우
				
				// DB에는 암호화된 비밀번호가 저장되어 있기 때문에
				// 비교할 때에도 암호화 후 비교 가능
				String encryptPwd = EncryptUtil.getEncryptSHA256(userPwd);
				
				MemberVO checkPW = memberDAO.isMember(userId, encryptPwd);
				if(checkPW == null){
					request.setAttribute("loginResult", "비밀번호가 일치하지 않습니다!");
					url = "PoemPongPong/loginPage.jsp";
				}else{
					
					HttpSession session = request.getSession();
					session.setAttribute("loginMember", checkPW.getMemberID());
					url = "poemIndex.jsp";
				}
			} else {
				// 아이디가 존재하지 않을 경우
				// 로그인 실패 이유 메세지 추가 후 로그인 페이지로 전달
				request.setAttribute("loginResult", "아이디가 존재하지 않습니다!");
				url = "PoemPongPong/loginPage.jsp";
			}
			
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request,response);
		

	}

}
