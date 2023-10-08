public class Tesista extends Utente{
    private int pcRichiesto;

    public Tesista(Tutor monitor, int pcRichiesto){
        super(monitor);
        this.pcRichiesto = pcRichiesto;
    }

    public int getPcRichiesto() {
        return pcRichiesto;
    }
}
