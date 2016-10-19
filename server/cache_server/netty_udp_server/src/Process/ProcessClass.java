package Process;

import java.util.LinkedList;

import SubClass.OneOfList;
import SubClass.SC;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public class ProcessClass {

	FunctionGather gather = new FunctionGather();
	
	public ProcessClass(){
		
	}
	
	public synchronized ChannelFuture DoProcess(ChannelHandlerContext ctx, DatagramPacket packet, String strMsg){
		String msg[] = strMsg.split(SC._del);
		
		int nVal = 0;
		switch (msg[0]) {
		case "test":
			SC.Print0("test");
			break;
			
		case "GET": //관리자에게 위치를 받는다 GET#학원명!차 별명#lat#lon#
						//레디스에 학원명#차별명을 키값으로 저장한다. 클라이언트가 차별명을 알기위해선
						//클라이언트가 학원을 선택했을때 차별명도 알수 있어야 한다.
			SC.Print0("GET");
			gather.GetCarLocated(ctx, msg[1], msg[2], msg[3]);
			break;
		case "DONE": //관리자 운행 종료 시  DONE#학원명#
			SC.Print0("DONE");
			gather.DoneCarLocated(ctx, msg[1]);
			break;
			
		case "START": //학생이 차량 위치를 묻는다 START#학원명
			SC.Print0("START");
			gather.StartCarLocated(ctx, packet, msg[1]);

//			LinkedList<OneOfList> list = SC.clients.get("1번차");
//			System.out.println("START: "+list.getFirst().GetCtx());
			break;
		case "RESTART": //학생이 차량 위치를 re 묻는다 START#학원명
			SC.Print0("RESTART");
			gather.ReStartCarLocated(ctx, packet, msg[1]);
			//gather.StopCarLocated(ctx, msg[1]);
			break;
		case "STOP": //학생이 맵 그만볼때 STOP#학원명
			SC.Print0("STOP");
			gather.StopCarLocated(ctx, packet, msg[1]);
			break;
//		case "LIVE": //생존 체크 LIVE#학원명
//			System.out.println("LIVE");
//			gather.LIVE(ctx, packet, msg[1]);
//			break;
		default:
			SC.Print0("default");
			//ctx.close();
			break;
		}
		
		return null;
		
	}
	
	
}
