package rpc.exception;

import rpc.enumeration.RpcError;

public class SerializeException extends RuntimeException {

    public SerializeException(String message) {
        super(message);
    }
}
