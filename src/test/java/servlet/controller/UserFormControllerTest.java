package servlet.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserFormControllerTest {

    @Test
    @DisplayName("/user/form.html 요청이 들어오면 해당 컨트롤러를 호출하고, user/form 뷰 네임을 반환한다.")
    void process() {
        UserFormController userFormController = new UserFormController();
        String viewName = userFormController.process(null);

        assertEquals("user/form", viewName);
    }
}