package com.poem.pong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.poem.common.DBConnection;
import com.poem.common.vo.RelayPoemMainVO;
import com.poem.common.vo.RelayPoemReplyVO;
import com.poem.common.vo.WeeklyLikeItVO;
import com.poem.common.vo.WeeklyPoemVO;

public class RelayPoemMainDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	// RelayPoemMainDAO 클래스의 SingleTon
	private static RelayPoemMainDAO instance = new RelayPoemMainDAO();
	private ArrayList<RelayPoemMainVO> relayPoemList;
	
	private RelayPoemMainDAO() { }
	
	public static RelayPoemMainDAO getInstance() {
		return instance;
	}
	// --------------------------------------------------

	// 전체 조회
	public ArrayList<RelayPoemMainVO> selectAllRelayPoem() {
		relayPoemList = new ArrayList<RelayPoemMainVO>();
		
		String sql = "select * from RELAYPOEMMAIN order by RelayMain_Index asc";
		con = DBConnection.startDB();

		try {
			psmt = con.prepareCall(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				relayPoemList.add(new RelayPoemMainVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		return relayPoemList;
	}
	// ----------------------------------------------
	
	/**
	 * 현재 작성중인 릴레이 시를 반환하는 메서드
	 * made by Dragon
	 * @return RelayPoemMainVO
	 */
	public RelayPoemMainVO currentRelayPoemMain(){		
		RelayPoemMainVO relayPoemReply = null;
		
		// 현재 작성중인 릴레이 시를 가져오는 쿼리
		// 오라클 : select * from WEEKLYPOEM where BESTPOEM=1 and rownum=1 order by UPLOADDATE desc;
//				select *
//				from RELAYPOEMMAIN
//				where rownum=1 
//				order by RELAYMAIN_INDEX desc;
		// mysql
		String sql = "select * from RELAYPOEMMAIN order by RELAYMAIN_INDEX desc limit 1";
		
		// 오라클
		/*String sql = "select * from RELAYPOEMMAIN where rownum=1 order by RELAYMAIN_INDEX desc";*/
		
		con = DBConnection.startDB();

		try {
			psmt = con.prepareCall(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()){				
				// WeeklyPoemVO 형식에 맞춰 추가해야함
				relayPoemReply = new RelayPoemMainVO(rs.getInt(1),				// relayMain_Index
														rs.getString(2),		// subject_Name
														rs.getString(3),		// title
														rs.getString(4),		// contents
														rs.getString(5));		// poets
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		
		return relayPoemReply;
	}
}
