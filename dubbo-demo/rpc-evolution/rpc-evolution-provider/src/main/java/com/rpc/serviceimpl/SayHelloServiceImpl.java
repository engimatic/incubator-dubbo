package com.rpc.serviceimpl;

import com.rpc.service.ISayHelloService;

/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-15 下午2:05
 */
public class SayHelloServiceImpl implements ISayHelloService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
