package rpc.common;

import rpc.entity.RpcRequest;

/*
 * 客户端通用请求接口
 */
public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}
