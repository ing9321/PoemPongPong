package com.poem.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnection {
	private static Connection con;
	private static DBConnection dbcon = new DBConnection();
	
	private DBConnection(){
	}
	   
	public static Connection startDB(){
		// 서버 DB 연결
//		try {
//			Context initCtx = new InitialContext();
//			DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/zedai7518");
//			con = ds.getConnection();
//		} catch (NamingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//서버 DB 연결 테스트
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zedai7518", "zedai7518", "vhddlek!@");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		// 은지 DB 연결
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			con = DriverManager.getConnection("jdbc:oracle:thin:@210.180.120.86:1521:xe", "poempong", "pong");
//			   
//		} catch (ClassNotFoundException e) {
//			System.out.println("드라이버를 찾을 수 없음");
//			System.exit(0);
//		} catch (SQLException e) {
//			System.out.println("서버 접속 실패");
//			System.exit(0);
//		}
		
		return con;
	}

	public static DBConnection getDbcon() {
		return dbcon;
	}

	public static void closeDB(PreparedStatement psmt, ResultSet rs){
		try {
			if(psmt != null)
				psmt.close();
			if(rs != null)
				rs.close();
			if(con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
