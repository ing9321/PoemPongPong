package com.poem.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.poem.common.DBConnection;
import com.poem.common.vo.RelayLikeItVO;
import com.poem.common.vo.SubjectVO;

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
	
	// 전체 조회
	public ArrayList<RelayLikeItVO> selectAllRelayLikeIt() {
		relayLikeList = new ArrayList<RelayLikeItVO>();
		
		String sql = "select * from RELAYLIKEIT order by RelayLikeIt_Index asc";
		con = DBConnection.startDB();

		try {
			psmt = con.prepareCall(sql);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				relayLikeList.add(new RelayLikeItVO(rs.getInt(1), rs.getInt(2), rs.getString(3)));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		return relayLikeList;
	}
	// ----------------------------------------------
	
	// 테이블 초기화---------------------------------
	public void clearLikeTable() {
		String sql = "delete from RELAYLIKEIT";
		
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
	// ---------- 테이블 초기화 메서드 clearLikeTable() 끝 ----------
	
	// memberid로 리스트검색
	public ArrayList<RelayLikeItVO> selectRelayLikeIt(String memberID){
		ArrayList<RelayLikeItVO> memberRelayLikeList = new ArrayList<RelayLikeItVO>();
		
		String sql = "select * from RELAYLIKEIT where Member_ID=?";
		con = DBConnection.startDB();

		try {
			psmt = con.prepareCall(sql);
			psmt.setString(1, memberID);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				memberRelayLikeList.add(new RelayLikeItVO(rs.getInt(1), rs.getInt(2), rs.getString(3)));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		return memberRelayLikeList;
	}
}
