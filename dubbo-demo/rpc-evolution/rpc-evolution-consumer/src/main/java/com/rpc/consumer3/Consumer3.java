package com.rpc.consumer3;


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
import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * @description: 消费者1代
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-15 下午1:41
 */
public class Consumer3 {

    public void consumer3() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        String host = "0.0.0.0";
        int port = 18080;

        try {
            String interfaceName = ISayHelloService.class.getName();

            Method method = ISayHelloService.class.getMethod("sayHello", String.class);
            Object[] agrs = {"world"};
            RequestEntity entity = new RequestEntity(interfaceName, method.getName(), agrs);

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
                    pipeline.addLast(new NettyClientHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            if (channelFuture.isSuccess()) {
                System.err.println("connect success!");
            }
            Channel channel = channelFuture.channel();
            channel.writeAndFlush(entity);
            channel.closeFuture().sync();
        } catch (InterruptedException | NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }
}
