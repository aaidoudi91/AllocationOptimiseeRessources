package app;

import java.util.ArrayList;

/**
 * Classe Colon : représente un colon dans la colonie avec ses préférences,
 * ses relations avec d'autres colons et l'objet assigné.
 */
public class Colon {
    private final String nom;
    private final ArrayList<String> preferences; // Liste triée des préférences d'objets du colon
    private final ArrayList<Colon> relations; // Liste des autres colons avec qui ce colon a des relations négatives
    private String objetAssigne; // Objet actuellement assigné au colon

    /**
     * Constructeur de la classe Colon.
     * @param nom du colon
     */
    public Colon(String nom) {
        this.nom = nom;
        this.preferences = new ArrayList<>();
        this.relations = new ArrayList<>();
        this.objetAssigne = null;
    }

    /**
     * Ajoute un objet à la liste des préférences du colon.
     * @param objet à ajouter aux préférences
     */
    public void ajouterPreference(String objet) {
        if (objet == null) {
            throw new NullPointerException("La préférence ne peut pas être nul");
        }
        if (preferences.contains(objet)){
            throw new NullPointerException("La préférence existe deja.");
        }
        preferences.add(objet);
    }

    /**
     * Ajoute un autre colon à la liste des relations du colon.
     * @param autreColon avec qui établir une relation
     */
    public void ajouterRelation(Colon autreColon) {
        if (autreColon.getNom().equals(nom)){
            throw new IllegalArgumentException("Un colon ne peut pas être en inimité avec lui-même");
        }
        if (relations.contains(autreColon)){
            throw new IllegalArgumentException("La relation existe deja.");
        }
        relations.add(autreColon);
    }

    public String getNom() {
        return nom;
    }
    public ArrayList<String> getPreferences() { return preferences; }
    public ArrayList<Colon> getRelations() {
        return relations;
    }
    public String getObjetAssigne() {
        return objetAssigne;
    }

    public void setObjetAssigne(String objet) {
        if (objet == null) {
            throw new NullPointerException("L'objet assigné ne peut pas être nul");
        }
        this.objetAssigne = objet;
    }
    public void supprimerPreferences() {
        preferences.clear();
    }

    /**
     * Représente le colon sous forme de chaîne de caractères.
     * @return une chaîne contenant le nom, les préférences,
     *       les relations du colon et l'objet assigné.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Colon: ").append(nom)
                .append(", Préférences: ").append(preferences);
        if (!relations.isEmpty()){
            sb.append(", Relations: [");
            for (Colon relation : relations) {
                sb.append(relation.getNom()).append(", ");
            }
            sb.delete(sb.length()-2, sb.length());
            sb.append("], Objet assigné: ").append(objetAssigne);
        } else {
            sb.append(", Pas de relation, Objet assigné: ").append(objetAssigne);
        }
        return sb.toString();
    }
}