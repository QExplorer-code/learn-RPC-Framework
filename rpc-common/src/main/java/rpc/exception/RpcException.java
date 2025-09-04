package rpc.exception;

import rpc.enumeration.RpcError;

public class RpcException extends RuntimeException {

    public RpcException(RpcError rpcError, String detail) {
        super(rpcError.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError rpcError) {
        super(rpcError.getMessage());
    }
}
