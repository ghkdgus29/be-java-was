package webserver;

public class HttpResponse {

    private final String headers;
    private final byte[] messageBody;

    public HttpResponse(String headers, byte[] messageBody) {
        this.headers = headers;
        this.messageBody = messageBody;
    }

    public String getHeaders() {
        return headers;
    }

    public byte[] getMessageBody() {
        return messageBody;
    }
}
