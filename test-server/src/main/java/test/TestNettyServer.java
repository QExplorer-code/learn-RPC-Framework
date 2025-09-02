package test;

import rpc.api.HelloService;
import rpc.netty.server.NettyServer;
import rpc.provider.DefaultServiceProvider;
import rpc.provider.ServiceProvider;

public class TestNettyServer {
    public static void main(String[] args) {
        ServiceProvider registry = new DefaultServiceProvider();

        // 服务提供
        HelloService helloService = new HelloServiceImpl();
        registry.addServiceProvider(helloService);

        // 服务启动
        NettyServer server = new NettyServer();
        server.start(9998);
    }
}
