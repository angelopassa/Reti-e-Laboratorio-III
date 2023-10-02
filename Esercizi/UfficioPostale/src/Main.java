import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService generaPersone = Executors.newSingleThreadScheduledExecutor();
        generaPersone.scheduleAtFixedRate(new GeneraPersona(), 0, 1500, TimeUnit.MILLISECONDS);
        //ogni persona entra nell'ufficio ogni secondo e mezzo

        Ufficio u = new Ufficio(4, 4);

        while (true){
            Persona p = (Persona) GeneraPersona.queue.poll(3, TimeUnit.SECONDS);
            if (!u.servi(p)){
                GeneraPersona.queue.add(p);
            }
        }
    }
}