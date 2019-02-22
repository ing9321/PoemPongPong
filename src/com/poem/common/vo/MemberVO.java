package com.poem.common.vo;

/******* DB 회원테이블 구조에 따라 수정 필요 ********/
public class MemberVO {
	private String memberID; // PK : 회원 아이디
	private String memberPWD; // 회원 비밀번호
	private String memberPoet; // 회원 닉네임
	private String memberPhoto; // 회원 프로필 사진
	private String memberEmail; // 회원 이메일
	private int memberPath; // 회원 가입 경로
	private int memberDormancy; // 휴면 회원
	
	// 기본 생성자
	public MemberVO() { }
	
	// DB 테이블 구조에 따른 생성자
	public MemberVO(String memberID, String memberPWD, String memberPoet, String memberPhoto, String memberEmail, int memberPath, int memberDormancy) {
		this.memberID = memberID;
		this.memberPWD = memberPWD;
		this.memberPoet = memberPoet;
		this.memberPhoto = memberPhoto;
		this.memberEmail = memberEmail;
		this.memberPath = memberPath;
		this.memberDormancy = memberDormancy;
	}

	// get/set 메서드
	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getMemberPWD() {
		return memberPWD;
	}

	public void setMemberPWD(String memberPWD) {
		this.memberPWD = memberPWD;
	}

	public String getMemberPoet() {
		return memberPoet;
	}

	public void setMemberPoet(String memberPoet) {
		this.memberPoet = memberPoet;
	}

	public String getMemberPhoto() {
		return memberPhoto;
	}

	public void setMemberPhoto(String memberPhoto) {
		this.memberPhoto = memberPhoto;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	
	public int getMemberPath() {
		return memberPath;
	}

	public void setMemberPath(int memberPath) {
		this.memberPath = memberPath;
	}
	
	public int getMemberDormancy() {
		return memberDormancy;
	}

	public void setMemberDormancy(int memberDormancy) {
		this.memberDormancy = memberDormancy;
	}

	// 기본 toString 메서드
	@Override
	public String toString() {
		return "MemberVO [MemberID=" + memberID + ", MemberPWD=" + memberPWD + ", MemberPoet=" + memberPoet
				+ ", MemberPhoto=" + memberPhoto + ", MemberEmail=" + memberEmail
				+ ", memberPath=" + memberPath +", memberDormancy=" + memberDormancy + "]";
	}
	
}
