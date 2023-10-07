public class Utente implements Runnable{
    private int accessi;
    private Tutor monitor;

    public Utente(Tutor monitor){
        this.monitor = monitor;
        this.accessi = (int)(Math.random() * 10) + 1;
        //ogni utente può fare da 1 a 10 accessi
    }

    @Override
    public void run() {
        for (int i = 0; i < this.accessi; i++){
            monitor.entraLaboratorio(this);
            System.out.println(Thread.currentThread().getName() + ": " + this.toString() + " entra nel laboratorio");
            try {
                Thread.sleep((int)(Math.random() * 1000) + 1);
                //ogni utente occupa un computer per un tempo che può andare da 1ms a 1s
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            monitor.esciLaboratorio(this);
            System.out.println(Thread.currentThread().getName() + ": " + this.toString() + " esce dal laboratorio");
        }
    }
}
