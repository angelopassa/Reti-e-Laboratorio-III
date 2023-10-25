import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        ConcurrentHashMap<Character, Integer> map = new ConcurrentHashMap<Character, Integer>();

        for(String filepath : args){
            pool.submit(new ContaFile(map, filepath));
        }

        pool.shutdown();
        while (!pool.isTerminated());

        try{
            BufferedWriter out = new BufferedWriter(new FileWriter("output.txt"));
            Set<ConcurrentHashMap.Entry<Character, Integer>> entrySet = map.entrySet();
            for (ConcurrentHashMap.Entry<Character, Integer> entry : entrySet) {
                out.write(entry.getKey() + ", " + entry.getValue());
                out.newLine();
            }
            out.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}