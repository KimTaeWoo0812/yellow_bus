package net.computeering.newschoolbus.UDP;


import android.os.NetworkOnMainThreadException;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;

/*
 * 사용법: 여기서 소켓클래스 싱글톤으로 연다.
 * 맨 처음 SetData()로 소켓 열고, 초기화 하고
 * GetMsg()로 받고
 * SendMsg()로 준다.
 * 끝
 */
public class UDP_SC {
	//public static final String serverIp = "119.18.122.104";
	public static InetAddress host = null;
	public static final int port = 5001;
	public static String _del="";//데이터 구분자
	public static String _endDel="";//어레이리스트 종료 구분자
	public static String _endSendDel="";//데이터 끝 구분자
	public static boolean isPrint = true;
	public static UDP_Socket socketClass;
	public static boolean  stopThread = true;
	public static DatagramSocket socket = null;


	public static void SetData(){

		try {
			host = InetAddress.getByName("192.168.43.30");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		socketClass = UDP_Socket.shared();
		stopThread = true;
		_del= "";
		_endDel= "";
		_endSendDel= "";
		_del+=(char)200;
		_endDel+=(char)201;
		_endSendDel+=(char)202;
	}
	public static void Print0(Object str){
		if(isPrint)
			System.out.println(str);
	}
	public static String GetMsg(){
		return socketClass.DeQue();
	}

	public static void SendMsg(String msg){
		byte[] dummyData = new byte[256];

		Log.e("UDP_보내는 메시지   :  ", msg);
		dummyData = msg.getBytes();
		Log.e("UDP_보내는 메시지  2 :  "," "+ dummyData);
		DatagramPacket sendPacket = new DatagramPacket(dummyData, dummyData.length, host, port);
		try {
			socket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NetworkOnMainThreadException e){

		}





//		try {
//			ByteBuffer buf = ByteBuffer.allocate(2048);
//			buf.clear();
//			buf.put(msg.getBytes());
//			buf.flip();
//
//			System.out.println("UDP_보내는 메시지: "+ msg);
//
//			try {
//				int bytesSent = socket.channel.send(buf, new InetSocketAddress(serverIp, port));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (NotYetConnectedException  e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	//소켓 종료 구문. 입력 인자로 학원명#차량명 을 받는다.

	/**
	 * schoolName은 학원명#차량명이다.
	 * @throws Exception
	 */
	public static void CloseChannel() throws Exception{
		socketClass.CloseSocket();
	}
}
