package servlet.controller;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.RequestHandler;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UserSaveController implements Controller {

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public String process(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> parameters = httpRequest.getParameters();

        String userId = decodeParameter(parameters, USER_ID);
        String password = decodeParameter(parameters, PASSWORD);
        String name = decodeParameter(parameters, NAME);
        String email = decodeParameter(parameters, EMAIL);

        User user = new User(userId, password, name, email);
        Database.addUser(user);

        logger.debug("생성한 유저 : {}", user);

        return "redirect:/";
    }

    private String decodeParameter(Map<String, String> parameters, String parameterName) {
        String param = parameters.get(parameterName);
        return URLDecoder.decode(param, StandardCharsets.UTF_8);
    }
}
