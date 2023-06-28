package server;

import com.wkk.rpc.idl.hello.HelloRequest;
import com.wkk.rpc.idl.hello.HelloResponse;
import com.wkk.rpc.idl.hello.HelloService;

/**
 * 类描述: TODO
 *
 * @author kongweikun@163.com
 * @date 2023/6/28
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public HelloResponse hello(HelloRequest request) {
        String name = request.getName();
        String retMsg = "hello: " + name;
        HelloResponse response = new HelloResponse(retMsg);
        return response;
    }

    @Override
    public HelloResponse hi(HelloRequest request) {
        String name = request.getName();
        String retMsg = "hi: " + name;
        HelloResponse response = new HelloResponse(retMsg);
        return response;
    }
}
