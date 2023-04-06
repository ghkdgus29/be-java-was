package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartLine {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final String TEMPLATES_PATH = "src/main/resources/templates";
    private static final String STATIC_PATH = "src/main/resources/static";
    private static final List<String> STATIC_TYPE = List.of("css", "js", "fonts", "png", "ico");

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
        if (STATIC_TYPE.stream().anyMatch((type) -> url.endsWith("." + type))) {
            return STATIC_PATH + url;
        }
        if (url.equals("/")) {
            return TEMPLATES_PATH + "/index.html";
        }
        return TEMPLATES_PATH + url;
    }

    /**
     * 요청 url을 바탕으로 타입을 구분
     * @param url
     * @return (html, css, js, fonts, png, ico)
     */
    private String decideRequestType(String url) {
        String requestType = STATIC_TYPE.stream()
                .filter(type -> url.endsWith("." + type))
                .findAny()
                .orElse("html");

        logger.info("파싱된 type {}", requestType);

        return requestType;
    }
}
