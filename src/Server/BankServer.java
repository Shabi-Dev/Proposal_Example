package Server;

import java.io.*;
import java.net.*;

public class BankServer {
    private static final int PORT = 12345;
    private BankLogic bankLogic;

    public BankServer() {
        bankLogic = new BankLogic();
    }

    public static void main(String[] args) {
        BankServer server = new BankServer();
        System.out.println("BankServer gestartet und wartet auf Verbindungen...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Neuer Client verbunden: " + clientSocket.getInetAddress());

                // Client in einem neuen Thread verarbeiten
                new Thread(() -> server.handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            out.println("Willkommen beim BankServer!");

            // Authentifizierung
            out.println("Benutzername:");
            String username = in.readLine();

            out.println("Passwort:");
            String password = in.readLine();

            if (!bankLogic.authenticate(username, password)) {
                out.println("Anmeldung fehlgeschlagen.");
                return;
            }
            out.println("Anmeldung erfolgreich!");

            // Benutzerinteraktion
            String option;
            do {
                out.println("Optionen: 1) Kontostand anzeigen, 2) Überweisung, 3) Abmelden");
                option = in.readLine();

                switch (option) {
                    case "1":
                        out.println("Kontostand: " + bankLogic.getBalance(username) + " EUR");
                        break;
                    case "2":
                        out.println("Empfänger (Benutzername):");
                        System.out.println("Test");
                        String toUser = in.readLine();
                        System.out.println(toUser);
                        out.println("Betrag:");
                        double amount = Double.parseDouble(in.readLine());
                        if (bankLogic.transfer(username, toUser, amount)) {
                            out.println("Überweisung erfolgreich!");
                        } else {
                            out.println("Überweisung fehlgeschlagen!");
                        }
                        break;
                    case "3":
                        out.println("Abmeldung erfolgreich.");
                        break;
                    default:
                        out.println("Ungültige Option.");
                        break;
                }
            } while (!"3".equals(option));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

