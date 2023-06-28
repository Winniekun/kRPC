package com.wkk.rpc.core.server;


import com.wkk.rpc.core.codec.RPCRequestBody;
import com.wkk.rpc.core.codec.RPCResponseBody;
import com.wkk.rpc.core.protocol.RPCRequest;
import com.wkk.rpc.core.protocol.RPCResponse;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Objects;

/**
 * 类描述: 服务端
 *
 * @author kongweikun@163.com
 * @date 2023/6/28
 */
public class RPCWorker implements Runnable {

    private Socket socket;

    private HashMap<String, Object> registeredService;

    public RPCWorker(Socket socket, HashMap<String, Object> registeredService) {
        this.socket = socket;
        this.registeredService = registeredService;
    }


    @Override
    public void run() {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {

            // 1. 获取传输层的结果
            RPCRequest request = (RPCRequest) ois.readObject();

            // 2. 判断协议是否正常
            String header = request.getHeader();
            if (!Objects.equals("version=1", header)) {
                return;
            }
            // 3. 将request中的body部分解码出来变成RpcRequestBody
            byte[] body = request.getBody();
            ByteArrayInputStream bais = new ByteArrayInputStream(body);
            ObjectInputStream oisNew = new ObjectInputStream(bais);
            RPCRequestBody requestBody = (RPCRequestBody) oisNew.readObject();

            // 4. 调用服务
            Object service = registeredService.get(requestBody.getInterfaceName());
            Method method = service.getClass().getMethod(requestBody.getMethodName(), requestBody.getArgTypes());
            Object result = method.invoke(service, requestBody.getArgs());

            RPCResponseBody responseBody = RPCResponseBody.builder()
                    .object(result)
                    .build();

            // 5. 构造 RPCResponseBody
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oosNew = new ObjectOutputStream(baos);
            oosNew.writeObject(responseBody);
            byte[] bytes = baos.toByteArray();

            // 6. 构造 RPCResponse
            RPCResponse response = RPCResponse.builder()
                    .header("version=1")
                    .body(bytes)
                    .build();

            // 7. 发送
            oos.writeObject(response);
            oos.flush();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
