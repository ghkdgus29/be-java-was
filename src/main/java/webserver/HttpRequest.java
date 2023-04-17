package webserver;

import model.RequestLine;
import util.RequestMethod;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final int PARAM_NAME_IDX = 0;
    private static final int PARAM_VALUE_IDX = 1;
    private static final String COOKIE = "Cookie";


    private final RequestLine requestLine;
    private final Map<String, String> headers;
    private final String messageBody;
    private final Map<String, String> parameters;
    private final Map<String, String> cookies;

    public HttpRequest(String requestLine, Map<String, String> headers, String messageBody) {
        this.requestLine = new RequestLine(requestLine);
        this.headers = headers;
        this.messageBody = messageBody;
        this.parameters = setParameters();
        this.cookies = setCookies();
    }

    public String getMethod() {
        return requestLine.getMethod();
    }
    public String getUrl() {
        return requestLine.getUrl();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    private Map<String, String> setParameters() {
        if (requestLine.getMethod().equals(RequestMethod.GET)) {
            return requestLine.getParamMap();
        }

        if (requestLine.getMethod().equals(RequestMethod.POST)) {
            return parseParametersBy(messageBody);
        }

        return null;
    }
    
    private Map<String, String> parseParametersBy(String messageBody) {
        HashMap<String, String> parameters = new HashMap<>();

        if (messageBody == null) {
            return null;
        }

        String[] parameterPairs = messageBody.split("&");
        for (String pair : parameterPairs) {
            parameters.put(pair.split("=")[PARAM_NAME_IDX], pair.split("=")[PARAM_VALUE_IDX]);
        }

        return parameters;
    }

    private Map<String, String> setCookies() {
        if (!headers.containsKey(COOKIE)) {
            return null;
        }

        HashMap<String, String> cookies = new HashMap<>();

        String[] splitedCookies = headers.get(COOKIE).split(";");

        for (String cookie : splitedCookies) {
            String cookieName = cookie.split("=")[PARAM_NAME_IDX].trim();
            String cookieValue = cookie.split("=")[PARAM_VALUE_IDX].trim();
            cookies.put(cookieName, cookieValue);
        }

        return cookies;
    }
}
