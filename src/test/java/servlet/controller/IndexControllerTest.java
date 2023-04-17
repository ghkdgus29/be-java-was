package servlet.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class IndexControllerTest {

    @Test
    @DisplayName("/ 요청이 들어오면 해당 컨트롤러를 호출하고, index 뷰 네임을 반환한다.")
    void process() {
        IndexController indexController = new IndexController();

        String viewName = indexController.process(null, new HttpResponse());
        assertEquals("index", viewName);
    }
}