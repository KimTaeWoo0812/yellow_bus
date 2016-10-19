package com.javalec.member;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalec.command.Command;
import com.javalec.main.SC;

public class Member_Command implements Command{
	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response, int CASE) {
		Member_Dao dao = new Member_Dao();
		boolean val = false;
		String schoolName = request.getParameter("schoolName");
		
		switch (CASE) {
		case SC.CASE_LIST:
			System.out.println("test: "+schoolName);
			
			ArrayList<Member_Dto> dtos = new ArrayList<Member_Dto>();
			
			dtos = dao.MemberList(schoolName);
			
			for(int i=0;i<dtos.size();i++){
				System.out.println(dtos.get(i).GetId());
			}
			
			
			System.out.println(dtos.size());
			request.setAttribute("schoolName", schoolName);
			request.setAttribute("list", dtos);
			return 1;
			
		case SC.CASE_ADD:
//			System.out.println(request.getParameter("id"));
//			System.out.println(request.getParameter("name"));
//			System.out.println(request.getParameter("parent1"));
//			System.out.println(request.getParameter("parent2"));
//			System.out.println(request.getParameter("role"));
			
			
			val = dao.MemberAdd(request.getParameter("id"), 
					request.getParameter("name"),
					 schoolName, request.getParameter("parent1"), 
					 request.getParameter("parent2"),
					 request.getParameter("role"));
			
			if(!val)
				return 2;
			
			dtos = dao.MemberList(schoolName);
			request.setAttribute("list", dtos);
			break;
		case SC.CASE_DELETE:
			val = dao.MemberDelete(request.getParameterValues("check"), request.getParameter("schoolName"));
			dtos = dao.MemberList(schoolName);
			request.setAttribute("list", dtos);
			break;
//		case SC.CASE_RENAME:
//			val = dao.MemberReRole((ArrayList<Member_Dto>)request.getAttribute("data"), request.getParameter("schoolName"));
//			dtos = dao.MemberList(schoolName);
//			request.setAttribute("list", dtos);
//			break;
//			
//		case SC.CASE5:
//			val = dao.ParentAdd((ArrayList<Member_Dto>)request.getAttribute("data"), request.getParameter("schoolName"));
//			dtos = dao.MemberList(schoolName);
//			request.setAttribute("list", dtos);
//			break;
		}
		if(val){
//			ArrayList<Beacon_Dto> dtos = new ArrayList<Beacon_Dto>();
//			dtos = dao.BeaconList(request.getParameter("schoolName"));
//
//			request.setAttribute("list", dtos);
			return 1;//성공
		}
		
		return 0;//실패
		
	}
	
}
