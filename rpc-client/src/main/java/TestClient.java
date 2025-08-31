import rpc.api.CalculateService;
import rpc.api.HelloObject;
import rpc.api.HelloService;
import rpc.client.RpcClientProxy;

import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy rpcClientProxy = new RpcClientProxy("127.0.0.1", 9999);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(111, "this is a test");
        String result = helloService.hello(helloObject);
        System.out.println(result);

        System.out.println("1: add, 2: sub, -1: exit");
        Scanner scanner =  new Scanner(System.in);
        int flag = scanner.nextInt();
        while (flag != -1) {
            switch (flag) {
                case 1:
                    CalculateService calculateService = rpcClientProxy.getProxy(CalculateService.class);
                    int tempRes = calculateService.add(1, 2);
                    System.out.println(tempRes);
                    break;
                case 2:
                    CalculateService calculateService2 = rpcClientProxy.getProxy(CalculateService.class);
                    int  tempRes2 = calculateService2.sub(1, 2);
                    System.out.println(tempRes2);
                    break;
                default:
                    break;
            }
            flag = scanner.nextInt();
        }
        System.out.println("exit...");

    }
}
