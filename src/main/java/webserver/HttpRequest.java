package webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final int METHOD = 0;
    private static final int URL = 1;
    private static final int PARAMETER = 1;
    private static final int PARAM_NAME = 0;
    private static final int PARAM_VALUE = 1;


    public static String parseMethod(String startLine) {
        return splitAndGet(startLine, " ", METHOD);
    }

    public static String parseUrl(String startLine) {
        String url = splitAndGet(startLine, " ", URL);

        if (url.contains("?")) {                    // 만약 url 에 파라미터가 존재하면
            url = url.split("\\?")[0];        // 파라미터 부분은 제거
        }

        if (url.equals("/")) {
            url = "/index.html";
        }
        return url;
    }

    public static Map<String, String> parseParams(String startLine) {
        String url = splitAndGet(startLine, " ", URL);

        if (!url.contains("?")) {               // 만약 url 에 파라미터가 존재하지 않으면
            return null;                        // 파싱 종료
        }

        String paramLine = splitAndGet(url, "\\?", PARAMETER);
        String[] params = paramLine.split("&");

        return makeParamMap(params);
    }

    /**
     * 요청 메시지의 start-line 을 빈칸으로 쪼개고, part 에 해당하는 부분을 반환
     *
     * @param part METHOD(0), URL(1)
     */
    private static String splitAndGet(String line, String delimiter, int part) {
        return line.split(delimiter)[part];
    }


    /**
     * 요청 메시지로 부터 파싱한 parameter 부분을 paramName 과 paramValue로 나누고
     * paramMap을 만들어 반환
     */
    private static HashMap<String, String> makeParamMap(String[] params) {
        HashMap<String, String> paramMap = new HashMap<>();

        for (String param : params) {
            String paramName = param.split("=")[PARAM_NAME];
            String paramValue = param.split("=")[PARAM_VALUE];
            paramMap.put(paramName, paramValue);
        }

        return paramMap;
    }
}
