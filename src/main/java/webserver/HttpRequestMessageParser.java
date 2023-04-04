package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestMessageParser {

    public static String parseUrl(String startLine) {
        String url = startLine.split(" ")[1];
        if (url.equals("/")) {
            url = "/index.html";
        }
        return url;
    }

    public static Map<String, String> parseParams(String url) {
        if (!url.contains("?")) {
            return null;
        }

        String paramLine = url.split("\\?")[1];
        String[] params = paramLine.split("&");

        HashMap<String, String> paramMap = new HashMap<>();

        for (String param : params) {
            String paramName = param.split("=")[0];
            String paramValue = param.split("=")[1];
            paramMap.put(paramName, paramValue);
        }

        return paramMap;
    }
}
