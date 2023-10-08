import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        if (args.length != 2){
            System.out.println("Numero di parametri non corretto:\n1° parametro: Nr. posti Sala 2.\n2° parametro: Intervallo di chiusura sportello.");
            return;
        }

        ScheduledExecutorService generaPersone = Executors.newSingleThreadScheduledExecutor();
        generaPersone.scheduleAtFixedRate(new GeneraPersona(), 0, 1500, TimeUnit.MILLISECONDS);
        //ogni persona entra nell'ufficio ogni secondo e mezzo

        Ufficio u = new Ufficio(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        while (true){
            Persona p;
            while((p = (Persona) GeneraPersona.queue.peek()) == null);
            if (u.servi(p)){
                GeneraPersona.queue.poll();
            }
        }
    }
}