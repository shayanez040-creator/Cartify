import com.sun.net.httpserver.HttpServer;
import handlers.LoginHandler;
import handlers.ProductHandler;

import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/login", new LoginHandler());
        server.createContext("/products", new ProductHandler());

        server.setExecutor(null);
        server.start();

        System.out.println("Backend running at http://localhost:8000/");
    }
}
