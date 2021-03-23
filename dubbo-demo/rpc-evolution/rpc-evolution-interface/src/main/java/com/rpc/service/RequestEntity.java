package com.rpc.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

/**
 * @description:
 * @author: tianjin
 * @email: eternity_bliss@sina.cn
 * @create: 2021-03-15 下午2:55
 */
public class RequestEntity implements Serializable {
    private String id;
    private String interfaceName;
    private String methodName;
    private Object[] parameters;

    public RequestEntity(String interfaceName, String methodName, Object[] parameters) {
        this.id = UUID.randomUUID().toString();
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.parameters = parameters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "RequestEntity{" +
                "interfaceName='" + interfaceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
