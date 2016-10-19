package Process;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import SubClass.OneOfList;
import SubClass.SC;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

public class SendThread extends Thread {// 돌면서 전송 처리, 최대 대기시간 3초
	public final static int delayTime = 3501;

	// int count = 0;
	public SendThread() {

	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		// Iterator<String> it = null;
		Iterator iterator2;
		LinkedList<OneOfList> list;
		Set key;

		StringBuilder lat = new StringBuilder();
		StringBuilder lon = new StringBuilder();
		String keyName = "";

		OneOfList data = new OneOfList();
		for (; SC.stopThread;) {
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (end - start > delayTime) {

				if (SC.forSurviveDelayTime) {

					while (SC.forSurviveDelayTime) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
				// System.err.println("카운트: "+count);
				// System.out.println("runing " + end + " " + start);
				// do something
				// 해쉬맵에 있는걸 순서대로 받아와서 거기 안에 리스트의 주소 모두에 전송하면된다.
				try {
					if (!SC.clients.isEmpty()) {
						key = SC.clients.keySet();
						for (iterator2 = key.iterator(); iterator2.hasNext();) {
							try {
								keyName = (String) iterator2.next();
								// 여기 keyName으로 레디스에서 값을 찾는다.
							} catch (java.util.ConcurrentModificationException e) {
								System.err.println("익셉션");
								break;
							}

							String[] strArr = RedisModule.GetFromRedis(keyName).split(SC._del);
							/**
							 * 이부분에 레디스 삽입!
							 */
							lat.append(strArr[0]);
							lon.append(strArr[1]);
							// System.out.println("keyName: " + keyName);

							list = SC.clients.get(keyName);
							System.out.println("list size: " + list.size() + "  key name: " + keyName);
							// String lat1 = lat.toString();
							// String lon1 = lon.toString();
							try {
								if (!list.isEmpty()) {

									// for (OneOfList data : list) {
									for (int i = 0; i < list.size(); i++) {

										data = list.get(i);
										// System.out.println(data.GetCtx());//
										// 이 부분에서
										// ctx가
										// 목적지
										// 주소이다.

										data.GetCtx()
												.write(new DatagramPacket(
														Unpooled.copiedBuffer(keyName + SC._del + lat.toString()
																+ SC._del + lon.toString(), CharsetUtil.UTF_8),
														data.GetPacket().sender()));
										// count++;
										System.out.println("보내는 메시지: " + keyName + SC._del + lat.toString() + SC._del
												+ lon.toString() + SC._endSendDel);

										// data.GetCtx()
										// .write(new DatagramPacket(
										// Unpooled.copiedBuffer(keyName +
										// SC._del + lat1 + SC._del + lon1,
										// CharsetUtil.UTF_8),
										// data.GetPacket().sender()));
										// count++;
										// System.out.println("보내는 메시지:
										// "+keyName + SC._del + lat1 + SC._del
										// + lon1 +SC._endSendDel);

										data.GetCtx().flush();

									}
								}
							} catch (NullPointerException e) {
								System.err.println("err");
							}
							lat.setLength(0);
							lon.setLength(0);

						}

					}
				} catch (redis.clients.jedis.exceptions.JedisConnectionException e) {
					e.printStackTrace();
					RedisModule.StartRedis();
					System.err.println("err");
				} 
				start = System.currentTimeMillis();
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			end = System.currentTimeMillis();

		}

	}
}
