package servlet.controller;

import db.Database;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpResponse;

import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserSaveControllerTest {

    @AfterEach
    void clearDB() {
        Database.deleteAll();
    }

    @Test
    @DisplayName("/user/create 요청이 들어오면, 해당 컨트롤러를 호출하여 User를 생성하고 redirect:/ 뷰 네임을 반환한다.")
    void process() {
        UserSaveController userSaveController = new UserSaveController();

        String viewName = userSaveController.process(Map.of("userId", "1234", "password", "1234", "name", "123", "email", "123%40123"), new HttpResponse());

        assertEquals("redirect:/", viewName);

        Collection<User> users = Database.findAll();
        User findUser = Database.findUserById("1234");

        assertThat(users.size()).isEqualTo(1);

        assertThat(findUser.getUserId()).isEqualTo("1234");
        assertThat(findUser.getPassword()).isEqualTo("1234");
        assertThat(findUser.getName()).isEqualTo("123");
        assertThat(findUser.getEmail()).isEqualTo("123%40123");
    }
}