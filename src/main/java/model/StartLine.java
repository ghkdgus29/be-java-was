package model;

import java.util.Map;

public class StartLine {

    private static final String HTML_PATH = "src/main/resources/templates";
    private static final String STYLE_PATH = "src/main/resources/static";
    private final String method;
    private final String path;
    private final Map<String, String> paramMap;
    private final boolean cssRequest;

    public StartLine(String method, String path, Map<String, String> paramMap) {
        this.method = method;
        this.path = decideAbsolutePath(path);
        this.paramMap = paramMap;
        this.cssRequest = path.contains(".css");
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

    private String decideAbsolutePath(String url) {
        if (url.contains(".css")) {
            return STYLE_PATH + url;
        }

        if (url.equals("/")) {
            url = "/index.html";
        }

        return HTML_PATH + url;
    }
}
