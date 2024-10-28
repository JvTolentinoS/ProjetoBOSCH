package conexaofabrica;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPAddress {
    public static void main(String[] args) {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println("IP do computador: " + ip.getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("Erro ao obter o IP.");
            e.printStackTrace();
        }
    }
}
