package SubClass;

import java.util.Collections;
import java.util.HashMap;

import io.netty.channel.ChannelHandlerContext;

public class SC {
	public static String _del="";//데이터 구분자
	public static String _endDel="";//어레이리스트 종료 구분자
	public static String _endSendDel="";//데이터 끝 구분자
	public static volatile HashMap<String, ChannelHandlerContext> clients;
	public static boolean stopThread = true;
	public static boolean isPrint = true;
	public static int countAccept = 0;
	
	public static void SetData(){
		clients = new HashMap<String, ChannelHandlerContext>();
		Collections.synchronizedMap(clients);
		_del+=(char)200;
		_endDel+=(char)201;
		_endSendDel+=(char)202;
		
	}
	
	public synchronized static void Print0(Object str){
		if(isPrint)
			System.out.println(str);
	}
}
