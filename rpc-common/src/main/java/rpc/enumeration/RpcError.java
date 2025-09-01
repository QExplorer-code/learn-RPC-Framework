package rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RpcError {
    SERVICE_INVOCATION_FAILURE("服务调用失败"),
    SERVICE_NOT_FOUND("找不到对应服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务未实现接口"),
    UNKNOWN_MAGIC_NUMBER("不识别的魔数"),
    UNKNOWN_PACKAGE_TYPE("不识别的数据包类型"),
    UNKNOWN_SERIALIZER("不识别的(反)序列化器");

    private final String message;
}
