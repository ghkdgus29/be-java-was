package util;

import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserSessionTest {

    private static final String COOKIE_NAME = "sid";

    @Test
    @DisplayName("uuid를 key, 현재 로그인한 User 객체를 value 로 userSession 에 저장한다.")
    void sessionSaveAndGet() {
        String uuid = UUID.randomUUID().toString();
        User user = new User("hyun", "1234", "hyun", "hyun@naver.com");
        UserSession.addUser(uuid, user);

        Map<String, String> cookies = Map.of(COOKIE_NAME, uuid);
        User sessionUser = UserSession.get(cookies);

        assertThat(sessionUser).isEqualTo(user);
    }

}
