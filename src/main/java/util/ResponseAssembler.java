package util;

import model.RequestType;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ResponseAssembler {

    public static HttpResponse askHttpResponse(HttpRequest httpRequest, String absolutePath) throws IOException {
        byte[] messageBody = Files.readAllBytes(new File(absolutePath).toPath());

        return new HttpResponse(response200Header(messageBody.length, httpRequest.getRequestType()), messageBody);
    }

    private static String response200Header(int lengthOfBodyContent, RequestType requestType) {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK \r\n");
        sb.append("Content-Type: " + requestType.getContentType() + "\r\n");
        sb.append("Content-Length: " + lengthOfBodyContent + "\r\n");
        sb.append("\r\n");

        return sb.toString();
    }
}
