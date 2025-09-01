import rpc.api.HelloObject;
import rpc.api.HelloService;
import rpc.common.RpcClient;
import rpc.common.RpcClientProxy;
import rpc.netty.client.NettyClient;

public class TestNettyClient {
    public static void main(String[] args) {
        // 代理（连接服务端）
        RpcClient nettyClient = new NettyClient("127.0.0.1", 9998);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(nettyClient);

        // 服务调用
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
