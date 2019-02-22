package com.poem.pong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.poem.common.DBConnection;
import com.poem.common.vo.RelayPoemReplyVO;

public class RelayPoemReplyDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	private ArrayList<RelayPoemReplyVO> relayReplyList;
	
	// RelayPoemReplyDAO 클래스의 SingleTon
	private static RelayPoemReplyDAO instance = new RelayPoemReplyDAO();

	private RelayPoemReplyDAO() { }
	
	public static RelayPoemReplyDAO getInstance() {
		return instance;
	}
	// --------------------------------------------------

	// 릴레이 댓글 전체 조회
	// 은지 : 릴레이 댓글 인덱스 순으로 출력
	public ArrayList<RelayPoemReplyVO> selectAllRelayReply() {
		relayReplyList = new ArrayList<RelayPoemReplyVO>();
		
		String sql = "select * from RELAYPOEMREPLY order by RelayReply_Index asc";
		con = DBConnection.startDB();

		try {
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				relayReplyList.add(new RelayPoemReplyVO(rs.getInt(1),
														rs.getString(2),
														rs.getString(3),
														rs.getInt(4),
														RelayPoemReplyVO.chaneFormat(rs.getTimestamp(5))));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		return relayReplyList;
	}
	// ----------------------------------------------
	
	// 릴레이 댓글 중 좋아요 많은 5개 댓글 조회
	public ArrayList<RelayPoemReplyVO> selectBestRelayReply() {
		ArrayList<RelayPoemReplyVO> bestReplyList = new ArrayList<RelayPoemReplyVO>();
		
		// mySQL
		String sql = "select * from RELAYPOEMREPLY order by LikeCount desc limit 5";
		
		// 오라클
		/*String sql = "select * from (select * from RELAYPOEMREPLY order by LikeCount desc) where rownum <= 5";*/
		
		con = DBConnection.startDB();
		try {
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				bestReplyList.add(new RelayPoemReplyVO(rs.getInt(1),
													rs.getString(2),
													rs.getString(3),
													rs.getInt(4),
													RelayPoemReplyVO.chaneFormat(rs.getTimestamp(5))));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		return bestReplyList;
	}
	// ----------------------------------------------
	
	// 댓글의 좋아요 카운트 1증가
	public int increaseLikeCount(int relayReplyIndex) {
		int result = 0;
		int likeCount = 0;
		
		String sql = "select LikeCount from RELAYPOEMREPLY where RelayReply_Index=?";
		String sql2 = "update RELAYPOEMREPLY set LikeCount=? where RelayReply_Index=?";
		
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, relayReplyIndex);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				likeCount = rs.getInt(1);
			}
			
			likeCount++;
			
			psmt = con.prepareStatement(sql2);
			psmt.setInt(1, likeCount);
			psmt.setInt(2, relayReplyIndex);
			result = psmt.executeUpdate();
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
		return result;
	}
	
	// 댓글의 좋아요 카운트 1감소
	public int decreaseLikeCount(int relayReplyIndex) {
		int result = 0;
		int likeCount = 0;
		
		String sql = "select LikeCount from RELAYPOEMREPLY where RelayReply_Index=?";
		String sql2 = "update RELAYPOEMREPLY set LikeCount=? where RelayReply_Index=?";
		
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, relayReplyIndex);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				likeCount = rs.getInt(1);
			}
			
			likeCount--;
			
			psmt = con.prepareStatement(sql2);
			psmt.setInt(1, likeCount);
			psmt.setInt(2, relayReplyIndex);
			result = psmt.executeUpdate();
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
		return result;
	}
	
	// 댓글 추가
	public int insertRelayReply(String memberID, String contents) {
		int result = 0;

		String insertsql = "insert into RELAYPOEMREPLY values(?, ?, ?, 0, now())";
		
		
		int lastIndex = 0;
		
		if(relayReplyList.size() > 0){
				lastIndex = relayReplyList.get(relayReplyList.size() - 1).getRelayReplyIndex();
		}
		
		con = DBConnection.startDB();
		try{
			psmt = con.prepareStatement(insertsql);
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
}
