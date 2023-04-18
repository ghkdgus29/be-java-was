package session;

import model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserSession {

    private static final String COOKIE_NAME = "sid";

    private static final Map<String, User> userSession  = new HashMap<>();


    public static String addUser(User user) {
        String uuid = UUID.randomUUID().toString();
        userSession.put(uuid, user);
        return uuid;
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

    public static Boolean contains(Map<String, String> cookies) {
        if (cookies == null) {
            return false;
        }
        if (!cookies.containsKey(COOKIE_NAME)) {
            return false;
        }

        return userSession.containsKey(cookies.get(COOKIE_NAME));
    }
}
