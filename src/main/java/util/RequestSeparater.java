package util;

import webserver.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestSeparater {

    private static final int HEADER_NAME_IDX = 0;
    private static final int HEADER_VALUE_IDX = 1;
    private static final String CONTENT_LENGTH = "Content-Length";

    private final String requestLine;
    private final Map<String, String> headers;
    private final String messageBody;

    public RequestSeparater(final BufferedReader br) throws IOException {
        this.requestLine = setRequestLine(br);
        this.headers = setHeaders(br);
        this.messageBody = setMessageBody(br);
    }

    public HttpRequest askHttpRequest() {
        return new HttpRequest(requestLine, headers, messageBody);
    }

    public String getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMessageBody() {
        return messageBody;
    }

    private String setRequestLine(final BufferedReader br) throws IOException {
        return br.readLine();
    }

    private Map<String, String> setHeaders(final BufferedReader br) throws IOException {
        HashMap<String, String> headers = new HashMap<>();

        String line = br.readLine();
        while (!line.equals("")) {
            addHeader(line, headers);
            line = br.readLine();
        }

        return headers;
    }

    private String setMessageBody(final BufferedReader br) throws IOException {
        if (!headers.containsKey(CONTENT_LENGTH)) {
            return null;
        }

        int contentLength = Integer.parseInt(headers.get(CONTENT_LENGTH));
        char[] messageBody = new char[contentLength];
        br.read(messageBody);
        return String.valueOf(messageBody);
    }

    private void addHeader(String line, Map<String, String> headers) {
        String[] splitedLine = line.split(":", 2);
        String headerName = splitedLine[HEADER_NAME_IDX].trim();
        String headerValue = splitedLine[HEADER_VALUE_IDX].trim();
        headers.put(headerName, headerValue);
    }


}
