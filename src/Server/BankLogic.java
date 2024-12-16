package Server;

import java.util.HashMap;

public class BankLogic {
    private HashMap<String, String> users = new HashMap<>(); // Benutzername und Passwort
    private HashMap<String, Double> balances = new HashMap<>(); // Benutzername und Kontostand

    public BankLogic() {
        initializeData();
    }

    // Initialisierung der Benutzerdaten und Kontostände
    private void initializeData() {
        users.put("Shabi", "password1");
        balances.put("Shabi", 1000.0);

        users.put("Tobias", "password2");
        balances.put("Tobias", 1500.0);

        users.put("Finn", "password3");
        balances.put("Finn", 2000.0);

        users.put("Morten", "password3");
        balances.put("Morten", 2000.0);
    }

    // Authentifizierung der Benutzer
    public boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    // Kontostand abrufen
    public double getBalance(String username) {
        return balances.getOrDefault(username, 0.0);
    }

    // Überweisungslogik
    public boolean transfer(String fromUser, String toUser, double amount) {
        if (!balances.containsKey(fromUser) || !balances.containsKey(toUser)) {
            return false; // Empfänger oder Sender existieren nicht
        }

        double fromBalance = balances.get(fromUser);
        if (fromBalance < amount) {
            return false; // Nicht genug Guthaben oder ungültiger Betrag
        }

        balances.put(fromUser, fromBalance - amount);
        balances.put(toUser, balances.get(toUser) + amount);
        return true; // Überweisung erfolgreich
    }
}
