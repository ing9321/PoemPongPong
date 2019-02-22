package com.poem.pong.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poem.pong.dao.WritePoemDAO;

/**
 * Servlet implementation class WritePoemUserServlet
 */
@WebServlet("/writePoemUser.do")
public class WritePoemUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WritePoemUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String loginMember = (String)session.getAttribute("loginMember");
				
		// writeMethod : 글을 수정하는건지 새로 쓰는건지 판별 / newPoem : 새 시 / modify : 수정
		String writeMethod = request.getParameter("writeMethod");
		String memberID = loginMember;
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		contents = contents.replaceAll("\r\n", "<br>");
		String subject_name = request.getParameter("subject_name");
		String background = request.getParameter("background");
		
		System.out.println(request.getParameter("background"));
		System.out.println(writeMethod);
		System.out.println(title);
		System.out.println(contents);
		System.out.println(subject_name);
		System.out.println(memberID);
		System.out.println(background);
		
		WritePoemDAO writePoemDAO = WritePoemDAO.getInstance();
		
		if(writeMethod.equals("newPoem")){
			writePoemDAO.insertWeeklyPoem(subject_name, memberID, title, contents, background);
		}else if(writeMethod.equals("modify")){
			int modifyIndex = Integer.parseInt(request.getParameter("modifyIndex"));
			writePoemDAO.updateWeeklyPoem(modifyIndex, title, contents, background);
		}
		
		response.sendRedirect("weeklyPoemUser.do");
		
//		RequestDispatcher rd = request.getRequestDispatcher("weeklyPoemUser.do");
//		rd.forward(request, response);
	}

}
