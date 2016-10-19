package com.javalec.car;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalec.command.Command;
import com.javalec.main.SC;

public class Car_Command implements Command{

	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response, int CASE) {
		Car_Dao dao = new Car_Dao();
		boolean val = false;
		String schoolName = "";
		switch (CASE) {
		case SC.CASE_LIST:
			ArrayList<String> dtos = new ArrayList<String>();
			schoolName = request.getParameter("schoolName");
			dtos = dao.CarList(schoolName);
			System.out.println("테스트:  "+schoolName);
			//System.out.println("싸이즈    "+test+"   "+dtos.size());
			request.setAttribute("schoolName", schoolName);
			request.setAttribute("list", dtos);
			return 1;
			
		case SC.CASE_ADD:
			schoolName = request.getParameter("schoolName");
			val = dao.CarAdd(schoolName, request.getParameter("carName"));
			System.out.println("테스트2:  "+schoolName);
			dtos = dao.CarList(schoolName);
			request.setAttribute("list", dtos);
			break;
		case SC.CASE_DELETE:
			schoolName = request.getParameter("schoolName");
			val = dao.CarDelete(schoolName, request.getParameterValues("check"));
			
			dtos = dao.CarList(schoolName);
			request.setAttribute("list", dtos);
			break;
		case SC.CASE_RENAME:
			val = dao.CarReName(request.getParameter("schoolName"), 
					request.getParameter("carName"),
					request.getParameter("newCarName"));
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
