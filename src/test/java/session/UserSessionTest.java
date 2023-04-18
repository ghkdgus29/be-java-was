package session;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import session.UserSession;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserSessionTest {

    private static final String COOKIE_NAME = "sid";

    @Test
    @DisplayName("uuid를 key, 현재 로그인한 User 객체를 value 로 userSession 에 저장한다.")
    void sessionSaveAndGet() {
        User user = new User("hyun", "1234", "hyun", "hyun@naver.com");
        String uuid = UserSession.addUser(user);

        Map<String, String> cookies = Map.of(COOKIE_NAME, uuid);
        User sessionUser = UserSession.get(cookies);

        assertThat(sessionUser).isEqualTo(user);
    }

}
