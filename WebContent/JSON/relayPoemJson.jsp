<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.poem.common.DBConnection"%>
<%@page import="com.poem.common.vo.RelayPoemMainVO"%>
    
<%
	// 마지막 인덱스 릴레이시는 제외 : 릴레이 진행중이니까
	String sql = "SELECT * FROM RELAYPOEMMAIN WHERE RelayMain_Index != ";
			sql += "(SELECT RelayMain_Index FROM RELAYPOEMMAIN ORDER BY RelayMain_Index DESC LIMIT 1 )";
	
	// DB 연결
	Connection con = DBConnection.startDB();

	// Query 등록 및 실행
	PreparedStatement psmt = con.prepareStatement(sql);
	ResultSet rs = psmt.executeQuery();
	
	JSONArray jsonArr = new JSONArray(); // 배열

	while(rs.next()){
		JSONObject obj = new JSONObject(); // json 내용 담을 객체
		
		// 객체로 전달 받으려면 사용하세용
		RelayPoemMainVO poem = new RelayPoemMainVO(rs.getInt(1),
											rs.getString(2),
											rs.getString(3),
											rs.getString(4),
											rs.getString(5));
		
		obj.put("INDEX", rs.getInt(1));
		obj.put("SUBJECT", rs.getString(2));
		obj.put("TITLE", rs.getString(3));
		obj.put("CONTENTS", rs.getString(4));
		obj.put("POETS", rs.getString(5));
		
		// obj.put(rs.getInt(1), poem);
		jsonArr.add(obj);
		
	}
	
	
	// DB close
	DBConnection.closeDB(psmt, rs);
	out.print(jsonArr);
%>