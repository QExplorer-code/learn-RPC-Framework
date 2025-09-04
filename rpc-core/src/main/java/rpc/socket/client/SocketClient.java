package rpc.socket.client;

import rpc.common.RpcClient;
import rpc.entity.RpcRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.entity.RpcResponse;
import rpc.enumeration.ResponseCode;
import rpc.enumeration.RpcError;
import rpc.exception.RpcException;
import rpc.loadbalancer.LoadBalancer;
import rpc.loadbalancer.RandomLoadBalancer;
import rpc.registry.NacosServiceRegistry;
import rpc.registry.ServiceRegistry;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private final ServiceRegistry serviceRegistry;

    public SocketClient() {
        this(new RandomLoadBalancer());
    }

    public SocketClient(LoadBalancer loadBalancer) {
        serviceRegistry = new NacosServiceRegistry(loadBalancer);
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        InetSocketAddress inetSocketAddress = serviceRegistry.lookupService(rpcRequest.getInterfaceName());
        // TODO 考虑无服务可用

        String host =  inetSocketAddress.getAddress().getHostAddress();
        int port = inetSocketAddress.getPort();

        try (Socket socket = new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream((socket.getInputStream()));
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            RpcResponse rpcResponse = (RpcResponse) objectInputStream.readObject();
            if(rpcResponse == null) {
                logger.error("服务调用失败，service：{}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            if(rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()) {
                logger.error("调用服务失败, service: {}, response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            return rpcResponse.getData();
        } catch (Exception e) {
            logger.error("调用时发生错误：", e);
            return null;
        }
    }
}
