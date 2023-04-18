package servlet.controller;

import model.User;
import session.UserSession;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserListController implements Controller {

    @Override
    public String process(HttpRequest httpRequest, HttpResponse httpResponse) {
        User sessionUser = UserSession.get(httpRequest.getCookies());

        if (sessionUser == null) {
            return "redirect:/";
        }

        return "user/list";
    }
}
