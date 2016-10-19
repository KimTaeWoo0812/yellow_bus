package Server_main;

import java.net.InetAddress;
import java.util.Iterator;
import java.util.Set;

import Process.a1_ProcessClass;
import Process.test;
import SubClass.SC;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class a3_Server_Handler extends SimpleChannelInboundHandler<String> {

	a1_ProcessClass process = new a1_ProcessClass();
	//DB Db;
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		SC.countAccept++;//누적 접속인원 체크
//		ctx.write(InetAddress.getLocalHost().getHostName()
//				+ " 서버에 접속 하셨습니다!"+SC._endSendDel);
//		ctx.flush();

	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, String request)
			throws Exception {
		System.out.println("\t\tget Msg: " + request);
		
		String temp = "a";
//		for(int i=0;i<1200;i++){
//			temp+="a";
//		}
			
		
//		ctx.write(temp+SC._endSendDel);
//		ctx.flush();
		
		
		ChannelFuture future = process.DoProcess(ctx, request);

		
		
		// future.addListener(ChannelFutureListener.CLOSE);
	}

	// @Override
	// public void channelRead(ChannelHandlerContext ctx, Object request) throws
	// Exception {
	// // Generate and write a response.
	// System.out.println("들어옴");
	// System.out.println("asdasdasd Msg: "+request);
	//
	// ctx.write(" test 를 받았다");
	// ctx.flush();
	//
	// }
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	// 예외
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		LoginOut(ctx);
		
	}
	protected synchronized void LoginOut(ChannelHandlerContext ctx) {
		Set key = SC.clients.keySet();
		for (Iterator iterator2 = key.iterator(); iterator2.hasNext();) {
			String keyName = (String) iterator2.next();
			if (ctx.equals(SC.clients.get(keyName))) {
				String id = keyName;
				SC.clients.remove(id);
				System.out.println("Logout id: " + id);
				// Db.LoginOut(id);

				ctx.close();

				return;
			}
		}
	}
}
