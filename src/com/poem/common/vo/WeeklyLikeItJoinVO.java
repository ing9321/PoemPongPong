package com.poem.common.vo;

public class WeeklyLikeItJoinVO {
	
	private WeeklyLikeItVO wl;
	private WeeklyPoemVO wp;
	
	public WeeklyLikeItJoinVO() { }
	public WeeklyLikeItJoinVO(WeeklyLikeItVO wl, WeeklyPoemVO wp) {
		
		this.wl = wl;
		this.wp = wp;
	}
	
	public WeeklyLikeItVO getWl() {
		return wl;
	}
	public void setWl(WeeklyLikeItVO wl) {
		this.wl = wl;
	}
	public WeeklyPoemVO getWp() {
		return wp;
	}
	public void setWp(WeeklyPoemVO wp) {
		this.wp = wp;
	}
	
	@Override
	public String toString() {
		return "WeeklyLikeItJoinVO [wl=" + wl + ", wp=" + wp + "]";
	}
	
}
