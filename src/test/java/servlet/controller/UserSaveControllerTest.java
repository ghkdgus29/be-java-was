package servlet.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserSaveControllerTest {

    @Test
    @DisplayName("/user/create 요청이 들어오면, 해당 컨트롤러를 호출하여 User를 생성하고 redirect:/ 뷰 네임을 반환한다.")
    void process() {
        UserSaveController userSaveController = new UserSaveController();

        String viewName = userSaveController.process(Map.of("userId", "1234", "password", "1234", "name", "123", "email", "123%40123"));

        assertEquals("redirect:/", viewName);
    }
}