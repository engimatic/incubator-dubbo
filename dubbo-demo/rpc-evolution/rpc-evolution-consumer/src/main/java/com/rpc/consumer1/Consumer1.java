package com.rpc.consumer1;


import com.rpc.service.ISayHelloService;
import com.rpc.service.RequestEntity;

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
public class Consumer1 {
    public void consumer1() {
        Socket socket = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        String interfaceName = ISayHelloService.class.getName();
        try {
            Method method = ISayHelloService.class.getMethod("sayHello", String.class);
            Object[] agrs = {"world"};
            socket = new Socket("127.0.0.1", 18080);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeUTF(interfaceName);   //接口名称
            outputStream.writeUTF(method.getName());    //方法名称
            outputStream.writeObject(method.getParameterTypes());
            outputStream.writeObject(agrs);

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



}
