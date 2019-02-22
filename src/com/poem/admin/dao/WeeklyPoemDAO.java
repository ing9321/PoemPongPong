package com.poem.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.poem.common.DBConnection;
import com.poem.common.vo.WeeklyPoemVO;

public class WeeklyPoemDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	private ArrayList<WeeklyPoemVO> weeklyPoemList; // 전체 조회 후 전역변수로 저장
	
	// WeeklyPoemDAO 클래스의 SingleTon
	private static WeeklyPoemDAO instance = new WeeklyPoemDAO();
		
	private WeeklyPoemDAO() { }
	
	public static WeeklyPoemDAO getInstance() {
		return instance;
	}
	// ---------- WeeklyDAO 클래스 SingleTon 설정 끝 ----------
	
	// WeeklyPoem 전체 조회 ---------------------------------------
	public ArrayList<WeeklyPoemVO> selectAllWeeklyPoem() {
		weeklyPoemList = new ArrayList<WeeklyPoemVO>();
		
		// 은지 : WeeklyPoem_Index 오름차순으로 정렬해서 출력
		String sql = "select * from WEEKLYPOEM order by WeeklyPoem_Index asc";
		con = DBConnection.startDB();

		try {
			psmt = con.prepareCall(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				weeklyPoemList.add(new WeeklyPoemVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), WeeklyPoemVO.chaneFormat(rs.getTimestamp(8)), rs.getInt(9), rs.getString(10)));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		return weeklyPoemList;
	}
	// ---------- WeeklyPoem 전체 조회 메서드 selectAllWeeklyPoem() 끝 ----------
	
	// WeeklyPoem index 조회 (수정할 때 사용) ---------------------------------------
	public WeeklyPoemVO selectWeeklyPoem(int weeklyPoemIndex) {
		WeeklyPoemVO poem = null;
		
		String sql = "select * from WEEKLYPOEM where WeeklyPoem_Index=?";
		con = DBConnection.startDB();

		try {
			psmt = con.prepareCall(sql);
			psmt.setInt(1, weeklyPoemIndex);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				poem = new WeeklyPoemVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), WeeklyPoemVO.chaneFormat(rs.getTimestamp(8)), rs.getInt(9), rs.getString(10));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return poem;
	}
	// ---------- WeeklyPoem 전체 조회 메서드 selectAllWeeklyPoem() 끝 ----------
	
	
	// 제목 검색
	public WeeklyPoemVO searchWeeklyPoemTitle(String title) {
		WeeklyPoemVO weeklyPoem = null;
		
		String sql = "select * from WEEKLYPOEM where Title=?";
		con = DBConnection.startDB();
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, title);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				weeklyPoem = new WeeklyPoemVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), WeeklyPoemVO.chaneFormat(rs.getTimestamp(8)), rs.getInt(9), rs.getString(10));
			}
			
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      } finally {
	    	  DBConnection.closeDB(psmt, rs);
	      }
		
		return weeklyPoem;
	}
	// ---------- 제목 검색 메서드 searchWeeklyPoemTitle() 끝 ----------
	
	// 주제 검색
	public WeeklyPoemVO searchWeeklyPoemSubject(String subject) {
		WeeklyPoemVO weeklyPoem = null;
		
		String sql = "select * from WEEKLYPOEM where Subject_Name=?";
		con = DBConnection.startDB();
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, subject);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				weeklyPoem = new WeeklyPoemVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), WeeklyPoemVO.chaneFormat(rs.getTimestamp(8)), rs.getInt(9), rs.getString(10));
			}
			
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      } finally {
	    	  DBConnection.closeDB(psmt, rs);
	      }
		
		return weeklyPoem;
	}
	// ---------- 주제 검색 메서드 searchWeeklyPoemSubject() 끝 ----------
	
	// WeeklyPoem 추가
	public int insertWeeklyPoem(String subject, String memberID, String title, String contents, int bestPoem, String background) {
		int result = 0;
		
		String sql = "insert into WEEKLYPOEM values(?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
		
		System.out.println(weeklyPoemList);
		
		int lastIndex = weeklyPoemList.get(weeklyPoemList.size()-1).getWeeklyPoemIndex();
		
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, lastIndex+1);
			psmt.setString(2, subject);
			psmt.setString(3, memberID);
			psmt.setString(4, title);
			psmt.setString(5, contents);
			psmt.setInt(6, 0);
			psmt.setInt(7, 0);
			psmt.setInt(8, bestPoem);
			psmt.setString(9, background);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
		return result;
	}
	
	// WeeklyPoem 수정 (업데이트) -----------------------------
	// updateWeeklyPoem(weeklyPoemIndex, weeklyPoemSubject, weeklyPoemPoet, weeklyPoemTitle, weeklyPoemContents, weeklyPoemViewCount, weeklyPoemLikeCount, weeklyPoemBestPoem)
	public int updateWeeklyPoem(int index, String subject, String memberID, String title, String contents, int viewCount, int likeCount, int bestPoem, String background) {
		int result = 0;
		String sql = "update WEEKLYPOEM set Subject_Name=?, Member_ID=?, Title=?, Contents=?, ViewCount=?, LikeCount=?, BestPoem=?, Background=?  where WeeklyPoem_Index=?";
		con = DBConnection.startDB();
      
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, subject);
			psmt.setString(2, memberID);
			psmt.setString(3, title);
			psmt.setString(4, contents);
			psmt.setInt(5, viewCount);
			psmt.setInt(6, likeCount);
			psmt.setInt(7, bestPoem);
			psmt.setString(8, background);
			psmt.setInt(9, index);
         	result = psmt.executeUpdate();
         
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return result;
	}
	// ---------- 수정 메서드 updateWeeklyPoem() 끝 ----------
   
	
	// WeeklyPoem 삭제 ----------------------------------------
	public int deleteWeeklyPoem(int weeklyPoemIndex) {
		int result = 0;
		String sql = "update WEEKLYPOEM set Title=?, Contents=? where WeeklyPoem_Index=?";
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			psmt.setString(1, "규제된 글");
			psmt.setString(2, "관리자에 의해 규제된 글입니다.");
			psmt.setInt(3, weeklyPoemIndex);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return result;
	}
	// ---------- 삭제 메서드 deleteWeeklyPoem() 끝 ----------
	
	 // WeeklyPoem 베스트 시 선정
	public void weeklyPoemAddBest() {
		int weeklyPoem_Index = 0;
		String sql = "select WeeklyPoem_Index "
				+ "from WEEKLYPOEM "
				+ "where LikeCount = "
				+ "(select max(LikeCount) "
				+ "from WEEKLYPOEM "
				+ "where UploadDate between date_add(now(), interval -7 day) and now()"
				+ "order by UploadDate desc)";
		
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				weeklyPoem_Index = rs.getInt(1);
			}
			
			if(weeklyPoem_Index != 0) {
				sql = "update WEEKLYPOEM "
						+ "set BestPoem = 1 "
						+ "where WeeklyPoem_Index = ?";
				
				psmt = con.prepareStatement(sql);
				psmt.setInt(1, weeklyPoem_Index);
				psmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
	}
	
	
}
