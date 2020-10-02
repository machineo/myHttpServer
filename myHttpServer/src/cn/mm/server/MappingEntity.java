package cn.mm.server;

import java.util.ArrayList;
import java.util.List;

public class MappingEntity {
    private String servletName;
    private List<String> urlPattern;

    public MappingEntity() {
        urlPattern = new ArrayList<>();
    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public List<String> getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(List<String> urlPattern) {
        this.urlPattern = urlPattern;
    }
}
