package Process;

import java.util.LinkedList;

import SubClass.OneOfList;
import SubClass.SC;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public class FunctionGather {
	
	String strMsg = "";

	public FunctionGather() {
		// 이제 레디스 연결하고 이 클래스 정리하고,
		// 주기적으로 보내는 로직 작성
		
		RedisModule redis = new RedisModule();
		redis.StartRedis();
		SC.Print0("DB 접속 from a2_FunctionGather");
	}

	private synchronized void SendMsg(ChannelHandlerContext ctx, String msg) {
		// ByteBuf buffer = Unpooled.buffer(1023);
		// byte[] str = msg.getBytes();
		System.out.println("보내는 메시지: " + msg);
		ctx.write(msg + "\n");
		ctx.flush();
		// buffer.writeBytes(msg.getBytes());
		// ctx.writeAndFlush(buffer);
	}

	// 관리자에게 위치를 받는다 GIVE#학원명!차 별명#lat#lon#
	public synchronized void GetCarLocated(ChannelHandlerContext ctx, String name, String lat, String lon) {

		if (!SC.clients.containsKey(name)) { // 학원이 맵에 없다면 추가
			LinkedList<OneOfList> list = new LinkedList<OneOfList>();
			SC.clients.put(name, list);
			System.out.println("차량 추가: "+name);
		}
		else{
			System.out.println("이미 있는 차량: "+name);
		}
		System.out.println("차량 운행 시작 or 현재 위치: " + lat + " " + lon);
		// 레디스에 값을 넣는다. key는 name
		RedisModule.SaveToRedis(name, lat+SC._del+lon);
		

	}

	// 관리자 운행 종료 시 DONE#학원명#
	public synchronized void DoneCarLocated(ChannelHandlerContext ctx, String name) {

		System.out.println("111111  " + name);
		System.out.println("2222222 " + SC.clients.containsKey(name));
		if (SC.clients.containsKey(name)) { // 학원이 맵에 있다면
			SC.clients.remove(name);
			System.out.println("차량 운행 종료 : " + name);
			RedisModule.Del_key(name);
		} else {
			// 없다면, 맵을 전부 검사해서 정리한다. or 무시
			System.out.println("차량 운행 종료 에러!! : " + name);
		}

	}

	// 학생이 차량 위치를 묻는다 GAT#학원명
	public synchronized void StartCarLocated(ChannelHandlerContext ctx, DatagramPacket packet, String name) {
		if (RedisModule.HasExists(name)) { // 학원이 있다면
			LinkedList<OneOfList> list = SC.clients.get(name); // 학원의 접속 회원 리스트를

			OneOfList data = new OneOfList(ctx, packet);
			try {
				if (!list.contains(data)) { // 리스트에 이 회원이 없다면
					list.add(data); // 회원을 추가한다.
					SC.clients.put(name, list);
					SC.Print0("학생 추가!");
				}

				// 여기에 레디스에서 위치를 가져와서 보내주는 로직 구현
				// System.out.println("학생 차량 위치 요구 : " + name);
				else {
					// 삽입 못하는 상황. 메시지 전송
					System.out.println("차량이 아직 없음 : " + name);
				}

			} catch (java.lang.NullPointerException e) {

			}
			// TODO: handle exception
		}

	}

	// 학생이 차량 위치를 다시 묻는다 GAT#학원명
	public synchronized void ReStartCarLocated(ChannelHandlerContext ctx, DatagramPacket packet, String name) {

		if (RedisModule.HasExists(name)) { // 학원이 있다면
			LinkedList<OneOfList> list = SC.clients.get(name); // 학원의 접속 회원 리스트를
																// 가져와서

			OneOfList data = new OneOfList(ctx, packet);

			if (!list.contains(data)) { // 리스트에 이 회원이 없다면
				list.add(data); // 회원을 추가한다.
				SC.clients.put(name, list);
			}

			// 여기에 레디스에서 위치를 가져와서 보내주는 로직 구현
			System.out.println("학생 차량 위치 요구 : " + name);
		} else {
			// 삽입 못하는 상황. 메시지 전송
			System.out.println("차량이 아직 없음 : " + name);
		}
	}

	// 학생이 맵 그만볼때 STOP#학원명
	public synchronized void StopCarLocated(ChannelHandlerContext ctx, DatagramPacket packet, String name) {

		if (SC.clients.containsKey(name)) { // 학원이 있다면
			LinkedList<OneOfList> list = SC.clients.get(name); // 학원의 접속 회원 리스트를
									
			OneOfList data = new OneOfList(ctx, packet);

			/*
			 * 비교를  ctx로 해야 한다. for 문으로 size 만큼 돌면서 검사해서 같은걸 찾아야한다
			 */
//			OneOfList temp = list.getFirst();
//
//			System.out.println("========================test=====");
//			System.out.println(temp);
//			System.out.println(data);
//			System.out.println(temp.GetCtx());
//			System.out.println(ctx);
//			System.out.println("========================test=====");
			
			boolean out = true;
			for(int i=0; i<list.size();i++){
				if(list.get(i).GetCtx().equals(ctx)){
					list.remove(data);
					SC.clients.put(name, list);
					System.out.println("학생 서스비 끝 : " + name);
					out = false;
					break;
				}
			}
			if(out){
				// 회원이 리스트에 없다. 잘못된 상황 메시지 전송 or 무시
				System.out.println("학생 서스비 끝 / 이미 삭제됬음 : " + name);
			}
		} else {
			// 학원이 없다. 잘못된 상황 메시지 전송 or 무시
			System.out.println("학생 서스비 끝 / 학원이 없다. 에러: " + name);
		}

	}
	
//	public synchronized void LIVE(ChannelHandlerContext ctx, DatagramPacket packet, String name) {
//		if (RedisModule.HasExists(name)) { // 학원이 있다면
//			LinkedList<OneOfList> list = SC.checkClients.get(name); // 학원의 접속 회원 리스트를
//																// 가져와서
//
//			OneOfList data = new OneOfList(ctx, packet);
//			data.SetCheck(SC.liveCheck);
//			if (list.contains(data)) { // 리스트에 이 회원이 있다면
//				data.SetCheck(SC.liveCheck);
//				list.add(data); // 회원을 추가한다.
//				SC.clients.put(name, list);
//			}
//
//			// 여기에 레디스에서 위치를 가져와서 보내주는 로직 구현
//			System.out.println("LIVE : " + name);
//		} else {
//			// 삽입 못하는 상황. 메시지 전송
//			System.out.println("LIVE 실패 : " + name);
//		}
//	}

}
