package servlet.controller;

import session.UserSession;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.Map;

public class UserListController implements Controller {

    @Override
    public String process(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> cookies = httpRequest.getCookies();

        if (!UserSession.contains(cookies)) {
            return "redirect:/";
        }

        return "user/list";
    }
}
