package com.poem.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;
import com.poem.common.DBConnection;
import com.poem.common.vo.SubjectVO;

public class SubjectDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
		
	// SubjectDAO 클래스의 SingleTon
	private static SubjectDAO instance = new SubjectDAO();
	private ArrayList<SubjectVO> subjectList;
	
	private SubjectDAO() { }
	
	public static SubjectDAO getInstance() {
		return instance;
	}
	// --------------------------------------------------

	// 주제 전체 조회-----------------------------
	public ArrayList<SubjectVO> selectAllSubject() {
		subjectList = new ArrayList<SubjectVO>();
		
		String sql = "select * from SUBJECT order by Subject_Index ASC";
		con = DBConnection.startDB();

		try {
			psmt = con.prepareCall(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				subjectList.add(new SubjectVO(rs.getString(1), rs.getInt(2)));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		return subjectList;
	}
	// ---------- 전체 조회 메서드 selectAllSubject() 끝 ----------
	
	// 주제 추가 -----------------------------------------
	public int insertSubject(String subjectName) {
		int result = 0;
		String sql = "insert into SUBJECT values(?, ?)";
		con = DBConnection.startDB();
		
		// 은지 : 새로 추가될 주제 인덱스 설정
		// 은지 : 가장 마지막 데이터의 인덱스를 얻어와서 +1 증가시켜 추가해준다.
		int lastIndex = subjectList.get(subjectList.size()-1).getSubjectIndex();
		
		try{
			psmt = con.prepareStatement(sql);
			psmt.setString(1, subjectName);
			// subject_index값 동적으로 생성
			psmt.setInt(2, lastIndex+1);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return result;
	}
	// ---------- 주제 추가 메서드 insertSubejct() 끝 ----------
	
	// 주제 삭제 ----------------------------------------
	public int deleteSubject(String subjectName) {
		int result = 0;
		String sql = "delete from SUBJECT where Subject_Name=?";
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			psmt.setString(1, subjectName);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return result;
	}
	// ---------- 주제 삭제 메서드 deleteSubejct() 끝 ----------
	
	// 주제 수정 ----------------------------------------
	public int updateSubject(String oldSubjectName, String newSubjectName) {
		int result = 0;
		String sql = "update SUBJECT set Subject_Name=? where Subject_Name=?";
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			psmt.setString(1, newSubjectName);
			psmt.setString(2, oldSubjectName);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return result;
	}
	// ---------- 주제 수정 메서드 updateSubejct() 끝 ----------
	
	// 주제 수정 ----------------------------------------
	public int updateSubject() {
		int result = 0;
		int size = 0;
		
		String sql = "select count(*)-1 from SUBJECT";
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()) {
				size = rs.getInt(1);
			}
			
			if(size != 0) {
				Random random = new Random();
				// size가 3일 경우 1~3 랜덤 출력
				int subject_Index = random.nextInt(size)+1;
				System.out.println(subject_Index);
				sql = "update SUBJECT set Subject_Index=? where Subject_Name='0'";
				
				psmt = con.prepareStatement(sql);
				psmt.setInt(1, subject_Index);
				result = psmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return result;
	}
	// ---------- 주제 수정 메서드 updateSubejct() 끝 ----------
	    
	
	// 랜덤으로 주제 선택 --------------------------------------
	public String chooseRandomSubject() {
		String selectedSubject = "";
		
		Random random = new Random();
		int subjectIndex = random.nextInt(subjectList.size());
		
		selectedSubject = subjectList.get(subjectIndex).getSubjectName();
		
		return selectedSubject;
	}
}
