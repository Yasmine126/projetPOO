public class ObjetConnecte {
    private String nom; // Nom de l'objet (radiateur, sèche-serviette, etc.)
    private int temperature; // Température actuelle
    private boolean presence; // Capteur de présence
    private boolean fenetreOuverte; // Capteur de fenêtre ouverte
 
    // Constructeur
    public ObjetConnecte(String nom) {
        this.nom = nom;
        this.temperature = 20;
        this.presence = true;
        this.fenetreOuverte = false;
    }    
 
    // Getter et Setter pour la température
    public int getTemperature() {
        return temperature;
    }
 
    public void setTemperature(int temperature) {
        if (temperature >= 10 && temperature <= 25) {
            this.temperature = temperature;
            System.out.println("Température réglée à " + temperature + "°C pour " + nom + ".");
        } else {
            System.out.println("La température doit être entre 10°C et 25 °C");
        }
    }
 
    public boolean isPresence() {
        return presence;
    }
 
    public void setPresence(boolean presence) {
        this.presence = presence;
    }
 
   
    public boolean isFenetreOuverte() {
        return fenetreOuverte;
    }
 
    public void setFenetreOuverte(boolean fenetreOuverte) {
        this.fenetreOuverte = fenetreOuverte;
    }
 
    // Vérifier les alertes
    public void verifierAlertes() {
        if (fenetreOuverte) {
            System.out.println("Alerte : La fenêtre est ouverte pour " + nom + " !");
        }
        if (!presence) {
            System.out.println("Alerte : Aucune présence détectée pour " + nom + " !");
        }
        if (!fenetreOuverte && presence) {
            System.out.println("Aucune alerte pour " + nom + ".");
        }
    }
}

 