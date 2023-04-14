package servlet.controller;

import db.Database;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.Map;

class LoginControllerTest {

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";

    @AfterEach
    void clearDB() {
        Database.deleteAll();
    }

    @Test
    @DisplayName("해당 컨트롤러는 파라미터로 넘어온 id, password와 DB회원들의 id, password를 비교하여, 로그인이 성공하면 redirect:/ 를 viewName 으로 반환한다.")
    void loginSuccess() {
        LoginController loginController = new LoginController();

        Database.addUser(new User("hyun", "1234", "황현", "gus@naver.com"));

        String viewName = loginController.process(new HttpRequest("POST /user/login HTTP/1.1", Map.of("mock", "mock"), "userId=hyun&password=1234"), new HttpResponse());
        Assertions.assertThat(viewName).isEqualTo("redirect:/");
    }

    @Test
    @DisplayName("해당 컨트롤러는 파라미터로 넘어온 id, password와 DB회원들의 id, password를 비교하여, 로그인이 실패하면 user/login_failed 를 viewName 으로 반환한다.")
    void loginFail() {
        LoginController loginController = new LoginController();

        Database.addUser(new User("yoon", "1234", "황윤", "yoon@naver.com"));

        String viewName = loginController.process(new HttpRequest("POST /user/login HTTP/1.1", Map.of("mock", "mock"), "userId=hyun&password=1234"), new HttpResponse());
        Assertions.assertThat(viewName).isEqualTo("user/login_failed");
    }

}
