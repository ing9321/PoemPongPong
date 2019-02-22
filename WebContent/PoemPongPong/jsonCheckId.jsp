<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.poem.common.DBConnection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String joinId = request.getParameter("joinId");
	System.out.println(joinId);
	String sql = "select Member_ID from MEMBER where Member_ID=?";
	
	// DB 연결
	Connection con = DBConnection.startDB();

	// Query 등록 및 실행
	PreparedStatement psmt = con.prepareStatement(sql);
	psmt.setString(1, joinId);
	
	ResultSet rs = psmt.executeQuery();
	
	JSONArray arr = new JSONArray();
	
	while(rs.next()){
		String id = rs.getString(1);
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		
		if(obj != null)
			arr.add(obj);
	}
	
	// DB close
	DBConnection.closeDB(psmt, rs);
	out.print(arr);
%>