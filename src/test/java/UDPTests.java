import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.ServerUDP;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UDPTests {

    private ClientUDP client;
    private ServerUDP serv;

    @Before
    public void initTestEnvironment() throws Exception {
        client = new ClientUDP();
        serv = new ServerUDP(5555);
        serv.start();
    }
    @Test
    public void testEnvoyerRecevoir() throws Exception {
        String str = "Message recu : " + "test 1";
        client.sendBroadcast(str);

    }
}
