import java.util.*;
import java.util.concurrent.*;

public class Persona implements Runnable{
    private int numero;

    public Persona(int numero){
        this.numero = numero;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": Persona nr." + this.numero + " viene servita allo sportello");
        try {
            Thread.sleep((int)(Math.random() * 10001) + 3000);
            //ogni persona si ferma allo sportello per un intervallo di tempo di 3-13 secondi
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + ": Persona nr." + this.numero + " ha finito allo sportello");
    }
}
