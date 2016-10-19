package Process;

import io.netty.channel.ChannelHandlerContext;

import java.util.Iterator;

import SubClass.SC;

public class test  extends Thread{
	public static int temp = 0;
	public test(){
		System.out.println("스레드 시작!");
	}
	@Override
	public void run(){
		
		for(;;){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(temp);
		}
		
	}
	public void sendToAll(String message) {
		Iterator<String> it = SC.clients.keySet().iterator();

		// ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		// Charset cs = Charset.forName("UTF-8");
		// buffer = cs.encode(message);
		System.out.println("전체 메시지: " + message+"   "+SC.clients.size());
		while (it.hasNext()) {
			try {
				ChannelHandlerContext ctx = SC.clients.get(it.next());
				// SocketChannel sc = process.clients.get(it.next());
				ctx.write(message+"\n");
				ctx.flush();
			} catch (Exception e) {
			}
		}
	}
}
