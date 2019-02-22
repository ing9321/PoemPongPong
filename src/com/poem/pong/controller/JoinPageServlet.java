package com.poem.pong.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poem.pong.dao.MemberDAO;
import com.poem.common.EncryptUtil;
import com.poem.common.vo.MemberVO;

/**
 * Servlet implementation class JoinPageServlet
 */
@WebServlet("/memberJoin.do")
public class JoinPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinPageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 로그인페이지에서 join 버튼을 눌러 location.href로 접근했을 때
		// 회원가입 페이지 jsp 화면 출력
		RequestDispatcher rd = request.getRequestDispatcher("PoemPongPong/joinPage.jsp");
		rd.forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	
		String url = "";
	
		// ********************* SNS로 아이디가 가입되어 있는지 확인 ************************
		
		MemberDAO memberDAO = MemberDAO.getInstance();

		String joinId = request.getParameter("joinId");
		MemberVO resultSNS = memberDAO.isSNSMember(joinId);
//		MemberVO resultSNS = memberDAO.getMemberInfo(joinId);
		
		if(resultSNS != null){
			System.out.println("SNS 가입되어 있어서 로그인 처리");
			HttpSession session = request.getSession();
			session.setAttribute("loginMember", resultSNS.getMemberID());
			url = "poemIndex.jsp";
		}else{
			// ****************** 가입되어있지 않으면 로그인 처리 ****************************************
			System.out.println("SNS 가입되어있지 않아서 가입 처리");
			// joinPage에서 얻은 정보
			String joinPwd = request.getParameter("joinPwd");
			String joinEmail = request.getParameter("joinEmail");
			int joinPath = Integer.parseInt(request.getParameter("joinPath"));
			
			// joinPage2에서 얻은 정보
			String joinPoet = request.getParameter("joinPoet");
			String joinPhoto = request.getParameter("joinPhoto");

			// joinPage2까지 모든 정보를 얻었다면 회원등록
			if(joinPoet != null){
				System.out.println(joinId);
							
				int result = memberDAO.insertMember(joinId, joinPwd, joinEmail, joinPoet, joinPhoto, joinPath);
			
				if(result == 1){
					url ="PoemPongPong/joinCelebration.jsp";
				}

			}else{
				// joinPage2의 정보가 없으면 joinPage2로 이동
				
				// 비밀번호 암호화
				String encryptPwd = EncryptUtil.getEncryptSHA256(joinPwd);
				
				request.setAttribute("joinId", joinId);
				request.setAttribute("joinPwd", encryptPwd);
				request.setAttribute("joinEmail", joinEmail);
				request.setAttribute("joinPath", joinPath+"");
				url="PoemPongPong/joinPage2.jsp";
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request,response);
		
	}

}
