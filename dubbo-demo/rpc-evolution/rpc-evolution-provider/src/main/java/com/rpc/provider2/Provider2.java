package com.rpc.provider2;

import com.rpc.service.RequestEntity;
import com.rpc.serviceimpl.SayHelloServiceImpl;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-15 下午2:01
 */
public class Provider2 {
    public void provider2() {
        Map<String,Object> serviceMap = new HashMap<>();
        serviceMap.put("com.rpc.service.ISayHelloService", new SayHelloServiceImpl());
        ServerSocket server = null;
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            server = new ServerSocket(18080);

            while (true) {
                Socket socket = server.accept();
                inputStream = new ObjectInputStream(socket.getInputStream());

                RequestEntity requestEntity = (RequestEntity) inputStream.readObject();
                System.out.println("provider2 entity:" + requestEntity);
                Object service = serviceMap.get(requestEntity.getInterfaceName());

                Object result = MethodUtils.invokeMethod(service, requestEntity.getMethodName(), requestEntity.getParameters());

                outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(result);
            }
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (server != null) {
                    server.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
