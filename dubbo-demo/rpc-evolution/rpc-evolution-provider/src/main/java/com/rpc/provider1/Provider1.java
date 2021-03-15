package com.rpc.provider1;

import com.rpc.service.ISayHelloService;
import com.rpc.serviceimpl.SayHelloServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-15 下午2:01
 */
public class Provider1 {
    public void provider1() {
        ServerSocket server = null;
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            server = new ServerSocket(18080);

            while (true) {
                Socket socket = server.accept();
                inputStream = new ObjectInputStream(socket.getInputStream());
                String interfaceName = inputStream.readUTF();   //接口名称
                System.out.println("interfaceName from comsumer: " + interfaceName);
                String methodName = inputStream.readUTF();  //方法名称
                System.out.println("methodName from comsumer: " + methodName);
                Class<?>[] parameterTypes = (Class<?>[]) inputStream.readObject();  //参数类型
                System.out.println("parameterTypes from comsumer: " + parameterTypes);
                Object[] parameters = (Object[]) inputStream.readObject();  //参数对象
                System.out.println("Parameters from comsumer: " + Arrays.toString(parameters));
                Class<?> serviceInterfaceClass = Class.forName(interfaceName);
                Object service = new SayHelloServiceImpl(); //1对1  直接new一个对象来使用
                //获取要调用的方法
                Method method = serviceInterfaceClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(service, parameters);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                //将执行结果返回给调用端
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
