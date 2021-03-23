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

    private Map<String, MessageCallBack> pool;

    public RequestSendHandler(Map<String, MessageCallBack> pool) {
        this.pool = pool;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ResponseEntity response = (ResponseEntity) msg;
        String messageId = response.getId();
        MessageCallBack callBack = pool.get(messageId);
        if (callBack != null) {
            pool.remove(messageId);
            callBack.over(response);
        }
    }


}
