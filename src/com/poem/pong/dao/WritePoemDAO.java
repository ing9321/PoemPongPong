package com.poem.pong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.poem.common.DBConnection;

public class WritePoemDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	// WritePoemDAO 클래스의 SingleTon
	private static WritePoemDAO instance = new WritePoemDAO();
	
	private WritePoemDAO() { }
	
	public static WritePoemDAO getInstance() {
		return instance;
	}
	// --------------------------------------------------
	
	/**
	 * 7일 시집 글쓰기
	 * @param subject_name 주제명
	 * @param memberID 멤버아이디
	 * @param title 시 제목
	 * @param contents 시 내용
	 * @param background 배경
	 * @return 결과 반환
	 */
	public int insertWeeklyPoem(String subject_name, String memberID, String title, String contents, String background) {
		int lastIndex = 0;
		int result = 0;
		
		con = DBConnection.startDB();
		
		// 최신 글 index 가져오기
		// oracle
//		String sql = "select weeklypoem_index from (select * from WEEKLYPOEM order by UPLOADDATE desc) where rownum=1";
		
		// mysql
		 String sql = "select max(WeeklyPoem_Index) from WEEKLYPOEM";
				
		try {
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				lastIndex = rs.getInt(1);
			}

			// oracle
//			sql = "insert into WEEKLYPOEM values(?, ?, ?, ?, ?, 0, 0, sysdate, 0, ?)";
			
			// mysql
			 sql = "insert into WEEKLYPOEM values(?, ?, ?, ?, ?, 0, 0, now(), 0, ?)";
			
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, lastIndex+1);
			psmt.setString(2, subject_name);
			psmt.setString(3, memberID);
			psmt.setString(4, title);
			psmt.setString(5, contents);
			psmt.setString(6, background);
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return result;
	}
	
	
	public int updateWeeklyPoem(int modifyIndex, String title, String contents, String background) {
		int result = 0;
		
		// mysql & oracle
		String sql = "update WEEKLYPOEM set Title=?, Contents=?, Background=? where WeeklyPoem_Index=?";
		con = DBConnection.startDB();
      
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, title);
			psmt.setString(2, contents);
			psmt.setString(3, background);
			psmt.setInt(4, modifyIndex);
         	result = psmt.executeUpdate();
         
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return result;
	}
}
