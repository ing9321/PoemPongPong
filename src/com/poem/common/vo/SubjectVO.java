package com.poem.common.vo;

public class SubjectVO {
	private String subjectName; // PK : 주제명
	private int subjectIndex; // 주제 인덱스
	
	// 기본 생성자
	public SubjectVO() { }
	// DB 테이블 구조에 따른 생성자
	public SubjectVO(String subjectName, int subjectIndex) {
		this.subjectName = subjectName;
		this.subjectIndex = subjectIndex;
	}

	// get/set 메서드
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	
	public int getSubjectIndex() {
		return subjectIndex;
	}

	public void setSubjectIndex(int subjectIndex) {
		this.subjectIndex = subjectIndex;
	}
	
	// toString 메서드
	@Override
	public String toString() {
		return "SubjectVO [subjectName=" + subjectName + ", subjectIndex=" + subjectIndex + "]";
	}
	
}
