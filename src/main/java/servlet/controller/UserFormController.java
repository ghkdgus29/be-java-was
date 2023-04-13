package servlet.controller;

import webserver.HttpResponse;

import java.util.Map;

public class UserFormController implements Controller{

    @Override
    public String process(Map<String, String> parameters, HttpResponse httpResponse) {
        return "user/form";
    }
}
