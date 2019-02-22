package com.poem.common.vo;

public class RelayPoemReplyJoinVO {
	private RelayPoemReplyVO r;
	private MemberVO m;
	
	public RelayPoemReplyJoinVO() { }
	public RelayPoemReplyJoinVO(RelayPoemReplyVO r, MemberVO m) {
		super();
		this.r = r;
		this.m = m;
	}
	
	public RelayPoemReplyVO getR() {
		return r;
	}
	public void setR(RelayPoemReplyVO r) {
		this.r = r;
	}
	public MemberVO getM() {
		return m;
	}
	public void setM(MemberVO m) {
		this.m = m;
	}
	
	@Override
	public String toString() {
		return "RelayPoemReplyJoinVO [r=" + r + ", m=" + m + "]";
	}
}
