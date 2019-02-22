package com.poem.pong.controller;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poem.common.vo.MemberVO;
import com.poem.pong.dao.MemberDAO;

@WebServlet("/contactUs.do")
public class ContactUsServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactUsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "PoemPongPong/contactUs.jsp";
		
		HttpSession session = request.getSession();
		
		String loginMember = (String)session.getAttribute("loginMember");

		if(loginMember != null){
			MemberDAO memberDAO = MemberDAO.getInstance();
			MemberVO memberInfo = memberDAO.isMember(loginMember);
//			MemberVO memberInfo = memberDAO.getMemberInfo(loginMember);
			
			request.setAttribute("memberEmail", memberInfo.getMemberEmail());
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String url = "PoemPongPong/sendMailPage.jsp";
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String subject = request.getParameter("subject");
		String content = request.getParameter("contents");
		String memberEmail = request.getParameter("memberEmail");
		  
		Properties p = new Properties();
		  
		p.put("mail.smtp.host","smtp.naver.com");
		p.put("mail.smtp.port", "465");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.port", "465");
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");
		  
		try{
		    Authenticator auth = new SMTPAuthenticatior();
		    Session ses = Session.getInstance(p, auth);
		      
		    ses.setDebug(true);
		    MimeMessage msg = new MimeMessage(ses);
		 
		    msg.setSubject(subject);
		 
		    StringBuffer buffer = new StringBuffer();
		    buffer.append("보낸 사람 : ");
		    buffer.append(memberEmail+"<br/>");
		    buffer.append("제목 : ");
		    buffer.append(subject+"<br/>");
		    buffer.append("내용 : ");
		    buffer.append(content+"<br/>");
		    Address fromAddr = new InternetAddress(from);
		    msg.setFrom(fromAddr);
		 
		    Address toAddr = new InternetAddress(to);
		    msg.addRecipient(Message.RecipientType.TO, toAddr);
		     
		    msg.setContent(buffer.toString(), "text/html;charset=UTF-8");
		    Transport.send(msg);
		 	
		} catch(Exception e){
		    e.printStackTrace();
		    return;
		}
		
		request.setAttribute("message", "문의는 접수했퐁");
		
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

}
