package servlet.controller;

import webserver.HttpResponse;

import java.util.Map;

public interface Controller {

    String process(Map<String, String> parameters, HttpResponse httpResponse);
}
