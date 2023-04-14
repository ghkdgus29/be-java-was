package servlet.controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.Map;

public class UserFormController implements Controller{

    @Override
    public String process(HttpRequest httpRequest, HttpResponse httpResponse) {
        return "user/form";
    }
}
