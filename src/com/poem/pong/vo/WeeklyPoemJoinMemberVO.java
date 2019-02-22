package com.poem.pong.vo;

import com.poem.common.vo.MemberVO;
import com.poem.common.vo.WeeklyPoemVO;

/**
 * 7일 시집 베스트 시
 * WEEKLYPOEM 테이블 + MEMBER 테이블
 * @author Dragon
 *
 */
public class WeeklyPoemJoinMemberVO {
	private WeeklyPoemVO w;
	private MemberVO m;
	
//	private int w_weeklyPoemIndex; // PK : 주간 시 전체 인덱스
//	private int subject_Index; // FK : 주제테이블 인덱스 참조
//	private String member_ID; // FK : 회원테이블 아이디 참조
//	private String title; // 주간 시 제목
//	private String contents; // 주간 시 내용
//	private int viewCount; // 주간 시 조회수
//	private int likeCount; // 주간 시 좋아요수
//	private Timestamp uploadDate; // 주간 시 작성 일자
//	private int bestPoem; // 주간 시 베스트 여부
//
//	private String m_member_PWD; // 회원 비밀번호
//	private String member_Poet; // 회원 닉네임
//	private String member_Photo; // 회원 프로필 사진
//	private String member_Email; // 회원 이메일
//	private int member_Path; // 회원 가입 경로
//	private int member_Dormancy; // 휴면 회원
	
	
	public WeeklyPoemJoinMemberVO() {	}
	
	public WeeklyPoemJoinMemberVO(WeeklyPoemVO w, MemberVO m){
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
