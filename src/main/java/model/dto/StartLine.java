package model.dto;

import java.util.Map;

public class StartLine {

    private final String method;
    private final String url;
    private final Map<String, String> paramMap;

    public StartLine(String method, String url, Map<String, String> paramMap) {
        this.method = method;
        this.url = url;
        this.paramMap = paramMap;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }
}
