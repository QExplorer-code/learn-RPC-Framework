package test;


import rpc.api.HelloService;
import rpc.api.HelloObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServiceImpl implements HelloService {
    private static final Logger log = LoggerFactory.getLogger(HelloServiceImpl.class);
    public String hello(HelloObject object) {
        log.info("接收到：{}", object.getMessage());
        return "调用返回值，id=" + object.getId();
    }
}
