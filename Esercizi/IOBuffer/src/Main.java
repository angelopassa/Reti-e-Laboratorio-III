import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.StandardOpenOption;

/*
File di 381 KB:
    Channel Indiretti: 8ms
    Channel Diretti: 1ms
    Transfer To: 16ms
    Buffered Stream: 0ms
    Not Buffered Stream: 1ms
File di 6.8 MB:
    Channel Indiretti: 16ms
    Channel Diretti: 5ms
    Transfer To: 12ms
    Buffered Stream: 8ms
    Not Buffered Stream: 12ms
*/

public class Main {
    public static void main(String[] args) {
        if (args.length < 2){
            System.out.println("1° Parametro: Path del file sorgente da copiare");
            System.out.println("2° Parametro: Path del file destinazione");
            System.exit(-1);
        }

        String src = args[0];
        String dst = args[1];
        System.out.println("Con Channel Indiretti: " + Main.Channel(src, dst, false) + "ms");
        System.out.println("Con Channel Diretti: " + Main.Channel(src, dst, true) + "ms");
        System.out.println("Con 'Transfer To': " + Main.WithTrasferTo(src, dst) + "ms");
        System.out.println("Con 'BufferedStream': " + Main.BufferedStream(src, dst) + "ms");
        System.out.println("Con Stream non Bufferizzati: " + Main.ByteArray(src, dst) + "ms");
    }

    public static long Channel(String in_filepath, String out_filepath, boolean direct){
        try{
            long start = System.currentTimeMillis();

            ReadableByteChannel in = Channels.newChannel(new FileInputStream(in_filepath));
            WritableByteChannel out = Channels.newChannel(new FileOutputStream(out_filepath));

            ByteBuffer buffer;
            if (direct)
                buffer = ByteBuffer.allocateDirect(16 * 1024);
            else
                buffer = ByteBuffer.allocate(16 * 1024);

            while(in.read(buffer) != -1){
                buffer.flip();

                while (buffer.hasRemaining())
                    out.write(buffer);

                buffer.clear();
            }

            in.close();
            out.close();

            long end = System.currentTimeMillis();

            return end - start;
        }catch (Exception e){
            System.out.println(e.getMessage() + 1);
        }
        return -1;
    }

    public static long WithTrasferTo(String in_filepath, String out_filepath){
        try{
            long start = System.currentTimeMillis();

            File in_file = new File(in_filepath);
            FileChannel in = FileChannel.open(in_file.toPath(), StandardOpenOption.READ);
            File out_file = new File(out_filepath);
            FileChannel out = FileChannel.open(out_file.toPath(), StandardOpenOption.WRITE, StandardOpenOption.CREATE);;

            out.transferFrom(in, 0, in.size());
            in.close();
            out.close();

            long end = System.currentTimeMillis();

            return end - start;
        }catch (Exception e){
            System.out.println(e.getMessage() + 2);
        }
        return -1;
    }

    public static long BufferedStream(String in_filepath, String out_filepath){
        try {
            long start = System.currentTimeMillis();

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(in_filepath));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(out_filepath));

            byte[] array;
            while((array = in.readAllBytes()).length != 0)
                out.write(array);

            in.close();
            out.close();

            long end = System.currentTimeMillis();

            return end - start;

        } catch (Exception e) {
            System.out.println(e.getMessage() + 3);
        }

        return -1;
    }

    public static long ByteArray(String in_filepath, String out_filepath){
        try{
            long start = System.currentTimeMillis();

            FileInputStream in = new FileInputStream(in_filepath);
            FileOutputStream out = new FileOutputStream(out_filepath);

            byte[] array;
            while((array = in.readAllBytes()).length != 0)
                out.write(array);

            in.close();
            out.close();

            long end = System.currentTimeMillis();

            return end - start;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return -1;
    }
}