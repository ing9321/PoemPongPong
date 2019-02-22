package com.poem.common.vo;

public class WeeklyLikeItVO {
	private int weeklyLikeItIndex; // PK : 주간 좋아요 인덱스
	private int weeklyPoemIndex; // FK : 주간시 테이블의 인덱스 참조
	private String memberID; // FK : 멤버테이블의 회원ID 참조
	
	// 기본 생성자
	public WeeklyLikeItVO() { 	}
	// DB 테이블 구조에 따른 생성자
	public WeeklyLikeItVO(int weeklyLikeItIndex, int weeklyPoemIndex, String memberId) {
		this.weeklyLikeItIndex = weeklyLikeItIndex;
		this.weeklyPoemIndex = weeklyPoemIndex;
		this.memberID = memberId;
	}

	// get/set 메서드
	public int getWeeklyLikeItIndex() {
		return weeklyLikeItIndex;
	}

	public void setWeeklyLikeItIndex(int weeklyLikeItIndex) {
		this.weeklyLikeItIndex = weeklyLikeItIndex;
	}

	public int getWeeklyPoemIndex() {
		return weeklyPoemIndex;
	}

	public void setWeeklyPoem_Index(int weeklyPoemIndex) {
		this.weeklyPoemIndex = weeklyPoemIndex;
	}

	public String getMemberId() {
		return memberID;
	}

	public void setMemberId(String memberID) {
		this.memberID = memberID;
	}

	// toString 메서드
	@Override
	public String toString() {
		return "WeeklyLikeItVO [weeklyLikeItIndex=" + weeklyLikeItIndex + ", weeklyPoemIndex=" + weeklyPoemIndex
				+ ", memberId=" + memberID + "]";
	}
	
}
