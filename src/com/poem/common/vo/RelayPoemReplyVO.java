package com.poem.common.vo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class RelayPoemReplyVO {
	private int relayReplyIndex; // PK : 릴레이댓글 인덱스
	private String memberID; // FK : 멤버테이블 회원ID 참조
	private String contents; // 댓글 내용
	private int likeCount; // 좋아요 수
	private String uploadDate; // 작성일
	
	// 기본 생성자
	public RelayPoemReplyVO() { }
	// DB 테이블 구조에 따른 생성자
	public RelayPoemReplyVO(int relayReplyIndex, String memberID, String contents, int likeCount,
			String uploadDate) {
		this.relayReplyIndex = relayReplyIndex;
		this.memberID = memberID;
		this.contents = contents;
		this.likeCount = likeCount;
		this.uploadDate = uploadDate;
	}

	// get/set 메서드
	public int getRelayReplyIndex() {
		return relayReplyIndex;
	}

	public void setRelayReplyIndex(int relayReplyIndex) {
		this.relayReplyIndex = relayReplyIndex;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
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

	// toString 메서드
	@Override
	public String toString() {
		return "RelayPoemReplyVO [relayReplyIndex=" + relayReplyIndex + ", memberID=" + memberID + ", contents="
				+ contents + ", likeCount=" + likeCount + ", uploadDate=" + uploadDate + "]";
	}
	
	static public String chaneFormat(Timestamp time){
		SimpleDateFormat sdfCurrent = new SimpleDateFormat ("yyyy-MM-dd"); 
		String timeChange = sdfCurrent.format(time); 
		
		return timeChange;
	}
}
