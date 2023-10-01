import java.util.concurrent.*;

public class Main {
    public static void main(String[] args)  {

        if (args.length != 2){
            System.out.println("Numero di parametri sbagliato. \n 1° parametro: # di thread. \n 2° parametro: # di task.");
            return;
        }
        int nThreads = Integer.parseInt(args[0]);
        int nTasks = Integer.parseInt(args[1]);

        ExecutorService executor = null;
        try {
            executor = Executors.newFixedThreadPool(nThreads);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < nTasks; i++){
            executor.submit(new isPrime());
        }

        executor.shutdown();
    }
}