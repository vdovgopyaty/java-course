package ru.vdovgopyaty.cloud.server;

import ru.vdovgopyaty.cloud.common.FileMessage;
import ru.vdovgopyaty.cloud.common.FileRequest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MainHandler extends ChannelInboundHandlerAdapter {
    private final String STORAGE_FOLDER = "serverStorage";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client connected...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg == null) {
                return;
            }
            System.out.println("Type of got message: " + msg.getClass());

            if (msg instanceof FileRequest) {
                FileRequest fileRequest = (FileRequest) msg;
                Path path = Paths.get("serverStorage/" + fileRequest.getName());
                if (Files.exists(path)) {
                    FileMessage fileMessage = new FileMessage(path);
                    ctx.writeAndFlush(fileMessage);
                }
            } else if (msg instanceof FileMessage) {
                FileMessage fileMessage = (FileMessage) msg;
                Files.write(Paths.get(STORAGE_FOLDER + "/" + fileMessage.getName()), fileMessage.getData(),
                        StandardOpenOption.CREATE);
            } else {
                System.out.printf("Server received wrong object!");
                return;
            }
        } finally {
            ReferenceCountUtil.release(msg);
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
