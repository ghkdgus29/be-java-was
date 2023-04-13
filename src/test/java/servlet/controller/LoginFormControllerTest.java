package servlet.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginFormControllerTest {

    @Test
    @DisplayName("/user/login.html 요청이 들어오면 해당 컨트롤러를 호출하고, user/login.html 뷰 네임을 반환한다.")
    void process() {
        LoginFormController loginFormController = new LoginFormController();

        String viewName = loginFormController.process(null);
        assertEquals("user/login", viewName);
    }
}