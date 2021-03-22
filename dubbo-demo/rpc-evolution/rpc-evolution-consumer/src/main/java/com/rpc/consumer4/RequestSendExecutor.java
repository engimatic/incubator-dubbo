package com.rpc.consumer4;

import com.google.common.reflect.Reflection;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-22 下午5:17
 */
public class RequestSendExecutor {
    public static <T> T execute(Class<T> rpcInterface) {
        Future<T> future = new FutureTask<>()
        return Reflection.newProxy(rpcInterface, new RequestSendProxy());
    }
}
