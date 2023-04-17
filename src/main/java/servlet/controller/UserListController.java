package servlet.controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public class UserListController implements Controller {

    @Override
    public String process(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "user/list";
    }
}
