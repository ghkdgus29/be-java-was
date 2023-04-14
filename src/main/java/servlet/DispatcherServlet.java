package servlet;

import servlet.controller.*;
import util.StatusCode;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet {

    private static final int REDIRECT_URL_IDX = 1;
    private static final Map<String, Controller> controllerMap = addController();

    private static Map<String, Controller> addController() {
        HashMap<String, Controller> controllerMap = new HashMap<>();

        controllerMap.put("/", new IndexController());
        controllerMap.put("/index.html", new IndexController());
        controllerMap.put("/user/form.html", new UserFormController());
        controllerMap.put("/user/create", new UserSaveController());
        controllerMap.put("/user/login.html", new LoginFormController());
        controllerMap.put("/user/login", new LoginController());

        return controllerMap;
    }


    public static void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String requestUrl = httpRequest.getUrl();
        Controller controller = controllerMap.getOrDefault(requestUrl, new DefaultController());            // 컨트롤러 맵에 없는 요청의 경우, DefaultController 호출

        String viewName = controller.process(httpRequest, httpResponse);

        if (viewName.startsWith("redirect:")) {                                                             // redirect 요청일 경우
            String redirectUrl = viewName.split(":")[REDIRECT_URL_IDX];
            httpResponse.setStatusCode(StatusCode.FOUND);
            httpResponse.setRedirectUrl(redirectUrl);
        }

        httpResponse.setRequestType(viewName);
        httpResponse.setContent(viewName);
    }
}
