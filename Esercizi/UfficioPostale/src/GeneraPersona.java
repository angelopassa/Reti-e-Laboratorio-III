import java.util.concurrent.*;

public class GeneraPersona implements Runnable{
    private static int num = 0;
    //non occorre rendere la variabile thread safe dato che viene modificata da un singolo thread
    public static LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": Persona nr." + num + " entra nella sala 1");
        queue.add(new Persona(num++));
    }
}
