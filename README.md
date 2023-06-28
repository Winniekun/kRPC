# kRPC
> 最近在学习 GO，自然就看到了 RPC 部分内容，然后就尝试用 Java 撸一个简单 RPC 框架


# 实现流程

## 第一步：写IDL文件
IDL.Hello的内容直接编写完毕。

按照grpc的方式，编写接口HelloService，以及里面的消息体HelloRequest和HelloResponse，客户端和服务器都使用这同一套接口

## 第二步：编写RPC协议
RpcRequest和RpcResponse都是RPC协议，RPC协议包括header和body两部分，header我们用String表示，body我们用序列化后的byte[]流表示，这里的字节流的序列化的方式可以是Java的序列化方式，可以换成JSON序列化方式，也可以用Thrift、PB序列化方式，为了简单，我们这次直接用Java的序列化方式。

然后body中被序列化的内容，因为是codec层的工作，放在了codec包中，RPCReuqest要调用一个方法，需要知道接口名、方法名、参数、参数类型，因此把这些东西放进RpcRequestBody中即可，后面把它序列化后房价RpcRequest的body字节流中；同理，RPCResponse 的body中，只需要一个被序列化后的Java Object即可。

## 第三步：分析
我们的rpcClient要执行一个函数hello，传入参数HelloRequest，然后返回HelloResponse。

整个流程就是：客户端获得rpcService对象，使用rpcService对象执行hello方法，那么rpcService底层实现就发送一条RpcRequest协议（对比HTTP协议）把：要执行的接口名+方法名+参数类型+具体参数序列化后，放进RpcRequest协议的body字节流中，然后给RpcRequest加上header，发给服务端，服务端解析出Rpc协议的body（对比HTTP协议解析body）中的接口名、方法名等，直接调用本地的接口的实现，然后将返回值包装成一条RpcResponse消息，发送给客户端即可，rpcService底层将该response消息解析，从body中拿到（也是对比HTTP解析body）返回值，然后返回给客户端。

## 第四步：客户端实现（动态代理）
客户端方面，客户端本地只有IDL.Hello中的内容，没有方法的具体实现，也就是说要调用一个没有实现的接口，显然，我们使用Java反射的动态代理特性，实例化一个接口，将调用接口方法“代理”给InvocationHandler中的invoke来执行，在Invoke中获取到接口名、方法名等包装成Rpc协议，发送给服务端，然后等待服务端返回。

## 第五步：服务端实现（反射调用）
服务端方面，本地需要实现接口的方法，然后在启动监听网络之前注册所有的接口，当消息到来的时候，根据RpcRequestBody中的接口名拿到接口对象，然后用反射的方式调用即可，将调用结果包装成RpcResponse，发送给客户端。

## 第六步：测试
编写测试用例

一共两个接口，HelloService和PingService，HelloService有两个方法：hello和hi，PingService有一个方法：ping