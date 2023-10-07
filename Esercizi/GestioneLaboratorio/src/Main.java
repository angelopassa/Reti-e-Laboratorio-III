import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        if (args.length != 3){
            System.out.println(
                    "Numero di parametri errato:"+"\n1° parametro: Nr. di studenti."
                    +"\n2° parametro: Nr. di tesisti."+"\n3° parametro: Nr. di professori."
            );
            System.exit(-1);
        }

        int nrStudenti = Integer.parseInt(args[0]);
        int nrTesisti = Integer.parseInt(args[1]);
        int nrProfessori = Integer.parseInt(args[2]);

        Tutor monitor = new Tutor();
        ExecutorService service = Executors.newCachedThreadPool();
        LinkedList<Utente> utenti = new LinkedList<Utente>();

        for (int i = 0; i < nrStudenti; i++){
            utenti.add(new Studente(monitor));
        }
        for (int i = 0; i < nrTesisti; i++){
            utenti.add(new Tesista(monitor, (int)(Math.random() * 20)));
        }
        for (int i = 0; i < nrProfessori; i++){
            utenti.add(new Professore(monitor));
        }

        for (Utente u: utenti) {
            service.submit(u);
        }

        service.shutdown();
    }
}