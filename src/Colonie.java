import java.util.*;

// Classe Colonie : gère l'ensemble des colons et leurs relations dans la colonie
public class Colonie {
    private Map<String, Colon> colons; // Dictionnaire associant les noms de colons à leurs objets Colon respectifs

    public Colonie() {
        this.colons = new HashMap<>();
    }

    // Ajoute un colon dans la colonie
    public void ajouterColon(String nom) {
        if (nom == null || nom.isEmpty()) {
            throw new IllegalArgumentException("Le nom du colon ne peut pas être nul ou vide.");
        }
        else if (colons.containsKey(nom)) {
            System.out.println("Le colon " + nom + " existe déjà.");
        } else {
            colons.put(nom, new Colon(nom));  // Ajoute un nouveau colon s'il n'est pas déjà présent
        }
    }

    // Ajoute une relation entre deux colons
    public void ajouterRelation(String nom1, String nom2) {
        if (nom1 == null || nom1.isEmpty() || nom2 == null || nom2.isEmpty()) {
            throw new IllegalArgumentException("Les noms des colons ne peuvent pas être nuls ou vides.");
        }
        else if (nom1.equals(nom2)) {
            throw new IllegalArgumentException("Un colon ne peut pas être en inimité avec lui-même.");
        }
        Colon colon1 = colons.get(nom1); // Récupère le colon par son nom
        Colon colon2 = colons.get(nom2);
        if (colon1 != null && colon2 != null) { // Vérifie que les deux colons existent
            colon1.ajouterRelation(colon2); // Ajoute une relation du premier colon vers le second
            colon2.ajouterRelation(colon1); // ... du second colon vers le premier
        } else {
            System.out.println("Erreur : colon(s) introuvable(s)."); // Message d'erreur si un des colons n'existe pas
        }
    }

    // Ajoute les préférences d'un colon
    public void ajouterPreferences(String nom, List<Integer> preferences) {
        Colon colon = colons.get(nom); // Récupère le colon par son nom
        if (colon != null) { // Vérifie que le colon existe
            if (preferences == null || preferences.isEmpty()) {
                throw new IllegalArgumentException("La liste des préférences ne peut pas être vide.");
            }
            preferences.forEach(colon::ajouterPreference); // Ajoute chaque préférence à la liste des préférences du colon
        } else {
            System.out.println("Erreur : colon introuvable."); // Message d'erreur si le colon n'existe pas
        }
    }

    // Vérifie si chaque colon a une liste de préférences complète
    public void verifierPreferencesCompletes() {
        colons.values().forEach(colon -> { // Parcourt tous les colons de la colonie
            if (colon.getPreferences().isEmpty()) { // Vérifie si le colon n'a pas de préférences
                System.out.println("Préférences manquantes pour le colon : " + colon.getNom()); // Affiche un message si des préférences manquent
            }
        });
    }

    // Affecte chaque objet aux colons selon leurs préférences (selon la méthode spécifiée pour la partie 1)
    public void assignerObjets() {
        Set<Integer> objetsDisponibles = new HashSet<>(); // Ensemble des objets encore disponibles pour les colons
        for (int i = 1; i <= colons.size(); i++) { // Initialise les objets de 1 à n
            objetsDisponibles.add(i); // Ajoute chaque objet à l'ensemble des objets disponibles
        }

        for (Colon colon : colons.values()) { // Parcourt tous les colons de la colonie
            for (Integer preference : colon.getPreferences()) { // Parcourt les préférences du colon
                if (objetsDisponibles.contains(preference)) { // Si l'objet préféré est disponible
                    colon.setObjetAssigne(preference); // Assigne cet objet au colon
                    objetsDisponibles.remove(preference); // Retire l'objet de la liste des objets disponibles
                    break; // Sort de la boucle dès qu'un objet est assigné
                }
            }
        }
    }

    // Calcule le nombre de colons jaloux en fonction des objets assignés aux colons et à leurs relations
    public int calculerColonsJaloux() {
        int colonsJaloux = 0; // Compteur de colons jaloux
        for (Colon colon : colons.values()) { // Parcourt tous les colons de la colonie
            for (Colon relation : colon.getRelations()) { // Parcourt les colons en relation
                // Si le colon préfère l'objet de la relation à celui qui lui a été assigné, il est jaloux
                if (colon.getPreferences().indexOf(relation.getObjetAssigne()) < colon.getPreferences().indexOf(colon.getObjetAssigne())) {
                    colonsJaloux++; // Incrémente le compteur de jalousie
                    break; // Un colon jaloux n'est compté qu'une seule fois
                }
            }
        }
        return colonsJaloux; // Retourne le nombre total de colons jaloux
    }

    // Échange les objets assignés entre deux colons
    public void echangerRessources(String nom1, String nom2) {
        if (nom1 == null || nom1.isEmpty() || nom2 == null || nom2.isEmpty()) {
            throw new IllegalArgumentException("Les noms des colons ne peuvent pas être nuls ou vides.");
        }
        Colon colon1 = colons.get(nom1); // Récupère le premier colon par son nom
        Colon colon2 = colons.get(nom2);
        if (colon1 != null && colon2 != null) { // Vérifie que les deux colons existent
            Integer temp = colon1.getObjetAssigne(); // Stocke temporairement l'objet du premier colon
            colon1.setObjetAssigne(colon2.getObjetAssigne()); // Affecte l'objet du second colon au premier
            colon2.setObjetAssigne(temp); // Affecte l'objet initial du premier colon au second
        } else {
            System.out.println("Erreur : colon(s) introuvable(s)."); // Message d'erreur si un des colons n'existe pas
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Colonie:\n");
        for (Colon colon : colons.values()) {
            sb.append(colon.toString()).append("\n");
        }
        return sb.toString();
    }
}