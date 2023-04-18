package servlet.controller;

import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.UserSession;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.Map;

class UserListControllerTest {

    @Test
    @DisplayName("/user/list.html 요청 시, 로그인 한 유저는 유저 목록을 보여주는 user/list 뷰네임을 반환한다.")
    void process() {
        String uuid = UserSession.addUser(new User("hyun", "1234", "hyun", "hyun@naver.com"));
        HttpRequest httpRequest = new HttpRequest("GET /user/list.html HTTP/1.1", Map.of("Cookie", "sid=" + uuid), null);
        HttpResponse httpResponse = new HttpResponse();

        UserListController userListController = new UserListController();
        String viewName = userListController.process(httpRequest, httpResponse);

        Assertions.assertThat(viewName).isEqualTo("user/list");
    }

    @Test
    @DisplayName("/user/list.html 요청 시, 로그인 하지 않은 경우엔 메인페이지로 리디렉션한다.")
    void processFail() {
        UserSession.addUser(new User("hyun", "1234", "hyun", "hyun@naver.com"));
        HttpRequest httpRequest = new HttpRequest("GET /user/list.html HTTP/1.1", Map.of("mock", "mock"), null);
        HttpResponse httpResponse = new HttpResponse();

        UserListController userListController = new UserListController();
        String viewName = userListController.process(httpRequest, httpResponse);

        Assertions.assertThat(viewName).isEqualTo("redirect:/");
    }
}
