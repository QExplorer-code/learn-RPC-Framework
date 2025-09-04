package test;

import rpc.api.HelloService;
import rpc.netty.server.NettyServer;
import rpc.provider.DefaultServiceProvider;
import rpc.provider.ServiceProvider;

public class TestNettyServer {
    public static void main(String[] args) {

        // 服务启动
        NettyServer nettyServer = new NettyServer("127.0.0.1", 9999);

        // 服务提供
        HelloService helloService = new HelloServiceImpl();
        nettyServer.publishService(helloService, HelloService.class);
        nettyServer.start();
    }
}
