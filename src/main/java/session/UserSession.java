package session;

import model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserSession {

    private static final String SESSION_KEY = "sid";

    private static final Map<String, User> userSession  = new HashMap<>();


    public static String addUser(User user) {
        String uuid = UUID.randomUUID().toString();
        userSession.put(uuid, user);
        return uuid;
    }

    public static User get(Map<String, String> cookies) {
        if (!hasSession(cookies)) {
            return null;
        }

        String uuid = cookies.get(SESSION_KEY);
        return userSession.get(uuid);
    }

    private static Boolean hasSession(Map<String, String> cookies) {
        if (cookies == null) {
            return false;
        }
        if (!cookies.containsKey(SESSION_KEY)) {
            return false;
        }

        return userSession.containsKey(cookies.get(SESSION_KEY));
    }
}
