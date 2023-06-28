package com.wkk.rpc.idl.hello;

/**
 * 类描述: TODO
 *
 * @author kongweikun@163.com
 * @date 2023/6/28
 */
public interface HelloService {
    HelloResponse hello(HelloRequest request);

    HelloResponse hi(HelloRequest request);
}
