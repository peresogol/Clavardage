import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.ServerUDP;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UDPTests {

    ClientUDP client;
    ServerUDP serv;

    @Before
    public void initTestEnvironment() throws Exception {
        client = new ClientUDP(4444);
        serv = new ServerUDP(4444);
    }
    @Test
    public void testEnvoyerRecevoir() throws Exception {
        serv.start();
        client.send("BONJOUR LE MONDE");
        Thread.sleep(10);
        String tmp = serv.getReceived();
        assertEquals("BONJOUR LE MONDE", tmp);
        client.close();
    }
}
