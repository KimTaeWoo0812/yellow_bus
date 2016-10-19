//package SubClass;
//
//import java.util.Scanner;
//
//import Process.DB;
//
//public class ServerManage implements Runnable {
//	static Scanner in = new Scanner(System.in);
//	static String temp="";
//	static DB db;
//	public ServerManage(DB db){
//		 this.db=db;
//	 }
//
//	@Override
//	public void run() {
//		for (; SC.stopThread;) {
//			if (in.hasNextLine()) {
//				GetOrder_From_Manager();
//			}
//
//		}
//	}
//
//	/**
//	 * 관리자로부터 명령어 입력받는 함수
//	 */
//	public static void GetOrder_From_Manager() {
//		String order = in.nextLine();
//		switch (order) {
////		case "user":
////			System.out.println("누적 접속 인원: " + countAccept);
////			System.out.println("현재 " + process.clients.size() + "명이 접속 중입니다.");
////
////			break;
////			
////		case "notice":
////			temp = in.nextLine();
////			notice = temp;
////			System.out.println("공지 변경: " + temp);
////			hasNotice = true;
////			break;
////		case "showNotice":
////			System.out.println("공지 내용: " + notice);
////			break;
////		case "reNotice":
////			System.out.println("공지 제거?(Y / N) ");
////			temp = in.nextLine();
////			
////			if(temp.compareTo("Y") == 0||temp.compareTo("y") == 0){
////				notice = "";
////				hasNotice = false;
////			}
////			break;
////		case "getNotice":
////			System.out.println("DB에서 공지 가져오기 .. hasNotis = true ");
////			notice=db.getNotice();
////			hasNotice = true;
////			
////			break;
////		case "print":
////			System.out.println("!print ?(Y / N) ");
////			temp = in.nextLine();
////			if(temp.compareTo("Y") == 0||temp.compareTo("y") == 0){
////				ProcessThread.isPrint = !ProcessThread.isPrint;
////			}
////			break;
////		case "isPrint":
////			System.out.println("isPrint: "+ProcessThread.isPrint);
////			break;
////		case "countAccept":
////			System.out.println("countAccept 초기화?(Y / N) ");
////			temp = in.nextLine();
////			if(temp.compareTo("Y") == 0||temp.compareTo("y") == 0){
////				countAccept = 0;
////			}
////			break;
//			
//		case "help":
//			System.out.println("Order===========================");
//			System.out.println("user -> 누적 접속 인원 수, 현재 접속 인원수");
//			System.out.println("notice + 공지 내용 -> 공지변경 / 생성");
//			System.out.println("showNotice -> 공지 보기");
//			System.out.println("reNotice -> 공지 제거");
//			System.out.println("getNotice -> DB에서 공지 읽기");
//			System.out.println("print -> 화면 출력 boolean");
//			System.out.println("isPrint -> print 설정 출력 ");
//			System.out.println("countAccept -> 누적 접속 인원 수 초기화");
//			System.out.println("end============================");
//			break;
//		}
//		
//		
//	}
//}
