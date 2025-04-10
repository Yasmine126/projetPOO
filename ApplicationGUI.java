import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ApplicationGUI {
    private static String FICHIER_UTILISATEUR = "Utilisateur.txt";
    private static JFrame frame;
    private static JPanel panel;

    private static ObjetConnecte radiateur = new ObjetConnecte("Radiateur");
    private static ObjetConnecte secheServiette = new ObjetConnecte("Sèche-serviette");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Gestion des Appareils Connectés");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null); // Centre la fenêtre
            panel = new JPanel();
            panel.setLayout(new BorderLayout());
           
            frame.add(panel);
            showMainScreen();
            frame.setVisible(true);
        });
    }

    private static void showMainScreen() {
        panel.removeAll();

        JLabel titleLabel = new JLabel("Bienvenue dans notre appli", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.ITALIC, 26));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        JButton loginButton = createFlatButton("Se connecter");
        JButton createAccountButton = createFlatButton("Créer un compte");

        buttonPanel.add(loginButton);
        buttonPanel.add(createAccountButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> showLoginDialog());
        createAccountButton.addActionListener(e -> showCreateAccountDialog());

        frame.revalidate();
        frame.repaint();
    }

    private static JButton createFlatButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(245, 245, 220));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial",Font.BOLD,22));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

    private static void showLoginDialog() {
        String username = JOptionPane.showInputDialog(frame, "Entrez votre nom d'utilisateur : ");
        if (username != null) {
            String password = new String(JOptionPane.showInputDialog(frame, "Entrez votre mot de passe : "));
            if (password != null) {
                try {
                    if (seConnecter(username, password)) {
                        JOptionPane.showMessageDialog(frame, "Connexion réussie !");
                        showMainMenu(); // Passer à l'écran principal de l'application après la connexion réussie
                    } else {
                        JOptionPane.showMessageDialog(frame, "Nom d'utilisateur ou mot de passe incorrect.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    private static void showCreateAccountDialog() {
        String username = JOptionPane.showInputDialog(frame, "Entrez un nom d'utilisateur : ");
        if (username != null) {
            String password = null;
            boolean validPassword = false;
    
            // Demander à l'utilisateur de saisir un mot de passe valide (au moins 6 caractères)
            while (!validPassword) {
                password = new String(JOptionPane.showInputDialog(frame, "Entrez un mot de passe : "));
                if (password != null && password.length() >= 4) {
                    validPassword = true;
                } else {
                    JOptionPane.showMessageDialog(frame, "Le mot de passe doit avoir au moins 4 caractères. Veuillez réessayer.");
                }
            }
    
            try {
                if (!verifierUtilisateurExiste(username)) {
                    enregistrerUtilisateur(username, password);
                    JOptionPane.showMessageDialog(frame, "Compte créé avec succès !");
                    showMainMenu(); // Passer à l'écran principal après la création du compte
                } else {
                    JOptionPane.showMessageDialog(frame, "Utilisateur déjà existant.");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    

    private static boolean verifierUtilisateurExiste(String nomUtilisateur) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_UTILISATEUR))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(",");
                if (parts[0].equals(nomUtilisateur)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void enregistrerUtilisateur(String nomUtilisateur, String password) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_UTILISATEUR, true))) {
            writer.write(nomUtilisateur + "," + password);
            writer.newLine();
        }
    }

    private static boolean seConnecter(String nomUtilisateur, String password) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_UTILISATEUR))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] identifiants = ligne.split(",");
                if (identifiants[0].equals(nomUtilisateur) && identifiants[1].equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void showMainMenu() {
        panel.removeAll();
        
        JLabel titleLabel = new JLabel("Menu Principal", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
        JButton tempButton = createFlatButton("Réglage de la température");
        JButton scheduleButton = createFlatButton("Planifier le chauffage");
        JButton consumptionButton = createFlatButton("Suivre la consommation");
        JButton alertsButton = createFlatButton("Vérifier les alertes");
        JButton quitButton = createFlatButton("Quitter");

        buttonPanel.add(tempButton);
        buttonPanel.add(scheduleButton);
        buttonPanel.add(consumptionButton);
        buttonPanel.add(alertsButton);
        buttonPanel.add(quitButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        tempButton.addActionListener(e -> reglageTemperature());
        scheduleButton.addActionListener(e -> planifierChauffage());
        consumptionButton.addActionListener(e -> suivreConsommation());
        alertsButton.addActionListener(e -> verifierAlertes());
        quitButton.addActionListener(e -> System.exit(0));

        frame.revalidate();
        frame.repaint();
    }

  


    private static void reglageTemperature() {
        // Choix de l'appareil à régler
        String[] appareils = {"Radiateur", "Sèche-serviette"};
        String choixAppareil = (String) JOptionPane.showInputDialog(frame, "Choisissez un appareil : ", "Réglage Température", JOptionPane.PLAIN_MESSAGE, null, appareils, appareils[0]);
    
        if (choixAppareil != null) {
            String choixPiece = null;
            if (choixAppareil.equals("Radiateur")) {
                // Choix de la pièce pour le radiateur
                String[] pieces = {"Salon", "Salle de bain", "Chambre", "Cuisine", "Couloir"};
                choixPiece = (String) JOptionPane.showInputDialog(frame, "Choisissez une pièce pour le Radiateur : ", "Réglage Température", JOptionPane.PLAIN_MESSAGE, null, pieces, pieces[0]);
            } else if (choixAppareil.equals("Sèche-serviette")) {
                // Le choix de la pièce pour le sèche-serviette est fixé à Salle de bain
                choixPiece = "Salle de bain";
            }
    
            if (choixPiece != null) {
                // Demander la température après avoir choisi l'appareil et la pièce
                String temperature = JOptionPane.showInputDialog(frame, "Entrez une température (10°C à 25°C) : ");
                try {
                    int temp = Integer.parseInt(temperature);
                    if (temp >= 10 && temp <= 25) {
                        if (choixAppareil.equals("Radiateur")) {
                            radiateur.setTemperature(temp);
                            JOptionPane.showMessageDialog(frame, "Température du Radiateur dans le " + choixPiece + " réglée à " + temp + "°C.");
                        } else {
                            secheServiette.setTemperature(temp);
                            JOptionPane.showMessageDialog(frame, "Température du Sèche-serviette dans la " + choixPiece + " réglée à " + temp + "°C.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Température invalide. Veuillez entrer une valeur entre 10°C et 25°C.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frame, "Entrée invalide. Veuillez entrer un nombre entier.");
                }
            }
        }
    }
    

    private static void planifierChauffage() {
        // Demander l'heure de début
        String heureDebut = JOptionPane.showInputDialog(frame, "Entrez l'heure de début (0-23) : ");
        
        // Vérifier si l'utilisateur a annulé ou fermé la fenêtre
        if (heureDebut == null) {
            return; // Si l'utilisateur annule ou ferme la fenêtre, on quitte la méthode
        }
    
        // Demander l'heure de fin
        String heureFin = JOptionPane.showInputDialog(frame, "Entrez l'heure de fin (0-23) : ");
        
        // Vérifier si l'utilisateur a annulé ou fermé la fenêtre
        if (heureFin == null) {
            return; // Si l'utilisateur annule ou ferme la fenêtre, on quitte la méthode
        }
    
        try {
            int debut = Integer.parseInt(heureDebut);
            int fin = Integer.parseInt(heureFin);
    
            if (debut >= 0 && fin <= 23 && fin > debut) {
                JOptionPane.showMessageDialog(frame, "Chauffage planifié de " + debut + "h à " + fin + "h.");
            } else {
                JOptionPane.showMessageDialog(frame, "Heures invalides.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Entrée invalide.");
        }
    }
    



    private static void suivreConsommation() {
        String heures = JOptionPane.showInputDialog(frame, "Entrez le nombre d'heures de fonctionnement : ");
        if (heures==null){
            return;
        }
        try {
            int h = Integer.parseInt(heures);
            double consommation = h * 1.2; // Exemple
            JOptionPane.showMessageDialog(frame, "Consommation estimée : " + consommation + " kWh.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Entrée invalide.");
        }
    }

    private static void verifierAlertes() {
        // Implémentation de vérification des alertes pour les appareils
        JOptionPane.showMessageDialog(frame, "Vérification des alertes...");
    }
}
