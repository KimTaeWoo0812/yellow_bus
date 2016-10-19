package net.computeering.newschoolbus.TCP;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

//Ŭ���̾�Ʈ ����
public class AndroidSocket extends Thread {

    public static final String serverIp = "192.168.43.30";
    public static final int port = 5000;
    public static AndroidSocket _shared = null;
    public static SocketChannel client;
    public static boolean  stopThread = false;

    private String strTemp;
    public static final int QUEUESIZE = 500;
    public volatile static String Queue[] = new String[QUEUESIZE];
    public volatile static int top = 0;
    public volatile static int rear = 0;

    public static synchronized AndroidSocket shared() {

        if (_shared == null) {
            _shared = new AndroidSocket();
            _shared.start();
        }

        return _shared;
    }

    public static synchronized boolean HasMsg() {
        if (top == rear)
            return false;
        else
            return true;
    }

    /**
     * 메시지 큐에 넣기
     * @param msg
     */
    public static void InQue(String msg){
        Queue[top % QUEUESIZE] = msg;
        top++;
    }

    /**
     * 받은 메시지 빼오기
     * @return
     */
    public static String DeQue(){
        while (!HasMsg()){
            if(ServerCheck.canNotGet) {
                ServerCheck.canNotGet = false;
                return "100";
            }
        }
        /*
        이부분에 빙글빙글 도는 로직 넣으면 된다.
         */
        Log.e("DeQue  ",Queue[rear % QUEUESIZE]);
        String msg =  Queue[rear % QUEUESIZE];
        if(msg.equals(null)){
            Log.e("DeQue null_ "," ");
            msg =  Queue[rear % QUEUESIZE];
        }

        ServerCheck.flag_forServer = false;
        rear++;
        return msg;
    }

    public static void CloseSocket() throws Exception {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(2048);
            Charset cs = Charset.forName("UTF-8");
            buffer = cs.encode("LOGOUT" + TCP_SC._del + "\n");
            try {
                client.write(buffer);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch (android.os.NetworkOnMainThreadException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            buffer.flip();
            buffer.compact();
            stopThread = true;
            _shared = null;
            client.socket().close();
            client.close();
        } catch (NotYetConnectedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Selector selector = null;
        Charset cs = Charset.forName("UTF-8");

        int sizeTemp=0;
        String strTemp22="";

        try {

            System.out.println("Client :: started");

            client = SocketChannel.open();
            client.configureBlocking(false);


            client.connect(new InetSocketAddress(serverIp, port));
            selector = Selector.open();
            client.register(selector, SelectionKey.OP_READ);

            while (!Thread.interrupted() && !client.finishConnect()) {
                Thread.sleep(10);
            }

            System.out.println("Client :: connected");

            while (!Thread.interrupted() && !stopThread) {

                selector.select(3000);

                Iterator<SelectionKey> iter = selector.selectedKeys()
                        .iterator();
                while (!Thread.interrupted() && !stopThread && iter.hasNext()) {
                    SelectionKey key = iter.next();
                    ByteBuffer buffer = ByteBuffer.allocate(2047);


                    if (key.isReadable()) {
                        //buffer.flip();
                        int len = client.read(buffer);
                        if (len < 0) {
                            System.out.println("Client :: server closed");
                            stopThread = true;
                            break;
                        } else if (len == 0) {
                            continue;
                        }
                        buffer.flip();

                        //!@
                        //byte[] b = new byte[1024];
                        int size = buffer.remaining();

                        //if (b.length < size) {
                        //	size = b.length;
                        //}
//							buffer.get(b, 0, size);
//							buffer.put(b, 0, size);









                        CharBuffer cb = cs.decode(buffer);
                        // sayToServer("zsdasdas�޾Ҥ�������");
                        System.out.printf("From TCP Server : ");

                        strTemp = "";




                        while (cb.hasRemaining()) {
                            strTemp += cb.get();
                        }
                        System.out.println(strTemp);
                        String strs[] = strTemp.split(TCP_SC._endSendDel);
                        for(int i=0;i<strs.length;i++){
                            System.out.println(strs[i]);
                            InQue(strs[i]);
                            Log.e("서버 Get: ",strs[i]+"    "+top+" "+rear);

                        }

                        buffer.compact();

                    }
                }
            }
            client.socket().close();
            client.close();
            selector.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.socket().close();
                    client.close();
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Client :: done");
        }

    }

    public synchronized static void SendMessage(String text) throws IOException {
        try {
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);// Ŀ�� ���۸� ���� �ٷ�� ����
            Charset cs = Charset.forName("UTF-8");
            buffer = cs.encode(text);
            System.out.println("TCP_보내는 메시지: "+text);
            client.write(buffer);

            buffer.flip();
            buffer.compact();// ���� �κ��� ByteBuffer �� ������ �̵�
        } catch (NotYetConnectedException  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // int len = client.write(ByteBuffer.wrap(text.getBytes()));
    }
}