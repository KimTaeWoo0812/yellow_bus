package com.javalec.beacon;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalec.command.Command;
import com.javalec.main.SC;

public class Beacon_Command implements Command {
	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response, int CASE) {
		Beacon_Dao dao = new Beacon_Dao();
		boolean val = false;
		ArrayList<Beacon_Dto> dtos = new ArrayList<Beacon_Dto>();
		String schoolName="";
		switch (CASE) {
		case SC.CASE_LIST:
			
			//String schoolName = (String) request.getAttribute("schoolName");
			//System.out.println("test:   "+schoolName);
			schoolName = (String) request.getParameter("schoolName");
			System.out.println("test:"+schoolName+"끝");
			dtos = dao.BeaconList(schoolName);
			
			request.setAttribute("schoolName", schoolName);
			request.setAttribute("list", dtos);
			return 1;
			
		case SC.CASE_ADD:
			schoolName = (String) request.getParameter("schoolName");
			val = dao.BeaconAdd(schoolName,
					request.getParameter("beaconName"), request.getParameter("identifier"),
					request.getParameter("isCar"));
			System.out.println("test2:"+schoolName+"끝");
			dtos = dao.BeaconList(schoolName);
			request.setAttribute("list", dtos);
			
			
			break;
		case SC.CASE_DELETE:
			val = dao.BeaconDelete(request.getParameterValues("check"));
			schoolName = (String) request.getParameter("schoolName");
			dtos = dao.BeaconList(schoolName);
			request.setAttribute("list", dtos);
			break;
		case SC.CASE_RENAME:
			val = dao.BeaconReName(request.getParameter("beaconName"), 
					request.getParameter("identifier"));
			break;
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
