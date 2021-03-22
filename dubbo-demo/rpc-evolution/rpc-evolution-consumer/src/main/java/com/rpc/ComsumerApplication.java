package com.rpc;

import com.rpc.consumer1.Consumer1;
import com.rpc.consumer2.Consumer2;
import com.rpc.consumer3.Consumer3;

/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-15 下午2:00
 */
public class ComsumerApplication {
    public static void main(String[] args) {
        consumer3();
    }

    public static void consumer1() {
        Consumer1 consumer1 = new Consumer1();
        consumer1.consumer1();
    }

    public static void consumer2() {
        Consumer2 consumer2 = new Consumer2();
        consumer2.consumer2();
    }

    public static void consumer2plus1() {
        Consumer2 consumer2 = new Consumer2();
        consumer2.consumer2plus1();
    }
    public static void consumer3() {
        Consumer3 consumer3 = new Consumer3();
        consumer3.consumer3();
    }

}
