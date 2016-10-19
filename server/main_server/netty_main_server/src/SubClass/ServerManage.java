package SubClass;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import Process.DB;

public class ServerManage implements Runnable {
	static Scanner in = new Scanner(System.in);
	static String temp="";
	static DB db;
	public ServerManage(DB db){
		 this.db=db;
	 }

	@Override
	public void run() {
		for (; SC.stopThread;) {
			if (in.hasNextLine()) {
				GetOrder_From_Manager();
			}

		}
	}

	/**
	 * 관리자로부터 명령어 입력받는 함수
	 */
	public static void GetOrder_From_Manager() {
		String order = in.nextLine();
		switch (order) {
		case "user":
			System.out.println("누적 접속 인원: " + SC.countAccept);
			System.out.println("현재 " + SC.clients.size() + "명이 접속 중입니다.");

			break;
			
//		case "notice":
//			temp = in.nextLine();
//			notice = temp;
//			System.out.println("공지 변경: " + temp);
//			hasNotice = true;
//			break;
//		case "showNotice":
//			System.out.println("공지 내용: " + notice);
//			break;
//		case "reNotice":
//			System.out.println("공지 제거?(Y / N) ");
//			temp = in.nextLine();
//			
//			if(temp.compareTo("Y") == 0||temp.compareTo("y") == 0){
//				notice = "";
//				hasNotice = false;
//			}
//			break;
//		case "getNotice":
//			System.out.println("DB에서 공지 가져오기 .. hasNotis = true ");
//			notice=db.getNotice();
//			hasNotice = true;
//			
//			break;
		case "print":
			System.out.println("!print ?(Y / N) ");
			temp = in.nextLine();
			if(temp.compareTo("Y") == 0||temp.compareTo("y") == 0){
				SC.isPrint = !SC.isPrint;
			}
			break;
		case "isPrint":
			System.out.println("isPrint: "+SC.isPrint);
			break;
		case "countAccept":
			System.out.println("countAccept 초기화?(Y / N) ");
			temp = in.nextLine();
			if(temp.compareTo("Y") == 0||temp.compareTo("y") == 0){
				SC.countAccept = 0;
			}
			break;
			
		case "newTable":
			ArrayList<String> list = LogManager.CheckTables();
			String nowTameName = GetNewTableName();
			if(!list.contains(nowTameName)){
				System.out.println("현재 월 테이블이 없습니다. 생성하시겠습니까?(Y / N) ");
				temp = in.nextLine();
				if(temp.compareTo("Y") == 0||temp.compareTo("y") == 0){
					int check = LogManager.Make_Log_Table(nowTameName);
					if(check == 1){
						LogManager.LogTableName.add(nowTameName);
						LogManager.LogTableNameSizeRedefinition();
					}
				}
			}
			else
				System.out.println("현재 월 테이블이 이미 있습니다!");
			
			
			break;
		case "help":
			System.out.println("Order===========================");
			System.out.println("user -> 누적 접속 인원 수, 현재 접속 인원수");
			System.out.println("print -> 화면 출력 boolean");
			System.out.println("isPrint -> print 설정 출력 ");
			System.out.println("countAccept -> 누적 접속 인원 수 초기화");
			System.out.println("newTable -> 현재 월 테이블 생성");
			System.out.println("end============================");
			break;
		}
		
		
	}
	static String GetNewTableName() {
		long time = System.currentTimeMillis(); 
        SimpleDateFormat ff = new SimpleDateFormat("yyyy");
        
        SimpleDateFormat f = new SimpleDateFormat("MM");
        String temp;
        
        switch (f.format(new Date(time))) {
		case "01":
			temp = "01";
			break;
		case "02":
			temp = "01";
			break;
		case "03":
			temp = "01";
			break;
		case "04":
			temp = "04";
			break;
		case "05":
			temp = "04";
			break;
		case "06":
			temp = "04";
			break;
		case "07":
			temp = "07";
			break;
		case "08":
			temp = "07";
			break;
		case "09":
			temp = "07";
			break;
		case "10":
			temp = "10";
			break;
		case "11":
			temp = "10";
			break;
		case "12":
			temp = "10";
			break;
		default:
			temp = "00";
			break;
		}
        
        
        return "log_"+ff.format(new Date(time))+temp;
	}
}
