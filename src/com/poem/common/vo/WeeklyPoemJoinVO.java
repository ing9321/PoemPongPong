package com.poem.common.vo;

public class WeeklyPoemJoinVO {
	private WeeklyPoemVO w;
	private MemberVO m;
	
	public WeeklyPoemJoinVO() {	}
	
	public WeeklyPoemJoinVO(WeeklyPoemVO w, MemberVO m){
		this.w = w;
		this.m = m;
	}

	public WeeklyPoemVO getW() {
		return w;
	}

	public void setW(WeeklyPoemVO w) {
		this.w = w;
	}

	public MemberVO getM() {
		return m;
	}

	public void setM(MemberVO m) {
		this.m = m;
	}
}
