package org.code4j.jproxy.client;/**
 * Description : 
 * Created by YangZH on 16-5-25
 *  下午10:47
 */

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Description :
 * Created by YangZH on 16-5-25
 * 下午10:47
 */

public class ProxyClient {


    CloseableHttpClient httpclient = HttpClients.createDefault();
    HttpPost httpPost;
    HttpGet httpGet;
    CloseableHttpResponse response = null;
    public ProxyClient(String url) {
        httpPost = new HttpPost(url);
        httpGet = new HttpGet(url);
    }

    public void setUrl(String url){
        httpPost = new HttpPost(url);
        httpGet = new HttpGet(url);
    }
    //    private ChannelFuture future;
//
//    public void connect(String remoteHost,int remotePort){
//        System.out.println("代理访问端正在链接远程服务器.....");
//        EventLoopGroup group = new NioEventLoopGroup();
//        Bootstrap b = new Bootstrap();
//        try {
//            b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
//                @Override
//                protected void initChannel(SocketChannel socketChannel) throws Exception {
//                    socketChannel.pipeline().addLast(new HttpResponseEncoder());
//                    socketChannel.pipeline().addLast(new HttpRequestDecoder());
//                    socketChannel.pipeline().addLast(new ClientHandler());
//                }
//            }).option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_BACKLOG, 128);
//            future = b.connect(remoteHost, remotePort).sync();
//            sendRequest();
//            sendRequest();
//            future.channel().closeFuture().sync();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }finally {
//            group.shutdownGracefully();
//        }
//    }

    public String fetchText(HttpMethod method){
        String body = "";
        CloseableHttpResponse response = null;
        if (HttpMethod.POST.equals(method)){
            httpPost.setHeader(HttpHeaderNames.CONTENT_TYPE.toString(), "text/html");
            try {
                response = httpclient.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            httpGet.setHeader(HttpHeaderNames.CONTENT_TYPE.toString(), "text/html");
            try {
                response = httpclient.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            try {
                body = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);
                //释放链接
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }

    public byte[] fetchImage(HttpMethod method){
        byte[] body = {};
        CloseableHttpResponse response = null;
        if (HttpMethod.POST.equals(method)){
            httpPost.setHeader(HttpHeaderNames.CONTENT_TYPE.toString(), "text/html");
            try {
                response = httpclient.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            httpGet.setHeader(HttpHeaderNames.CONTENT_TYPE.toString(), "text/html");
            try {
                response = httpclient.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            try {
                body = EntityUtils.toByteArray(entity);
                EntityUtils.consume(entity);
                //释放链接
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }

//    public void sendRequest(){
//        System.out.println("客户端发送消息");
//        URI uri = null;
//        try {
//            uri = new URI("http://127.0.0.1:9524");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        String msg = "Are you ok?";
//        DefaultFullHttpRequest request = null;
//        try {
//            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
//                    uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        // 构建http请求
//        request.headers().set(HttpHeaderNames.HOST, uri.getHost());
//        request.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/html");
//        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes()+"");
//        this.future.channel().writeAndFlush(request);
//    }

    public static void main(String[] args) throws URISyntaxException, IOException {

    }
}