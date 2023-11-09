import java.net.*;
import java.util.concurrent.*;

public class Server {
    public static final int PORT = 1600;

    public static void main(String[] args) {
        int core = 8;

        try(
                ServerSocket socket = new ServerSocket(PORT);
                ExecutorService worker_pool = new ThreadPoolExecutor(core, core, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>())
            )
        {
            while (true){
                Socket client = socket.accept();
                worker_pool.submit(new ServerWorker(client));
                System.out.println("Nuovo client connesso su " + client.getPort());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
