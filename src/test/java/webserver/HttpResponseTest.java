package webserver;

import model.StartLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Comparator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpResponseTest {

    @Test
    @DisplayName("클라이언트가 HTML 을 요청하는 경우, HTTP Response 헤더의 Content-Type 은 text/html 이다.")
    void requestHTML() {
        String url = "/";
        byte[] body = "hiiiiiiiiiiiiiii\n".getBytes();


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpResponse.sendResponse200(new DataOutputStream(outputStream), body, new StartLine("GET", url, Map.of("mock", "mock")));

        String expectedResponseMessage = "HTTP/1.1 200 OK \r\n" +
                "Content-Type: text/html;charset=utf-8\r\n" +
                "Content-Length: 17\r\n" +
                "\r\n" +
                "hiiiiiiiiiiiiiii\n";

        assertEquals(expectedResponseMessage, outputStream.toString());
    }

    @Test
    @DisplayName("클라이언트가 CSS 를 요청하는 경우, HTTP Response 헤더의 Content-Type 은 text/css 이다.")
    void requestCSS() {
        String url = "/css/bootstrap.min.css";
        byte[] body = "hiiiiiiiiiiiiiii\n".getBytes();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpResponse.sendResponse200(new DataOutputStream(outputStream), body, new StartLine("GET", url, Map.of("mock", "mock")));

        String expectedResponseMessage = "HTTP/1.1 200 OK \r\n" +
                "Content-Type: text/css\r\n" +
                "Content-Length: 17\r\n" +
                "\r\n" +
                "hiiiiiiiiiiiiiii\n";

        assertEquals(expectedResponseMessage, outputStream.toString());
    }

    @Test
    @DisplayName("클라이언트가 JS 를 요청하는 경우, HTTP Response 헤더의 Content-Type 은 application/javascript 이다.")
    void requestJS() {
        String url = "/js/scripts.js";
        byte[] body = "hiiiiiiiiiiiiiii\n".getBytes();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpResponse.sendResponse200(new DataOutputStream(outputStream), body, new StartLine("GET", url, Map.of("mock", "mock")));

        String expectedResponseMessage = "HTTP/1.1 200 OK \r\n" +
                "Content-Type: application/javascript\r\n" +
                "Content-Length: 17\r\n" +
                "\r\n" +
                "hiiiiiiiiiiiiiii\n";

        assertEquals(expectedResponseMessage, outputStream.toString());
    }

    @Test
    @DisplayName("클라이언트가 PNG 를 요청하는 경우, HTTP Response 헤더의 Content-Type 은 image/png 이다.")
    void requestPNG() {
        String url = "/please/giveMe.png";
        byte[] body = "hiiiiiiiiiiiiiii\n".getBytes();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpResponse.sendResponse200(new DataOutputStream(outputStream), body, new StartLine("GET", url, Map.of("mock", "mock")));

        String expectedResponseMessage = "HTTP/1.1 200 OK \r\n" +
                "Content-Type: image/png\r\n" +
                "Content-Length: 17\r\n" +
                "\r\n" +
                "hiiiiiiiiiiiiiii\n";

        assertEquals(expectedResponseMessage, outputStream.toString());
    }

    @Test
    @DisplayName("클라이언트가 ICO 를 요청하는 경우, HTTP Response 헤더의 Content-Type 은 image/avif 이다.")
    void requestICO() {
        String url = "/favicon.ico";
        byte[] body = "hiiiiiiiiiiiiiii\n".getBytes();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpResponse.sendResponse200(new DataOutputStream(outputStream), body, new StartLine("GET", url, Map.of("mock", "mock")));

        String expectedResponseMessage = "HTTP/1.1 200 OK \r\n" +
                "Content-Type: image/avif\r\n" +
                "Content-Length: 17\r\n" +
                "\r\n" +
                "hiiiiiiiiiiiiiii\n";
    }

}
