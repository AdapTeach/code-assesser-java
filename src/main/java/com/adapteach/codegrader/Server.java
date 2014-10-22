package com.adapteach.codegrader;

import com.google.gson.JsonSyntaxException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class Server {

    private static void enableCORS(final String origin, final String methods, final String headers) {
        before((Request request, Response response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
        });
    }

    private final int portNumber;

    private boolean started;

    public Server() {
        this(4567);
    }

    public Server(int portNumber) {
        this.portNumber = portNumber;
    }

    public void start() {

        if (started) return;
        started = true;

        setPort(portNumber);

        enableCORS("*", "*", "*");

        Injector injector = Guice.createInjector(new MainModule());

        Controller controller = injector.getInstance(Controller.class);
        controller.publish();

        after((request, response) -> response.type("application/json"));

        exception(JsonSyntaxException.class, (e, request, response) -> {
            response.status(400);
            response.body("Malformed JSON : " + e.getCause().getMessage());
        });

        exception(NumberFormatException.class, (e, request, response) -> {
            response.status(400);
            e.printStackTrace();
            response.body("Unsupported JSON Format : A number was expected " + e.getMessage());
        });

    }

}
