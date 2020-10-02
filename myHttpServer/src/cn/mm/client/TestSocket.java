package cn.mm.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class TestSocket {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("172.28.192.19",8686);
        OutputStream os = socket.getOutputStream();
        boolean autoflush = true;
        PrintWriter out = new PrintWriter(os, autoflush);
        BufferedReader in = new BufferedReader(
                new InputStreamReader( socket.getInputStream() ));

        // send an HTTP request to the web server
        out.println("GET /index.jsp HTTP/1.1");
        out.println("Host: localhost:8080");
        out.println("Connection: Close");
        // CRLF
        out.println();

        // read the response
        boolean loop = true;
        StringBuffer sb = new StringBuffer(8096);
        while (loop) {
            if ( in.ready() ) {
                int i=0;
                while (i!=-1) {
                    i = in.read();
                    sb.append((char) i);
                }
                loop = false;
            }
            Thread.currentThread().sleep(50);
        }

        // display the response to the out console
        System.out.println(sb.toString());
        socket.close();
    }
}