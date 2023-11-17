import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class Client {
    public static int port = 1060;

    public static void main(String[] args){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("--Inserisci stringa da inviare al server");
        String data = keyboard.nextLine();
        ByteBuffer buffer = ByteBuffer.allocateDirect(32 * 1024);
        for (char x : data.toCharArray())
            buffer.putChar(x);

        try(SocketChannel client = SocketChannel.open()) {
            client.connect(new InetSocketAddress("localhost", Client.port));
            buffer.flip();
            client.write(buffer);

            buffer.clear();
            client.read(buffer);
            buffer.flip();

            StringBuilder out = new StringBuilder();
            while(buffer.hasRemaining())
                out.append(buffer.getChar());

            System.out.println("--Risposta del server--");
            System.out.println(out);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
