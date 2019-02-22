package com.poem.common.vo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/******* DB 주간 시 테이블 구조에 따라 수정 필요 ********/
public class WeeklyPoemVO {
	private int weeklyPoemIndex; // PK : 주간 시 전체 인덱스
	private String subjectName; // FK : 주제테이블 이름 참조
	private String memberID; // FK : 회원테이블 아이디 참조
	private String title; // 주간 시 제목
	private String contents; // 주간 시 내용
	private int viewCount; // 주간 시 조회수
	private int likeCount; // 주간 시 좋아요수
	private String uploadDate; // 주간 시 작성 일자
	private int bestPoem; // 주간 시 베스트 여부
	private String background; // 주간 시 배경 (경로 포함)

	
	// 기본 생성자
	public WeeklyPoemVO() { }
	// DB 테이블 구조에 따른 생성자

	public WeeklyPoemVO(int weeklyPoemIndex, String subjectName, String memberID,
						String title, String contents, int viewCount, int likeCount,
						String uploadDate, int bestPoem, String background) {

		this.weeklyPoemIndex = weeklyPoemIndex;
		this.subjectName = subjectName;
		this.memberID = memberID;
		this.title = title;
		this.contents = contents;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.uploadDate = uploadDate;
		this.bestPoem = bestPoem;
		this.background = background;
	}

	// get/set 메서드
	public int getWeeklyPoemIndex() {
		return weeklyPoemIndex;
	}

	public void setWeeklyPoem_Index(int weeklyPoemIndex) {
		this.weeklyPoemIndex = weeklyPoemIndex;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	public int getBestPoem() {
		return bestPoem;
	}

	public void setBestPoem(int bestPoem) {
		this.bestPoem = bestPoem;
	}
	
	public String getBackground() {
		return background;
	}
	
	public void setBackground(String background) {
		this.background = background;
	}

	// toString 메서드
	@Override
	public String toString() {
		return weeklyPoemIndex + "^" + subjectName + "^" + memberID
				+ "^" + title + "^" + contents + "^" + viewCount + "^" + likeCount
				+ "^" + uploadDate + "^" + bestPoem + "^" + background;

	}
	
	static public String chaneFormat(Timestamp time){
		SimpleDateFormat sdfCurrent = new SimpleDateFormat ("yyyy-MM-dd"); 
		String timeChange = sdfCurrent.format(time); 
		
		return timeChange;
	}
}
