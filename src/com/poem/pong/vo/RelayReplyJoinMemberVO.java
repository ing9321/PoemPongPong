package com.poem.pong.vo;

import com.poem.common.vo.MemberVO;
import com.poem.common.vo.RelayPoemReplyVO;

public class RelayReplyJoinMemberVO {
	private RelayPoemReplyVO r;
	private MemberVO m;
	
	public RelayReplyJoinMemberVO() { }
	public RelayReplyJoinMemberVO(RelayPoemReplyVO r, MemberVO m) {
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
		return "RelayReplyJoinMemberVO [r=" + r + ", m=" + m + "]";
	}
	
}
