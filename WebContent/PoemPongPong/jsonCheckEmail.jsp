<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.poem.common.DBConnection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String joinEmail = request.getParameter("joinEmail");
	String myEmail = request.getParameter("memberEmail");

	String sql = "";
	
	// DB 연결
	Connection con = DBConnection.startDB();
	PreparedStatement psmt = null;

	// 파라메터로 넘겨받은 값에 의해 선택적 sql문 실행
	if(myEmail != null){
		sql = "select Member_Email from MEMBER where Member_Email=? and Member_Email not in(?)";
		psmt = con.prepareStatement(sql);
		psmt.setString(1, joinEmail);
		psmt.setString(2, myEmail);
	} else {
		sql = "select Member_Email from MEMBER where Member_Email=?";
		psmt = con.prepareStatement(sql);
		psmt.setString(1, joinEmail);
	}
	
	ResultSet rs = psmt.executeQuery();
	
	JSONArray arr = new JSONArray();
	
	while(rs.next()){
		String email = rs.getString(1);
		JSONObject obj = new JSONObject();
		obj.put("email", email);
		
		if(obj != null)
			arr.add(obj);
	}
	
	// DB close
	DBConnection.closeDB(psmt, rs);
	out.print(arr);
%>