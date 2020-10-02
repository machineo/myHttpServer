package cn.mm.test;

import cn.mm.server.Request;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

public class RequestTest {
    @Test
    public void fun1() throws FileNotFoundException {
        //System.out.println(System.getProperty("user.dir"));
        String src = "D:\\javaCode\\SimpleSample\\WebStudy01\\httpServer\\myHttpServer\\src\\get.txt";
        Request req = new Request(new FileInputStream(src));
        System.out.println(req.toString());
        Map<String, ArrayList<String>> parameterMap = req.getParameterMap();
        for(String key: parameterMap.keySet()){
            System.out.println(key + ": " + parameterMap.get(key));
        }
    }
}
