package rpc.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.omg.PortableInterceptor.HOLDING;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rpc.codec.CommonDecoder;
import rpc.codec.CommonEncoder;
import rpc.common.RpcClient;
import rpc.entity.RpcRequest;
import rpc.entity.RpcResponse;
import rpc.loadbalancer.LoadBalancer;
import rpc.loadbalancer.RandomLoadBalancer;
import rpc.netty.server.NettyServerHandler;
import rpc.registry.NacosServiceRegistry;
import rpc.registry.ServiceRegistry;
import rpc.serializer.JsonSerializer;
import rpc.serializer.KryoSerializer;

import java.net.InetSocketAddress;

public class NettyClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private ServiceRegistry serviceRegistry;

    private static final Bootstrap bootstrap;

    public NettyClient() {
        this(new RandomLoadBalancer());
    }

    public NettyClient(LoadBalancer loadBalancer) {
        this.serviceRegistry = new NacosServiceRegistry(loadBalancer);
    }

    static {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
//                        添加处理器：入站顺序，出战逆序
//                        pipeline.addLast(new CommonEncoder(new JsonSerializer()));
                        pipeline.addLast(new CommonEncoder(new KryoSerializer()));
                        pipeline.addLast(new CommonDecoder());
                        pipeline.addLast(new NettyClientHandler());
                    }
                });
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try {
            InetSocketAddress inetSocketAddress = serviceRegistry.lookupService(rpcRequest.getInterfaceName());
            // TODO 考虑无服务可用

            String host = inetSocketAddress.getAddress().getHostAddress();
            int port = inetSocketAddress.getPort();

            ChannelFuture future = bootstrap.connect(host, port).sync();
            logger.info("连接到服务器{}：{}", host, port);
            Channel channel = future.channel();
            if (channel != null) {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if (future1.isSuccess()) {
                        logger.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                    } else {
                        logger.error("发送消息时发生错误: ", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse = channel.attr(key).get();
                return rpcResponse.getData();
            }
        } catch (InterruptedException e) {
            logger.error("发送消息时发生错误: ", e);
        }
        return null;
    }
}
