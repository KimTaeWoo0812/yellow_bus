package com.javalec.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalec.beacon.Beacon_Command;
import com.javalec.car.Car_Command;
import com.javalec.command.Command;
import com.javalec.login.Login_Command;
import com.javalec.main.SC;
import com.javalec.member.Member_Command;

/**
 * Servlet implementation class FrontComtroller
 */
@WebServlet("*.do")
public class FrontComtroller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FrontComtroller() {
		super();
		System.out.println("FrontComtroller");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doGet_Login");
		ActionDo(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost_Login");
		ActionDo(request, response);
	}

	private void ActionDo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("actionDo_Login");
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String com = uri.substring(conPath.length());
		
		request.setCharacterEncoding("EUC-KR");

		String viewPage = null;
		Command command = null;
		int val;

		System.out.println(com);
		switch (com){
		case "/test.do":
			System.out.println("test\ttest\ttest\ttest\t");
			viewPage = "ALogin.jsp";
			break;
		case "/Login.do":
			command = new Login_Command();
			val = command.execute(request, response, 1);
			if(val == 1) // 己傍
				viewPage = "main.jsp";
			else
				viewPage = "ALogin.jsp";
			break;
		case "/main.do":
			command = new Login_Command();
			val = command.execute(request, response, 2);
			if(val == 1) // 己傍
				viewPage = "main.jsp";
			else
				viewPage = "Not.jsp";
			break;
		case "/reNotice.do":
			command = new Login_Command();
			val = command.execute(request, response, 3);
			if(val == 1) // 己傍
				viewPage = "main.jsp";
			else
				viewPage = "Not.jsp";
			break;
		case "/deleteNotice.do":
			command = new Login_Command();
			val = command.execute(request, response, 4);
			if(val == 1) // 己傍
				viewPage = "main.jsp";
			else
				viewPage = "Not.jsp";
			break;
			
		case "/beaconList.do":
			command = new Beacon_Command();
			val = command.execute(request, response, SC.CASE_LIST);

			//System.out.println("beaconList   "+val);
			if(val == 1) // 己傍
				viewPage = "beacon.jsp";
			else
				viewPage = "Not.jsp";
			break;
			
		case "/beaconAdd.do":
			command = new Beacon_Command();
			val = command.execute(request, response, SC.CASE_ADD);
			if(val == 1) // 己傍
				viewPage = "beacon.jsp";
			else
				viewPage = "Not_overlap.jsp";
			break;
			
		case "/beaconDelete.do":
			command = new Beacon_Command();
			val = command.execute(request, response, SC.CASE_DELETE);
			if(val == 1) // 己傍
				viewPage = "beacon.jsp";
			else
				viewPage = "Not.jsp";
			break;
			
		case "/beaconReName.do":
			command = new Beacon_Command();
			val = command.execute(request, response, SC.CASE_RENAME);
			if(val == 1) // 己傍
				viewPage = "beacon.jsp";
			else
				viewPage = "Not.jsp";
			break;
			
			
			
		case "/carList.do":
			command = new Car_Command();
			val = command.execute(request, response, SC.CASE_LIST);

			System.out.println("CarList   "+val);
			if(val == 1) // 己傍
				viewPage = "car.jsp";
			else
				viewPage = "Not.jsp";
			break;
			
		case "/carAdd.do":
			command = new Car_Command();
			val = command.execute(request, response, SC.CASE_ADD);
			if(val == 1) // 己傍
				viewPage = "car.jsp";
			else
				viewPage = "Not_overlap.jsp";
			break;
			
		case "/carDelete.do":
			command = new Car_Command();
			val = command.execute(request, response, SC.CASE_DELETE);
			if(val == 1) // 己傍
				viewPage = "car.jsp";
			else
				viewPage = "Not.jsp";
			break;
			
		case "/carReName.do":
			command = new Car_Command();
			val = command.execute(request, response, SC.CASE_RENAME);
			if(val == 1) // 己傍
				viewPage = "car.jsp";
			else
				viewPage = "Not.jsp";
			break;
			
			////////////////////////////
			/////////////
			///////////////////////////
			
		case "/memberList.do":
			command = new Member_Command();
			val = command.execute(request, response, SC.CASE_LIST);

			if(val == 1) // 己傍
				viewPage = "member.jsp";
			else
				viewPage = "Not.jsp";
			break;
			
		case "/memberAdd.do":
			command = new Member_Command();
			val = command.execute(request, response, SC.CASE_ADD);
			if(val == 1) // 己傍
				viewPage = "member.jsp";
			else if(val == 2)
				viewPage = "Not.jsp";
			else
				viewPage = "Not.jsp";
			break;
			
		case "/memberDelete.do":
			command = new Member_Command();
			val = command.execute(request, response, SC.CASE_DELETE);
			if(val == 1) // 己傍
				viewPage = "member.jsp";
			else
				viewPage = "Not.jsp";
			break;
			
		case "/memberReRole.do":
			command = new Member_Command();
			val = command.execute(request, response, SC.CASE_RENAME);
			if(val == 1) // 己傍
				viewPage = "member.jsp";
			else
				viewPage = "Not.jsp";
			break;
			
		case "/parentAdd.do":
			command = new Member_Command();
			val = command.execute(request, response, SC.CASE5);
			if(val == 1) // 己傍
				viewPage = "member.jsp";
			else
				viewPage = "Not.jsp";
			break;
			
			
			
			
			
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}

}
