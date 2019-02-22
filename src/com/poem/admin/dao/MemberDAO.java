package com.poem.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.poem.common.DBConnection;
import com.poem.common.vo.MemberVO;

public class MemberDAO {
   // DBConnection
   private Connection con;
   private PreparedStatement psmt;
   private ResultSet rs;
   
   private ArrayList<MemberVO> memList; // 전체 회원 조회 후 MemberDAO의 전역변수로 리스트 저장
   
   // MemberDAO 클래스의 SingleTon
   private static MemberDAO instance = new MemberDAO();
   
   private MemberDAO() { }
   
   public static MemberDAO getInstance() {
      return instance;
   }
   // ---------- MemberDAO 클래스 SingleTon 설정 끝 ----------
   
   // 회원 전체 조회 ----------------------------------
   public ArrayList<MemberVO> selectAllMember() {
      memList = new ArrayList<MemberVO>();
      
      String sql = "select * from MEMBER";
      con = DBConnection.startDB();

      try {
         psmt = con.prepareStatement(sql);
         rs = psmt.executeQuery();
         
         while(rs.next()){
             memList.add(new MemberVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));
         }
         
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBConnection.closeDB(psmt, rs);
      }
      return memList;
   }
   // ---------- 회원 전체 조회 메서드 selectAllMember() 끝 ----------
   
   // 특정 회원 조회 ---------------------------------------------
   public MemberVO selectMember(String memberID){
	   MemberVO member = null;
	   
	   String sql = "select * from MEMBER where Member_ID=?";
	   con = DBConnection.startDB();
		 try {
		    psmt = con.prepareStatement(sql);
		    psmt.setString(1, memberID);
		    rs = psmt.executeQuery();
		    
		    while(rs.next()){
		        member = new MemberVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7));
		    }
		    
		 } catch (Exception e) {
		    e.printStackTrace();
		 } finally {
		       DBConnection.closeDB(psmt, rs);
		 }
	   
	   return member;
   }
   
   // 회원 선택 조회 ----------------------------------------
   public ArrayList<MemberVO> searchMemberID(String memberID) {
      ArrayList<MemberVO> findMemList = new ArrayList<MemberVO>();
      
      // sql문 select 조회
//      String sql = "select * from MEMBER where Member_ID like ?";
//      con = DBConnection.startDB();
//      try {
//         psmt = con.prepareStatement(sql);
//         psmt.setString(1, "%"+ memberID + "%");
//         rs = psmt.executeQuery();
//         
//         while(rs.next()){
//             findMemList.add(new MemberVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7)));
//         }
//         
//      } catch (Exception e) {
//         e.printStackTrace();
//      } finally {
//            DBConnection.closeDB(psmt, rs);
//      }
      
      for(int i = 0; i < memList.size(); i++){
    	  if(memList.get(i).getMemberID().contains(memberID)){
    		  findMemList.add(memList.get(i));
    	  }
      }
      
      return findMemList;
   }
   // ---------- 회원 선택 조회 메서드 selectMember() 끝 ----------

   // 회원 추가 ---------------------------------------
   public int insertMember(String memberID, String memberPWD, String memberPoet, String memberPhoto, String memberEmail) {
      int result = 0;
      String sql = "insert into MEMBER values(?, ?, ?, ?, ?, 0, 0)";
      // 은지 : insert sql문에서 6, 7번째 항목은 기본값 0으로 설정하였습니다.
      // 은지 : 관리자가 회원추가하는 거니까 가입경로 우리 사이트라서
      // 은지 : 방금 가입한 회원이 휴면계정이거나 탈퇴계정은 아닐거니까
      
      con = DBConnection.startDB();
      
      try{
         psmt = con.prepareStatement(sql);
         psmt.setString(1, memberID);
         psmt.setString(2, memberPWD);
         psmt.setString(3, memberPoet);
         psmt.setString(4, memberPhoto);
         psmt.setString(5, memberEmail);
         result = psmt.executeUpdate();
         
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBConnection.closeDB(psmt, rs);
      }
      
      return result;
   }
   // ---------- 회원 추가 메서드 insertMember() 끝 ----------
   
   // 회원 정보 수정 (업데이트) -----------------------------
   public int updateMember(String memberID, String memberPWD, String memberPoet, String memberPhoto, String memberEmail, int memberPath, int memberDormancy) {
      int result = 0;
      String sql = "update MEMBER set Member_PWD=?, Member_Poet=?, Member_Photo=?, Member_Email=?, Member_Path=?, Member_Dormancy=? where Member_ID=?";
      con = DBConnection.startDB();
      
      try {
         psmt = con.prepareStatement(sql);
         psmt.setString(1, memberPWD);
         psmt.setString(2, memberPoet);
         psmt.setString(3, memberPhoto);
         psmt.setString(4, memberEmail);
         psmt.setInt(5, memberPath);
         psmt.setInt(6, memberDormancy);
         psmt.setString(7, memberID);
         result = psmt.executeUpdate();
         
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBConnection.closeDB(psmt, rs);
      }
      return result;
   }
   // ---------- 회원 정보 수정 메서드 updateMember() 끝 ----------
   
   // 회원 강퇴 (삭제) -----------------------------------
   // 은지 : 사실상 delete가 아닌 update문
   // 은지 : Member 테이블에서 Dormancy 컬럼 항목을 변경해주는 것
   public int deleteMember(String memberID) {
      int result = 0;
      MemberVO member = null;
      
      // 회원List에서 삭제할 회원이 있는지 확인
      for(int i = 0; i < memList.size(); i++) {
         if(memList.get(i).getMemberID().equals(memberID)) {
            member = memList.get(i);
         } else {
            // 삭제할 회원이 존재하지 않습니다
         }
      }
      
      if(member != null) {
//        String sql = "delete from MEMBER where Member_ID=?";
    	  // 2017.06.12 수정함
    	  String sql = "update MEMBER set Member_Dormancy=2 where Member_ID=?";
    	  con = DBConnection.startDB();

         try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, memberID);
            result = psmt.executeUpdate();
            
         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            DBConnection.closeDB(psmt, rs);
         }
      } 
      
      return result;
   }
   // ---------- 회원 강퇴 메서드 deleteMember() 끝 ----------
   
   // DB에 int형으로 저장된 가입경로를 string 형태로 반환 -----
   public String checkMemberPath(int memberPath) {
	    String strMemberPath = "";
	    switch(memberPath) {
	    	case 0:
				strMemberPath = "포엠퐁퐁";
				break;
			case 1:
				strMemberPath = "페이스북";
				break;
			case 2:
				strMemberPath = "카카오통";
				break;
			default :
				strMemberPath = "기타 오류";
				break;
		}
		   
		return strMemberPath;
	}
	// ---------- 회원 가입 경로 메서드 checkMemberPath() 끝 ----------
	
	// DB에 int형으로 저장된 회원 활동 상태를 string 형태로 반환 -------
	public String checkMemberDormancy(int memberDormancy) {
		String strMemberDormancy = "";
		switch(memberDormancy) {
		case 0:
			strMemberDormancy = "활동회원";
			break;
		case 1:
			strMemberDormancy = "휴면회원";
			break;
		case 2:
			strMemberDormancy = "탈퇴회원";
			break;
		default:
			strMemberDormancy = "기타 오류";
			break;
		}
		return strMemberDormancy;
	}
	// ---------- 회원 활동 상태 메서드 checkMemberDormancy() 끝 ----------
	
	// 회원ID를 통해 회원 닉네임만 반환하는 메서드
	public String searchMemberPoet(String memberID) {
		String memberPoet = "";
		
		String sql = "select Member_Poet from MEMBER where Member_ID=?";
		
		con = DBConnection.startDB();
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, memberID);
			rs = psmt.executeQuery();
			
			while(rs.next()){
				memberPoet = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeDB(psmt, rs);
		}
		
		return memberPoet;
	}
	// ---------- 회원 닉네임 반환 메서드 searchMemberPoet() 끝 ----------
}