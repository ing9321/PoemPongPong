package com.poem.pong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.poem.common.DBConnection;
import com.poem.common.vo.MemberVO;
import com.poem.common.vo.RelayPoemReplyVO;
import com.poem.pong.vo.RelayReplyJoinMemberVO;

public class RelayReplyJoinDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	private ArrayList<RelayReplyJoinMemberVO> relayReplyMemberList;
	
	// RelayPoemReplyDAO 클래스의 SingleTon
	private static RelayReplyJoinDAO instance = new RelayReplyJoinDAO();

	private RelayReplyJoinDAO() { }
	
	public static RelayReplyJoinDAO getInstance() {
		return instance;
	}
	// --------------------------------------------------

	// 회원정보를 포함한 릴레이 댓글 전체 조회
	public ArrayList<RelayReplyJoinMemberVO> selectAllRelayReply() {
		relayReplyMemberList = new ArrayList<RelayReplyJoinMemberVO>();
		
		String sql = "select * from RELAYPOEMREPLY r NATURAL join MEMBER m order by r.RelayReply_Index desc";
		con = DBConnection.startDB();

		try {
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			// memberID, relayreplyIndex, contents, likeCount, uploadDate
			// memberPWD, memberPoet, memberPhoto, memberEmail, memberPath
			// memberDormancy
			while(rs.next()){
				RelayPoemReplyVO r = new RelayPoemReplyVO(rs.getInt(2),
														rs.getString(1),
														rs.getString(3),
														rs.getInt(4),
														RelayPoemReplyVO.chaneFormat(rs.getTimestamp(5)));
				MemberVO m = new MemberVO(rs.getString(1),
										rs.getString(6),
										rs.getString(7),
										rs.getString(8),
										rs.getString(9),
										rs.getInt(10),
										rs.getInt(11));
				
				relayReplyMemberList.add(new RelayReplyJoinMemberVO(r, m));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		return relayReplyMemberList;
	}
	// ----------------------------------------------
	
	// 릴레이 댓글 중 좋아요 많은 5개 댓글 조회
	public ArrayList<RelayReplyJoinMemberVO> selectBestRelayReply() {
		ArrayList<RelayReplyJoinMemberVO> bestReplyList = new ArrayList<RelayReplyJoinMemberVO>();
		
		// mySQL
		String sql = "select * from RELAYPOEMREPLY r NATURAL join MEMBER m order by LikeCount desc limit 5";
		
		// 오라클
		/*String sql = "select * from (select * from RELAYPOEMREPLY r NATURAL join Member m order by LikeCount desc) where rownum <= 5";*/
		
		con = DBConnection.startDB();
		try {
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				RelayPoemReplyVO r = new RelayPoemReplyVO(rs.getInt(2),
						rs.getString(1),
						rs.getString(3),
						rs.getInt(4),
						RelayPoemReplyVO.chaneFormat(rs.getTimestamp(5)));
				MemberVO m = new MemberVO(rs.getString(1),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8),
						rs.getString(9),
						rs.getInt(10),
						rs.getInt(11));
				
				bestReplyList.add(new RelayReplyJoinMemberVO(r, m));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		return bestReplyList;
	}
	// ----------------------------------------------
}
