package com.rpc.provider4;


/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-15 下午2:01
 */
public class Provider4 {
    public void provider4() {
        NettyServer nettyServer = NettyServer.getInstance();
        nettyServer.init();
    }
}
