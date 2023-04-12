package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartLine {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final int METHOD_IDX = 0;
    private static final int PARAM_NAME_IDX = 0;
    private static final int PARAM_VALUE_IDX = 1;

    private final String method;
    private final RequestType requestType;
    private final String path;
    private final Map<String, String> paramMap;


    public StartLine(String requestLine) {
        this.method = parseMethod(requestLine);

        String url = parseUrl(requestLine);
        this.requestType = RequestType.of(url);
        this.path = requestType.getAbsolutePath(url);

        this.paramMap = parseParams(requestLine);
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

    private String parseMethod(String requestLine) {
        return requestLine.split(" ")[METHOD_IDX];
    }

    private String parseUrl(String requestLine) {
        Matcher matcher = Pattern.compile("/.*(?=\\?)|/.*(?= )").matcher(requestLine);
        String url = "";
        if (matcher.find()) {
            url = matcher.group();
        }
        return url;
    }

    private Map<String, String> parseParams(String requestLine) {
        if (!requestLine.contains("?")) {               // 만약 url 에 파라미터가 존재하지 않으면
            return null;                              // 파싱 종료
        }

        Matcher matcher = Pattern.compile("(?<=\\?).*(?= )").matcher(requestLine);
        String parameter = "";
        if (matcher.find()) {
            parameter = matcher.group();
        }
        String[] params = parameter.split("&");

        return makeParamMap(params);
    }


    /**
     * 요청 메시지로 부터 파싱한 parameter 부분을 paramName 과 paramValue로 나누고
     * paramMap을 만들어 반환
     */
    private HashMap<String, String> makeParamMap(String[] params) {
        HashMap<String, String> paramMap = new HashMap<>();

        for (String param : params) {
            String paramName = param.split("=")[PARAM_NAME_IDX];
            String paramValue = param.split("=")[PARAM_VALUE_IDX];
            paramMap.put(paramName, paramValue);
        }

        return paramMap;
    }

}
