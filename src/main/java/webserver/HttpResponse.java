package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;

public class HttpResponse {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public static void sendResponse200(DataOutputStream dos, byte[] body) {
        try {
            dos.writeBytes(response200Header(body.length));

            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static String response200Header(int lengthOfBodyContent) {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK \r\n");
        sb.append("Content-Type: text/html;charset=utf-8\r\n");
        sb.append("HTTP/1.1 200 OK \r\n");
        sb.append("Content-Type: text/html;charset=utf-8\r\n");
        sb.append("Content-Length: " + lengthOfBodyContent + "\r\n");
        sb.append("\r\n");

        return sb.toString();
    }

}
