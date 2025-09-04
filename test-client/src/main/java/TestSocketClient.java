import rpc.api.CalculateService;
import rpc.api.HelloObject;
import rpc.api.HelloService;
import rpc.common.RpcClientProxy;
import rpc.loadbalancer.RoundRobinLoadBalancer;
import rpc.socket.client.SocketClient;

import java.util.Scanner;

public class TestSocketClient {
    public static void main(String[] args) {
        // 代理（连接服务端）
        SocketClient socketClient = new SocketClient(new RoundRobinLoadBalancer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(socketClient);

        // 服务调用
        System.out.println("0: hello, 1: add, 2: sub, -1: exit");
        Scanner scanner =  new Scanner(System.in);
        int flag = scanner.nextInt();
        while (flag != -1) {
            switch (flag) {
                case 0:
                    HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
                    HelloObject helloObject = new HelloObject(111, "this is a test");
                    System.out.println(helloService.hello(helloObject));
                    break;
                case 1:
                    CalculateService calculateService = rpcClientProxy.getProxy(CalculateService.class);
                    System.out.println(calculateService.add(1, 2));
                    break;
                case 2:
                    CalculateService calculateService2 = rpcClientProxy.getProxy(CalculateService.class);
                    System.out.println(calculateService2.sub(1, 2));
                    break;
                default:
                    break;
            }
            flag = scanner.nextInt();
        }
        System.out.println("exit...");
    }
}
