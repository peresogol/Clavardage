import org.example.NetworkManager.ClientUDP;
import org.example.NetworkManager.ServerUDP;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UDPTests {

    private ClientUDP client;
    private ServerUDP serv;
    private String str;

    @Before
    public void initTestEnvironment() throws Exception {

    }
    @Test
    public void testEnvoyerRecevoir() throws Exception {
        String[] msg = {"Message recu : test 1", "Message recu : test 2", "Message recu : test 3", "Message recu : test 4", "Message recu : test 5"};

        serv = new ServerUDP(5555, packet -> setVar(new String(packet.getData(), 0, packet.getLength())));
        serv.start();

        for(String s : msg){
            ClientUDP.sendBroadcast(s);
            Thread.sleep(2000);
            assertEquals(s, this.str);
        }
    }

    public void setVar(String s){
        this.str = s;
    }
}


