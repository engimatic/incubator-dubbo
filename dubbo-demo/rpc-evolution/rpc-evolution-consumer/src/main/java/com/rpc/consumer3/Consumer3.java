package com.rpc.consumer3;


import com.rpc.service.ISayHelloService;
import com.rpc.service.RequestEntity;
import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/**
 * @description: 消费者1代
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-15 下午1:41
 */
public class Consumer3 {

    public void consumer3() {
        Socket socket = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        String interfaceName = ISayHelloService.class.getName();
        try {
            Method method = ISayHelloService.class.getMethod("sayHello", String.class);
            Object[] agrs = {"world"};
            socket = new Socket("127.0.0.1", 18080);
            RequestEntity entity = new RequestEntity(interfaceName, method.getName(), agrs);

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(entity);

            inputStream = new ObjectInputStream(socket.getInputStream());
            Object result = inputStream.readObject();
            System.out.println("Consumer result:" + result);
        } catch (NoSuchMethodException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void consumer2plus1() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int threadCount = 10;
        CountDownLatch end = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            Runnable runnable = doSomeThing1(i, end);
            new Thread(runnable).start();
        }
        try {
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }


    private Runnable doSomeThing1(int i, CountDownLatch end) {
        return () -> {
            Socket socket = null;
            ObjectOutputStream outputStream = null;
            ObjectInputStream inputStream = null;
            String interfaceName = ISayHelloService.class.getName();
            try {
                Method method = ISayHelloService.class.getMethod("sayHello", String.class);
                Object[] agrs = {"world" + i};
                socket = new Socket("127.0.0.1", 18080);
                RequestEntity entity = new RequestEntity(interfaceName, method.getName(), agrs);

                outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(entity);

                inputStream = new ObjectInputStream(socket.getInputStream());
                Object result = inputStream.readObject();
                System.out.println("Consumer result " + i + ": " + result);
                end.countDown();

            } catch (NoSuchMethodException | IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
