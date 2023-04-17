package webserver;

import model.RequestType;
import util.StatusCode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private static final String HTTP_VERSION = "HTTP/1.1";
    private StatusCode statusCode = StatusCode.OK;
    private Map<String, String> headers = new HashMap<>();
    private byte[] messageBody = {};
    private String redirectUrl;

    private RequestType requestType;

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public boolean isRedirect() {
        return statusCode.isRedirect();
    }

    public void setCookie(String cookieName, String cookieValue) {
        addHeader("Set-Cookie", cookieName + "=" + cookieValue + "; Path=/");
    }

    public void setContent(String viewName) throws IOException {
        if (isRedirect()) {
            addHeader("Location", redirectUrl);
            return;
        }

        this.messageBody = Files.readAllBytes(new File(requestType.getAbsolutePath(viewName)).toPath());
        addHeader("Content-Type", requestType.getContentType());
        addHeader("Content-Length", String.valueOf(messageBody.length));
    }

    public void setRequestType(String viewName) {
        this.requestType = RequestType.of(viewName);
    }

    public byte[] toBytes() {
        StringBuilder sb = new StringBuilder();

        sb.append(HTTP_VERSION + " " + statusCode.getStatusValue() + " " + statusCode + " \r\n");

        headers.forEach((k, v) -> {
            sb.append(k + ": " + v + "\r\n");
        });
        sb.append("\r\n");

        byte[] headers = sb.toString().getBytes();
        byte[] messageBody = this.messageBody;

        return concatHeadersAndBody(headers, messageBody);
    }

    private static byte[] concatHeadersAndBody(byte[] headers, byte[] messageBody) {
        byte[] result = new byte[headers.length + messageBody.length];
        System.arraycopy(headers, 0, result, 0, headers.length);
        System.arraycopy(messageBody, 0, result, headers.length, messageBody.length);
        return result;
    }
}
