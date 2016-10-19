package SubClass;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;

public class OneOfList {
	ChannelHandlerContext ctx;
	DatagramPacket packet;
	
	public OneOfList(){
		
	}
	public OneOfList(ChannelHandlerContext ctx, DatagramPacket packet){
		this.ctx = ctx;
		this.packet = packet;
	}
	public ChannelHandlerContext GetCtx(){
		return ctx;
	}
	public DatagramPacket GetPacket(){
		return packet;
	}
}
