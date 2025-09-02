package rpc.common;

import rpc.entity.RpcRequest;

/*
 * 服务端通用接口
 */
public interface RpcServer {
    void start();

    <T> void publishService(Object service, Class<T> serviceClass);
}
