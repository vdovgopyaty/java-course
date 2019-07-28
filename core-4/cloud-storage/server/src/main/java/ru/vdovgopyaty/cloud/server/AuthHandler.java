package ru.vdovgopyaty.cloud.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.vdovgopyaty.cloud.common.messages.AccessAllowedMessage;
import ru.vdovgopyaty.cloud.common.messages.AccessDeniedMessage;
import ru.vdovgopyaty.cloud.common.messages.AuthMessage;
import ru.vdovgopyaty.cloud.common.messages.Message;

public class AuthHandler extends ChannelInboundHandlerAdapter {

    private AuthService authService;

    AuthHandler(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client requests authentication");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg == null) {
            return;
        }

        if (msg instanceof AuthMessage) {
            AuthMessage authMessage = (AuthMessage) msg;
            String userToken = authService.getToken(authMessage.getLogin(), authMessage.getPassword());
            if (userToken != null) {
                ctx.fireChannelRead(authMessage);
                ctx.writeAndFlush(new AccessAllowedMessage(userToken));
                System.out.println("Access allowed");
            } else {
                ctx.writeAndFlush(new AccessDeniedMessage());
                System.out.println("Access denied");
            }
        } else {
            Message message = (Message) msg;
            if (message.getToken() != null) {
                String login = authService.getLoginByToken(message.getToken());
                System.out.println("User token: " + message.getToken() + ", login: " + login);
                ctx.fireChannelRead(message);
            } else {
                System.out.println("Client not authenticated!");
                return;
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
