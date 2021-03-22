package com.rpc.consumer4;

import com.rpc.consumer3.NettyClientHandler;
import com.rpc.service.ISayHelloService;
import com.rpc.service.RequestEntity;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;


/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-22 下午4:15
 */
public class NettyClient {

    private RequestSendHandler requestSendHandler;

    public static NettyClient getInstance(){
        return NettyClientHolder.instance;
    }

    private static class NettyClientHolder{
        private static final NettyClient instance = new NettyClient();
    }

    private NettyClient() {
        this.requestSendHandler = new RequestSendHandler();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        String host = "0.0.0.0";
        int port = 18080;
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.group(eventLoopGroup);
            bootstrap.remoteAddress(host, port);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel)
                        throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0,
                            4));
                    pipeline.addLast(new LengthFieldPrepender(4));
                    pipeline.addLast(new ObjectEncoder());
                    pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                    pipeline.addLast(requestSendHandler);
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            if (channelFuture.isSuccess()) {
                System.err.println("connect success!");
            }
            Channel channel = channelFuture.channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public RequestSendHandler getRequestSendHandler() {
        return requestSendHandler;
    }
}
