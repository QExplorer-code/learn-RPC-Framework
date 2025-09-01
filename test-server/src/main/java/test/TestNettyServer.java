package test;

import rpc.api.HelloService;
import rpc.netty.server.NettyServer;
import rpc.registry.DefaultServiceRegistry;
import rpc.registry.ServiceRegistry;

public class TestNettyServer {
    public static void main(String[] args) {
        ServiceRegistry registry = new DefaultServiceRegistry();

        // 服务注册
        HelloService helloService = new HelloServiceImpl();
        registry.register(helloService);

        // 服务启动
        NettyServer server = new NettyServer();
        server.start(9998);
    }
}
