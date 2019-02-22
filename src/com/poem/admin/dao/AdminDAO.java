package com.poem.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.poem.common.DBConnection;
import com.poem.common.vo.AdminVO;


public class AdminDAO {
	// AdminDAO 클래스의 SingleTon
	private static AdminDAO instance = new AdminDAO();
	private ArrayList<AdminVO> adminList = new ArrayList<AdminVO>();
	private Connection con;
	private PreparedStatement psmt;
	private ResultSet rs;
	
	private AdminDAO(){}
	
	public static AdminDAO getInstance(){
		return instance;
	}
	
	// --------------------------------------------------
	
	// admin 조회
	public boolean isAdmin(String AdminID, String AdminPWD){
		String sql = "select * from ADMIN where Admin_ID = ? and Admin_PWD = ?";
		con = DBConnection.startDB();
		
		try{
			psmt = con.prepareCall(sql);
			psmt.setString(1, AdminID);
			psmt.setString(2, AdminPWD);
			rs = psmt.executeQuery();
			
			if(rs.next()){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally{
			DBConnection.closeDB(psmt, rs);
		}
		
		return false;
	}
	
}
