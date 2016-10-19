package UDP_main;

import java.net.InetAddress;

import Process.ProcessClass;
import SubClass.SC;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

@Sharable
public class Server_Handler extends SimpleChannelInboundHandler<DatagramPacket> {

	 ProcessClass process = new ProcessClass();
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.write(InetAddress.getLocalHost().getHostName() + " 서버에 접속 하셨습니다!\r\n");
		ctx.flush();

	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
		String request = packet.content().toString(CharsetUtil.UTF_8);
		
		
		SC.Print0("\t\tget: " + request);
//		System.out.println(packet);

		 ChannelFuture future = process.DoProcess(ctx, packet, request);

//		 ctx.write(new DatagramPacket(Unpooled.copiedBuffer("서버측 받은 메시지: "+request,CharsetUtil.UTF_8), packet.sender()));
//		 ctx.flush();
//		 for(int i=0;i<100;i++){
//			 try {
//				Thread.sleep(300);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			 ctx.write(new DatagramPacket(Unpooled.copiedBuffer("서버측 받은 메시지: "+request,CharsetUtil.UTF_8), packet.sender()));
//			 ctx.flush();
//		 }
//		 try {
//			 System.out.println("asd'");
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 
//		 ctx.write(new DatagramPacket(Unpooled.copiedBuffer("222",CharsetUtil.UTF_8), packet.sender()));
//		 ctx.flush();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	// 예외
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();

	}
}
