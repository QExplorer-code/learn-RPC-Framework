package rpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.entity.RpcRequest;
import rpc.entity.RpcResponse;
import rpc.enumeration.SerializerCode;
import rpc.exception.SerializeException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class KryoSerializer implements  CommonSerializer{

    private static final Logger logger = LoggerFactory.getLogger(KryoSerializer.class);

    private static final ThreadLocal<Kryo> kryoLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        // 测试过不注册也能正常收发。了解注册的作用？什么情况下要注册？
        kryo.register(RpcResponse.class);
        kryo.register(RpcRequest.class);
        kryo.setReferences(true);
        kryo.setRegistrationRequired(false);
        return kryo;
    });
    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)) {
            Kryo kryo = kryoLocal.get();
            kryo.writeObject(output, obj);
            kryoLocal.remove();
            // 踩坑：原先在这里output.close()，导致序列化后byte[]为空，发出去空内容；服务端反序列化出错。
            return output.toBytes();
        } catch (IOException e) {
            logger.error("kryo序列化时出错：", e);
            throw new SerializeException("kryo序列化时有错误发生");
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)) {
            Kryo kryo = kryoLocal.get();
            Object obj = kryo.readObject(input, clazz);
            kryoLocal.remove();
            return obj;
        } catch (Exception e) {
            logger.error("kryo反序列化时出错：", e);
            throw new SerializeException("kryo反序列化时有错误发生");
        }
    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("KRYO").getCode();
    }
}
