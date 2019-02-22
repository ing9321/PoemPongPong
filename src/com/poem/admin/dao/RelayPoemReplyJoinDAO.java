package com.poem.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.poem.common.DBConnection;
import com.poem.common.vo.MemberVO;
import com.poem.common.vo.RelayPoemReplyJoinVO;
import com.poem.common.vo.RelayPoemReplyVO;

public class RelayPoemReplyJoinDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	ArrayList<RelayPoemReplyJoinVO> relayReplyJoinList; // RelayPoemReply와 Member Join 결과 List
	
	// RelayPoemReplyJoinDAO 클래스의 SingleTon
	private static RelayPoemReplyJoinDAO instance = new RelayPoemReplyJoinDAO();
	
	private RelayPoemReplyJoinDAO() { }
	
	public static RelayPoemReplyJoinDAO getInstance() {
		return instance;
	}
	// ---------- RelayPoemReplyJoinDAO 클래스 SingleTon 설정 끝 ----------
	
	// Join한 릴레이 시 전체 조회 --------------------------------------
	public ArrayList<RelayPoemReplyJoinVO> selectAllRelayPoemReplyJoin() {
		relayReplyJoinList = new ArrayList<RelayPoemReplyJoinVO>();
		
		String sql = "select * from RELAYPOEMREPLY r natural join MEMBER m order by r.RelayReply_Index asc";
	
		con = DBConnection.startDB();

		try {
			psmt = con.prepareCall(sql);
			rs = psmt.executeQuery();
		
			// Member_ID, RelayReply_Index, Contents, LikeCount, UploadDate
			// Member_PWD, Member_Poet, Member_Photo, Member_Email, Member_Path
			// Member_Dormancy
			while(rs.next()){
				RelayPoemReplyVO r = new RelayPoemReplyVO(rs.getInt(2), // relayReplyIndex
												rs.getString(1), // memberID
												rs.getString(3), // contents
												rs.getInt(4), // likeCount
												RelayPoemReplyVO.chaneFormat(rs.getTimestamp(5))); // uploadDate
				MemberVO m = new MemberVO(rs.getString(1), // memberID
										rs.getString(6), // memberPWD
										rs.getString(7), // memberPoet
										rs.getString(8), // memberPhoto
										rs.getString(9), // memberEmail
										rs.getInt(10), // memberPath
										rs.getInt(11)); // memberDormancy

				relayReplyJoinList.add(new RelayPoemReplyJoinVO(r, m));

			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		
		return relayReplyJoinList;
	}
	// ---------- Join한 릴레이 시 전체 조회 selectAllRelayPoemReplyJoin() 끝 ----------
	
	// Join한 릴레이 댓글 index를 통한 선택 조회 -------------------------------------
	public RelayPoemReplyJoinVO selectRelayReplyJoin(int index) {
		RelayPoemReplyJoinVO selectedReply = null;
		
		for(int i = 0; i < relayReplyJoinList.size(); i++) {
			if(relayReplyJoinList.get(i).getR().getRelayReplyIndex() == index) {
				selectedReply = relayReplyJoinList.get(i);
			}
		}
		
		return selectedReply;
	}
	// ---------- Join한 릴레이 댓글 index 조회 selectRelayReplyJoin() 끝 ----------
	
	// 삭제는 join이 불필요하므로 WeeklyPoemDAO를 통해 실행
}
