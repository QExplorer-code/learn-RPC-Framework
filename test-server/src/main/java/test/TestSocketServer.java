package test;

import rpc.api.CalculateService;
import rpc.api.HelloService;
import rpc.provider.DefaultServiceProvider;
import rpc.socket.server.SocketServer;

public class TestSocketServer {
    public static void main(String[] args) {
        // 服务提供
        HelloService helloService = new HelloServiceImpl();
        CalculateService calculateService = new CalculateServiceImpl();

        // 服务启动
        SocketServer socketServer = new SocketServer("127.0.0.1", 9999);
        socketServer.publishService(helloService, HelloService.class);
        socketServer.publishService(calculateService, CalculateService.class);
        socketServer.start();
    }
}
