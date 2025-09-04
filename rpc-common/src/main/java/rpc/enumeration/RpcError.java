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
    UNKNOWN_SERIALIZER("不识别的(反)序列化器"),
    RESPONSE_NOT_MATCH("响应与请求号不匹配"),
    FAILED_TO_CONNECT_TO_SERVICE_REGISTRY("连接注册中心失败"),
    FAILED_TO_LOOK_UP_SERVICE("获取服务失败"),
    // TODO 考虑服务发现时没有服务可用
    SERVICE_NOT_AVAILABLE("暂无服务可用"),
    REGISTER_SERVICE_FAILED("注册服务失败");

    private final String message;
}
