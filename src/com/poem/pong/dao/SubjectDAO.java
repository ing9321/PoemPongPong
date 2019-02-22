package com.poem.pong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.poem.common.DBConnection;

public class SubjectDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
		
	// SubjectDAO 클래스의 SingleTon
	private static SubjectDAO instance = new SubjectDAO();
	
	private SubjectDAO() { }
	
	public static SubjectDAO getInstance() {
		return instance;
	}
	// --------------------------------------------------

	// 현재 주제 조회-----------------------------
	public String selectSubject() {
		String subject_name = null;
		
//		select sub1.subject_name
//		from subject sub1, subject sub2
//		where sub1.SUBJECT_INDEX = sub2.SUBJECT_NAME and sub2.SUBJECT_INDEX = 0;
		
		// oracle
//		String sql = "select sub1.subject_name "
//				+ "from subject sub1, subject sub2 "
//				+ "where sub1.SUBJECT_INDEX = sub2.SUBJECT_NAME and sub2.SUBJECT_INDEX = 0";
		
		// mysql
		String sql = "select Subject_Name from SUBJECT where Subject_Index = "
				+ "(select Subject_Index from SUBJECT where Subject_Name = '0') "
				+ "and Subject_Name != '0'";
		
//		select *
//		from SUBJECT
//		where Subject_Index = 
//		(select Subject_Index
//		from SUBJECT
//		where Subject_Name = '0') limit 1;
		
		con = DBConnection.startDB();

		try {
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				subject_name = rs.getString(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return subject_name;
	}
	// ---------- 현재 주제 조회 메서드 끝 ----------
}
