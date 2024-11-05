import java.util.ArrayList;

// Classe Colon : représente un colon dans la colonie avec ses préférences, relations et l'objet qui lui est assigné
public class Colon {
    private String nom;
    private ArrayList<Integer> preferences; // Liste triée des préférences d'objets du colon
    private ArrayList<Colon> relations; // Liste des autres colons avec qui ce colon a des relations négatives
    private Integer objetAssigne; // Objet actuellement assigné au colon

    public Colon(String nom) {
        this.nom = nom;
        this.preferences = new ArrayList<>();
        this.relations = new ArrayList<>();
        this.objetAssigne = null;
    }

    // Ajoute un objet à la liste des préférences du colon
    public void ajouterPreference(int objet) {
        preferences.add(objet);
    }

    // Ajoute un autre colon à la liste des relations du colon
    public void ajouterRelation(Colon autreColon) {
        relations.add(autreColon);
    }

    public String getNom() {
        return nom;
    }
    public ArrayList<Integer> getPreferences() {
        return preferences;
    }
    public ArrayList<Colon> getRelations() {
        return relations;
    }
    public Integer getObjetAssigne() {
        return objetAssigne;
    }

    // Définit l'objet assigné au colon
    public void setObjetAssigne(Integer objet) {
        this.objetAssigne = objet;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Colon: ").append(nom)
                .append(", Préférences: ").append(preferences)
                .append(", Objet assigné: ").append(objetAssigne)
                .append(", Relations: ");
        for (Colon relation : relations) {
            sb.append(relation.getNom()).append(", ");
        }
        return sb.toString();
    }
}