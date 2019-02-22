<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="org.json.simple.*"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.poem.common.DBConnection"%>
<%@page import="com.poem.common.vo.MemberVO"%>
<%@page import="com.poem.common.EncryptUtil"%>
    
<%
	String memberID = (String)request.getParameter("memberID");
	String originPWD = (String)request.getParameter("password");
	
	
	//DB에는 암호화된 비밀번호가 저장되어 있기 때문에
	// 비교할 때에도 암호화 후 비교 가능
	String memberPWD = EncryptUtil.getEncryptSHA256(originPWD);

	// sql
	String sql = "select * from MEMBER where Member_ID=? and Member_PWD=?";
	
	// DB 연결
	Connection con = DBConnection.startDB();

	// Query 등록 및 실행
	PreparedStatement psmt = con.prepareStatement(sql);
	psmt.setString(1, memberID);
	psmt.setString(2, memberPWD);
	
	ResultSet rs = psmt.executeQuery();
	
	JSONArray jsonArr = new JSONArray(); // 배열
	
	while(rs.next()){
		JSONObject jsonMember = new JSONObject(); // json 내용 담을 객체
		
		// 객체형태로 전달받으려면 사용
		MemberVO member = new MemberVO(rs.getString(1), // memberID
									rs.getString(2), // memberPWD
									rs.getString(3), // memberPoet
									rs.getString(4), // memberPhoto
									rs.getString(5), // memberEmail
									rs.getInt(6), // memberPath
									rs.getInt(7)); // memberDormancy
									
		jsonMember.put("ID", rs.getString(1));
		// jsonMember.put("pw", rs.getString(2));
		jsonMember.put("Poet", rs.getString(3));
		jsonMember.put("Photo", rs.getString(4));
		jsonMember.put("Email", rs.getString(5));
		// jsonMember.put("path", rs.getInt(6));
		// jsonMember.put("dormancy", rs.getInt(7));
		
		// jsonMember.put("login", member);

		jsonArr.add(jsonMember);
	}
	// DB close
	DBConnection.closeDB(psmt, rs);
	out.print(jsonArr);
%>