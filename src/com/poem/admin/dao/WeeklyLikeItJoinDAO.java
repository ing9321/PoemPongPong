package com.poem.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.poem.common.DBConnection;
import com.poem.common.vo.MemberVO;
import com.poem.common.vo.WeeklyLikeItJoinVO;
import com.poem.common.vo.WeeklyLikeItVO;
import com.poem.common.vo.WeeklyPoemVO;

public class WeeklyLikeItJoinDAO {
	
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	private ArrayList<WeeklyLikeItJoinVO> weeklyLikeJoinList; // 전체 회원 조회 후 MemberDAO의 전역변수로 리스트 저장

	// WeeklyLikeItJoinDAO 클래스의 SingleTon
	private static WeeklyLikeItJoinDAO instance = new WeeklyLikeItJoinDAO();
	
	private WeeklyLikeItJoinDAO() { }
	public static WeeklyLikeItJoinDAO getInstance() {
		return instance;
		}
	// ---------- WeeklyLikeItJoinDAO 클래스 SingleTon 설정 끝 ----------
	
	// Join한 7일 시집 좋아요 전체 조회 --------------------------------------
	public ArrayList<WeeklyLikeItJoinVO> selectAllWeeklyLikeJoin() {
		weeklyLikeJoinList = new ArrayList<WeeklyLikeItJoinVO>();
		
		String sql = "select wl.WEEKLYLIKEIT_INDEX, wl.WEEKLYPOEM_INDEX, wl.MEMBER_ID likedmember, wp.SUBJECT_NAME, wp.MEMBER_ID author, wp.TITLE, wp.CONTENTS, wp.VIEWCOUNT, wp.LIKECOUNT, wp.UPLOADDATE, wp.BESTPOEM, wp.BACKGROUND from WEEKLYLIKEIT wl, WEEKLYPOEM wp where wl.WeeklyPoem_Index=wp.WeeklyPoem_Index order by wl.WeeklyLikeIt_Index asc";
	
		con = DBConnection.startDB();

		try {
			psmt = con.prepareCall(sql);
			rs = psmt.executeQuery();
		
			while(rs.next()){
				WeeklyLikeItVO wl = new WeeklyLikeItVO(rs.getInt(1), // weeklyLikeItIndex
													rs.getInt(2), // weeklyPoemIndex
													rs.getString(3)); // memberID
				WeeklyPoemVO wp = new WeeklyPoemVO(rs.getInt(2), // weeklyPoemIndex
												rs.getString(4), // subject
												rs.getString(5), // memberID
												rs.getString(6), // title
												rs.getString(7), // contents
												rs.getInt(8), // viewCount
												rs.getInt(9), // likeCount
												rs.getString(10), // uploadDate
												rs.getInt(11), // bestPoem
												rs.getString(12)); // background

				weeklyLikeJoinList.add(new WeeklyLikeItJoinVO(wl, wp));

			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		
		return weeklyLikeJoinList;
	}
	// ---------- Join한 7일 시집 좋아요 전체 조회 selectAllWeeklyLikeJoin() 끝 ----------
	
}
