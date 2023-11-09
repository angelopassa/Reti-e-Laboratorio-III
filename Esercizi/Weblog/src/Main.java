import java.io.*;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Path del log file: ");
        String logpath = keyboard.nextLine();
        System.out.print("Single o Multithread (0=Single, 1=Multi) ? ");
        int singleThread = keyboard.nextInt();

        BufferedReader in = new BufferedReader(new FileReader(logpath));
        String line;
        long start = System.currentTimeMillis();

        if (singleThread == 0){
            while((line = in.readLine()) != null){
                String[] numbers = line.split(" - - ");
                InetAddress addr = InetAddress.getByName(numbers[0]);
                System.out.println(Thread.currentThread().getName() + ": " + addr.getHostName() + " - - " + numbers[1]);
            }
        }else{
            int nCores = Runtime.getRuntime().availableProcessors();
            ExecutorService pool = Executors.newFixedThreadPool(nCores);
            LinkedBlockingQueue<String> coda = new LinkedBlockingQueue<>();
            for (int i = 0; i < nCores; i++)
                pool.submit(new Task(coda));

            while((line = in.readLine()) != null)
                coda.put(line);
            coda.put("finished");

            pool.shutdown();

            while (!pool.isTerminated());
        }

        long end = System.currentTimeMillis();

        System.out.println("Tempo impiegato: " + (end - start) + " ms");
    }
}