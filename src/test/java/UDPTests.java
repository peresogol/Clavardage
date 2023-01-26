import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.ServerUDP;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UDPTests {

    private ClientUDP client;
    private ServerUDP serv;
    private static String str = null;

    @Before
    public void initTestEnvironment() throws Exception {
        serv = new ServerUDP(5555, packet -> setVar(new String(packet.getData(), 0, packet.getLength())));
        serv.start();
        ClientUDP.setDestPort(5555);
    }

    @After
    public void removeStringValue() throws Exception {
        str = null;
    }


    @Test
    public void testMessage() throws Exception {
        String s = "message";
        InetAddress addr = InetAddress.getByName("localhost");
        ClientUDP.sendMessage(s, addr);
        int cpt = 0;
        while(str == null && cpt < 10) {
            Thread.sleep(1000);
            cpt++;
            ClientUDP.sendMessage(s, addr);
        }
        assertEquals(s, this.str);
    }

    @Test
    public void testBroadcast() throws Exception {
        String s = "message";
        ClientUDP.sendBroadcast(s);
        int cpt = 0;
        while(str == null && cpt < 10) {
            Thread.sleep(1000);
            cpt++;
            ClientUDP.sendBroadcast(s);
        }
        assertEquals(s, this.str);
    }

    public void setVar(String s){
        str = s;
    }
}


