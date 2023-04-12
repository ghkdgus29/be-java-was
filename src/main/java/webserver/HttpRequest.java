package webserver;

import model.StartLine;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {

    private final StartLine startLine;
    private final Map<String, String> headers;
    private final String messageBody;

    public HttpRequest(String requestLine, Map<String, String> headers, String messageBody) {
        this.startLine = new StartLine(requestLine);
        this.headers = headers;
        this.messageBody = messageBody;
    }


}
