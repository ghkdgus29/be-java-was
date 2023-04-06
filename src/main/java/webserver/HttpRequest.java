package webserver;

import model.StartLine;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {

    private static final int METHOD_IDX = 0;
    private static final int PARAM_NAME_IDX = 0;
    private static final int PARAM_VALUE_IDX = 1;

    public static StartLine getStartLine(String startLineChunk) {
        return new StartLine(parseMethod(startLineChunk),
                parseUrl(startLineChunk),
                parseParams(startLineChunk));
    }

    private static String parseMethod(String startLine) {
        return startLine.split(" ")[METHOD_IDX];
    }

    private static String parseUrl(String startLine) {
        Matcher matcher = Pattern.compile("/.*(?=\\?)|/.*(?= )").matcher(startLine);
        String url = "";
        if (matcher.find()) {
            url = matcher.group();
        }
        return url;
    }

    private static Map<String, String> parseParams(String startLine) {
        if (!startLine.contains("?")) {               // 만약 url 에 파라미터가 존재하지 않으면
            return null;                              // 파싱 종료
        }

        Matcher matcher = Pattern.compile("(?<=\\?).*(?= )").matcher(startLine);
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
    private static HashMap<String, String> makeParamMap(String[] params) {
        HashMap<String, String> paramMap = new HashMap<>();

        for (String param : params) {
            String paramName = param.split("=")[PARAM_NAME_IDX];
            String paramValue = param.split("=")[PARAM_VALUE_IDX];
            paramMap.put(paramName, paramValue);
        }

        return paramMap;
    }
}
