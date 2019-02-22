package com.poem.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.poem.common.DBConnection;
import com.poem.common.vo.MemberVO;
import com.poem.common.vo.WeeklyPoemJoinVO;
import com.poem.common.vo.WeeklyPoemVO;

public class WeeklyPoemJoinDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	ArrayList<WeeklyPoemJoinVO> weeklyPoemJoinList; // WeeklyPoem과 Member Join 결과 List
	
	// WeeklyPoemJoinDAO 클래스의 SingleTon
	private static WeeklyPoemJoinDAO instance = new WeeklyPoemJoinDAO();
	
	private WeeklyPoemJoinDAO() { }
	
	public static WeeklyPoemJoinDAO getInstance() {
		return instance;
	}
	// ---------- WeeklyPoemJoinDAO 클래스 SingleTon 설정 끝 ----------

	// Join한 7일 시집 전체 조회 --------------------------------------
	// (FK) MemberID 를 통해 조인한 WeeklyPoem 테이블과 Member 테이블의 모든 정보를 갖는 전체 List;
	public ArrayList<WeeklyPoemJoinVO> selectAllWeeklyPoemJoin() {
		weeklyPoemJoinList = new ArrayList<WeeklyPoemJoinVO>();
		
		String sql = "select * from WEEKLYPOEM w natural join MEMBER m order by w.WeeklyPoem_Index asc";
	
		con = DBConnection.startDB();

		try {
			psmt = con.prepareCall(sql);
			rs = psmt.executeQuery();
		
			while(rs.next()){
				WeeklyPoemVO w = new WeeklyPoemVO(rs.getInt(2), // weeklyPoemIndex
												rs.getString(3), // subject
												rs.getString(1), // memberID
												rs.getString(4), // title
												rs.getString(5), // contents
												rs.getInt(6), // viewCount
												rs.getInt(7), // likeCount
												WeeklyPoemVO.chaneFormat(rs.getTimestamp(8)), // uploadDate
												rs.getInt(9), // bestPoem
												rs.getString(10)); // background
				
				MemberVO m = new MemberVO(rs.getString(1), // memberID
										rs.getString(11), // memberPWD
										rs.getString(12), // memberPoet
										rs.getString(13), // memberPhoto
										rs.getString(14), // memberEmail
										rs.getInt(15), // memberPath
										rs.getInt(16)); // memberDormancy

				weeklyPoemJoinList.add(new WeeklyPoemJoinVO(w, m));

			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		
		return weeklyPoemJoinList;
	}
	// ---------- Join한 7일 시집 전체 조회 selectAllWeeklyPoemJoin() 끝 ----------
	
	// Join한 7일 시집 index를 통한 선택 조회 -------------------------------------
	public WeeklyPoemJoinVO selectWeeklyPoemJoin(int weeklyPoemIndex) {
		WeeklyPoemJoinVO selectedPoem = null;
		
		for(int i = 0; i < weeklyPoemJoinList.size(); i++) {
			if(weeklyPoemJoinList.get(i).getW().getWeeklyPoemIndex() == weeklyPoemIndex) {
				selectedPoem = weeklyPoemJoinList.get(i);
			}
		}
		
		return selectedPoem;
	}
	// ---------- Join한 7일 시집 index 조회 selectWeeklyPoemJoin() 끝 ----------

	// Join한 7일 시집 주제별 검색 ----------------------------------------------
	public ArrayList<WeeklyPoemJoinVO> searchSubjectWeeklyPoem(String searchWeeklyPoem) {
		ArrayList<WeeklyPoemJoinVO> searchResultList = new ArrayList<WeeklyPoemJoinVO>();
		
		for(int i = 0; i < weeklyPoemJoinList.size(); i++) {
			String result = weeklyPoemJoinList.get(i).getW().getSubjectName();
			if(result.contains(searchWeeklyPoem)) {
				searchResultList.add(weeklyPoemJoinList.get(i));
			}
		}
		
		return searchResultList;
	}
	// ---------- Join한 7일 시집 주제별 검색 searchSubjectWeeklyPoem() 끝 -----------
	
	// Join한 7일 시집 작가명 검색 ----------------------
	public ArrayList<WeeklyPoemJoinVO> searchPoetWeeklyPoem(String searchWeeklyPoem) {
		ArrayList<WeeklyPoemJoinVO> searchResultList = new ArrayList<WeeklyPoemJoinVO>();
		
		for(int i = 0; i < weeklyPoemJoinList.size(); i++) {
			String result = weeklyPoemJoinList.get(i).getM().getMemberPoet();
			if(result.contains(searchWeeklyPoem)) {
				searchResultList.add(weeklyPoemJoinList.get(i));
			}
		}
		
		return searchResultList;
	}
	// ---------- Join한 7일 시집 작가별 검색 searchPoetWeeklyPoem() 끝 -----------

	// Join한 7일 시집 제목 검색 ----------------------
	public ArrayList<WeeklyPoemJoinVO> searchTitleWeeklyPoem(String searchWeeklyPoem) {
		ArrayList<WeeklyPoemJoinVO> searchResultList = new ArrayList<WeeklyPoemJoinVO>();
		
		for(int i = 0; i < weeklyPoemJoinList.size(); i++) {
			String result = weeklyPoemJoinList.get(i).getW().getTitle();
			if(result.contains(searchWeeklyPoem)) {
				searchResultList.add(weeklyPoemJoinList.get(i));
			}
		}
		
		return searchResultList;
	}
	// ---------- Join한 7일 시집 제목 검색 searchTitleWeeklyPoem() 끝 -----------
	
	// 7일 시집 추가 ---------------------------------------
	public int insertWeeklyPoemJoin(String memberPoet, String subject, String title, String contents, int bestPoem, String background) {
		
		String memberID = "";
		System.out.println(weeklyPoemJoinList);
		MemberDAO memberDao = MemberDAO.getInstance();
		ArrayList<MemberVO> memberList = memberDao.selectAllMember();
		for(int i = 0; i < memberList.size(); i++) {
			if(memberList.get(i).getMemberPoet().equals(memberPoet)) {
				memberID = memberList.get(i).getMemberID();
			} else {
				// 작성자가 존재하지 않습니다.
			}
		}
		System.out.println("memberID : " + memberID);
		
		int result = 0;
		if(memberID != null && memberID != "") {
			WeeklyPoemDAO weeklyPoemDao = WeeklyPoemDAO.getInstance();
			result = weeklyPoemDao.insertWeeklyPoem(subject, memberID, title, contents, bestPoem, background);
		} else {
			result = 0;
		}
      
		return result;
	}
	// ---------- 7일 시집 추가 메서드 insertWeeklyPoemJoin() 끝 ----------
	
	
	// Join한 7일 시집 수정 (update) -------------------------------------
	public int updateWeeklyPoemJoin(int index, String subject, String poet,
									String title, String contents, int viewCount,
									int likeCount, int bestPoem, String background) {
		
		String memberID = "";
		for(int i = 0; i < weeklyPoemJoinList.size(); i++) {
			if(weeklyPoemJoinList.get(i).getM().getMemberPoet().equals(poet)) {
				memberID = weeklyPoemJoinList.get(i).getM().getMemberID();
			} else {
				// 작성자가 존재하지 않습니다.
			}
		}
		
		int result = 0;
		if(memberID != null && memberID != "") {
			WeeklyPoemDAO weeklyPoemDao = WeeklyPoemDAO.getInstance();
			result = weeklyPoemDao.updateWeeklyPoem(index, subject, memberID, title, contents, viewCount, likeCount, bestPoem, background);
		} else {
			result = 0;
		}
		
		
		return result;
	}
	// ---------- Join한 7일 시집 수정 updateWeeklyPoemJoin() 끝 ----------
	
	// 삭제는 join이 불필요하므로 WeeklyPoemDAO를 통해 실행
	
}
