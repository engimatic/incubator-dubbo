package com.rpc.service;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-15 下午2:55
 */
public class ResponseEntity implements Serializable {
    private String messageId;
    private Integer code;
    private String msg;
    private Object res;

    private ResponseEntity() {
    }

    public ResponseEntity(String messageId, Integer code, String msg, Object res) {
        this.messageId = messageId;
        this.code = code;
        this.msg = msg;
        this.res = res;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getRes() {
        return res;
    }

    public void setRes(Object res) {
        this.res = res;
    }

    public static ResponseEntity ok(String messageId, Object object){
        return new ResponseEntity(messageId, 200,"success", object);
    }

}
