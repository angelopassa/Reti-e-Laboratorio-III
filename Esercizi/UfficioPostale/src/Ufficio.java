import java.util.concurrent.*;

public class Ufficio {
    private static final int sportelli = 4;
    private ExecutorService service;

    public Ufficio(int postiSala2, int keepAliveSecondi){
        this.service = new ThreadPoolExecutor(sportelli, sportelli, keepAliveSecondi, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(postiSala2));
    }

    public boolean servi(Runnable r){
        try{
            this.service.submit(r);
            return true;
        }catch (RejectedExecutionException e){
            return false;
        }
    }
}
