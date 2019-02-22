package com.poem.common.vo;

public class RelayLikeItVO {
	private int relayLikeItIndex; // PK : 릴레이 좋아요 인덱스
	private int relayReplyIndex; // FK : 릴레이메인테이블 인덱스 참조
	private String memberID; // FK : 멤버테이블 회원ID 참조
	
	// 기본 생성자
	public RelayLikeItVO() { }
	// DB 테이블 구조에 따른 생성자
	public RelayLikeItVO(int relayLikeItIndex, int relayReplyIndex, String memberID) {
		this.relayLikeItIndex = relayLikeItIndex;
		this.relayReplyIndex = relayReplyIndex;
		this.memberID = memberID;
	}
	
	// get/set 메서드
	public int getRelayLikeItIndex() {
		return relayLikeItIndex;
	}
	public void setRelayLikeItIndex(int relayLikeItIndex) {
		this.relayLikeItIndex = relayLikeItIndex;
	}
	
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
	
	// toString 메서드
	@Override
	public String toString() {
		return "RelayLikeItVO [RelayLikeItIndex=" + relayLikeItIndex + ", RelayReplyIndex=" + relayReplyIndex
				+ ", MemberID=" + memberID + "]";
	}
	
}
