import org.example.NetworkManager.SendUDP;
import org.example.NetworkManager.ReceiveUDP;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UDPTests {

    SendUDP client;
    ReceiveUDP serv;

    @Before
    public void initTestEnvironment() throws Exception {
        client = new SendUDP(4444);
        serv = new ReceiveUDP(4444);
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
