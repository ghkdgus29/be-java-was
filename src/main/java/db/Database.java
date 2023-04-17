package db;

import com.google.common.collect.Maps;
import model.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Database {
    private static Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static List<User> findAll() {
        return users.entrySet().stream()
                .map(e -> e.getValue())
                .collect(Collectors.toList());
    }

    public static void deleteAll() {
        users = Maps.newHashMap();
    }
}
