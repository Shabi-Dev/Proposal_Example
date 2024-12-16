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
        users.put("user1", "password1");
        balances.put("user1", 1000.0);

        users.put("user2", "password2");
        balances.put("user2", 1500.0);

        users.put("user3", "password3");
        balances.put("user3", 2000.0);

        users.put("user4", "password3");
        balances.put("user4", 2000.0);
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
