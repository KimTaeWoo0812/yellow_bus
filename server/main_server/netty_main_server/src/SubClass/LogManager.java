package SubClass;

import java.util.ArrayList;

import Process.DB;

public class LogManager {
	public static ArrayList<String> LogTableName;
	public static int LogTableName_size = 0;
	
	//처음 DB 190번째 줄에서 초기화함, LogTableName와 LogTableName_size를 초기화한다.
	public static void SetLog(){
		ArrayList<String> list = DB.CheckTables();
		LogTableName = new ArrayList<String>();
		//첨부터 끝까지 검사해서 첫 3글자가 log인것만 순서대로 새로운 변수(static)에 저장한다
		
		int size = list.size();
		int i=0;
		
		for(i=0;i<size;i++){//테이블의 앞글자만 저장하는 로직
			SC.Print0("from LogManager: 테이블 이름들 확인.... "+list.get(i));
		}
		for(i=0;i<size;i++){//테이블의 앞글자만 저장하는 로직
			if(list.get(i).substring(0,3).equals("log"))//앞 3글자가 log이면
				LogTableName.add(list.get(i));
		}
		LogTableNameSizeRedefinition();
		
		
		
	}
	public static void LogTableNameSizeRedefinition(){ // LogTableName_size를 갱신한다
		LogTableName_size = LogTableName.size();
	}
	public static String GetRearLogTableName(){ // 최신 로그 테이블 이름을 가져온다
		//SC.Print0("from LogManager: 최신 로그 테이블 이름을 가져온다...."+LogTableName.get(LogTableName.size()-1));
		try{
			return LogTableName.get(LogTableName.size()-1);
		} catch(ArrayIndexOutOfBoundsException e){//혹시 모르는 에러
			e.printStackTrace();
			return "";
		}
	}
	public static String GetR_RearLogTableName(){ // 최신 바로 이전 로그 테이블 이름을 가져온다
		try{
			return LogTableName.get(LogTableName.size()-2);
		} catch(ArrayIndexOutOfBoundsException e){//혹시 모르는 에러
			e.printStackTrace();
			return "";
		}
	}
	public static ArrayList<String> CheckTables(){ // DB에서 테이블 목록을 가져온다
		return DB.CheckTables();
	}
	public static int Make_Log_Table(String newName){ // 새로운 로그 테이블을 생성한다
		return DB.Make_Log_Table(newName);
	}
	
	
	
}
