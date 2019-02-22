package com.poem.common;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class FindInfoMail {
	
	public FindInfoMail(){
		
	}
	
	public void sendMail(int type, String memberEmail, String memberInfo) throws MessagingException{
		
		String host = "smtp.naver.com";
		final String username = "hotsnickers";
		final String password = "vhddlek!@";
		int port = 465;
		
		// 메일 내용
		String recipient = memberEmail;
		String subject = "[포엠퐁퐁] 회원 정보 찾기 메일 전송 서비스";
		String body = "";
		if(type == 0){
			body = "조회하신 회원의 아이디는 " + memberInfo + "입니다.";
		} else if(type == 1){
			body = "조회 결과를 통해 임의로 생성된 임시 비밀번호는 " + memberInfo + "입니다.";
			body += "\n로그인 후 회원정보 수정을 통해 안전한 비밀번호로 변경해주세요.";
		}
		
		Properties props = System.getProperties();
		
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			String un = username;
			String pw = password;
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(un, pw);
			}
			
		});
		session.setDebug(true);
		
		Message mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress("hotsnickers@naver.com"));
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		mimeMessage.setSubject(subject);
		mimeMessage.setText(body);
		Transport.send(mimeMessage);
	}

}
