import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ServerWorker implements Runnable{
    private final Socket client;
    private String round = "nextround";
    private String game = "n";
    private static final String MENU = "----- MENU ----- \n" +
            "1. Combatti \n" +
            "2. Bevi Pozione \n" +
            "3. Esci";
    private int player_health;
    private int monster_health;
    private int posion;

    public ServerWorker(Socket client){
        this.client = client;
    }

    @Override
    public void run() {
        try(
                this.client;
                Scanner in = new Scanner(this.client.getInputStream());
                PrintWriter out = new PrintWriter(this.client.getOutputStream(), true)
            ){

            boolean exit = false;
            do{
                this.player_health = ThreadLocalRandom.current().nextInt(10, 101);
                this.posion = ThreadLocalRandom.current().nextInt(1, 51);
                this.monster_health = ThreadLocalRandom.current().nextInt(10, 101);

                do{
                    String output = "Salute giocatore: " + this.player_health + '\n' +
                            "Pozione: " + this.posion + '\n' +
                            "Salute mostro: " + this.monster_health + '\n' + MENU;

                    int lines = (int)(output.chars().filter(c -> c == '\n').count()) + 1;
                    out.println(lines);
                    out.println(output);

                    int choice = in.nextInt();

                    switch (choice){
                        case 1:
                            play();
                            if (this.player_health <= 0 && this.monster_health <= 0) this.round = "draw";
                            else if(this.player_health <= 0) this.round = "lose";
                            else if(this.monster_health <= 0) this.round = "win";
                            else this.round = "nextround";
                            break;
                        case 2:
                            drink();
                            break;
                        case 3:
                            exit = true;
                            break;
                    }

                    if (exit) break;

                    out.println(this.round);
                } while(this.round.equals("nextround"));

                if (exit) break;

                out.println("Salute giocatore: " + this.player_health + " - Salute mostro: " + this.monster_health);
                if (this.round.equals("win") || this.round.equals("draw"))
                    this.game = in.next();
            }while (this.game.equals("y"));

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void play(){
        int x = ThreadLocalRandom.current().nextInt(0, this.player_health + 1);
        int k = ThreadLocalRandom.current().nextInt(0, this.monster_health + 1);
        this.player_health -= x;
        this.monster_health -= k;
    }

    private void drink(){
        if(posion != 0){
            int k = ThreadLocalRandom.current().nextInt(1, this.posion + 1);
            this.posion -= k;
            this.player_health += k;
        }
    }
}
