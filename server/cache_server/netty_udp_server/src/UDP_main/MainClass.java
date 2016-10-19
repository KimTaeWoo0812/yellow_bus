package UDP_main;

import SubClass.SC;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class MainClass {
	private static final int PORT = Integer.parseInt(System.getProperty("port", "5001"));

    public static void main(String[] args) throws Exception {
    	SC.SetData();
        EventLoopGroup group = new NioEventLoopGroup(4);
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioDatagramChannel.class)
             .handler(new Server_Handler());
            
            System.out.println("main");
            
            ChannelFuture future = b.bind(PORT).sync();
            future.channel().closeFuture().sync();
            //b.bind(PORT).sync().channel().closeFuture().await();
        } finally {
        	 System.out.println("end");
            group.shutdownGracefully();
        }
    }
}


