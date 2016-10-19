package Process;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import SubClass.OneOfList;
import SubClass.SC;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

public class Survive_Check extends Thread{
	private final int delayTime = (360 * 60 * 1000);
	
	public Survive_Check(){
		System.out.println("Survive_Check start!");
	}
	
	@Override
	public void run(){
		for(;SC.stopThread;){
			try {
				Thread.sleep(delayTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Iterator iterator2;
			Set key;
			String keyName;
			
			
			if (!SC.clients.isEmpty()) {

				SC.forSurviveDelayTime = true;
				try {
					Thread.sleep(SendThread.delayTime+100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				key = SC.clients.keySet();
				for (iterator2 = key.iterator(); iterator2.hasNext();) {
					String keyname = (String) iterator2.next();
					System.out.println("해쉬맵 갱신: "+keyname);
					
					LinkedList<OneOfList> list = new LinkedList<OneOfList>();
					SC.clients.put(keyname, list);
					
					//keyName = (String) iterator2.next();
					
				}
				
				SC.forSurviveDelayTime = false;
			}
			
		}
	}
}
