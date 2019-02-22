package com.poem.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.poem.common.DBConnection;
import com.poem.common.vo.WeeklyLikeItVO;

public class WeeklyLikeItDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	// WeeklyLikeItDAO 클래스의 SingleTon
	private static WeeklyLikeItDAO instance = new WeeklyLikeItDAO();
	private ArrayList<WeeklyLikeItVO> weekLikeList;
	
	private WeeklyLikeItDAO() { }
	
	public static WeeklyLikeItDAO getInstance() {
		return instance;
	}
	// --------------------------------------------------
	
	// 전체 조회
		public ArrayList<WeeklyLikeItVO> selectAllWeeklyLike() {
			weekLikeList = new ArrayList<WeeklyLikeItVO>();
			
			String sql = "select * from WEEKLYLIKEIT";
			con = DBConnection.startDB();

			try {
				psmt = con.prepareCall(sql);
				rs = psmt.executeQuery();
				
				while(rs.next()){
					// WeeklyLikeItVO 형식에 맞춰 추가해야함
					weekLikeList.add(new WeeklyLikeItVO());
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
					DBConnection.closeDB(psmt, rs);
			}
			return weekLikeList;
		}
		// ----------------------------------------------
}
