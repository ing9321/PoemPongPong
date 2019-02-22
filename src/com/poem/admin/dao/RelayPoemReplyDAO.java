package com.poem.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.poem.common.DBConnection;
import com.poem.common.vo.RelayPoemReplyVO;
import com.poem.common.vo.WeeklyLikeItVO;

public class RelayPoemReplyDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	// RelayPoemReplyDAO 클래스의 SingleTon
	private static RelayPoemReplyDAO instance = new RelayPoemReplyDAO();
	private ArrayList<RelayPoemReplyVO> relayReplyList;
	
	private RelayPoemReplyDAO() { }
	
	public static RelayPoemReplyDAO getInstance() {
		return instance;
	}
	// --------------------------------------------------

	// 전체 조회
	public ArrayList<RelayPoemReplyVO> selectAllRelayReply() {
		relayReplyList = new ArrayList<RelayPoemReplyVO>();
		
		String sql = "select * from RELAYPOEMREPLY order by RelayReply_Index asc";
		con = DBConnection.startDB();

		try {
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				relayReplyList.add(new RelayPoemReplyVO(rs.getInt(1), // RelayReply_ndex
														rs.getString(2), // member_ID
														rs.getString(3), // Contents
														rs.getInt(4), // LikeCount
														RelayPoemReplyVO.chaneFormat(rs.getTimestamp(5)))); // UploadDate
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		return relayReplyList;
	}
	// ----------------------------------------------
	
	// 댓글 규제용 삭제처리
	public int blockRelayReply(int index){
		int result = 0;
		
		String sql = "update RELAYPOEMREPLY set Contents=? where RelayReply_Index=?";
		
		con = DBConnection.startDB();
		try{
			psmt = con.prepareStatement(sql);
			psmt.setString(1, "관리자에 의해 규제된 댓글입니다.");
			psmt.setInt(2, index);
			result = psmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally{
			DBConnection.closeDB(psmt, rs);
		}
		
		return result;
	}
		
	// 릴레이 댓글 추가
	// 은지 : 유저가 댓글 추가할 때도 이용
	public int insertRelayReply(String memberID, String contents) {
		int result = 0;
		
		String sql = "insert into RELAYPOEMREPLY values(?, ?, ?, 0, now())";
		
		int lastIndex = 0;
		
		if(relayReplyList.size() > 0){
				lastIndex = relayReplyList.get(relayReplyList.size() - 1).getRelayReplyIndex();
		}
		con = DBConnection.startDB();
		try{
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, lastIndex+1);
			psmt.setString(2, memberID);
			psmt.setString(3, contents);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
		return result;
	}
	
	// 릴레이 댓글 삭제
	// 은지 : 유저가 댓글 삭제할 때도 이용
	public int deleteRelayReply(int index) {
		int result = 0;
		
		String sql = "delete from RELAYPOEMREPLY where RelayReply_Index=?";
		
		con = DBConnection.startDB();
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, index);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
		return result;
	}
	// ---------------------------------------------
	
	// 릴레이 댓글 수정
	// 은지 : 유저가 댓글 수정할 때도 이용
	public int updateRelayReply(int index, String oldContents, String newContents) {
		int result = 0;
		
		String sql = "update RELAYPOEMREPLY set Contents=? where RelayReply_Index=? and Contents=?";
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			psmt.setString(1, newContents);
			psmt.setInt(2, index);
			psmt.setString(3, oldContents);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
		return result;
	}

	// 좋아요가 가장 많은 댓글VO 찾기 ------------------------------------------
	public RelayPoemReplyVO selectBestReply() {
		RelayPoemReplyVO bestReply = null;
		
		// max함수는 single-group 함수라서 다른 컬럼을 부르지 못함
		// String sql = "select max(LikeCount), contents, member_ID from RELAYPOEMREPLY";
		String sql = "select * from RELAYPOEMREPLY where LikeCount = (select max(LikeCount) from RELAYPOEMREPLY)";
		
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			// relayReplyIndex, memberID, contents, likeCount, uploadDate
			while(rs.next()) {
				bestReply = new RelayPoemReplyVO(rs.getInt(1),
												rs.getString(2),
												rs.getString(3),
												rs.getInt(4),
												RelayPoemReplyVO.chaneFormat(rs.getTimestamp(5)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
		return bestReply;
		
	}
	// ---------- 좋아요 가장 많은 댓글 반환 메서드 selectBestReply() 끝 ----------
	
	// 테이블 초기화 --------------------------------------------------------------
	public void clearReplyTable() {
		String sql = "delete from RELAYPOEMREPLY";
		
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
	}
	// ---------- 테이블 초기화 메서드 clearReplyTable() 끝 ----------
}
