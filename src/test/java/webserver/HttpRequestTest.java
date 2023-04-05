package webserver;

import model.dto.StartLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class HttpRequestTest {

    @Test
    @DisplayName("요청 메시지의 start-line 에서 요청 메서드를 파싱할 수 있다.")
    void parseMethod() {
        String startLineChunk = "GET /index.html HTTP/1.1";

        StartLine startLine = HttpRequest.getStartLine(startLineChunk);

        assertEquals("GET", startLine.getMethod());
    }

    @Test
    @DisplayName("요청 메시지의 start-line 에서 URL을 파싱할 수 있다.")
    void parseUrl() {
        String startLineChunk = "GET /user/create?userId=hyun&password=1234&name=%ED%99%A9%ED%98%84&email=ghkdgus29%40naver.com HTTP/1.1";

        StartLine startLine = HttpRequest.getStartLine(startLineChunk);

        assertEquals("/user/create", startLine.getUrl());
    }

    @Test
    @DisplayName("요청 메시지의 start-line 에 파라미터가 없더라도 URL을 파싱할 수 있다.")
    void parseUrlWithoutParam() {
        String startLineChunk = "GET /index.html HTTP/1.1";

        StartLine startLine = HttpRequest.getStartLine(startLineChunk);

        assertEquals("/index.html", startLine.getUrl());
    }

    @Test
    @DisplayName("요청 메시지의 URL에서 파라미터를 뽑아내 paramMap으로 반환한다.")
    void parseParams() {
        String startLineChunk = "GET /user/create?userId=hyun&password=1234&name=%ED%99%A9%ED%98%84&email=ghkdgus29%40naver.com HTTP/1.1";

        Map<String, String> expectedParams = Map.of("userId", "hyun",
                "password", "1234",
                "name", "%ED%99%A9%ED%98%84",
                "email", "ghkdgus29%40naver.com");

        StartLine startLine = HttpRequest.getStartLine(startLineChunk);

        assertEquals(expectedParams, startLine.getParamMap());
    }

    @Test
    @DisplayName("요청 메시지의 URL에 파라미터가 없으면 paramMap으로 null을 반환한다.")
    void parseNoParams() {
        String startLineChunk = "GET /user/create HTTP/1.1";

        StartLine startLine = HttpRequest.getStartLine(startLineChunk);

        assertNull(startLine.getParamMap());
    }
}
