package webserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestMessageParserTest {

    @Test
    @DisplayName("요청 메시지의 start-line 에서 URL을 파싱할 수 있다.")
    void parseUrl() {
        String firstLine = "GET /index.html HTTP/1.1";

        String url = HttpRequestMessageParser.parseUrl(firstLine);

        assertEquals("/index.html", url);
    }

    @Test
    @DisplayName("요청 메시지의 URL에서 파라미터를 뽑아내 paramMap으로 반환한다.")
    void parseParams() {
        String url = "/user/create?userId=hyun&password=1234&name=%ED%99%A9%ED%98%84&email=ghkdgus29%40naver.com";

        Map<String, String> expectedParams = Map.of("userId", "hyun",
                "password", "1234",
                "name", "%ED%99%A9%ED%98%84",
                "email", "ghkdgus29%40naver.com");

        Map<String, String> params = HttpRequestMessageParser.parseParams(url);

        assertEquals(expectedParams, params);
    }

    @Test
    @DisplayName("요청 메시지의 URL에 파라미터가 없으면 paramMap으로 null을 반환한다.")
    void parseNoParams() {
        String url = "/user/create";

        Map<String, String> params = HttpRequestMessageParser.parseParams(url);

        assertNull(params);
    }
}
