package com.simeon;

import com.fastcgi.FCGIInterface;
import com.simeon.view.ViewResolver;
import lombok.extern.java.Log;

import java.util.logging.Level;

@Log
public class Application {
    private final Dispatcher dispatcher;

    public Application() {
        MessageConverter messageConverter = new MessageConverter();
        ViewResolver viewResolver = new ViewResolver(messageConverter);
        HandlerMapping handlerMapping = new HandlerMapping();
        CheckAreaController controller = new CheckAreaController();
        handlerMapping.addController(controller);

        HandlerAdapter handlerAdapter = new HandlerAdapter(messageConverter);

        this.dispatcher = new Dispatcher(viewResolver, handlerAdapter, handlerMapping);
    }

    public void start() {
        FCGIInterface fcgiInterface = new FCGIInterface();
        while (fcgiInterface.FCGIaccept() >= 0) {
            // Отправка ответа через FCGIOutputStream
            try {
                StringBuilder requestBody = new StringBuilder();
                int c = System.in.read();
                while (c != -1) {
                    requestBody.append((char) c);
                    c = System.in.read();
                }
                String requestUri = System.getProperty("REQUEST_URI");
                int index = requestUri.indexOf(System.getProperty("sun.java.command"));

                HttpRequest httpRequest = new HttpRequest(
                        System.getProperty("REQUEST_METHOD"),
                        requestUri.substring(index
                                        + System.getProperty("sun.java.command").length()
                        ),
                        System.getProperty("QUERY_STRING"),
                        requestBody.toString()
                );

                String httpResponse = dispatcher.handle(httpRequest);

                System.out.println(httpResponse);

            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        }
    }
}
