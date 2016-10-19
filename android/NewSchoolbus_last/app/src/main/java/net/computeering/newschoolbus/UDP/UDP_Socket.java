package net.computeering.newschoolbus.UDP;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;

//Ŭ���̾�Ʈ ����
public class UDP_Socket extends Thread {
    public static final int QUEUESIZE = 100;
    public volatile static String Queue[] = new String[QUEUESIZE];
    public volatile static int top = 0;
    public volatile static int rear = 0;
    //private final String serverIp = "180.189.85.107";
    //private final String serverIp = "192.168.43.30";
    public static DatagramChannel channel;
    public static UDP_Socket _shared = null;
    public static boolean  stopThread = true;
    public static synchronized boolean HasMsg() {
        if (top == rear)
            return false;
        else
            return true;
    }
    public static void InQue(String msg){

        Queue[top % QUEUESIZE] = msg;
        top++;
    }
    /**
     * 받은 메시지 빼오기
     * @return
     */
    public static String DeQue(){
        while (!HasMsg())
            ;
        System.out.println(Queue[rear % QUEUESIZE]+"  "+top+"  "+rear);
        String msg = Queue[rear++ % QUEUESIZE];
        return msg;
    }
    public static synchronized UDP_Socket shared() {

        if (_shared == null) {
            _shared = new UDP_Socket();
            _shared.start();
        }

        return _shared;
    }

    public static void CloseSocket() throws Exception {
        UDP_SC.stopThread = true;
        _shared = null;
    }

    @Override
    public void run() {
        String msg = "";
        DatagramPacket sendPacket, receivePacket;
        byte[] timeData = new byte[256];
        try {
            UDP_SC.socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        byte[] dummyData = new byte[1];


        sendPacket = new DatagramPacket(dummyData, dummyData.length, UDP_SC.host, 5001);

        try {
            UDP_SC.socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e("UDP_받은 메시지 11  :  ", " ");
        while (stopThread) {
            Log.e("UDP_받은 메시지 22  :  ", " ");
            receivePacket = new DatagramPacket(timeData, timeData.length);
            try {
                UDP_SC.socket.receive(receivePacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("UDP_받은 메시지 33  :  ", " ");
            msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
            Log.e("UDP_받은 메시지 ALL  :  ", msg);
            String strs[] = msg.split(UDP_SC._endSendDel);
            for (int i = 0; i < strs.length; i++) {
                Log.e("UDP_받은 메시지  :  ", strs[i]);
                //System.out.println("UDP_받은 메시지  :  " + strs[i]);
                InQue(strs[i]);
            }
        }





//
//
//        InetAddress host = null;
//        DatagramSocket socket = null;
//        try {
//            socket = new DatagramSocket();
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
//        byte[] dummyData = new byte[256];
//        byte[] timeData = new byte[256];
//        DatagramPacket sendPacket, receivePacket;
//        String a = "asd";
//        dummyData = a.getBytes();
//        try {
//            host = InetAddress.getByName("192.168.43.30");
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//
//        sendPacket = new DatagramPacket(dummyData, dummyData.length, host, 5001);
//        receivePacket = new DatagramPacket(timeData, timeData.length);
//        String msg = new String(receivePacket.getData(), 0, receivePacket.getLength())
//        Log.e("test_:  ","  "+msg);
//
//        try {
//            socket.send(sendPacket);
//            socket.receive(receivePacket);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//
//
//        String msg = "test";
//        ByteBuffer buf = ByteBuffer.allocate(256);
//        buf.clear();
//        buf.put(msg.getBytes());
//        buf.flip();
////
//try {
//        int bytesSent = channel.send(buf, new InetSocketAddress(UDP_SC.serverIp, UDP_SC.port));
//    } catch (IOException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//    }
////
////
////
//        Charset cs = Charset.forName("UTF-8");
//        try {
//
//            System.out.println("Client :: started");
//
//            channel = DatagramChannel.open();
//            //channel.socket().bind(new InetSocketAddress(5000));
//            channel.connect(new InetSocketAddress(UDP_SC.serverIp, UDP_SC.port));
//
//            System.out.println("Client :: connected");
//
//            msg = "";
//            Log.e("UDP_test1 -> :  ", " "+ stopThread);
//        while (stopThread) {
//            Log.e("UDP_test1  :  ", "하이");
//            buf = ByteBuffer.allocate(256);
//            Log.e("UDP_test1  :  ", "11");
//            buf.clear();
//            Log.e("UDP_test1  :  ", "22");
//            channel.receive(buf);
//            Log.e("UDP_test1  :  ", "33");
//            buf.flip();
//            Log.e("UDP_test1  :  ", "하이2");
//            CharBuffer cb = cs.decode(buf);
////				boolean checkLoop = false;
////				while (checkLoop) {
//            msg = "";
//            Log.e("UDP_test2  :  ", " 3");
//            while (cb.hasRemaining()) {
//                Log.e("UDP_test3  :  ", " ");
////						temp2.append(cb.get());
////
////						if (temp2.toString().equals(UDP_SC._endSendDel)) {
////							checkLoop = true;
////							break;
////						}
////
////						msg += temp2.toString();
////						temp2.setLength(0);
//                msg += cb.get();
//            }
//            Log.e("UDP_받은 메시지 ALL  :  ", msg);
//            String strs[] = msg.split(UDP_SC._endSendDel);
//            for (int i = 0; i < strs.length; i++) {
//                Log.e("UDP_받은 메시지  :  ", strs[i]);
//                //System.out.println("UDP_받은 메시지  :  " + strs[i]);
//                UDP_Que.InQue(strs[i]);
//            }
////				}
//
//            buf.compact();
//
//        }
//
//
//        //!!!!!@!@!@이부분 꼭!!!
//        //학생이 맵 그만볼때 STOP#학원명
//        //관리자 운행 종료 시  DONE#학원명#
//        //UDP_SC.SendMsg("a");
//
//            channel.socket().close();
//            channel.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (channel != null) {
//                try {
//                    channel.socket().close();
//                    channel.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println("Client :: done");
//        }


    }

}