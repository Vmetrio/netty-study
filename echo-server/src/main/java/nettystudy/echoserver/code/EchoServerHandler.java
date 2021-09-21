package nettystudy.echoserver.code;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Description: 服务端处理程序
 * @create by wangmenghao on 11:33 2021/9/21
 */
// @Sharable标示一个ChannelHandler可以被多个Channel安全共享
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 每个信息入站都会被调用
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        //将日志消息是输出到控制台
        System.out.println("Server received: " + in.toString(CharsetUtil.UTF_8));
        //将所接收的消息返回给发送者。注意，这还没有冲刷数据
        ctx.write(in);
    }

    /**
     * 通知处理器最后的 channelRead() 是当前批处理中的最后一条消息时调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 冲刷所有待审消息到远程节点。关闭通道后，操作完成
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 读操作时捕获到异常时调用
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace(); //5 打印异常堆栈跟踪
        ctx.close(); //6 关闭通道
    }

}
