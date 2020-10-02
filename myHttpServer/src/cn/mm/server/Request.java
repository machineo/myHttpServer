package cn.mm.server;

import cn.mm.utils.CloseUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Request {
    //请求头中的换行
    private static final String CRLF="\r\n";
    //输入流
    private InputStream inputStream;
    //请求信息（所有）
    private String requestInfo;
    //请求方法
    private String method;
    //请求url
    private String url;
    //请求参数
    private Map<String, ArrayList<String>> parameterMap;

    //无参构造器
    public Request() {
        method = "";
        url = "";
        parameterMap = new HashMap<>();
    }

    //带参构造器，输入client的请求头数据流
    public Request(InputStream inputStream){
        this();
        this.inputStream = inputStream;
        StringBuilder sb = new StringBuilder();

        //从输入流中提取出请求信息
        BufferedInputStream bis = null;

//        byte[] b=new byte[1024];   //代表一次最多读取1KB的内容
//
//        int length = 0 ; //代表实际读取的字节数
//        while( (length = bufferedInputStream.read( b ) )!= -1 ){
//            //length 代表实际读取的字节数
//            bufferedOutputStream.write(b, 0, length );
//        }
        try {
            //TODO 读取怎么加缓冲区
            bis = new BufferedInputStream(inputStream);
            byte[] b = new byte[256];
            int len;
            while((len = bis.read(b))!=-1){
                sb.append(new String(b, 0, len));
                if(len<256){
                    break;
                }
            }
            requestInfo = sb.toString();
//            byte[] data = new byte[20480];
//            int len = inputStream.read(data);
//            requestInfo=new String(data, 0, len);
            System.out.println(Thread.currentThread().getName() + "   requsetIndfo：");
            System.out.println(requestInfo);
            //解析请求信息
            parseRequestInfo();
        } catch (IOException e) {
            return;
        }
    }

    private void parseRequestInfo() {
        //请求信息为null或空串，什么都不做，返回
        if(requestInfo == null || requestInfo.trim().length()==0){
            return;
        }

        //新建字符串存请求参数
        String parameterSring = "";

        //得到请求第一行数据: requestInfo: GET /login?username=213 HTTP/1.1
        String firstLine = requestInfo.substring(0,requestInfo.indexOf(CRLF));

        //得到method
        int index = firstLine.indexOf('/');
        method = firstLine.substring(0, index).trim();

        //得到url
        String urlString = firstLine.substring(index, firstLine.indexOf("HTTP/")).trim();

        //判断请求方式
        if(method.equalsIgnoreCase("post")){
            url = urlString; //post请求的url中不带参数，就是请求头中的url

            //post请求的参数就是最后一行
            parameterSring = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
        }else if(method.equalsIgnoreCase("get")){
            if(!urlString.contains("?")){
                url = urlString; //如果请求头中的url不带参数，那么直接赋值给url
            }else{
                //分割url
                String[] urlArr = urlString.split("\\?"); //问好需要转义
                url = urlArr[0];
                parameterSring = urlArr[1];
            }
        }else{
            throw new RuntimeException("你请求的方法不存在哦！！");
        }

        //解析请求参数字符串
        if(parameterSring != null || parameterSring.trim().length()!=0){
            parseParameter(parameterSring);
        }

    }

    private void parseParameter(String parameterSring) {
        String[] paramsArr = parameterSring.trim().split("&");
        for(String tmp : paramsArr){
            String[] paramArr = tmp.split("="); //每个参数键值对用‘=’分割

            if(paramArr.length == 0){ //数组长度等于0跳到下一次循环
                continue;
            }
            //判断参数键值对有没有值
            if(paramArr.length == 1){
                paramArr = Arrays.copyOf(paramArr, 2); //数组长度小于2扩展数组长度为2
                paramArr[1] = null;
            }

            String key = paramArr[0];
            String value = paramArr[1];

            //把参数添加进map,每个键可能对应多个值
            if(!parameterMap.containsKey(key)){
                parameterMap.put(key, new ArrayList<>());
            }
            ArrayList<String> arrayList = parameterMap.get(key);
            arrayList.add(value);
        }

    }

    public Map<String, ArrayList<String>> getParameterMap() {
        return parameterMap;
    }

    public String getUrl() {
        return url;
    }

    //通过key返回参数中的值
    public String getParameter(String key){
        if(parameterMap.get(key) == null){
            return null;
        }else{
            return parameterMap.get(key).get(0);
        }
    }

    public void closeIO(){
        CloseUtils.closeIO(inputStream);
    }
}
