import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class Server {
    public static int port = 1060;
    public static String response = "echoed by server - ";

    public static void main(String[] args) {
        try(ServerSocketChannel server = ServerSocketChannel.open(); Selector selector = Selector.open()){

            ServerSocket socket = server.socket();
            socket.bind(new InetSocketAddress(Server.port));
            server.configureBlocking(false);

            server.register(selector, SelectionKey.OP_ACCEPT);
            while (selector.select() != 0){
                Iterator<SelectionKey> readyKeys = selector.selectedKeys().iterator();
                while(readyKeys.hasNext()){
                    SelectionKey key = readyKeys.next();
                    readyKeys.remove();
                    if (key.isAcceptable()){
                        ServerSocketChannel serverKey = (ServerSocketChannel) key.channel();
                        SocketChannel client = serverKey.accept();
                        System.out.println("Client connesso" + client.getRemoteAddress());
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                    }else if(key.isReadable()){
                        SocketChannel client = (SocketChannel) key.channel();
                        System.out.println("Leggo da: " + client.getRemoteAddress());
                        ByteBuffer buffer = ByteBuffer.allocateDirect(32 * 1024);
                        for (char x : Server.response.toCharArray())
                            buffer.putChar(x);
                        client.read(buffer);
                        buffer.flip();
                        client.write(buffer);
                        key.cancel();
                        client.close();
                    }
                }
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}