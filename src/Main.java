import java.util.*;

public class Main {
    public static void main(String[] args) {
        Interface utilisateurInterface = new Interface();
        utilisateurInterface.lancer();
    }
}

// Classe Colon : représente un colon dans la colonie avec ses préférences, relations et l'objet qui lui est assigné
class Colon {
    private String nom;
    private List<Integer> preferences; // Liste triée des préférences d'objets du colon
    private List<Colon> relations; // Liste des autres colons avec qui ce colon a des relations négatives
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
    public List<Integer> getPreferences() {
        return preferences;
    }
    public List<Colon> getRelations() {
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

// Classe Colonie : gère l'ensemble des colons et leurs relations dans la colonie
class Colonie {
    private Map<String, Colon> colons; // Dictionnaire associant les noms de colons à leurs objets Colon respectifs

    public Colonie() {
        this.colons = new HashMap<>();
    }

    // Ajoute un colon dans la colonie
    public void ajouterColon(String nom) {
        colons.putIfAbsent(nom, new Colon(nom)); // Ajoute un nouveau colon s'il n'est pas déjà présent
    }

    // Ajoute une relation entre deux colons
    public void ajouterRelation(String nom1, String nom2) {
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


class Interface{
    public void lancer(){
        Scanner scanner = new Scanner(System.in);
        Colonie colonie = new Colonie();

        // Initialisation des colons
        System.out.print("Entrez le nombre de colons : ");
        int nombreColons = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nombreColons; i++) {
            String nom = Character.toString((char) ('A' + i));
            colonie.ajouterColon(nom);
        }

        boolean fin = false;
        while (!fin) {
            System.out.println("1) Ajouter une relation entre deux colons");
            System.out.println("2) Ajouter les préférences d’un colon");
            System.out.println("3) Fin");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consomme la ligne restante

            switch (choix) {
                case 1:
                    System.out.print("Entrez le nom des deux colons : ");
                    String nom1 = scanner.next();
                    String nom2 = scanner.next();
                    colonie.ajouterRelation(nom1, nom2);
                    break;
                case 2:
                    System.out.print("Entrez le nom du colon et ses " + nombreColons + " préférences : ");
                    String nomColon = scanner.next();
                    List<Integer> preferences = new ArrayList<>();
                    // Lire les n préférences de l'utilisateur
                    for (int i = 0; i < nombreColons; i++) {
                        preferences.add(scanner.nextInt());
                    }
                    colonie.ajouterPreferences(nomColon, preferences);
                    scanner.nextLine(); // Consomme la ligne restante
                    break;

                case 3:
                    colonie.verifierPreferencesCompletes();
                    colonie.assignerObjets();
                    System.out.println(colonie.toString());
                    fin = true;
                    break;
                default:
                    System.out.println("Option invalide. Réessayer.");
            }
        }

        fin = false;
        while (!fin) {
            System.out.println("1) Échanger les ressources de deux colons");
            System.out.println("2) Afficher le nombre de colons jaloux");
            System.out.println("3) Fin");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.print("Entrez le nom des deux colons : ");
                    String nom1 = scanner.next();
                    String nom2 = scanner.next();
                    colonie.echangerRessources(nom1, nom2);
                    break;
                case 2:
                    System.out.println("Nombre de colons jaloux : " + colonie.calculerColonsJaloux());
                    break;
                case 3:
                    System.out.println("Fin du programme.");
                    fin = true;
                    break;
                default:
                    System.out.println("Option invalide. Réessayer.");
            }
            System.out.println(colonie.toString());
        }
        scanner.close();
    }
}

