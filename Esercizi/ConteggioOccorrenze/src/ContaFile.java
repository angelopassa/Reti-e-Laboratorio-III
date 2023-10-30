import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class ContaFile implements Runnable{
    private ConcurrentHashMap<Character, Integer> map;
    private String filepath;

    public ContaFile(ConcurrentHashMap<Character, Integer> map, String filepath){
        this.map = map;
        this.filepath = filepath;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filepath));
            int c;
            while((c = in.read()) != -1){
                c = Character.toLowerCase(c);
                if(c >= 97 && c <= 122 && map.putIfAbsent((char)(c), 1) != null)
                    map.computeIfPresent((char)(c), ((Character k, Integer x) -> x + 1));
            }
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
