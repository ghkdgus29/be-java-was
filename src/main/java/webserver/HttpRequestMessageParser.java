package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestMessageParser {

    private static final int METHOD = 0;
    private static final int URL = 1;
    private static final int PARAMETER = 1;
    private static final int PARAMNAME = 0;
    private static final int PARAMVALUE = 1;


    public static String parseMethod(String startLine) {
        return startLine.split(" ")[METHOD];
    }

    public static String parseUrl(String startLine) {
        String url = startLine.split(" ")[URL];
        if (url.equals("/")) {
            url = "/index.html";
        }
        return url;
    }

    public static Map<String, String> parseParams(String url) {
        if (!url.contains("?")) {
            return null;
        }

        String paramLine = url.split("\\?")[PARAMETER];
        String[] params = paramLine.split("&");

        return makeParamMap(params);
    }

    private static HashMap<String, String> makeParamMap(String[] params) {
        HashMap<String, String> paramMap = new HashMap<>();

        for (String param : params) {
            String paramName = param.split("=")[PARAMNAME];
            String paramValue = param.split("=")[PARAMVALUE];
            paramMap.put(paramName, paramValue);
        }

        return paramMap;
    }
}
