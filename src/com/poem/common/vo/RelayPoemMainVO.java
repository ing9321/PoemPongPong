package com.poem.common.vo;

public class RelayPoemMainVO {
	private int relayMainIndex; // PK : 릴레이메인 인덱스
	private String subjectName; // FK : 주제테이블 주제명
	private String title; // 제목
	private String contents; // 내용
	private String poets; // 참여한 작성자들

	
	// 기본 생성자
	public RelayPoemMainVO() { }
	// DB 테이블 구조에 따른 생성자
	public RelayPoemMainVO(int relayMainIndex, String subjectName, String title, String contents, String poets) {
		this.relayMainIndex = relayMainIndex;
		this.subjectName = subjectName;

		this.title = title;
		this.contents = contents;
		this.poets = poets;
	}

	// get/set 메서드
	public int getRelayMainIndex() {
		return relayMainIndex;
	}

	public void setRelayMainIndex(int relayMainIndex) {
		this.relayMainIndex = relayMainIndex;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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

	public String getPoets() {
		return poets;
	}

	public void setPoets(String poets) {
		this.poets = poets;
	}
	
	@Override
	public String toString() {

		return relayMainIndex + "^" + subjectName + "^"
				+ title + "^" + contents + "^" + poets;

	}

	
}
