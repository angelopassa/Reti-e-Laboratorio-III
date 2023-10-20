public class Studente extends Utente{
    private Tutor monitor;
    private int pcAssegnato;

    public Studente(Tutor monitor){
        super(monitor);
    }

    public void setPcAssegnato(int pcAssegnato) {
        this.pcAssegnato = pcAssegnato;
    }

    public int getPcAssegnato() {
        return pcAssegnato;
    }
}
