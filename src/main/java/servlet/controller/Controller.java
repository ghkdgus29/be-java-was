package servlet.controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.Map;

public interface Controller {

    String process(HttpRequest httpRequest, HttpResponse httpResponse);
}
