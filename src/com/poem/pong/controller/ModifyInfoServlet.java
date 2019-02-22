package com.poem.pong.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poem.common.vo.MemberVO;
import com.poem.pong.dao.MemberDAO;

/**
 * Servlet implementation class ModifyInfoServlet
 */
@WebServlet("/modifyInfo.do")
public class ModifyInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String loginMember = (String)session.getAttribute("loginMember");
		
		MemberDAO memberDao = MemberDAO.getInstance();
		// 세션에 저장된 멤버정보 불러오기
		MemberVO myInfo = memberDao.isMember(loginMember);
//		MemberVO myInfo = memberDao.getMemberInfo(memberID);
		
		String memberID = myInfo.getMemberID();
		String memberPoet = myInfo.getMemberPoet();
		String memberEmail = myInfo.getMemberEmail();
		String memberPhoto = myInfo.getMemberPhoto();
		int memberPath = myInfo.getMemberPath();
		
		// 홈페이지가 아니라 페이스북이나 카카오톡으로 가입했을 경우
		switch(myInfo.getMemberPath()){
			case 1: // 페이스북
				memberID = "페이스북으로 가입한 회원입니다.";
				break;
			case 2: // 카카오톡
				memberID = "카카오톡으로 가입한 회원입니다.";
				break;
			default:
				break;
		}
		
		// 어트리뷰트를 통해 정보 넘기고 출력
		request.setAttribute("memberID", memberID);
		request.setAttribute("memberPoet", memberPoet);
		request.setAttribute("memberEmail", memberEmail);
		request.setAttribute("memberPhoto", memberPhoto);
		request.setAttribute("memberPath", memberPath);

		RequestDispatcher rd = request.getRequestDispatcher("PoemPongPong/modifyInfoPage.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		String memberID = (String)request.getParameter("myID");
		String memberPoet = (String)request.getParameter("myPoet");
		String memberEmail = (String)request.getParameter("myEmail");
		String memberPhoto = (String)request.getParameter("myPhoto");
		
		MemberDAO memberDao = MemberDAO.getInstance();
		
		int result = memberDao.updateMemberDetail(memberID, memberPoet, memberEmail, memberPhoto);
		
		if(result == 1){
			request.setAttribute("msgComplete", "정보 수정을 완료했습니다!");
			
			// request : myPage.jsp로 이동하면 MemberVO 정보가 없어서 500에러
			// 			 myPage.do로 이동하면 페이지는 이동하나 주소값이 변경되지 않음
			// RequestDispatcher rd = request.getRequestDispatcher("myPage.do");
			// rd.forward(request, response);
			
			response.sendRedirect("myPage.do");
		}
	}

}
