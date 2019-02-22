package com.poem.pong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.poem.common.DBConnection;
import com.poem.common.vo.WeeklyLikeItVO;

public class WeeklyLikeItJoinDAO {
	
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	// WeeklyLikeItJoinDAO 클래스의 SingleTon
	private static WeeklyLikeItJoinDAO instance = new WeeklyLikeItJoinDAO();
	
	private WeeklyLikeItJoinDAO() { }
	public static WeeklyLikeItJoinDAO getInstance() {
		return instance;
		}
	// ---------- WeeklyLikeItJoinDAO 클래스 SingleTon 설정 끝 ----------
	
	// 좋아요 처리
	public void processWeeklyPoemLikeIt(int weeklyPoemIndex, String memberID){
		boolean result = false;
		int weeklyLikeItIndex = 0;
		int addCount = 0, likeCount = 0;
		
		// oracle & mysql
		String sql = "select * from WEEKLYLIKEIT where WEEKLYPOEM_INDEX=? and MEMBER_ID=?";
		
		con = DBConnection.startDB();

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, weeklyPoemIndex);
			psmt.setString(2, memberID);
			rs = psmt.executeQuery();
		
			while(rs.next()){
				// 좋아요를 이미 했을 때
				weeklyLikeItIndex = rs.getInt(1);
				result = true;
			}
			
			if(result){
				// 좋아요를 이미 했을 때
				
				// oracle & mysql
				sql = "delete from WEEKLYLIKEIT where weeklylikeit_index=?";
				
				psmt = con.prepareStatement(sql);
				psmt.setInt(1, weeklyLikeItIndex);
				psmt.executeUpdate();
				
				addCount = -1;
			}else{
				// 좋아요를 안했을 때
				// 최신 글 index 가져오기
				// oracle
//				sql = "select weeklylikeit_index from (select * from WEEKLYLIKEIT order by weeklylikeit_index desc) where rownum=1";
				
				// mysql
				//sql = "select weeklylikeit_index from (select * from WEEKLYLIKEIT order by weeklylikeit_index desc) as a limit 1";
				sql = "select * from WEEKLYLIKEIT order by weeklylikeit_index desc limit 1";
				
				psmt = con.prepareStatement(sql);
				rs = psmt.executeQuery();
				
				while(rs.next()){
					weeklyLikeItIndex = rs.getInt(1);
				}
				
				System.out.println(weeklyLikeItIndex);
				System.out.println(weeklyPoemIndex);
				System.out.println(memberID);
				
				// oracle & mysql
				sql = "insert into WEEKLYLIKEIT values(?, ?, ?)";
				
				psmt = con.prepareStatement(sql);
				psmt.setInt(1, weeklyLikeItIndex+1);
				psmt.setInt(2, weeklyPoemIndex);
				psmt.setString(3, memberID);
				
				psmt.executeUpdate();

				addCount = 1;
			}
			
			// WeeklyPoem 좋아요 업데이트 /////////////////////
			
			// 좋아요 갯수 가져오기
			// oracle & mysql
			sql = "select LIKECOUNT from WEEKLYPOEM where WeeklyPoem_Index=?";
			
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, weeklyPoemIndex);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				likeCount = rs.getInt(1);
				likeCount += addCount;
			}
			
			// 좋아요 갯수 갱신
			// oracle * mysql
			sql = "update WEEKLYPOEM set LIKECOUNT=? where WEEKLYPOEM_INDEX=?";
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, likeCount);
			psmt.setInt(2, weeklyPoemIndex);
			psmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
	}
	
	// memberId로 좋아요 리스트검색
	public ArrayList<WeeklyLikeItVO> selectWeeklyLikeIt(String memberID){
		ArrayList<WeeklyLikeItVO> weeklyLikeList = new ArrayList<WeeklyLikeItVO>();
		
		// oracle & mysql
		String sql = "select * from WEEKLYLIKEIT where Member_ID=?";
		con = DBConnection.startDB();

		try {
			psmt = con.prepareCall(sql);
			psmt.setString(1, memberID);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				weeklyLikeList.add(new WeeklyLikeItVO(rs.getInt(1), rs.getInt(2), rs.getString(3)));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return weeklyLikeList;
	}
}
