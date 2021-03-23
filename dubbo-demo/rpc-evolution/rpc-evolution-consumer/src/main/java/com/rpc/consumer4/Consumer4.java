package com.rpc.consumer4;

import com.rpc.service.ISayHelloService;

import java.util.concurrent.*;

/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-23 上午10:37
 */
public class Consumer4 {
    public void consumer4(){
        int num = 10;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(num);
        ExecutorService executorService = Executors.newFixedThreadPool(num);
        for(int i = 0; i < num; i++){
            int finalI = i;
            Runnable runnable = () -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                ISayHelloService helloService = RequestSendExecutor.execute(ISayHelloService.class);
                String result = helloService.sayHello("name" + finalI);
                System.out.println(finalI + "----" + result);
            };
//            new Thread(runnable).start();
            executorService.execute(runnable);
        }
        executorService.shutdown();
    }
}
