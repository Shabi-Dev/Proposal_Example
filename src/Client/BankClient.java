package Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class BankClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println(in.readLine()); // Willkommen

            // Anmeldung
            System.out.print(in.readLine()); //Benutzername:
            String username = scanner.nextLine();
            out.println(username);

            System.out.print(in.readLine()); //Password:
            String password = scanner.nextLine();
            out.println(password);

            String response = in.readLine();
            System.out.println(response);
            if (!response.equals("Anmeldung erfolgreich!")) {
                return;
            }

            while (true) {
                System.out.println(in.readLine()); // Optionen
                String option = scanner.nextLine();
                out.println(option);

                if("1".equals(option)) {
                    System.out.println(in.readLine());
                }
                if ("2".equals(option)) { // Überweisung
                    System.out.print(in.readLine()); // Empfänger auffordern
                    out.println(scanner.nextLine()); // Eingabe senden

                    System.out.print(in.readLine()); // Betrag auffordern
                    out.println(scanner.nextLine()); // Eingabe senden

                    System.out.println(in.readLine()); // Überweisungsantwort vom Server ausgeben
                }
                if (option.equals("3")) {
                    System.out.println(in.readLine());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
