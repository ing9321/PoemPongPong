package com.poem.common;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.poem.common.dao.VisitCountDAO;

/**
 * 방문자 수 계산 클래스
 * web.xml에 listener 등록
 * 톰캣이 실행되면 리스너를 실행
 *
 */
@WebListener
public class VisitSessionListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public VisitSessionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent sessionEve)  { 
    	System.out.println("!");
        // 세션이 새로 생성되면 execute() 실행
    	// 디비 연결 안되서 잠깐 정지
//    	if(sessionEve.getSession().isNew()){
//    		execute(sessionEve);
//    	}
    }

	private void execute(HttpSessionEvent sessionEve) {
		VisitCountDAO visitCountDAO = VisitCountDAO.getInstance();

		// 방문자 수 ++
		visitCountDAO.setTodayCount();
		
		// 전체 방문자 수 얻기
		// int totalCount = visitCountDAO.getTotalCount();
		// 오늘 방문자 수 얻기
		// int todayCount = visitCountDAO.getTodayCount(); 
		
		// HttpSession session = sessionEve.getSession();
		
		// 방문자 수 담기
		// session.setAttribute("totalCount", totalCount);
		// session.setAttribute("todayCount", todayCount);
	}

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
         // TODO Auto-generated method stub
    }
	
}
