import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}

// Classe Colon : représente un colon dans la colonie avec ses préférences et relations
class Colon {
    private String nom;
    private List<Integer> preferences;
    private List<Colon> relations;
    public Colon(String nom) {
        this.nom = nom;
        this.preferences = new ArrayList<>();
        this.relations = new ArrayList<>();
    }
    public void ajouterPreference(int objet) {
        preferences.add(objet);
    }
    public void ajouterRelation(Colon autreColon) {
        relations.add(autreColon);
    }
    public String getNom() {
        return nom;
    }
    public List<Integer> getPreferences() {
        return preferences;
    }
    public List<Colon> getRelations() {
        return relations;
    }
}

// Classe Colonie : gère l'ensemble des colons et leurs relations dans la colonie
class Colonie {
    private Map<String, Colon> colons;

    // Constructeur de la classe Colonie
    public Colonie() {
        this.colons = new HashMap<>();
    }

    // Méthode pour ajouter un colon dans la colonie
    public void ajouterColon(String nom) {
        colons.putIfAbsent(nom, new Colon(nom));
    }

    // Méthode pour ajouter une relation entre deux colons
    public void ajouterRelation(String nom1, String nom2) {
        Colon colon1 = colons.get(nom1);
        Colon colon2 = colons.get(nom2);
        if (colon1 != null && colon2 != null) {
            colon1.ajouterRelation(colon2);
            colon2.ajouterRelation(colon1);
        } else {
            System.out.println("Erreur : colon(s) introuvable(s).");
        }
    }

    // Méthode pour ajouter les préférences d'un colon
    public void ajouterPreferences(String nom, List<Integer> preferences) {
        Colon colon = colons.get(nom);
        if (colon != null) {
            preferences.forEach(colon::ajouterPreference);
        } else {
            System.out.println("Erreur : colon introuvable.");
        }
    }

    // Méthode pour vérifier si tous les colons ont des préférences
    public void verifierPreferencesCompletes() {
        colons.values().forEach(colon -> {
            if (colon.getPreferences().isEmpty()) {
                System.out.println("Préférences manquantes pour le colon : " + colon.getNom());
            }
        });
    }
}
