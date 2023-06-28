package com.wkk.rpc.core.client;

import com.wkk.rpc.core.codec.RPCRequestBody;
import com.wkk.rpc.core.codec.RPCResponseBody;
import com.wkk.rpc.core.protocol.RPCRequest;
import com.wkk.rpc.core.protocol.RPCResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * 类描述: RPC 代理
 *
 * @author kongweikun@163.com
 * @date 2023/6/28
 */
public class RPCProxy implements InvocationHandler {

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class<?>[]{clazz},
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 1、将调用所需信息编码成bytes[], 构造RPCRequest
        RPCRequestBody rpcRequestBody = RPCRequestBody.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .argTypes(method.getParameterTypes())
                .args(args)
                .build();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(rpcRequestBody);
        byte[] bytes = baos.toByteArray();

        // 2、创建RPC协议，将Header、Body的内容设置好
        RPCRequest request = RPCRequest.builder()
                .header("version=1")
                .body(bytes)
                .build();

        // 3. 协议传输 并获取结果
        RPCTransfer transfer = new RPCTransfer();
        RPCResponse response = transfer.sendRequest(request);

        // 4. 解析协议
        String header = response.getHeader();
        byte[] body = response.getBody();
        if (!Objects.equals("version=1", header)) {
            return null;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(body);
        ObjectInputStream ois = new ObjectInputStream(bais);
        RPCResponseBody responseBody = (RPCResponseBody) ois.readObject();
        return responseBody.getObject();
    }
}
