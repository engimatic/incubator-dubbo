package com.rpc.service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-15 下午5:00
 */
public class MyObjectOutputStream extends ObjectOutputStream {


    public MyObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

}
