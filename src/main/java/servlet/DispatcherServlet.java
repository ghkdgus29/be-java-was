package servlet;

import servlet.controller.Controller;
import servlet.controller.IndexController;
import servlet.controller.UserFormController;
import webserver.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet {

    private static final Map<String, Controller> controllerMap = addController();

    private static Map<String, Controller> addController() {
        HashMap<String, Controller> controllerMap = new HashMap<>();

        controllerMap.put("/", new IndexController());
        controllerMap.put("/user/form.html", new UserFormController());

        return controllerMap;
    }


    public static String service(HttpRequest httpRequest) {
        String requestUrl = httpRequest.getUrl();

        Controller controller = controllerMap.get(requestUrl);
        if (controller == null) {
            return requestUrl;
        }

        return controller.process(httpRequest.getParameters());
    }
}
