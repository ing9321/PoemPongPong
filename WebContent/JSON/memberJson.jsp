<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.poem.common.DBConnection"%>
<%@page import="com.poem.common.vo.MemberVO"%>
    
<%
	// 전체 회원 목록 출력
	String sql = "select * from MEMBER";
	
	// DB 연결
	Connection con = DBConnection.startDB();

	// Query 등록 및 실행
	PreparedStatement psmt = con.prepareStatement(sql);
	
	ResultSet rs = psmt.executeQuery();
	
	JSONArray jsonArr = new JSONArray(); // 배열
	
	while(rs.next()){
		JSONObject jsonMember = new JSONObject(); // json 내용 담을 객체
		
		MemberVO member = new MemberVO(rs.getString(1), // memberID
									rs.getString(2), // memberPWD
									rs.getString(3), // memberPoet
									rs.getString(4), // memberPhoto
									rs.getString(5), // memberEmail
									rs.getInt(6), // memberPath
									rs.getInt(7)); // memberDormancy
		
		/* jsonMember.put("id", rs.getString(1));
		jsonMember.put("pw", rs.getString(2));
		jsonMember.put("post", rs.getString(3));
		jsonMember.put("photo", rs.getString(4));
		jsonMember.put("email", rs.getString(5));
		jsonMember.put("path", rs.getInt(6));
		jsonMember.put("dormancy", rs.getInt(7)); */

		jsonMember.put("회원", member);
		jsonArr.add(jsonMember);
		
	}
	
	
	// DB close
	DBConnection.closeDB(psmt, rs);
	out.print(jsonArr);
%>