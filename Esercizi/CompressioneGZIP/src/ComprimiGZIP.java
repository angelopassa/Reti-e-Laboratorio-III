import java.io.*;
import java.util.zip.*;

public class ComprimiGZIP implements Runnable{
    private String filepath;

    public ComprimiGZIP(String filepath){
        this.filepath = filepath;
    }

    @Override
    public void run() {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filepath));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filepath + ".gz"));
            BufferedOutputStream gzip = new BufferedOutputStream(new GZIPOutputStream(out));

            byte[] content = in.readAllBytes();
            gzip.write(content);

            gzip.close();
            out.close();
            in.close();

        } catch (FileNotFoundException e) {
            System.out.println(filepath + ": file non trovato.");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Errore di I/0" + e.getMessage());
            System.exit(-1);
        }
    }
}
