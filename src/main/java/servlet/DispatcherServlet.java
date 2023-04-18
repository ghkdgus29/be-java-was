package servlet;

import model.User;
import servlet.controller.*;
import session.UserSession;
import util.RequestMethod;
import util.StatusCode;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet {

    private static final int REDIRECT_URL_IDX = 1;
    private static final Map<String, Controller> getControllerMap = addGetController();

    private static final Map<String, Controller> postControllerMap = addPostController();

    private static Map<String, Controller> addGetController() {
        HashMap<String, Controller> controllerMap = new HashMap<>();

        controllerMap.put("/", new IndexController());
        controllerMap.put("/index.html", new IndexController());
        controllerMap.put("/user/form.html", new UserFormController());
        controllerMap.put("/user/login.html", new LoginFormController());
        controllerMap.put("/user/list.html", new UserListController());

        return controllerMap;
    }

    private static Map<String, Controller> addPostController() {
        HashMap<String, Controller> controllerMap = new HashMap<>();

        controllerMap.put("/user/create", new UserSaveController());
        controllerMap.put("/user/login", new LoginController());

        return controllerMap;
    }


    public static void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        Controller controller = getController(httpRequest.getMethod(), httpRequest.getUrl());
        String viewName = controller.process(httpRequest, httpResponse);

        if (viewName.startsWith("redirect:")) {                                                                 // redirect 요청일 경우
            String redirectUrl = viewName.split(":")[REDIRECT_URL_IDX];
            httpResponse.setStatusCode(StatusCode.FOUND);
            httpResponse.setRedirectUrl(redirectUrl);
        }

        User sessionUser = UserSession.get(httpRequest.getCookies());

        httpResponse.setRequestType(viewName);
        httpResponse.setContent(viewName, sessionUser);
    }

    private static Controller getController(String requestMethod, String requestUrl) {
        if (requestMethod.equals(RequestMethod.GET)) {
            return getControllerMap.getOrDefault(requestUrl, new DefaultController());
        }

        return postControllerMap.getOrDefault(requestUrl, new DefaultController());
    }
}
