import java.io.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Numero di thread: ");
        String input = keyboard.readLine();
        int nThreads = 0;
        try {
            nThreads = Integer.parseInt(input);
        }catch (Exception e){
            System.out.println("Inserire unxxx numero");
        }

        ExecutorService executor = null;
        try {
            executor = Executors.newFixedThreadPool(nThreads);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        System.out.print("Numero di task: ");
        input = keyboard.readLine();
        int nTasks = 0;
        try {
            nTasks = Integer.parseInt(input);
        }catch (Exception e){
            System.out.println("Inserire un numero");
        }

        for (int i = 0; i < nTasks; i++){
            executor.submit(new isPrime());
        }
    }
}