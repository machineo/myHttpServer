package cn.mm.server;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;
import java.util.Map;

public class XMLParser {
    private static ServletContext servletContext;
    static{
        try{
            servletContext = new ServletContext();
            //创建解析器工厂
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //得到解析器
            SAXParser saxParser = factory.newSAXParser();
            XMLHandler XMLHandler = new XMLHandler();
            //TODO 这里需要理解
            saxParser.parse(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("web.xml"), XMLHandler);

            //得到所有的servletList和MappingList
            //里面存放的是一个一个实体
            //其中servletEntity中存放servletname-servletclass
            //mappingEntity中存放servletname-urlpatters（这也是一个list）
            //现在要把这些内容转化为两个map（也就是ServletContext对象）
            List<ServletEntity> servletEntityList = XMLHandler.getServletEntityList();
            List<MappingEntity> mappingEntityList = XMLHandler.getMappingEntityList();

            Map<String, String> servletMap = servletContext.getServletMap();
            Map<String, String> mappingMap = servletContext.getMappingMap();

            //把servletEntityList中的内容存放到servletMap中
            for(ServletEntity se: servletEntityList){
//                System.out.println(se.getServletName() + " + " + se.getServletClass());
                servletMap.put(se.getServletName(), se.getServletClass());
            }

            //把mappingEntityList中的内容存放到mappingMap中
            //这里一个servletname可能对应多个urlparttern
            //但urlpattern是唯一的，所以把它作为键
            for(MappingEntity me: mappingEntityList){
                List<String> urlPattern = me.getUrlPattern();
                for(String url: urlPattern){
//                    System.out.println(url + " + " + me.getServletName());
                    mappingMap.put(url, me.getServletName());
                }
            }

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static Servlet urlToServlet(String url){
        if(url == null || url.trim().length()==0){
            return null;
        }
        //从mappingMap得到servletname（别名）
        String servletName = servletContext.getMappingMap().get(url);
        //从servletMap得到servlet的真实名字（包名.类名）
        String servlet = servletContext.getServletMap().get(servletName);

        //要确保该类有空构造函数存在
        try {
            return (Servlet) Class.forName(servlet).newInstance();
        } catch (Exception e) {
            return null;
        }
    }

}
