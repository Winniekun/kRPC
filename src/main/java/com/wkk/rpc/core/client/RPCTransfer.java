package com.wkk.rpc.core.client;

import com.wkk.rpc.core.protocol.RPCRequest;
import com.wkk.rpc.core.protocol.RPCResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 类描述: 转换，传入 RPCRequest 输出 RPCResponse
 *
 * @author kongweikun@163.com
 * @date 2023/6/28
 */
public class RPCTransfer {

    public RPCResponse sendRequest(RPCRequest request) {
        try (Socket socket = new Socket("localhost", 9000)) {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            oos.writeObject(request);
            oos.flush();

            RPCResponse response = (RPCResponse) ois.readObject();
            return response;


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
