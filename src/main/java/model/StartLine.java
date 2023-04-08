package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.List;
import java.util.Map;

public class StartLine {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final String method;
    private final RequestType requestType;
    private final String path;
    private final Map<String, String> paramMap;


    public StartLine(String method, String url, Map<String, String> paramMap) {
        this.method = method;
        this.requestType = RequestType.of(url);
        this.path = requestType.getAbsolutePath(url);
        this.paramMap = paramMap;
    }

    public String getMethod() {
        return method;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

}
