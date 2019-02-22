<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.poem.common.DBConnection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String joinPoet = request.getParameter("joinPoet");
	String myPoet = request.getParameter("memberPoet");
	
	String sql = "";
	
	// DB 연결
	Connection con = DBConnection.startDB();
	PreparedStatement psmt = null;

	// 파라메터로 넘겨받은 값에 의해 선택적 sql문 실행
	if(myPoet != null){
		sql = "select Member_Poet from MEMBER where Member_Poet=? and Member_Poet not in(?)";
		psmt = con.prepareStatement(sql);
		psmt.setString(1, joinPoet);
		psmt.setString(2, myPoet);
	} else {
		sql = "select Member_Poet from MEMBER where Member_Poet=?";
		psmt = con.prepareStatement(sql);
		psmt.setString(1, joinPoet);
	}
	
	ResultSet rs = psmt.executeQuery();
	
	JSONArray arr = new JSONArray();
	
	while(rs.next()){
		String poet = rs.getString(1);
		JSONObject obj = new JSONObject();
		obj.put("poet", poet);
		
		if(obj != null)
			arr.add(obj);
	}
	
	// DB close
	DBConnection.closeDB(psmt, rs);
	out.print(arr);
%>