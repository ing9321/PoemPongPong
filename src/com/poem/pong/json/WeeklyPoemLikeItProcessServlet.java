package com.poem.pong.json;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.poem.pong.dao.WeeklyLikeItJoinDAO;
import com.poem.common.vo.MemberVO;
import com.poem.common.vo.WeeklyLikeItJoinVO;



/**
 * Servlet implementation class LikeItServlet
 */
@WebServlet("/weeklyPoemLikeItProcessJSON.do")
public class WeeklyPoemLikeItProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeeklyPoemLikeItProcessServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		System.out.println("weeklyPoemLikeItProcessJSON start!!!");
		
		System.out.println(request.getParameter("id"));
		System.out.println(request.getParameter("stringdata"));
		
//		try {
//            int length = request.getContentLength();
//            byte[] input = new byte[length];
//            ServletInputStream sin = request.getInputStream();
//            int c, count = 0 ;
//            while ((c = sin.read(input, count, input.length-count)) != -1) {
//                count +=c;
//            }
//            sin.close();
// 
//            String recievedString = new String(input);
//            response.setStatus(HttpServletResponse.SC_OK);
//            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
// 
//            Integer doubledValue = Integer.parseInt(recievedString) * 2;
// 
//            writer.write(doubledValue.toString());
//            writer.flush();
//            writer.close();
// 
//        } catch (IOException e) {
//	        try{
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                response.getWriter().print(e.getMessage());
//                response.getWriter().close();
//            } catch (IOException ioe) {
//            }
//        }   
    }
}
