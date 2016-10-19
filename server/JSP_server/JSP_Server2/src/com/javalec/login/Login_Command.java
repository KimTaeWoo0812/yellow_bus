package com.javalec.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalec.command.Command;
import com.javalec.main.SC;

public class Login_Command implements Command{
	
	@Override
	public int execute(HttpServletRequest request, HttpServletResponse response, int CASE) {
		School_Dao dao = new School_Dao();
		School_Dto dto = new School_Dto();
		
		
		
		switch (CASE) {
		case 1:
			dto = dao.DB_Login(request.getParameter("id"), request.getParameter("pw"));
			
			if("0".equals(dto.name))
					return 0;
			break;
		case 2:
			dto = dao.DB_Maie(request.getParameter("schoolName"));
			if("0".equals(dto.name))
				return 0;
			break;
		case 3:
			System.out.println(request.getParameter("schoolName"));
			System.out.println(request.getParameter("notice"));
			dto = dao.ReNotice(request.getParameter("schoolName"),request.getParameter("notice"));
			
			break;
		case 4:
			
			dto = dao.DeleteNotice(request.getParameter("schoolName"));
			break;
			
		}
		request.setAttribute("info", dto);
		
		
		try{
			if(!dto.equals(null))
				return 1;
		}catch(NullPointerException e){
		}
		return 0;
	}
	
}
