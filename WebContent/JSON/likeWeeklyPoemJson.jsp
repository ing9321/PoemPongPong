<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.poem.common.DBConnection"%>
<%@page import="com.poem.common.vo.WeeklyPoemVO"%>
    
<%

	System.out.println("likeWeeklyPoemJSON.jsp start!!!");
	
	System.out.println(request.getParameter("id"));

	String id = request.getParameter("id");
	
	String sql = "SELECT * FROM "
			+ "(SELECT * FROM WEEKLYPOEM W "
			+ "NATURAL JOIN (SELECT WeeklyPoem_Index "
			+ "FROM WEEKLYLIKEIT WHERE Member_ID=?) L "
			+ "WHERE W.WeeklyPoem_Index = L.WeeklyPoem_Index) WL "
			+ "NATURAL JOIN MEMBER M "
			+ "order by WL.WeeklyPoem_Index desc";

	// DB 연결
	Connection con = DBConnection.startDB();

	// Query 등록 및 실행
	PreparedStatement psmt = con.prepareStatement(sql);
	psmt.setString(1, id);
	ResultSet rs = psmt.executeQuery();
	
	JSONArray jsonArr = new JSONArray(); // 배열

	while(rs.next()){
		JSONObject obj = new JSONObject(); // json 내용 담을 객체
		
		obj.put("ID", rs.getString(1));
		obj.put("INDEX", rs.getInt(2));
		obj.put("SUBJECT", rs.getString(3));
		obj.put("TITLE", rs.getString(4));
		obj.put("CONTENTS", rs.getString(5));
		obj.put("VIEWCOUNT", rs.getInt(6));
		obj.put("LIKECOUNT", rs.getInt(7));
		obj.put("UPLOADDATE", WeeklyPoemVO.chaneFormat(rs.getTimestamp(8)));
		obj.put("BESTPOEM", rs.getInt(9));
		obj.put("POET", rs.getString(12));
		obj.put("PROFILE", rs.getString(13));
		
		jsonArr.add(obj);
		
	}
	
	
	// DB close
	DBConnection.closeDB(psmt, rs);
	out.print(jsonArr);
%>