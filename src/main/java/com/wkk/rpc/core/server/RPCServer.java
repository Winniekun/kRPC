package com.wkk.rpc.core.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * 类描述: 服务端服务
 *
 * @author kongweikun@163.com
 * @date 2023/6/28
 */
public class RPCServer {


    private final ExecutorService threadPool;
    // interfaceName -> interfaceImplementation object
    private final HashMap<String, Object> registeredService;

    public RPCServer() {

        int corePoolSize = 5;
        int maximumPoolSize = 50;
        long keepAliveTime = 60;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        this.threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workingQueue, threadFactory);
        this.registeredService = new HashMap<>();
    }

    public void register(Object service) {
        registeredService.put(service.getClass().getInterfaces()[0].getName(), service);
    }

    /**
     * 启动服务
     * @param port
     */
    public void serve(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("server starting...");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                System.out.println("client connected, ip:" + socket.getInetAddress());
                threadPool.execute(new RPCWorker(socket, registeredService));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
