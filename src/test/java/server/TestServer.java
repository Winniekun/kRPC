package server;

import com.wkk.rpc.core.server.RPCServer;
import com.wkk.rpc.idl.hello.HelloService;

/**
 * 类描述: TODO
 *
 * @author kongweikun@163.com
 * @date 2023/6/28
 */
public class TestServer {

    public static void main(String[] args) {
        RPCServer rpcServer = new RPCServer(); // 真正的rpc server
        HelloService helloService = new HelloServiceImpl(); // 包含需要处理的方法的对象
        rpcServer.register(helloService); // 向rpc server注册对象里面的所有方法
        rpcServer.serve(9000);
    }
}
