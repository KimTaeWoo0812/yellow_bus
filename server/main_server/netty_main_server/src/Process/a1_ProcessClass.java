package Process;

import SubClass.LogManager;
import SubClass.SC;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

public class a1_ProcessClass {

	a2_FunctionGather gather = new a2_FunctionGather();
	
	
	public synchronized ChannelFuture DoProcess(ChannelHandlerContext ctx, String strMsg){
		String msg[] = strMsg.split(SC._del);
		//SC.Print0("\t\tget2: "+msg[0]);
		
		
		for(int i=0;i<LogManager.LogTableName.size();i++)
			System.out.println("1111111111111111111111111111111111111111111111111111111111111111111111   "+LogManager.LogTableName.get(i));
		
		
		switch (msg[0]) {
		case "test":
			System.out.println("test");
			gather.sendToAll(msg[1]);
			break;
		case "RODING":
			gather.RODING(ctx);
			break;
		case "JOINUS":
			gather.JOINUS(ctx, msg);
			break;
		case "IDCHECK":
			gather.IDCHECK(ctx, msg);
			break;
		case "LOGIN":
			gather.LOGIN(ctx, msg);
			break;
		case "LOGOUT":
			gather.LOGOUT(ctx);
			break;
		case "S_LIST":
			gather.S_LIST(ctx, msg);
			break;
		case "S_INFO":
			gather.S_INFO(ctx, msg);
			break;
		case "S_CAR":
			gather.S_CAR(ctx, msg);
			break;
		case "S_BEACON":
			gather.S_BEACON(ctx, msg);
			break;
			
		case "LOG":
			gather.LOG(ctx, msg);
			break;
		case "L_LIST_TO_S":
			gather.L_LIST_TO_S(ctx, msg);
			break;
		case "L_LIST_TO_P":
			gather.L_LIST_TO_P(ctx, msg);
			break;
		case "M_NOTICE":
			gather.M_NOTICE(ctx, msg);
			break;
		case "M_VIEW_MEMBER":
			gather.M_VIEW_MEMBER(ctx, msg);
			break;

		default:
			SC.Print0("잘못된 명령어!");
			break;
		}
		return null;
		
		
		
	}
}
