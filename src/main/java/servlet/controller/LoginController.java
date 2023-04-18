package servlet.controller;

import db.Database;
import model.User;
import util.UserSession;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.Map;
import java.util.Optional;

public class LoginController implements Controller{

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String SESSION_KEY = "sid";


    @Override
    public String process(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> parameters = httpRequest.getParameters();
        String userId = parameters.get(USER_ID);
        String password = parameters.get(PASSWORD);

        Optional<User> findUser = Database.findAll().stream()
                .filter(user -> user.getUserId().equals(userId))
                .filter(user -> user.getPassword().equals(password))
                .findFirst();

        if (findUser.isEmpty()) {
            return "user/login_failed";
        }

        String uuid = UserSession.addUser(findUser.get());
        httpResponse.setCookie(SESSION_KEY, uuid);

        return "redirect:/";
    }
}
