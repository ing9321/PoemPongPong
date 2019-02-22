package com.poem.pong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import com.poem.common.DBConnection;
import com.poem.common.EncryptUtil;
import com.poem.common.vo.MemberVO;

public class MemberDAO {
	// DBConnection
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	// MemberDAO 클래스의 SingleTon
	private static MemberDAO instance = new MemberDAO();
	
	private MemberDAO() { }
	
	public static MemberDAO getInstance() {
		return instance;
	}
	// --------------------------------------------------
	
	// 이메일로 회원 아이디 찾기
	public String findID(String memberEmail) {
		String memberId = "";
		
		String sql = "select Member_ID from MEMBER where Member_Email=?";
		
		con = DBConnection.startDB();

		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, memberEmail);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				memberId = rs.getString(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				DBConnection.closeDB(psmt, rs);
		}
		
		return memberId;
	}
	// ------------------------------------------------------
	
	// 아이디와 이메일로 비밀번호 찾기
	// 은지 : 일치하는 값이 있으면 랜덤으로 비밀번호를 생성
	// 새로운 비밀번호를 암호화
	// 암호화된 비밀번호 DB에 저장
	// 랜덤으로 생성했던 비밀번호를 리턴
	public String findPWD(String memberId, String memberEmail) {
		String memberPwd = "";
		
		String findSql = "select * from MEMBER where Member_ID=? and Member_Email=?";
		String setSql = "update MEMBER set Member_PWD=? where Member_ID=? and Member_Email=?";
		
		con = DBConnection.startDB();
		
		try {
			psmt = con.prepareStatement(findSql);
			psmt.setString(1, memberId);
			psmt.setString(2, memberEmail);
			rs = psmt.executeQuery();
			
			// select를 통해 일치하는 회원이 있다면
			if(rs.next()){
				
				// 랜덤한 20자리 문자+숫자 생성
				Random rand =new Random();
				StringBuffer sb =new StringBuffer();
				for(int i=0;i<20;i++){
				    if(rand.nextBoolean()){
				        sb.append((char)((int)(rand.nextInt(26))+97));
				    }else{
				        sb.append((rand.nextInt(10))); 
				    }
				}
				System.out.println("새로 생성된 비밀번호 : " + sb);

				// StringBuffer를 String으로 형변환해주어야함
				memberPwd = sb.toString();
				
				if(memberPwd != "" && memberPwd != null){
					// 비밀번호 암호화
					String encryptPwd = EncryptUtil.getEncryptSHA256(memberPwd);
					
					// 비밀번호 새로 설정
					psmt = con.prepareStatement(setSql);
					psmt.setString(1, encryptPwd);
					psmt.setString(2, memberId);
					psmt.setString(3, memberEmail);
					psmt.executeUpdate();
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeDB(psmt, rs);
		}
		
		return memberPwd;
	}
	
	/**
	 * DB insert문 참조
	 * @param Member_ID 아이디
	 * @param Member_Pwd 패스워드
	 * @param Member_Poet 작가명
	 * @param Member_Photo 프로필 사진
	 * @param Member_Email 이메일
	 * @param Member_Path 가입한 경로
	 * @return result = 1 : insert 성공 / result = -1 : insert 실패
	 */
	public int insertMember(String Member_ID, String Member_Pwd, String Member_Email, String Member_Poet, String Member_Photo, int Member_Path){
		int result = -1;
		
		// oracle & mysql
		String sql = "insert into MEMBER(Member_ID,Member_PWD,Member_Email,Member_Poet,Member_Photo, Member_Path) values(?,?,?,?,?,?)";
		con = DBConnection.startDB();
		
		try {
			psmt = con.prepareStatement(sql);
			
			psmt.setString(1, Member_ID);
			psmt.setString(2, Member_Pwd);
			psmt.setString(3, Member_Email);
			psmt.setString(4, Member_Poet);
			psmt.setString(5, Member_Photo);
			psmt.setInt(6, Member_Path);
			result = psmt.executeUpdate();//insert, update, delete(dml문)     select executeQuery
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally{
			try {
				DBConnection.closeDB(psmt, rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return result;
	}
	
	/**
	 * 로그인 메서드
	 * @param Member_ID 멤버 아이디
	 * @param Member_Pwd 멤버 비밀번호
	 * @return MemberVO VO형태로 반환
	 */
	public MemberVO isMember(String Member_ID, String Member_Pwd) {
		MemberVO memberVO = null;
		// oracle & mysql
		String sql = "select * from MEMBER where Member_ID =? and Member_Pwd =?";

		con = DBConnection.startDB();
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, Member_ID);
			psmt.setString(2, Member_Pwd);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				memberVO = new MemberVO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeDB(psmt, rs);
		}
		return memberVO;
	}
	
	public MemberVO isMember(String Member_ID) {
		MemberVO memberVO = null;
		
		// oracle & mysql
		String sql = "select * from MEMBER where Member_ID =?";

		con = DBConnection.startDB();
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, Member_ID);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				memberVO = new MemberVO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeDB(psmt, rs);
		}
		return memberVO;
	}
	
	public MemberVO isSNSMember(String Member_ID) {
		MemberVO memberVO = null;
		
		// oracle & mysql
		String sql = "select * from MEMBER where Member_ID=? and Member_Path != 0";

		con = DBConnection.startDB();
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, Member_ID);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				memberVO = new MemberVO(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeDB(psmt, rs);
		}
		return memberVO;
	}
	

	// modify member information 기초회원정보수정
	public int updateMemberDetail(String memberID, String memberPoet, String memberEmail, String memberPhoto){
		int result = 0;
		
		String sql = "update MEMBER set Member_Poet=?, Member_Email=?, Member_Photo=? where Member_ID=?";
		
		con = DBConnection.startDB();
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, memberPoet);
			psmt.setString(2, memberEmail);
			psmt.setString(3, memberPhoto);
			psmt.setString(4, memberID);
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
		return result;
	}
	
	// 비밀번호만 수정
	public int updateMemberPassword(String memberID, String memberPwd){
		int result = 0;
		
		String sql = "update MEMBER set Member_PWD=? where Member_ID=?";
		
		con = DBConnection.startDB();
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, memberPwd);
			psmt.setString(2, memberID);
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
		return result;
	}
}
