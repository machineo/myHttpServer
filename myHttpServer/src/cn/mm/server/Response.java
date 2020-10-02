package cn.mm.server;

import cn.mm.utils.CloseUtils;
import cn.mm.utils.StatusCode;

import javax.naming.Context;
import java.io.*;
import java.security.cert.CRL;
import java.sql.Connection;
import java.util.Date;

public class Response {
    //空格
    private static final String BLANK = " ";
    //回车换行
    private static final String CRLF = "\r\n";
    //响应头
    private StringBuilder respHead;
    //响应正文
    private StringBuilder respContext;
    //输出流
    private BufferedWriter bufferedOutputStream;

    public Response() {
        respHead = new StringBuilder();
        respContext = new StringBuilder();
    }

    public Response(OutputStream outputStream) {
        this();
        bufferedOutputStream = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    //创建响应头，其中需要根据状态码调整
    //    HTTP/1.1 200 OK
    //    Server:jkzmn Server/1.0.0
    //    Content-type:text/html;charset=utf8
    //    Date:Wed Sep 30 15:21:05 CST 2020
    //    Content-Length:176
    public void createRespHead(int statusCode){
        respHead.append("HTTP/1.1").append(BLANK).append(statusCode).append(BLANK);
        switch(statusCode){
            case 200:
                respHead.append("OK");
                break;
            case 500:
                respHead.append("SERVER ERROR");
                break;
            case 404:
                respHead.append("NOT FOUND");
                break;
        }

        respHead.append(CRLF);
        //封装服务器版本
        respHead.append("Server:jkzmn Server/1.0.0").append(CRLF);
        //封装返回数据格式和编码
        respHead.append("Content-type: text/html;charset=utf8").append(CRLF);
        //返回响应时间
        respHead.append("Date: " + new Date()).append(CRLF);
        //返回响应内容字节长度
        respHead.append("Content-length: "+ respContext.toString().getBytes().length).append(CRLF); //TODO
        respHead.append(CRLF); //分隔符
    }

    public void print(String msg){
        //需要输出什么只需要在正文部分添加上去即可
        respContext.append(msg);
    }

    public void pushToClient(int statusCode){
        switch (statusCode){
            // TODO 这里不一样
            case 404:
                respContext.append(StatusCode.NOTFOUND.getMsg());
                break;
            case 500:
                respContext.append(StatusCode.SERVERERROR.getMsg());
                break;
        }
        createRespHead(statusCode);
        try {

            bufferedOutputStream.append(respHead.toString());
            bufferedOutputStream.append(respContext.toString());
            bufferedOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeIO(){
        CloseUtils.closeIO(bufferedOutputStream);
    }

    public StringBuilder getRespContext() {
        return respContext;
    }
}
