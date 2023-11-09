import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static final int PORT = 1600;

    public static void main(String[] args){
        try(
                Socket server = new Socket("localhost", PORT);
                Scanner inKeyboard = new Scanner(System.in);
                Scanner in = new Scanner(server.getInputStream());
                PrintWriter out = new PrintWriter(server.getOutputStream(), true)
            ){

            String playagain = "n";
            boolean exit = false;
            do{
                String result = "";
                do{
                    StringBuilder menu = new StringBuilder();
                    int lines = in.nextInt();
                    for (int i = 0; i <= lines; i++){
                        menu.append(in.nextLine());
                        menu.append('\n');
                    }

                    System.out.println(menu);
                    System.out.print("Scegli: ");
                    int choice = inKeyboard.nextInt();
                    out.println(choice);
                    if (choice == 3){
                        exit = true;
                        break;
                    }

                    result = in.nextLine();
                }while (result.equals("nextround"));

                if (exit) break;

                if(result.equals("win"))
                    System.out.println("HAI VINTO!");
                else if(result.equals("lose"))
                    System.out.println("HAI PERSO");
                else
                    System.out.println("PAREGGIO");
                System.out.println(in.nextLine());

                if (result.equals("win") || result.equals("draw")){
                    System.out.print("Giocare di nuovo (y/n) ? ");
                    playagain = inKeyboard.next();
                    out.println(playagain);
                }
            }while(playagain.equals("y"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
