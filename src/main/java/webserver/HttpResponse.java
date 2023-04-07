package webserver;

import model.StartLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final Map<String, String> contentType = Map.of("css", "text/css",
            "js", "application/javascript",
            "fonts", "application/octet-stream",
            "html", "text/html;charset=utf-8",
            "png", "image/png",
            "ico", "image/avif");

    public static void sendResponse200(DataOutputStream dos, byte[] body, StartLine startLine) {
        try {
            dos.writeBytes(response200Header(body.length, startLine.getRequestType()));

            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static String response200Header(int lengthOfBodyContent, String requestType) {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK \r\n");
        sb.append("Content-Type: " + contentType.get(requestType) + "\r\n");
        sb.append("Content-Length: " + lengthOfBodyContent + "\r\n");
        sb.append("\r\n");

        return sb.toString();
    }
}
