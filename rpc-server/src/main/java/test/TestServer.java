package test;

import rpc.api.CalculateService;
import rpc.api.HelloService;
import rpc.registry.DefaultServiceRegistry;
import rpc.socket.server.SocketServer;

public class TestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        DefaultServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);

        CalculateService calculateService = new CalculateServiceImpl();
        serviceRegistry.register(calculateService);

        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.start(9999);
    }
}
