package util;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class UserSession {

    private static final String COOKIE_NAME = "sid";

    private static final Map<String, User> userSession  = new HashMap<>();


    public static void addUser(String uuid, User user) {
        userSession.put(uuid, user);
    }

    public static User get(Map<String, String> cookies) {
        if (!cookies.containsKey(COOKIE_NAME)) {
            throw new IllegalArgumentException("[ERROR] 로그인이 필요합니다.");
        }

        String uuid = cookies.get(COOKIE_NAME);

        if (!userSession.containsKey(uuid)) {
            throw new IllegalArgumentException("[ERROR] 로그인이 필요합니다.");
        }

        return userSession.get(uuid);
    }

}
