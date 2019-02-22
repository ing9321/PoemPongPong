package com.poem.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.poem.common.DBConnection;
import com.poem.common.vo.MemberVO;
import com.poem.common.vo.RelayLikeItJoinVO;
import com.poem.common.vo.RelayLikeItVO;
import com.poem.common.vo.RelayPoemReplyVO;

public class RelayLikeItJoinDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	ArrayList<RelayLikeItJoinVO> relayLikeItList;
	
	// RelayLikeItJoinDAO 클래스의 SingleTon
	private static RelayLikeItJoinDAO instance = new RelayLikeItJoinDAO();
	
	private RelayLikeItJoinDAO() { }
	
	public static RelayLikeItJoinDAO getInstance() {
		return instance;
	}
	// ---------- RelayLikeItJoinDAO 클래스 SingleTon 설정 끝 ----------
	
	// Join한 릴레이 좋아요 전체 조회 --------------------------------------
	public ArrayList<RelayLikeItJoinVO> selectAllRelayLikeItJoin() {
		relayLikeItList = new ArrayList<RelayLikeItJoinVO>();
		
		// natural join이 안된다아..
		String sql = "select * from RELAYLIKEIT rl, RELAYPOEMREPLY rr where rl.RELAYREPLY_INDEX=rr.RELAYREPLY_INDEX order by rl.RELAYLIKEIT_INDEX asc";
	
		con = DBConnection.startDB();

		try {
			psmt = con.prepareCall(sql);
			rs = psmt.executeQuery();
		
			while(rs.next()){
				
				RelayLikeItVO rl = new RelayLikeItVO(rs.getInt(1), // relayLikeItIndex
													rs.getInt(2), // relayReplyIndex
													rs.getString(3)); // memberID
				RelayPoemReplyVO rr = new RelayPoemReplyVO(rs.getInt(4), // relayReplyIndex
														rs.getString(5), // memberID
														rs.getString(6), // contents
														rs.getInt(7), // likeCount
														RelayPoemReplyVO.chaneFormat(rs.getTimestamp(8))); // uploadDate
				
				relayLikeItList.add(new RelayLikeItJoinVO(rl, rr));

			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		
		return relayLikeItList;
	}
	// ---------- Join한 릴레이 좋아요 전체 조회 selectAllRelayLikeItJoin() 끝 ----------
	
	// Join한 릴레이 좋아요 index를 통한 선택 조회 -------------------------------------
	public RelayLikeItJoinVO selectRelayLikeItJoin(int index) {
		RelayLikeItJoinVO selectedRelayLike = null;
		
		for(int i = 0; i < relayLikeItList.size(); i++) {
			if(relayLikeItList.get(i).getRl().getRelayLikeItIndex() == index) {
				selectedRelayLike = relayLikeItList.get(i);
			}
		}
		
		return selectedRelayLike;
	}
	// ---------- Join한 릴레이 좋아요 index 조회 selectRelayLikeItJoin() 끝 ----------
	
}
