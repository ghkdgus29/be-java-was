package model;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartLine {

    private static final String TEMPLATES_PATH = "src/main/resources/templates";
    private static final String STATIC_PATH = "src/main/resources/static";
    private static final List<String> STATIC_TYPE = List.of("css", "js", "fonts");

    private final String method;
    private final String path;
    private final Map<String, String> paramMap;
    private final String requestType;

    public StartLine(String method, String url, Map<String, String> paramMap) {
        this.method = method;
        this.path = decideAbsolutePath(url);
        this.paramMap = paramMap;
        this.requestType = decideRequestType(url);
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

    public String getRequestType() {
        return requestType;
    }

    private String decideAbsolutePath(String url) {
        if (url.startsWith("/css") || url.startsWith("/js") || url.startsWith("/fonts")) {
            return STATIC_PATH + url;
        }

        if (url.equals("/")) {
            url = "/index.html";
        }

        return TEMPLATES_PATH + url;
    }

    /**
     * 요청 url을 바탕으로 타입을 구분
     * @param url
     * @return (html, css, js, fonts)
     */
    private String decideRequestType(String url) {
        Matcher matcher = Pattern.compile("(?<=/).*(?=/)").matcher(url);        // 요청 url의 /~/  슬래쉬 사이의 ~부분을 추출

        if (matcher.find()) {
            String requestType = matcher.group();
            return STATIC_TYPE.contains(requestType) ? requestType : "html";
        }

        return "html";
    }
}
