package webserver;

import model.RequestType;
import model.RequestLine;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final int PARAM_NAME_IDX = 0;
    private static final int PARAM_VALUE_IDX = 1;


    private final RequestLine requestLine;
    private final Map<String, String> headers;
    private final String messageBody;
    private final Map<String, String> parameters;

    public HttpRequest(String requestLine, Map<String, String> headers, String messageBody) {
        this.requestLine = new RequestLine(requestLine);
        this.headers = headers;
        this.messageBody = messageBody;
        this.parameters = setParameters();
    }

    public String getMethod() {
        return requestLine.getMethod();
    }
    public String getUrl() {
        return requestLine.getUrl();
    }

    public String getAbsolutePath(String viewName) {
        return getRequestType().getAbsolutePath(viewName);
    }

    public RequestType getRequestType() {
        return requestLine.getRequestType();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    private Map<String, String> setParameters() {
        if (requestLine.getMethod().equals(GET)) {
            return requestLine.getParamMap();
        }

        if (requestLine.getMethod().equals(POST)) {
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


}
