package test;

import rpc.api.CalculateService;
import rpc.api.HelloService;
import rpc.provider.DefaultServiceProvider;
import rpc.socket.server.SocketServer;

public class TestSocketServer {
    public static void main(String[] args) {
        DefaultServiceProvider serviceRegistry = new DefaultServiceProvider();

        // 服务提供
        HelloService helloService = new HelloServiceImpl();
        serviceRegistry.addServiceProvider(helloService);

        CalculateService calculateService = new CalculateServiceImpl();
        serviceRegistry.addServiceProvider(calculateService);

        // 服务启动
        SocketServer socketServer = new SocketServer();
        socketServer.start(9999);
    }
}
