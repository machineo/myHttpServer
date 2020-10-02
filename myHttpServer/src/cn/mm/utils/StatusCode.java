package cn.mm.utils;

public enum StatusCode {

    NOTFOUND(404, "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>出错了！</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <h1>404 NOT FOUND！</h1>\n" +
            "</body>\n" +
            "</html>"),
    SERVERERROR(500,"<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>出错了！</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <h1>500 SERVER ERROR！</h1>\n" +
            "</body>\n" +
            "</html>");

    int code;
    String msg;
    //枚举类的构造函数不能是public的
    private StatusCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
