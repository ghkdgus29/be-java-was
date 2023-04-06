package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StartLineTest {

    private static final String HTML_PATH = "src/main/resources/templates";
    private static final String STYLE_PATH = "src/main/resources/static";

    @Test
    @DisplayName("일반 경로 url 이 생성자에 주입되면, HTML 경로를 반환하고, requestType은 html 이다.")
    void requestHtml() {
        String url = "/";
        String expectedUrl = HTML_PATH + "/index.html";

        StartLine startLine = new StartLine(null, url, null);

        assertEquals(expectedUrl, startLine.getPath());
        assertEquals("html", startLine.getRequestType());
    }

    @Test
    @DisplayName("css를 요청하는 url 이 생성자에 주입되면, CSS 경로를 반환하고 requestType는 css 이다.")
    void requestCss() {
        String url = "/css/bootstrap.min.css";
        String expectedUrl = STYLE_PATH + url;

        StartLine startLine = new StartLine(null, url, null);

        assertEquals(expectedUrl, startLine.getPath());
        assertEquals("css", startLine.getRequestType());
    }
}
