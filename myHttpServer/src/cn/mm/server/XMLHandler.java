package cn.mm.server;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XMLHandler extends DefaultHandler {

    //ServletEntity中存放servletname和servletclass属性
    private ServletEntity servletEntity;
    //mappingEntity中存放servletname和urlpatters（这是一个list）属性
    private MappingEntity mappingEntity;
    //解析后要把所有元素存放到实体中，再存放两个list中
    private List<ServletEntity> servletEntityList = new ArrayList<>();
    private List<MappingEntity> mappingEntityList = new ArrayList<>();

    private String beginTag;
    private boolean isMap;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName!=null){
            beginTag = qName;
            if(qName.equals("servlet")){
                isMap = false;
                //每次解析到一个servlet，都建一个新的servletEntity对象
                servletEntity = new ServletEntity();
            }else if(qName.equals("servlet-mapping")){
                isMap = true;
                mappingEntity = new MappingEntity();
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        //解析到文本的时候，beginTag已经变成了servlet-name或者servlet-class或者url-pattern
        if(beginTag != null){
            String text = new String(ch, start, length);
            if(!isMap){
                if(beginTag.equals("servlet-name")){
                    servletEntity.setServletName(text);
                }else if(beginTag.equals("servlet-class")){
                    servletEntity.setServletClass(text);
                }
            }else{
                if(beginTag.equals("servlet-name")){
                    mappingEntity.setServletName(text);
                }else if(beginTag.equals("url-pattern")){
                    mappingEntity.getUrlPattern().add(text);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName != null){
            if(qName.equals("servlet")){
                servletEntityList.add(servletEntity);
            }else if(qName.equals("servlet-mapping")){
                mappingEntityList.add(mappingEntity);
            }
        }
        /**
         * 每次一对标签内容解析结束，将beginTag置空
           每次换行会被当作空格处理时，会执行characters函数（每次进去先判断beginTag是否为null）
           这时候beginTag不为空，会继续执行characters的内容
           如果beginTag为空了，就不会再继续执行
         */
        beginTag = null;
    }

    public List<ServletEntity> getServletEntityList() {
        return servletEntityList;
    }

    public List<MappingEntity> getMappingEntityList() {
        return mappingEntityList;
    }

}
