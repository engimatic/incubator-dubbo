package com.rpc.provider3;

import com.rpc.service.MyThreadFactory;
import com.rpc.service.RequestEntity;
import com.rpc.serviceimpl.SayHelloServiceImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.nio.channels.spi.SelectorProvider;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;

/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-19 下午1:36
 */
public class NettyServer {

    public static NettyServer getInstance(){
        return NettyServerHolder.nettyServer;
    }

    private NettyServer() {
    }

    private static class NettyServerHolder{
        private static final NettyServer nettyServer = new NettyServer();
    }


    public void init() {
        int port = 18080;
        Map<String, Object> handlerMap = new ConcurrentHashMap<>();
        handlerMap.put("com.rpc.service.ISayHelloService", new SayHelloServiceImpl());

        ThreadFactory threadRpcFactory = new MyThreadFactory("NettyRPC ThreadFactory");
        int parallel = Runtime.getRuntime().availableProcessors() * 2;
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup(parallel, threadRpcFactory, SelectorProvider.provider());
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0,
                                    4));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                            pipeline.addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    RequestEntity requestEntity = (RequestEntity) msg;
                                    Object service = handlerMap.get(requestEntity.getInterfaceName());
                                    Object result = MethodUtils.invokeMethod(service, requestEntity.getMethodName(), requestEntity.getParameters());
                                    ctx.writeAndFlush(result);
                                }
                            } );
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind("0.0.0.0", port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
