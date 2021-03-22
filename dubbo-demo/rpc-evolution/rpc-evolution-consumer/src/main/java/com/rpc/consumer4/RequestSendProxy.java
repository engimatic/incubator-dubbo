package com.rpc.consumer4;

import com.google.common.reflect.AbstractInvocationHandler;
import com.rpc.service.RequestEntity;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-22 下午4:04
 */
public class RequestSendProxy extends AbstractInvocationHandler {
    @Override
    protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        RequestEntity request = new RequestEntity(method.getDeclaringClass().getName(),method.getName(), args);
        RequestSendHandler sendHandler = NettyClient.getInstance().getRequestSendHandler();
        return sendHandler.sendRequest(request);
    }
}
