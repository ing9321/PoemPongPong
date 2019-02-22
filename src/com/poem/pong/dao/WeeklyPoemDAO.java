package com.poem.pong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		// oracle & mysql
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
		
		// oracle & mysql
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
		
		// oracle & mysql
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
	   	
	// 작성자 검색
	public WeeklyPoemVO searchWeeklyPoemPoet(String MemberPoet) {
		WeeklyPoemVO weeklyPoem = null;
		
		// oracle & mysql
		String sql = "select * from WEEKLYPOEM where MemberPoet=?";
		con = DBConnection.startDB();
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, MemberPoet);
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
	// ---------- 작성자 검색 메서드 searchWeeklyPoemPoet() 끝 ----------
	
	// 주제 검색
	public WeeklyPoemVO searchWeeklyPoemSubject(String subject) {
		WeeklyPoemVO weeklyPoem = null;
		
		// oracle & mysql
		String sql = "select * from WEEKLYPOEM where subjectName=?";
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
		
		// mysql
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
			psmt.setInt(6, 0);					// viewCount
			psmt.setInt(7, 0);					// likeCount
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
		
		// oracle & mysql
		String sql = "update WEEKLYPOEM set Subject_Name=?, Member_ID=?, Title=?, Contents=?, ViewCount=?, LikeCount=?, BestPoem=?, Background=? where WeeklyPoem_Index=?";
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
	public void deleteWeeklyPoem(int weeklyPoemIndex) {
		
		// oracle & mysql
		String sql = "delete from WEEKLYPOEM where WeeklyPoem_Index=?";
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, weeklyPoemIndex);
			psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
	}
	// ---------- 삭제 메서드 deleteWeeklyPoem() 끝 ----------
	
	// WeeklyPoem 조회수 업데이트 ----------------------------------------
	public void updateViewcountWeeklyPoem(int weeklyPoemIndex) {
		System.out.println("weeklyPoemIndex : " + weeklyPoemIndex);
		
		// oracle & mysql
		String sql = "update WEEKLYPOEM set ViewCount=(select * from(select Viewcount from WEEKLYPOEM where WeeklyPoem_Index=?) as a)+1 where weeklyPoem_Index=?";
		
		// mysql
//		String sql = "update WEEKLYPOEM "
//				+ "set ViewCount= "
//				+ "(select * from "
//				+ "(select Viewcount from WEEKLYPOEM "
//				+ "where WeeklyPoem_Index=?) as a)+1 "
//				+ "where weeklyPoem_Index=?";

		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, weeklyPoemIndex);
			psmt.setInt(2, weeklyPoemIndex);
			
			psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
	}
	
}
