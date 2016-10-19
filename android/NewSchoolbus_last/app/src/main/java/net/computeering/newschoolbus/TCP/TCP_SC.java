package net.computeering.newschoolbus.TCP;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.nio.charset.Charset;

/*
 * 사용법: 여기서 소켓클래스 싱글톤으로 연다.
 * 맨 처음 SetData()로 소켓 열고, 초기화 하고
 * GetMsg()로 받고
 * SendMsg()로 준다.
 * 끝
 */
public class TCP_SC {
	public static String _del="";//데이터 구분자
	public static String _endDel="";//어레이리스트 종료 구분자
	public static String _endSendDel="";//데이터 끝 구분자
	public static boolean stopThread = true;
	public static boolean isPrint = true;
	//public static SocketChannel cli;
	public static AndroidSocket socket;


	public static void SetData(){
		socket = AndroidSocket.shared();

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

	/** 큐가 비었을 경우 받을때까지 무한루프 돈다.
	 * 이부분 타임아웃 걸어야댐
	 * @return
	 */
	public static String GetMsg(){
		return socket.DeQue();
	}
	public static void SendMsg(String msg){
		try {
			Log.e("TCP_Send: ", "" + msg);
			ByteBuffer buffer = ByteBuffer.allocate(1024);// Ŀ�� ���۸� ���� �ٷ�� ����
			Charset cs = Charset.forName("UTF-8");
			buffer = cs.encode(msg+"\n");
			System.out.println("Send :: "+msg);
			try {
				socket.client.write(buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			buffer.flip();
			buffer.compact();// ���� �κ��� ByteBuffer �� ������ �̵�
		} catch (NotYetConnectedException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void CloseChannel() throws Exception{
		socket.CloseSocket();
		socket._shared = null;
	}
}
