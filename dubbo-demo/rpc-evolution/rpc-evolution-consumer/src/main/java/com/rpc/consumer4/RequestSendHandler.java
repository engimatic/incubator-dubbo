package com.rpc.consumer4;

import com.rpc.service.RequestEntity;
import com.rpc.service.ResponseEntity;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-22 下午4:14
 */
public class RequestSendHandler extends ChannelInboundHandlerAdapter {
    private volatile ChannelHandlerContext ctx;

    private Map<String, MessageCallBack> pool = new ConcurrentHashMap<>();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.ctx = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ResponseEntity response = (ResponseEntity) msg;
        String messageId = response.getMessageId();
        MessageCallBack callBack = pool.get(messageId);
        if (callBack != null) {
            mapCallBack.remove(messageId);
            callBack.over(response);
        }
    }

    public ResponseEntity sendRequest(RequestEntity requestEntity) throws ExecutionException, InterruptedException {
        ctx.writeAndFlush(requestEntity);
    }
}
