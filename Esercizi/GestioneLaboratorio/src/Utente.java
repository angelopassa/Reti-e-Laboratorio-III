import java.util.concurrent.ThreadLocalRandom;

public class Utente implements Runnable{
    private int accessi;
    private Tutor monitor;
    private long ultimoAccesso = 0;

    public Utente(Tutor monitor){
        this.monitor = monitor;
        this.accessi = ThreadLocalRandom.current().nextInt(1, 10);
        //ogni utente può fare da 1 a 10 accessi
    }

    @Override
    public void run() {
        for (int i = 0; i < this.accessi; i++){
            monitor.entraLaboratorio(this);

            if (this instanceof Studente){
                System.out.println(Thread.currentThread().getName() + ": " + this + " entra nel laboratorio e usa il PC " + ((Studente) this).getPcAssegnato());
            }else if(this instanceof Tesista){
                System.out.println(Thread.currentThread().getName() + ": " + this + " entra nel laboratorio e usa il PC " + ((Tesista) this).getPcRichiesto() + " che aveva richiesto");
            }else{
                System.out.println(Thread.currentThread().getName() + ": " + this + " entra nel laboratorio");
            }

            long time = System.currentTimeMillis();

            if (this.ultimoAccesso == 0){
                System.out.println(Thread.currentThread().getName() + " [" + this +"] Primo accesso");
            }
            else{
                System.out.println(Thread.currentThread().getName() + " [" + this +"] Tempo trascorso dall'ultimo accesso: " + (time - this.ultimoAccesso) + " ms");
            }

            this.ultimoAccesso = time;

            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 1000));
                //ogni utente occupa un computer per un tempo che può andare da 1ms a 1s
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            monitor.esciLaboratorio(this);
            System.out.println(Thread.currentThread().getName() + ": " + this + " esce dal laboratorio");
        }
    }
}
