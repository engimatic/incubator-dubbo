package com.rpc;

import com.rpc.provider1.Provider1;
import com.rpc.provider2.Provider2;
import com.rpc.provider3.Provider3;

/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-15 下午2:11
 */
public class ProviderApplication {
    public static void main(String[] args) {
        provider3();
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
}
