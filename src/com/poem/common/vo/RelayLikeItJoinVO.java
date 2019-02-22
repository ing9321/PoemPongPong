package com.poem.common.vo;

public class RelayLikeItJoinVO {
	private RelayLikeItVO rl;
	private RelayPoemReplyVO rr;
	
	// 기본 생성자
	public RelayLikeItJoinVO() { }
	
	// pk, fk로 사용된 테이블을 매개변수로 한 생성자
	// 은지 : member는 조인할 때 중첩되어버려서 제거함미다..(17.06.20)
	public RelayLikeItJoinVO(RelayLikeItVO rl, RelayPoemReplyVO rr) {
		this.rl = rl;
		this.rr = rr;
	}

	// get, set 메서드
	public RelayLikeItVO getRl() {
		return rl;
	}

	public void setRl(RelayLikeItVO rl) {
		this.rl = rl;
	}

	public RelayPoemReplyVO getRr() {
		return rr;
	}

	public void setRr(RelayPoemReplyVO rr) {
		this.rr = rr;
	}

	@Override
	public String toString() {
		return "RelayLikeItJoinVO [rl=" + rl + ", rr=" + rr + "]";
	}
	
	
}
