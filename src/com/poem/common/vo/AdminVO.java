package com.poem.common.vo;

public class AdminVO {
	private String adminID; // 관리자 아이디
	private String adminPWD; // 관리자 비밀번호
	
	// 기본생성자
	public AdminVO() { }
	// DB 테이블 구조에 따른 생성자
	public AdminVO(String adminID, String adminPWD) {
		this.adminID = adminID;
		this.adminPWD = adminPWD;
	}
	
	// get/set 메서드
	public String getAdminID() {
		return adminID;
	}
	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}
	public String getAdminPWD() {
		return adminPWD;
	}
	public void setAdminPWD(String adminPWD) {
		this.adminPWD = adminPWD;
	}
	
	// 기본 toString 메서드
	@Override
	public String toString() {
		return "AdminVO [adminId=" + adminID + ", adminPw=" + adminPWD + "]";
	}
	
}
