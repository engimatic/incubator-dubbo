package com.rpc;

import com.rpc.provider1.Provider1;
import com.rpc.provider2.Provider2;
import com.rpc.provider3.NettyServer;
import com.rpc.provider3.Provider3;
import com.rpc.provider4.Provider4;

/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-15 下午2:11
 */
public class ProviderApplication {
    public static void main(String[] args) {
        provider4();
    }

    private static void provider1(){
        Provider1 provider1 = new Provider1();
        provider1.provider1();
    }

    private static void provider2(){
        Provider2 provider2 = new Provider2();
        provider2.provider2();
    }

    private static void provider3(){
        Provider3 provider3 = new Provider3();
        provider3.provider3();
    }

    private static void provider4(){
        Provider4 provider4 = new Provider4();
        provider4.provider4();
    }

}
