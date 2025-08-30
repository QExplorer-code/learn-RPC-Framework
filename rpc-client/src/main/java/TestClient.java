import rpc.api.HelloObject;
import rpc.api.HelloService;
import rpc.client.RpcClientProxy;

public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy = new RpcClientProxy("127.0.0.1", 9999);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(111, "this is a test");
        String result = helloService.hello(helloObject);
        System.out.println(result);
    }
}
