package clien;

import com.wkk.rpc.core.client.RPCProxy;
import com.wkk.rpc.idl.hello.HelloRequest;
import com.wkk.rpc.idl.hello.HelloResponse;
import com.wkk.rpc.idl.hello.HelloService;

/**
 * 类描述: TODO
 *
 * @author kongweikun@163.com
 * @date 2023/6/28
 */
public class TestClient {

    public static void main(String[] args) {
        // 获取RpcService
        RPCProxy proxy = new RPCProxy();
        HelloService helloService = proxy.getService(HelloService.class);
        // 构造出请求对象HelloRequest
        HelloRequest helloRequest = new HelloRequest("tom");
        // rpc调用并返回结果对象HelloResponse
        HelloResponse helloResponse = helloService.hello(helloRequest);
        // 从HelloResponse中获取msg
        String helloMsg = helloResponse.getMsg();
        // 打印msg
        System.out.println(helloMsg);

        // 调用hi方法
        HelloResponse hiResponse = helloService.hi(helloRequest);
        String hiMsg = hiResponse.getMsg();
        System.out.println(hiMsg);
    }
}
