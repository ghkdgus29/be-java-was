package servlet.controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

public class DefaultController implements Controller{

    @Override
    public String process(HttpRequest httpRequest, HttpResponse httpResponse) {
        return httpRequest.getUrl();                                                    // css, js 와 같은 static 요청 url 을 받아서 그냥 내보낸다.
    }
}
