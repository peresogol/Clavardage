import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.ServerUDP;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UDPTests {

    ClientUDP client;
    ServerUDP serv;

    @Before
    public void initTestEnvironment() throws Exception {
        client = new ClientUDP();
        serv = new ServerUDP();
    }
    @Test
    public void test() throws Exception {
        serv.start();
        assertEquals("BONJOUR LE MONDE", client.send("BONJOUR LE MONDE"));
        client.close();
    }
}
