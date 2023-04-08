package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StartLineTest {

    private static final String TEMPLATES_PATH = "src/main/resources/templates";
    private static final String STATIC_PATH = "src/main/resources/static";

    @Test
    @DisplayName("일반 경로 url 이 생성자에 주입되면, HTML 경로를 반환하고, requestType은 html 이다.")
    void requestHtml() {
        String mockMethod = "mock";
        String url = "/";
        Map<String, String> mockParam = Map.of("mock", "mock");

        String expectedUrl = TEMPLATES_PATH + "/index.html";
        StartLine startLine = new StartLine(mockMethod, url, mockParam);

        assertEquals(expectedUrl, startLine.getPath());
        assertEquals(RequestType.HTML, startLine.getRequestType());
    }

    @Test
    @DisplayName("css를 요청하는 url 이 생성자에 주입되면, CSS 경로를 반환하고 requestType는 css 이다.")
    void requestCss() {
        String mockMethod = "mock";
        String url = "/css/bootstrap.min.css";
        Map<String, String> mockParam = Map.of("mock", "mock");

        String expectedUrl = STATIC_PATH + url;
        StartLine startLine = new StartLine(mockMethod, url, mockParam);

        assertEquals(expectedUrl, startLine.getPath());
        assertEquals(RequestType.CSS, startLine.getRequestType());
    }
}
