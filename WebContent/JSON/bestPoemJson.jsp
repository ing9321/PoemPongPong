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
	
	String sql = "select W.WeeklyPoem_Index, W.Subject_Name, W.Title, W.Contents, W.ViewCount, W.LikeCount, W.UploadDate, ";
			sql += "M.Member_ID, M.Member_Poet, M.Member_Photo from WEEKLYPOEM W natural join MEMBER M where W.BestPoem=1 order by W.WeeklyPoem_Index desc";
	
	// DB 연결
	Connection con = DBConnection.startDB();

	// Query 등록 및 실행
	PreparedStatement psmt = con.prepareStatement(sql);
	
	ResultSet rs = psmt.executeQuery();
	
	JSONArray jsonArr = new JSONArray(); // 배열

	while(rs.next()){
		JSONObject obj = new JSONObject(); // json 내용 담을 객체
				
		obj.put("INDEX", rs.getInt(1));
		obj.put("SUBJECT", rs.getString(2));
		obj.put("TITLE", rs.getString(3));
		obj.put("CONTENTS", rs.getString(4));
		obj.put("VIEWCOUNT", rs.getInt(5));
		obj.put("LIKECOUNT", rs.getInt(6));
		obj.put("UPLOADDATE", WeeklyPoemVO.chaneFormat(rs.getTimestamp(7)));
		obj.put("ID", rs.getString(8));
		obj.put("POET", rs.getString(9));
		obj.put("PROFILE", rs.getString(10));

		jsonArr.add(obj);
		
	}
	
	
	// DB close
	DBConnection.closeDB(psmt, rs);
	out.print(jsonArr);
%>