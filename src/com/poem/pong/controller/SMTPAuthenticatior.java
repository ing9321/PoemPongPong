package com.poem.pong.controller;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
 
public class SMTPAuthenticatior extends Authenticator{
 
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
       return new PasswordAuthentication("hotsnickers@naver.com", "vhddlek!@");
    }
}
