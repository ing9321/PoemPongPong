package com.poem.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.poem.common.DBConnection;


/**
 * 방문자수를 관리하는 클래스
 * @author Dragon
 *
 */
public class VisitCountDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	// VisitCountDAO 클래스의 SingleTon
	private static VisitCountDAO instance = new VisitCountDAO();
	
	private VisitCountDAO(){}
	public static VisitCountDAO getInstance() {
		return instance;
	}
	
	//******************************************************************/
	
	public void setTodayCount(){
		con = DBConnection.startDB();
		
		// 오라클 SQL
		String sql = "insert into visit "
				+ "values(to_char(trunc(sysdate,'dd')))";
		try{
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public int getTodayCount(){
		int todayCount = 0;
		con = DBConnection.startDB();
		
		// 오라클
		// 최근날짜 접속자 수
		String sql = "select count(*) "
				+ "from visit "
				+ "where VISIT_DATE=to_char(trunc(sysdate,'dd'),'yyyy/mm/dd')";
		
		try {
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				todayCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return todayCount;
	}
	
	public int getTotalCount(){
		int totalCount = 0;
		con = DBConnection.startDB();
				
		String sql = "select count(*) from visit";
		
		try {
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				totalCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
}
