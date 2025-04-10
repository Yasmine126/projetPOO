"import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Projet_POO {

    private static String FICHIER_UTILISATEUR = "Utilisateur.txt";
    private static Scanner sc = new Scanner(System.in);

    private static ObjetConnecte radiateur = new ObjetConnecte("Radiateur");
    private static ObjetConnecte secheServiette = new ObjetConnecte("Sèche-serviette");

    public static void main(String[] args) throws IOException {

        System.out.println("Bienvenue dans l'application de gestion des appareils connectés !");
        System.out.println("Avant de commencer, vous devez créer un compte ou vous connecter.");

        boolean compteCree = false;
        while (!compteCree) {
            System.out.println("1. Créer un compte\n2. Se connecter\n3. Quitter");
            int choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1:
                    creerCompte();
                    compteCree = true;
                    break;
                case 2:
                    if (seConnecter()) {
                        compteCree = true;
                    }
                    break;
                case 3:
                    System.out.println("Au revoir !");
                    return;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }

        }

        // Affiche le menu principal
        afficherMenu();
        boolean continuer = true;

        // Menu principal après création du compte
        while (continuer) {
            System.out.print("Faites un choix : ");
            int choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {

                case 1:
                    reglageTemperature();
                    break;
                case 2:
                    planifierChauffage();
                    break;
                case 3:
                    suivreConsommation();
                    break;
                case 4:
                    verifierAlertes();
                    break;
                case 5:
                    System.out.println("Merci d'avoir utilisé notre application. À bientôt !");
                    continuer = false;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }

    }

    private static void afficherMenu() {
        System.out.println("\nMenu Principal :");
        System.out.println("1. Régler la température");
        System.out.println("2. Planifier le chauffage");
        System.out.println("3. Suivre la consommation");
        System.out.println("4. Vérifier les alertes");
        System.out.println("5. Quitter");
    }

    private static void creerCompte() throws IOException {
        System.out.println("\nCréation de compte utilisateur.");

        while (true) {
            System.out.print("Nom d'utilisateur : ");
            String nomUtilisateur = sc.nextLine();
            System.out.print("Mot de passe : ");
            String password = sc.nextLine();

            if (nomUtilisateur.isEmpty() || password.isEmpty()) {
                System.out.println("Erreur, Les champs ne peuvent pas être vides !");
            } else if (password.length() < 4) {
                System.out.println("Erreur, Le mot de passe doit contenir au moins 4 caractères.");
            } else if (verifierUtilisateurExiste(nomUtilisateur)) {
                System.out.println("Erreur ! Le compte existe déjà, Réessayer");
            } else {
                enregistrerUtilisateur(nomUtilisateur, password);
                System.out.println("Compte créé avec succès !");
                break; // Sortir de la boucle
            }
        }
    }

    // Enregistrer l'utilisateur dans un fichier
    private static void enregistrerUtilisateur(String nomUtilisateur, String password) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_UTILISATEUR, true))) {
            writer.write(nomUtilisateur + "," + password);
            writer.newLine();
        }
    }

    // Méthode pour vérifier si l'utilisateur existe déjà
    private static boolean verifierUtilisateurExiste(String nomUtilisateur) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_UTILISATEUR))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] parts = ligne.split(","); // Séparer le nom d'utilisateur et le mot de passe
                if (parts[0].equals(nomUtilisateur)) {
                    return true; // L'utilisateur existe déjà
                }
            }
        }
        return false; // L'utilisateur n'existe pas
    }

    private static boolean seConnecter() throws IOException {

        System.out.print("Nom d'utilisateur : ");
        String nomUtilisateur = sc.nextLine();
        System.out.print("Mot de passe : ");
        String password = sc.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER_UTILISATEUR))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                String[] identifiants = ligne.split(",");
                if (identifiants.length == 2 && identifiants[0].equals(nomUtilisateur) && identifiants[1].equals(password)) {
                    System.out.println("Connexion réussie ! Bienvenue " + nomUtilisateur);
                    return true;
                }
            }
        }
        System.out.println("Le compte n'existe pas ! Réessayer");
        return false;
    }

    // Régler la température
    private static void reglageTemperature() {
        System.out.println("\nRéglage de la température.");

        // Demander si c'est le Radiateur ou le Sèche-serviette
        System.out.println("Choisissez un appareil :");
        System.out.println("1. Radiateur");
        System.out.println("2. Sèche-serviette");
        System.out.print("Faites un choix : ");
        int choixAppareil = sc.nextInt();

        String piece = ""; // Variable pour la pièce
        int temperature;

        if (choixAppareil == 1) { // Radiateur
            // Si l'utilisateur choisit le Radiateur, on demande la pièce
            System.out.println("Choisissez une pièce pour le Radiateur :");
            System.out.println("1. Salon");
            System.out.println("2. Salle de bain");
            System.out.println("3. Chambre");
            System.out.println("4. Cuisine");
            System.out.println("5. Couloir");
            System.out.print("Faites un choix : ");
            int choixPiece = sc.nextInt();

            switch (choixPiece) {
                case 1:
                    piece = "Salon";
                    break;
                case 2:
                    piece = "Salle de bain";
                    break;
                case 3:
                    piece = "Chambre";
                    break;
                case 4:
                    piece = "Cuisine";
                    break;
                case 5:
                    piece = "Couloir";
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    return; // Sortir de la méthode si choix invalide
            }
        } else if (choixAppareil == 2) { // Sèche-serviette
            // Si c'est le Sèche-serviette, la pièce est automatiquement la Salle de bain
            piece = "Salle de bain";
        } else {
            System.out.println("Choix invalide, veuillez choisir 1 pour Radiateur ou 2 pour Sèche-serviette.");
            return; // Sortir de la méthode si choix invalide
        }

        // Demander la température
        System.out.print("Entrez une température entre 10°C et 25°C : ");
        temperature = sc.nextInt();

        if (temperature >= 10 && temperature <= 25) {
            if (choixAppareil == 1) {
                radiateur.setTemperature(temperature);
                System.out.println("La température du radiateur dans la " + piece + " est réglée à " + temperature + "°C.");
            } else {
                secheServiette.setTemperature(temperature);
                System.out.println("La température du sèche-serviette dans la " + piece + " est réglée à " + temperature + "°C.");
            }
        } else {
            System.out.println("Erreur : La température doit être entre 10°C et 25°C.");
        }

        // Après avoir réglé la température, demander si l'utilisateur veut régler un autre appareil
        System.out.println("\nVoulez-vous régler un autre appareil ? (O/N)");
        String reponse = sc.next();

        if (reponse.equalsIgnoreCase("O")) {
            reglageTemperature(); // Appel récursif pour régler un autre appareil
        } else {
            System.out.println("Retour au menu principal.");
        }
    }

    // Planifier le chauffage
    private static void planifierChauffage() {
        System.out.println("Planification du chauffage.");
        try {
            System.out.print("Entrez l'heure de début (0-23) : ");
            int heureDebut = sc.nextInt();
            System.out.print("Entrez l'heure de fin (0-23) : ");
            int heureFin = sc.nextInt();

            if (heureDebut >= 0 && heureDebut < 24 && heureFin > heureDebut && heureFin <= 24) {
                System.out.println("Chauffage programmé de " + heureDebut + "h à " + heureFin + "h.");
            } else {
                System.out.println("Erreur : Heures invalides.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrée invalide. Veuillez entrer des nombres entiers.");
            sc.nextLine(); // Consommer l'entrée invalide
        }
    }

    // Suivre la consommation
    private static void suivreConsommation() {
        System.out.println("Suivi de la consommation.");
        System.out.print("Choisissez un appareil (1. Radiateur, 2. Sèche-serviette) : ");
        int choix = sc.nextInt();
        if (choix == 1 || choix == 2) {
            System.out.print("Entrez le nombre d'heures de fonctionnement : ");
        } else {
            System.out.println("Choix invalide ! Veuillez réessayer");
        }

        try {
            int heures = sc.nextInt();
            double consommationParHeure = 1.2; // Exemple : 1.2 kWh par heure
            double consommationTotale = heures * consommationParHeure;

            System.out.println("Consommation estimée : " + consommationTotale + " kWh.");

            // Enregistrer la consommation dans un fichier
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Consommation.txt", true))) {
                writer.write("Durée : " + heures + " heures - Consommation : " + consommationTotale + " kWh");
                writer.newLine();
            }
            System.out.println("Consommation sauvegardée dans 'Consommation.txt'.");
        } catch (InputMismatchException e) {
            System.out.println("Entrée invalide. Veuillez entrer un nombre entier.");
            sc.nextLine(); // Consommer l'entrée invalide
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture dans le fichier.");
        }
    }

    // Vérifier et avertir en cas de problème
    private static void verifierAlertes() {
        System.out.println("\nVérification des alertes pour le radiateur :");
        radiateur.verifierAlertes();

        System.out.println("\nVérification des alertes pour le sèche-serviette :");
        secheServiette.verifierAlertes();
    }
}
