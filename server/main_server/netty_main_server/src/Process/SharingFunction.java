package Process;

import java.util.ArrayList;
import java.util.Iterator;

import SubClass.SC;
import io.netty.channel.ChannelHandlerContext;

public class SharingFunction {
	protected synchronized void sendToAll(String message) {
		Iterator<String> it = SC.clients.keySet().iterator();

		System.out.println("전체 메시지: " + message);
		while (it.hasNext()) {
				ChannelHandlerContext ctx = SC.clients.get(it.next());
				// SocketChannel sc = process.clients.get(it.next());
				ctx.write(message+SC._endSendDel);
				ctx.flush();
		}
	}

	protected synchronized void SendMsg(ChannelHandlerContext ctx, String msg) {
		// ByteBuf buffer = Unpooled.buffer(1023);
		// byte[] str = msg.getBytes();
		SC.Print0("보내는 메시지: " + msg);
		ctx.write(msg+SC._endSendDel);
		ctx.flush();
		// buffer.writeBytes(msg.getBytes());
		// ctx.writeAndFlush(buffer);
	}
	protected synchronized void SendMsg_Before_Process_List(ChannelHandlerContext ctx, ArrayList<StringBuilder> val, String query) {
		try {
			for (int i = 0; i < val.size(); i++) {
				if (!val.equals(null))
					SendMsg(ctx, query + SC._del + val.get(i).toString());
				else
					SendMsg(ctx, query + SC._del + 0);
			}
		} catch(NullPointerException e){
			e.printStackTrace();
		}
	}
	protected synchronized void SendMsg_Before_Process_List22(ChannelHandlerContext ctx, ArrayList<StringBuilder> val, String query) {
		try {
			for (int i = 0; i < val.size(); i++) {
				if (!val.equals(null))
					SendMsg(ctx, query + SC._del + val.get(i).toString());
				else
					SendMsg(ctx, query + SC._del + 0);
			}
		} catch(NullPointerException e){
			e.printStackTrace();
		}
	}
	protected synchronized void SendMsg_Before_Process_List2(ChannelHandlerContext ctx, ArrayList<StringBuilder> val) {
		try {
			for (int i = 0; i < val.size(); i++) {
				if (!val.equals(null))
					SendMsg(ctx, val.get(i).toString());
				else
					SendMsg(ctx,"0");
			}
		} catch(NullPointerException e){
			e.printStackTrace();
		}
	}
}
