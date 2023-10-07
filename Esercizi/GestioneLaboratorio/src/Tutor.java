import java.util.*;

public class Tutor {
    private Utente[] Computer;
    private int size = 0;
    private final int maxSize = 20;
    private LinkedList<Utente> ListaDiAttesa;

    public Tutor(){
        this.Computer = new Utente[this.maxSize];
        this.ListaDiAttesa = new LinkedList<Utente>();
    }

    public synchronized void entraLaboratorio(Utente utente){
        ListaDiAttesa.add(utente);
        if (utente instanceof Professore){
            while (this.size != 0){
                try{
                    wait();
                }catch (InterruptedException e){
                }
            }

            ListaDiAttesa.remove(utente);
            Computer[this.size++] = utente;
            //ci sono solo io, quindi andrò sempre nella posizione 0 dell'array

        }else if (utente instanceof Tesista){
            while ((this.Computer[0] instanceof Professore) || (Computer[((Tesista) utente).getPcRichiesto()] != null) || this.profInAttesa()){
                try{
                    wait();
                }catch (InterruptedException e){
                }
            }

            ListaDiAttesa.remove(utente);
            Computer[((Tesista) utente).getPcRichiesto()] = utente;
            this.size++;

        }else if(utente instanceof Studente){
            while((Computer[0] instanceof Professore) || this.size >= this.maxSize || this.profInAttesa() || this.tesistaInAttesa()){
                try{
                    wait();
                }catch (InterruptedException e){
                }
            }

            ListaDiAttesa.remove(utente);
            for (int i = 0; i < this.maxSize; i++){
                if (this.Computer[i] == null){
                    this.Computer[i] = utente;
                    this.size++;
                    break;
                }
            }
        }
    }

    public synchronized void esciLaboratorio(Utente utente){
        this.remove(utente);
        notifyAll();
    }

    private boolean profInAttesa(){
        for (Utente utente: this.ListaDiAttesa) {
            if (utente instanceof Professore) return true;
        }
        return false;
    }

    private boolean tesistaInAttesa(){
        for (Utente utente: this.ListaDiAttesa) {
            if (utente instanceof Tesista) return true;
        }
        return false;
    }

    private void remove(Utente r){
        for (int i = 0; i < this.maxSize; i++){
            if (this.Computer[i] == r){
                this.Computer[i] = null;
                this.size--;
                return;
            }
        }
    }

}
