import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;

public class Task implements Runnable{
    LinkedBlockingQueue<String> coda;

    public Task(LinkedBlockingQueue<String> coda){
        this.coda = coda;
    }

    @Override
    public void run() {
        try {
            String line;
            while (!((line = coda.take()).equals("finished"))){
                String[] numbers = line.split(" - - ");
                InetAddress addr = InetAddress.getByName(numbers[0]);
                System.out.println(Thread.currentThread().getName() + ": " + addr.getHostName() + " - - " + numbers[1]);
            }
            coda.put(line);
        } catch (InterruptedException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
