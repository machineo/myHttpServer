package cn.mm.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("-------client-------");
        //建立连接，使用socket创建客户端--服务器地址+端口
        Socket client = new Socket("172.28.192.19",8686);
        //输入输出流操作

        String data = null;

        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while(( data = bufferedReader.readLine()).length()>0){
            dos.writeUTF(data);
            dos.flush();
        }
        //释放资源
        dos.close();
        client.close();
    }
}
