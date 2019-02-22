package com.poem.pong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.poem.common.DBConnection;
import com.poem.common.vo.MemberVO;
import com.poem.common.vo.WeeklyPoemVO;
import com.poem.pong.vo.WeeklyPoemJoinMemberVO;

public class WeeklyPoemJoinDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	// WeeklyPoemDAO 클래스의 SingleTon
	private static WeeklyPoemJoinDAO instance = new WeeklyPoemJoinDAO();
	
	private WeeklyPoemJoinDAO() { }
	
	public static WeeklyPoemJoinDAO getInstance() {
		return instance;
	}
	// --------------------------------------------------
	
	/**
	 * 7일 시집의 베스트 시 전체를 반환하는 메서드
	 * @return 시 전체 리스트로 반환
	 */
	public ArrayList<WeeklyPoemJoinMemberVO> selectAllWeeklyBestPoem() {
		ArrayList<WeeklyPoemJoinMemberVO> weeklyBestPoemList = new ArrayList<WeeklyPoemJoinMemberVO>();
		
		// 7일 시집 중 가장 최신의 베스트 시부터 가져오는 쿼리
		// 오라클
//					select *
//					from WEEKLYPOEM W
//					natural join MEMBER M 
//					where W.BESTPOEM=1
//					order by W.UPLOADDATE desc;
		// mysql
//		select * from WEEKLYPOEM W
//		natural join MEMBER M 
//		where W.BESTPOEM=1
//		order by W.UPLOADDATE desc;
		
		String sql = "select * "
				+ "from WEEKLYPOEM W "
				+ "natural join MEMBER M "
				+ "where W.BESTPOEM=1 "
				+ "order by W.UPLOADDATE desc";
		
		con = DBConnection.startDB();

		try {
			psmt = con.prepareCall(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				WeeklyPoemVO w = new WeeklyPoemVO(rs.getInt(2),			// weeklyPoem_Index
										rs.getString(3),				// subject_Name
										rs.getString(1),				// member_Id
										rs.getString(4),				// title
										rs.getString(5),				// contents
										rs.getInt(6),					// viewCount
										rs.getInt(7),					// likeCount
										WeeklyPoemVO.chaneFormat(rs.getTimestamp(8)),				// uploadDate
										rs.getInt(9),					// bestPoem
										rs.getString(10));				// background
				
				MemberVO m = new MemberVO(rs.getString(1),			// member_Id
									rs.getString(11),				// member_PWD
									rs.getString(12),				// member_Poet
									rs.getString(13),				// member_Photo
									rs.getString(14),				// member_Email
									rs.getInt(15),					// member_Path
									rs.getInt(16));					// member_Dormancy
								
				weeklyBestPoemList.add(new WeeklyPoemJoinMemberVO(w, m));	
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		return weeklyBestPoemList;
	}

	
	/**
	 * 7일 시집 전체를 반환하는 메서드
	 * @return 시집 전체 리스트로 반환
	 */
	public ArrayList<WeeklyPoemJoinMemberVO> selectAllWeeklyPoem(){
		ArrayList<WeeklyPoemJoinMemberVO> weeklyPoemList = new ArrayList<WeeklyPoemJoinMemberVO>();
		
		// oracle & mysql
		String sql = "select * from WEEKLYPOEM W natural join MEMBER M order by W.WeeklyPoem_Index desc";
		
		con = DBConnection.startDB();

		try {
			psmt = con.prepareCall(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				WeeklyPoemVO w = new WeeklyPoemVO(rs.getInt(2),			// weeklyPoem_Index
										rs.getString(3),				// subject_name
										rs.getString(1),				// member_Id
										rs.getString(4),				// title
										rs.getString(5),				// contents
										rs.getInt(6),					// viewCount
										rs.getInt(7),					// likeCount
										WeeklyPoemVO.chaneFormat(rs.getTimestamp(8)),				// uploadDate
										rs.getInt(9),					// bestPoem
										rs.getString(10));				// background
				
				MemberVO m = new MemberVO(rs.getString(1),			// member_Id
									rs.getString(11),				// member_PWD
									rs.getString(12),				// member_Poet
									rs.getString(13),				// member_Photo
									rs.getString(14),				// member_Email
									rs.getInt(15),					// member_Path
									rs.getInt(16));					// member_Dormancy
								
				weeklyPoemList.add(new WeeklyPoemJoinMemberVO(w, m));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return weeklyPoemList;
	}
	
	/**
	 * 시인명으로 검색하여 작성된 시 반환
	 * @param memberPoet 시인명
	 * @return 시 전체 리스트 형태로 반환
	 */
	public ArrayList<WeeklyPoemJoinMemberVO> searchWeeklyPoemPoet(String memberPoet) {
		ArrayList<WeeklyPoemJoinMemberVO> weeklyPoemList = new ArrayList<WeeklyPoemJoinMemberVO>();
		
		// oracle & mysql
		String sql = "select * "
				+ "from WEEKLYPOEM W "
				+ "natural join MEMBER M "
				+ "where M.member_Poet like ? "
				+ "order by W.WeeklyPoem_Index desc";
		
		con = DBConnection.startDB();

		try {
			psmt = con.prepareCall(sql);
			// like문을 위한 %
			psmt.setString(1, "%"+memberPoet+"%");
			rs = psmt.executeQuery();
			
			while(rs.next()){
				WeeklyPoemVO w = new WeeklyPoemVO(rs.getInt(2),			// weeklyPoem_Index
										rs.getString(3),				// weeklypoem_index
										rs.getString(1),				// member_Id
										rs.getString(4),				// title
										rs.getString(5),				// contents
										rs.getInt(6),					// viewCount
										rs.getInt(7),					// likeCount
										WeeklyPoemVO.chaneFormat(rs.getTimestamp(8)),				// uploadDate
										rs.getInt(9),					// bestPoem
										rs.getString(10));				// background
				
				MemberVO m = new MemberVO(rs.getString(1),			// member_Id
									rs.getString(11),				// member_PWD
									rs.getString(12),				// member_Poet
									rs.getString(13),				// member_Photo
									rs.getString(14),				// member_Email
									rs.getInt(15),					// member_Path
									rs.getInt(16));					// member_Dormancy
								
				weeklyPoemList.add(new WeeklyPoemJoinMemberVO(w, m));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		return weeklyPoemList;
	}
	
	
	/**
	 * 7일 시집 좋아요한 것만 반환하는 메서드
	 * @param loginMember 로그인한 멤버
	 * @return 좋아요 한 시 리스트 형태로 반환
	 */
	public ArrayList<WeeklyPoemJoinMemberVO> selectLikeWeeklyPoem(String loginMember){
		ArrayList<WeeklyPoemJoinMemberVO> weeklyPoemList = new ArrayList<WeeklyPoemJoinMemberVO>();
		
		// mysql
		// 로그인 유저가 좋아요 한 시의 목록
		String sql = "SELECT * FROM "
				+ "(SELECT * FROM WEEKLYPOEM W "
				+ "NATURAL JOIN (SELECT WeeklyPoem_Index "
				+ "FROM WEEKLYLIKEIT WHERE Member_ID=?) L "
				+ "WHERE W.WeeklyPoem_Index = L.WeeklyPoem_Index) WL "
				+ "NATURAL JOIN MEMBER M "
				+ "order by WL.WeeklyPoem_Index desc";
		
		con = DBConnection.startDB();

		try {
			psmt = con.prepareCall(sql);
			psmt.setString(1, loginMember);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				WeeklyPoemVO w = new WeeklyPoemVO(rs.getInt(2),			// weeklyPoem_Index
										rs.getString(3),				// subject_name
										rs.getString(1),				// member_Id
										rs.getString(4),				// title
										rs.getString(5),				// contents
										rs.getInt(6),					// viewCount
										rs.getInt(7),					// likeCount
										WeeklyPoemVO.chaneFormat(rs.getTimestamp(8)),				// uploadDate
										rs.getInt(9),					// bestPoem
										rs.getString(10));				// background
				
				MemberVO m = new MemberVO(rs.getString(1),			// member_Id
									rs.getString(11),				// member_PWD
									rs.getString(12),				// member_Poet
									rs.getString(13),				// member_Photo
									rs.getString(14),				// member_Email
									rs.getInt(15),					// member_Path
									rs.getInt(16));					// member_Dormancy
								
				weeklyPoemList.add(new WeeklyPoemJoinMemberVO(w, m));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return weeklyPoemList;
	}
}
