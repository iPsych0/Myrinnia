package dev.ipsych0.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import dev.ipsych0.network.config.KryoConfig;
import dev.ipsych0.network.requests.TestRequest;
import dev.ipsych0.network.responses.TestResponse;

public class MyrinniaClient extends Listener {

    private static Client client;
    private static final int TIME_OUT = 5000;
    private static final String CONNECTION_STRING = "localhost";

    private MyrinniaClient() {
    }

    public static void init() {
        client = new Client();
        try {
            client.start();
            client.connect(TIME_OUT, CONNECTION_STRING, MyrinniaServer.TCP_PORT, MyrinniaServer.UDP_PORT);
            client.addListener(new MyrinniaClient());
            KryoConfig.init(client);
        } catch (Exception e) {
            System.err.println("Could not connect to client.");
            e.printStackTrace();
            System.exit(1);
        }
        TestRequest request = new TestRequest();
        request.setRequest("Je moeder");
        client.sendTCP(request);
    }

    public static Client get() {
        return client;
    }

    @Override
    public void received(Connection connection, Object o) {
        if (o instanceof TestResponse) {
            TestResponse response = (TestResponse) o;
            System.out.println("Response: " + response.getResponse());
        }
    }
}
