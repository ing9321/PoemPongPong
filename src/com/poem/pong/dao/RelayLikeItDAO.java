package com.poem.pong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.poem.common.DBConnection;
import com.poem.common.vo.RelayLikeItVO;

public class RelayLikeItDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	// RelayLikeItDAO 클래스의 SingleTon
	private static RelayLikeItDAO instance = new RelayLikeItDAO();
	private ArrayList<RelayLikeItVO> relayLikeList;
	
	private RelayLikeItDAO() { }
	
	public static RelayLikeItDAO getInstance() {
		return instance;
	}
	// --------------------------------------------------

	// 좋아요 추가
	public int insertRelayLikeIt(int relayReplyIndex, String memberID) {
		int result = 0;
		
		String indexSql = "select max(RelayLikeIt_Index) from RELAYLIKEIT";
		String insertSql = "insert into RELAYLIKEIT values(?, ?, ?)";
		
		int lastIndex = 0;
		
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(indexSql);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				lastIndex = rs.getInt(1);
			}
			
			psmt = con.prepareStatement(insertSql);
			psmt.setInt(1, lastIndex+1);
			psmt.setInt(2, relayReplyIndex);
			psmt.setString(3, memberID);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
		return result;
	}
	
	// 좋아요 삭제
	public int deleteRelayLikeIt(int likeIndex) {
		int result = 0;
		
		String sql = "delete from RELAYLIKEIT where RelayLikeIt_Index=?";
		
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, likeIndex);
			result = psmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		return result;
	}
	
	// 좋아요 여부 검사
	public boolean checkedLikeIt(int relayReplyIndex, String memberID) {
		int likeIndex = -1;
		boolean result = false;
		
		String sql = "select * from RELAYLIKEIT where RelayReply_Index=? and Member_ID=?";
		
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, relayReplyIndex);
			psmt.setString(2, memberID);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				likeIndex = rs.getInt(1);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
		// likeIndex가 있으면
		if(likeIndex != -1) {
			// 좋아요 삭제
			deleteRelayLikeIt(likeIndex);
			result = true;
		} else {
			// 좋아요 추가
			insertRelayLikeIt(relayReplyIndex, memberID);
			result = false;
		}
		
		return result;
	}
}
