package com.poem.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.poem.common.DBConnection;
import com.poem.common.vo.RelayPoemMainVO;
import com.poem.common.vo.RelayPoemReplyVO;

public class RelayPoemMainDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;

	private ArrayList<RelayPoemMainVO> relayPoemMainList; // 전체 조회 후 전역변수로 저장
	
	// RelayPoemMainDAO 클래스의 SingleTon
	private static RelayPoemMainDAO instance = new RelayPoemMainDAO();
	
	private RelayPoemMainDAO() { }
	
	public static RelayPoemMainDAO getInstance() {
		return instance;
	}
	// ---------- RelayPoemMainDAO 클래스 SingleTon 설정 끝 ----------
	
	// RelayPoemMain 전체 조회 ---------------------------------------
	public ArrayList<RelayPoemMainVO> selectAllRelayPoemMain() {
		relayPoemMainList = new ArrayList<RelayPoemMainVO>();
		
		String sql = "select * from RELAYPOEMMAIN order by RelayMain_Index asc";
		con = DBConnection.startDB();

		try {
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				relayPoemMainList.add(new RelayPoemMainVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		return relayPoemMainList;
	}
	// ---------- RelayPoemMain 전체 조회 메서드 selectAllRelayPoemMain() 끝 ----------
	
	// RelayPoemMain index 조회 (수정할 때 사용) ---------------------------------------
	public RelayPoemMainVO selectRelayPoemMain(int relayPoemMainIndex) {
		RelayPoemMainVO poem = null;
		
		String sql = "select * from RELAYPOEMMAIN where RelayMain_Index=?";
		con = DBConnection.startDB();

		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, relayPoemMainIndex);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				poem = new RelayPoemMainVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return poem;
	}
	// ---------- RelayPoemMain index 조회 메서드 selectRelayPoemMain() 끝 ----------
	
	// 릴레이 시 제목 검색 ----------------------------------------------------------
	// 은지 : 제목이 정확히 일치하지 않고 검색어가 포함되어 있는 결과값을 얻으려면 arraylist로 반환해야 함
	public RelayPoemMainVO searchRelayPoemMainTitle(String title) {
		RelayPoemMainVO poem = null;
		
		String sql = "select * from RELAYPOEMMAIN where Title=?";
		con = DBConnection.startDB();
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, title);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				poem = new RelayPoemMainVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			}
			
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      } finally {
	    	  DBConnection.closeDB(psmt, rs);
	      }
		
		return poem;
	}
	// ---------- 제목 검색 메서드 searchRelayPoemMainTitle() 끝 ----------
	
	// 릴레이 시 주제 검색 ------------------------------------------------
	public ArrayList<RelayPoemMainVO> searchRelayPoemMainSubject(String subject) {
		ArrayList<RelayPoemMainVO> selectedSubjectRelayPoem = new ArrayList<RelayPoemMainVO>();
		
		String sql = "select * from RELAYPOEMMAIN where Subject_Name=?";
		con = DBConnection.startDB();
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, subject);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				selectedSubjectRelayPoem.add(new RelayPoemMainVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			}
			
	      } catch (Exception e) {
	    	  e.printStackTrace();
	      } finally {
	    	  DBConnection.closeDB(psmt, rs);
	      }
		
		return selectedSubjectRelayPoem;
	}
	// ---------- 주제 검색 메서드 searchRelayPoemMainSubject() 끝 ----------
	
	// 릴레이 시 추가 -------------------------------------------------------
	public int insertRelayPoemMain(String subject, String title, String contents, String poets) {
		int result = 0;
		
		String sql = "insert into RELAYPOEMMAIN values(?, ?, ?, ?, ?)";
		// 은지 : RelayMain_Index는 동적으로 추가
		int lastIndex = relayPoemMainList.get(relayPoemMainList.size()-1).getRelayMainIndex();
		
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, lastIndex+1);
			psmt.setString(2, subject);
			psmt.setString(3, title);
			psmt.setString(4, contents);
			psmt.setString(5, poets);
			
			result = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
		return result;
	}
	// ---------- 릴레이 시 추가 메서드 insertRelayPoemMain() 끝 ----------
	
	// 릴레이 시 삭제 -----------------------------------------------------
	public int deleteRelayPoemMain(int index) {
		int result = 0;
		// String sql = "delete from RELAYPOEMMAIN where RelayMain_Index=?";
		String sql = "update RELAYPOEMMAIN set Title=?, Contents=? where RelayMain_Index=?";
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			psmt.setString(1, "규제된 글");
			psmt.setString(2, "관리자에 의해 규제된 글입니다.");
			psmt.setInt(3, index);
			
			result = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return result;
	}
	// ---------- 릴레이 시 삭제 메서드 deleteRelayPoemMain() 끝 ----------
	
	// 릴레이 시 수정 (업데이트) ------------------------------------------
	public int updateRelayPoemMain(int index, String subject, String title, String contents, String poets) {
		int result = 0;
		String sql = "update RELAYPOEMMAIN set Subject_Name=?, Title=?, Contents=?, Poets=? where RelayMain_Index=?";
		con = DBConnection.startDB();
      
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, subject);
			psmt.setString(2, title);
			psmt.setString(3, contents);
			psmt.setString(4, poets);
			psmt.setInt(5, index);
			
         	result = psmt.executeUpdate();
         
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return result;
	}
	// ---------- 릴레이 시 수정 메서드 updateRelayPoemMain() 끝 ----------
	
	// 좋아요가 가장 많은 릴레이 댓글을 메인에 추가하는 메서드 ------------
	public int addReplyToMain(String contents, String poet) {
		int result = 0;
		
		// 가장 마지막(작성중인) 릴레이 시 호출
		RelayPoemMainVO recentRelayMain = relayPoemMainList.get(relayPoemMainList.size()-1);
		
		// index, contents, poets 정보 받기
		int lastIndex = recentRelayMain.getRelayMainIndex();
		String beforeContents = recentRelayMain.getContents();
		String beforePoets = recentRelayMain.getPoets();

		// 매개변수로 전달받은 contents와 poet을 추가하기
		String afterContents = "";
		String afterPoets = "";
		if(beforeContents != "" && beforeContents != null) {
			afterContents = beforeContents + "<br>" + contents;
			
			// 만약 릴레이시에 참여했던 유저라면 중복으로 기입하지 않기
			if(beforePoets.contains(poet)){
				afterPoets = beforePoets;
			} else {
				afterPoets = beforePoets + ", " + poet;
			}
			
		} else {
			afterContents = contents;
			afterPoets = poet;
		}
				
		String sql = "update RELAYPOEMMAIN set Contents=?, Poets=? where RelayMain_Index=?";
		
		con = DBConnection.startDB();
	      
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, afterContents);
			psmt.setString(2, afterPoets);
			psmt.setInt(3, lastIndex);
         	result = psmt.executeUpdate();
         
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
		return result;
	}
	// ---------- 릴레이 메인에 댓글 추가 메서드 addReplyToMain() 끝 ----------
	
	public RelayPoemMainVO selectLastRelayPoem() {
		RelayPoemMainVO lastRelayPoem = null;
		
		RelayPoemMainVO lastOfRelayList = relayPoemMainList.get(relayPoemMainList.size()-1);
		int index = lastOfRelayList.getRelayMainIndex();
		
		String sql = "select * from RELAYPOEMMAIN where RelayMain_Index=?";
		
		con = DBConnection.startDB();
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, index);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				lastRelayPoem = new RelayPoemMainVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return lastRelayPoem;
	}
	
	// 새로운 릴레이 시 생성
	public int startNewRelay(String newSubject) {
		int result = 0;
		int newIndex = relayPoemMainList.get(relayPoemMainList.size()-1).getRelayMainIndex();
		
		String sql = "insert into RELAYPOEMMAIN values(?, ?, null, null, null)";
		
		con = DBConnection.startDB();
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, newIndex+1);
			psmt.setString(2, newSubject);
			result = psmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
		return result;
	}
	
	// 릴레이 시에 타이틀 설정
	public int setRelayMainTitle(String newTitle) {
		int result = 0;
		
		int lastIndex = relayPoemMainList.get(relayPoemMainList.size()-1).getRelayMainIndex();
		String sql = "update RELAYPOEMMAIN set Title=? where RelayMain_Index=?";
		
		con = DBConnection.startDB();
		try{
			psmt = con.prepareStatement(sql);
			psmt.setString(1, newTitle);
			psmt.setInt(2, lastIndex);
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
		return result;
	}
}
