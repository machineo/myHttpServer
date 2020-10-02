package cn.mm.server;

import cn.mm.utils.CloseUtils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    //默认服务器开启
    private boolean isShutting = false;

    public static void main(String[] args) throws IOException {
        InetAddress addr = InetAddress.getLocalHost();
        String hostAddr = addr.getHostAddress();
        System.out.println(hostAddr); //172.28.192.19

        System.out.println("-------server-------");
        //指定端口，使用ServerSocket创建服务器

        Server server = new Server();
        server.start();

    }

    //开启服务器
    public void start() {
        try {
            serverSocket = new ServerSocket(8686);
            receive();
        } catch (IOException e) {
            stop();
        }
    }

    //多线程模式，每个连接用一个线程处理
//    public void receive(){
//        while(!isShutting){
//            try {
//                new Thread(new Dispatcher(serverSocket.accept())).start();
//            } catch (Exception e) {
//                stop();
//            }
//        }
//    }

    public void receive() {
        ExecutorService executorService = Executors.newCachedThreadPool();
//        try {
//            Dispatcher dispatcher = new Dispatcher(serverSocket.accept());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        while (!isShutting) {
            try {
                executorService.execute(new Dispatcher(serverSocket.accept()));
            } catch (Exception e) {
                e.printStackTrace();
                executorService.shutdown();
                stop();
            }
        }
    }

    public void stop() {
        System.out.println("关闭服务器啦！");
        isShutting = true;
        CloseUtils.closeSocket(serverSocket);
    }
}
//    public void receive(){
//        //阻塞式等待客户端的连接
//        Socket client = null;
//        DataInputStream dis = null;
//        BufferedReader bf = null;
//
//        try {
//            client = server.accept();
//            System.out.println("一个客户端建立了连接！");
//
//            //输入输出流操作
//            StringBuilder sb = new StringBuilder();
//
//            bf = new BufferedReader(new InputStreamReader(client.getInputStream()));
////          dis = new DataInputStream(client.getInputStream());
//            String tmpS = null;
//            while((tmpS = bf.readLine()).length()>0){
//                sb.append(tmpS);
//                sb.append("\r\n");
//                if(tmpS == null) break;
////                System.out.println("客户端说：" + tmpS);
//            }

//        } catch (IOException e) {
//            try {
//                if(bf != null) bf.close();
//                if(client != null) client.close();
//                server.close();
//            } catch (IOException e1) {
//                throw new RuntimeException(e);
//            }
//        }finally {
//
//            //释放资源
//            try {
//                if(bf != null) bf.close();
//                if(client != null) client.close();
//                server.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

