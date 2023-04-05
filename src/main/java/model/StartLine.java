package model;

import java.util.Map;

public class StartLine {

    private static final String HTML_PATH = "src/main/resources/templates";
    private static final String STYLE_PATH = "src/main/resources/static";
    private final String method;
    private final String path;
    private final Map<String, String> paramMap;
    private final boolean cssRequest;
    private final boolean jsRequest;
    private final boolean fontRequest;

    public StartLine(String method, String url, Map<String, String> paramMap) {
        this.method = method;
        this.paramMap = paramMap;

        this.cssRequest = url.startsWith("/css");
        this.jsRequest = url.startsWith("/js");
        this.fontRequest = url.startsWith("/fonts");

        this.path = decideAbsolutePath(url);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public boolean isCssRequest() {
        return cssRequest;
    }

    public boolean isJsRequest() {
        return jsRequest;
    }

    public boolean isFontRequest() {
        return fontRequest;
    }

    private String decideAbsolutePath(String url) {
        if (cssRequest || jsRequest || fontRequest) {
            return STYLE_PATH + url;
        }

        if (url.equals("/")) {
            url = "/index.html";
        }

        return HTML_PATH + url;
    }
}
