package com.rpc.provider3;

/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-15 下午2:01
 */
public class Provider3 {
    public void provider3() {
        NettyServer nettyServer = NettyServer.getInstance();
        nettyServer.init();
    }
}
