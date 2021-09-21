package nettystudy.echoserver.code;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Description: 服务端主类
 * @create by wangmenghao on 11:32 2021/9/21
 */

public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        //创建 EventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup(); //3
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //创建 ServerBootstrap
            bootstrap.group(group)
                    //指定使用 NIO 的传输 Channel
                    .channel(NioServerSocketChannel.class)
                    //设置 socket 地址使用所选的端口
                    .localAddress(new InetSocketAddress(port))
                    //添加 EchoServerHandler 到 Channel 的 ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            //绑定的服务器;sync 等待服务器关闭
            ChannelFuture future = bootstrap.bind().sync();
            System.out.println(EchoServer.class.getName() + " started and listen on " + future.channel().localAddress());
            //关闭 channel 和 块，直到它被关闭
            future.channel().closeFuture().sync();
        } finally {
            //关闭 EventLoopGroup，释放所有资源
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws Exception {
//        if (args.length != 1) {
//            System.err.println("Usage: " + EchoServer.class.getSimpleName() + " <port>");
//            return;
//        }
        //设置端口值（抛出一个 NumberFormatException 如果该端口参数的格式不正确）
        int port = Integer.parseInt("8502");
        //呼叫服务器的 start() 方法
        new EchoServer(port).start();
    }
}
