package test;

import rpc.api.CalculateService;
import rpc.api.HelloService;
import rpc.registry.DefaultServiceRegistry;
import rpc.socket.server.SocketServer;

public class TestSocketServer {
    public static void main(String[] args) {
        DefaultServiceRegistry serviceRegistry = new DefaultServiceRegistry();

        // 服务注册
        HelloService helloService = new HelloServiceImpl();
        serviceRegistry.register(helloService);

        CalculateService calculateService = new CalculateServiceImpl();
        serviceRegistry.register(calculateService);

        // 服务启动
        SocketServer socketServer = new SocketServer();
        socketServer.start(9999);
    }
}
