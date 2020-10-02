package cn.mm.server;

import cn.mm.utils.CloseUtils;
import org.omg.CORBA.ARG_OUT;

import java.io.IOException;
import java.net.Socket;

/**
 * 分发器，每一个客户端连接开辟一个线程
 */
public class Dispatcher implements Runnable{

    private Request request;
    private Response response;
    private Socket client;

    private int statusCode = 200;

    public Dispatcher(Socket client) {

            this.client = client;
            System.out.println("============Dispatcher=========");


//        } catch (IOException e) {
//            //这里有异常说明是服务器出现了问题
//            statusCode = 500;
//            return;
//        }
    }

    @Override
    public void run() {
        //根据浏览器的输入流得到打包后的request对象
        try {
            request = new Request(client.getInputStream());
            //response通过输出流传给客户端
            response = new Response(client.getOutputStream());
        } catch (IOException e) {
            statusCode = 500;
            return;
        }

        System.out.println(Thread.currentThread().getName()+ " --> 开始运行啦" );
        String url = request.getUrl();

        Servlet servlet = XMLParser.urlToServlet(url);
        if(servlet == null){
            this.statusCode = 404; //访问的资源不存在
        }else{
            //执行后得到响应对象response
            servlet.service(request, response);
        }

        //TODO 把响应返回给客户端
        System.out.println("responseContext: "+ response.getRespContext().toString());
        response.pushToClient(statusCode);
        try {
            Thread.sleep(1000*30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()  + " --> 即将关闭");
        //TODO 关闭这个客户端线程
        request.closeIO();
        response.closeIO();
        CloseUtils.closeSocket(client);
    }
}
