package dev.ipsych0.network.config;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import dev.ipsych0.network.requests.TestRequest;
import dev.ipsych0.network.responses.TestResponse;

public class KryoConfig {
    private static Kryo kryo;

    private KryoConfig() {
    }

    public static void init(EndPoint connector) {
        if (connector == null) throw new IllegalArgumentException("Connector cannot be null!");

        if (kryo != null)
            throw new IllegalArgumentException("That connector is already configured.");

        kryo = connector.getKryo();
        register(kryo);
    }

    private static void register(Kryo kryo) {
        kryo.register(TestRequest.class);
        kryo.register(TestResponse.class);
    }

}
