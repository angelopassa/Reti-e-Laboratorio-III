import java.io.*;
import java.util.concurrent.*;

public class Main {
    private static ExecutorService threadpool;

    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Specificare almeno una directory");
            System.exit(-1);
        }

        Main.threadpool = Executors.newCachedThreadPool();

        for (String nomeDir : args){
            File dir = new File(nomeDir);
            File[] contents = dir.listFiles();

            if (contents == null){
                System.out.println(nomeDir + " non è una directory!");
                System.exit(-1);
            }

            Main.directoryRecursive(contents);
        }

        Main.threadpool.shutdown();
    }

    private static void directoryRecursive(File[] contents){
        for (File file : contents){
            if (file.isFile()){
                Main.threadpool.submit(new ComprimiGZIP(file.getAbsolutePath()));
            }else if(file.isDirectory()){
                Main.directoryRecursive(file.listFiles());
            }
        }
    }
}