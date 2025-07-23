package com.easymeeting.websocket.netty;

import com.easymeeting.config.AppConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Component
@Slf4j
public class NettyWebSocketStarter implements Runnable{
    /**
     * boss线程用于接收客户端连接
     */
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    /**
     * worker线程用于处理消息
     */
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    @Resource
    private HandlerTokenValidation handlerTokenValidation;

    @Resource
    private HandlerWebSocket handlerWebSocket;
    @Resource
    private AppConfig appConfig;
    @Override
    public void run() {
        try{
            log.info("Netty WebSocket Server Start{}" + Thread.currentThread().getName());
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.DEBUG)).
                    childHandler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            /**
                             * 对http协议支持，使用http的编码器，解码器
                             */
                            pipeline.addLast(new HttpServerCodec());
                            /**
                             * http消息聚合器，主要是将分片的消息聚合成完整的FullHttpRequest FullHttpResponse
                             */
                            pipeline.addLast(new HttpObjectAggregator(6*1024));
                            /**
                             *  int readerIdleTimeSeconds, 一段时间未收到客户端数据
                             *  int writerIdleTimeSeconds, 一段时间未向客户端发送数据
                             *  int allIdleTimeSeconds, 读写都无活动
                             */
                            pipeline.addLast(new IdleStateHandler(6,0,0));
                            pipeline.addLast(new HandlerHeartBeat());
                            pipeline.addLast(handlerTokenValidation);
                            /**
                             * websocket协议处理器
                             * /websocket 是访问路径
                             * subprotocols = null; 指定支持的子协议
                             * allowExtensions = true; 是否允许扩展
                             * maxFramePayloadLength = 65536; 最大帧长度
                             * allowMaskMismatch = true; 是否允许掩码不匹配
                             * closeTimeoutMillis = 10000L; 关闭超时时间
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/ws",null,true,65536,true,true,10000L));
                            pipeline.addLast(handlerWebSocket);
                        }
                    });
            Channel channel = serverBootstrap.bind(appConfig.getWsPort()).sync().channel();
            log.info("Netty WebSocket Server Start Success.Port:{}",appConfig.getWsPort());
            channel.closeFuture().sync();
        }
        catch (Exception e){
            log.error("Netty WebSocket Server Start Error",e);
        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
    @PreDestroy
    public void close(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
