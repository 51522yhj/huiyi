package com.easymeeting.websocket.netty;

import com.easymeeting.entity.dto.TokenUserInfoDto;
import com.easymeeting.redis.RedisComponent;
import com.easymeeting.websocket.ChannelContextUtils;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
@Component
@ChannelHandler.Sharable
public class HandlerTokenValidation extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Resource
    private RedisComponent redisComponent;
    @Autowired
    private ChannelContextUtils channelContextUtils;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        // 获取请求头
//        HttpHeaders headers = request.headers();
//        String token = headers.get("token");

        String uri = request.uri();
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
        List<String> tokens = queryStringDecoder.parameters().get("token");
        if (tokens == null || tokens.isEmpty()) {
            sendErrorResponse(ctx);
            return;
        }
        String token = tokens.get(0);
        TokenUserInfoDto tokenUserInfoDto = redisComponent.checkToken(token);
        if (tokenUserInfoDto== null) {
            sendErrorResponse(ctx);
            return;
        }
        // 对象引用计数加一，保证在下个handler前不被释放
       // ReferenceCountUtil.retain(request);
        ctx.fireChannelRead(request.retain());
        channelContextUtils.addChannel(tokenUserInfoDto.getUserId(), ctx.channel());
    }
    private void sendErrorResponse(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN, Unpooled.copiedBuffer("token无效", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
