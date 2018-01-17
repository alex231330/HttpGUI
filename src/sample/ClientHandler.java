package sample;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by alex231330 on 26.06.17.
 */
public class ClientHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String req = null;
        InputStream is = httpExchange.getRequestBody();
        is.read(req.getBytes());
    }

}

