package dev.ipsych0.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import dev.ipsych0.network.config.KryoConfig;
import dev.ipsych0.network.requests.TestRequest;
import dev.ipsych0.network.responses.TestResponse;

public class MyrinniaServer extends Listener {

    private static Server server;
    private static boolean running;
    public static final int TCP_PORT = 27777, UDP_PORT = 27778;

    private MyrinniaServer() {
    }

    public static void main(String[] args) {
        if (!running) {
            System.out.println("Initializing server...");

            server = new Server();
            KryoConfig.init(server);
            try {
                server.bind(TCP_PORT, UDP_PORT);
                server.start();
                server.addListener(new MyrinniaServer());
            } catch (Exception e) {
                System.err.println("Could not connect to server.");
                e.printStackTrace();
                System.exit(1);
            }

            running = true;
            System.out.println("Server is running!");
        }
    }

    public static Server get() {
        return server;
    }

    @Override
    public void received(Connection connection, Object o) {
        if (o instanceof TestRequest) {
            TestRequest request = (TestRequest) o;
            System.out.println("Request: " + request.getRequest());

            TestResponse response = new TestResponse();
            response.setResponse("Success!");
            connection.sendTCP(response);
        }
    }

    @Override
    public void connected(Connection connection) {
        System.out.println("A user has connected.");
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("A user disconnected.");
    }
}
