package UDP_main;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final Server_Handler SERVER_HANDLER = new Server_Handler();

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //\n 구분자
        pipeline.addLast(new DelimiterBasedFrameDecoder(256, Delimiters.lineDelimiter()));

        pipeline.addLast(SERVER_HANDLER);
    }
}
