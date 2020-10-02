package cn.mm.server;

import java.util.HashMap;
import java.util.Map;

public class ServletContext {
//    <servlet>
//	 	<servlet-name>login</servlet-name>
//	 	<servlet-class>jk.zmn.server.servlet.LoginServlet</servlet-class>
//    </servlet>
//    <servlet-mapping>
//	 	<servlet-name>login</servlet-name>
//	 	<url-pattern>/login</url-pattern>
//	 	<url-pattern>/</url-pattern>
//    </servlet-mapping>
    private Map<String, String> servletMap;
    private Map<String, String> mappingMap;

    public ServletContext() {
        servletMap = new HashMap<>();
        mappingMap = new HashMap<>();
    }

    public Map<String, String> getServletMap() {
        return servletMap;
    }

    public void setServletMap(Map<String, String> servletMap) {
        this.servletMap = servletMap;
    }

    public Map<String, String> getMappingMap() {
        return mappingMap;
    }

    public void setMappingMap(Map<String, String> mappingMap) {
        this.mappingMap = mappingMap;
    }
}
