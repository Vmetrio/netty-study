package nettystudy.echoclient.code;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Description: 客户端处理程序
 * 连接服务器
 * 发送信息
 * 发送的每个信息
 * 等待和接收从服务器返回的同样的信息关闭连接
 * @create by wangmenghao on 11:28 2021/9/21
 */
@Sharable  //@Sharable 标记这个类的实例可以在 channel 里共享
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    /**
     * 服务器的连接被建立后调用
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //当被通知该 channel 是活动的时候就发送信息
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!---meng", CharsetUtil.UTF_8));
    }

    /**
     * 数据后从服务器接收到调用
     * @param ctx
     * @param in
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        //记录接收到的消息
        System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));
    }

    /**
     * 捕获一个异常时调用
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //记录日志错误并关闭 channel
        cause.printStackTrace();
        ctx.close();
    }
}