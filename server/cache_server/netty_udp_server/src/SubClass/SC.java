package SubClass;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import Process.SendThread;
import Process.Survive_Check;

public class SC {
	public static String _del="";//데이터 구분자
	public static String _endDel="";//어레이리스트 종료 구분자
	public static String _endSendDel="";//데이터 끝 구분자
	public static volatile HashMap<String, LinkedList<OneOfList>> clients;
	public static boolean stopThread = true;
	public static boolean isPrint = true;
	static SendThread SendClass = new SendThread();
	static Survive_Check survive = new Survive_Check();
	public static boolean forSurviveDelayTime = false;
	
	
	public static void SetData(){
		clients = new HashMap<String, LinkedList<OneOfList>>();
		Collections.synchronizedMap(clients);
		_del+=(char)200;
		_endDel+=(char)201;
		_endSendDel+=(char)202;
		SendClass.start();
		survive.start();
	}
	public static void Print0(Object str){
		if(isPrint)
			System.out.println(str);
	}
	
}
