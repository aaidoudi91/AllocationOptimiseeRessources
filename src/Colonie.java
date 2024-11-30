import java.io.*;
import java.util.*;

// Classe Colonie : gère l'ensemble des colons et leurs relations dans la colonie
public class Colonie {
    private Map<String, Colon> colons; // Dictionnaire associant les noms de colons à leurs objets Colon respectifs
    private Set<String> ressources; // Ensemble des ressources disponibles dans la colonie

    public Colonie() {
        this.colons = new HashMap<>();
        this.ressources = new HashSet<>();
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
        if (nom1.equals(nom2)) {
            throw new IllegalArgumentException("Un colon ne peut pas être en inimité avec lui-même.");
        }
        Colon colon1 = colons.get(nom1); // Récupère le colon par son nom
        Colon colon2 = colons.get(nom2);
        if (colon1 == null || colon2 == null) { // Vérifie que les deux colons existent
            throw new IllegalArgumentException("Un ou les deux colons spécifiés n'existent pas.");
        }
        colon1.ajouterRelation(colon2); // Ajoute une relation du premier colon vers le second
        colon2.ajouterRelation(colon1); // ... du second colon vers le premier
    }

    // Ajoute les préférences d'un colon (modifiée lors de la Partie B pour recevoir des Strings)
    public void ajouterPreferences(String nom, List<String> preferences) {
        Colon colon = colons.get(nom); // Récupère le colon par son nom
        if (colon == null) { // Vérifie que le colon existe
            throw new IllegalArgumentException("Le colon n'existe pas");
        }
        if (!(ressources.containsAll(preferences) && preferences.containsAll(ressources))) {
            // Vérifie que les préférences données soient identiques et au même nombre qu'à l'origine
            throw new IllegalArgumentException("Les préférences indiqués ne sont pas celles spécifiées à l'origine");
        }
        if (!colon.getPreferences().isEmpty()) {
            colon.supprimerPreferences(); // Si on redéfinit des préférences, on supprime celle deja existantes
            System.out.println("Suppression des anciennes préférences...");
        }
        preferences.forEach(colon::ajouterPreference); // Ajoute chaque préférence à la liste des préférences du colon
    }

    // Vérifie si chaque colon a une liste de préférences complète
    public boolean verifierPreferencesCompletes() {
        boolean complet = true;
        StringBuilder sb = new StringBuilder();
        for (Colon colon : colons.values()) {
            if (colon.getPreferences().isEmpty()) {
                sb.append("Préférences manquantes pour le colon " + colon.getNom() + ".\n");
                complet = false;
            }
        }
        System.out.println(sb);
        return complet;
    }

    // Affecte chaque objet aux colons selon leurs préférences (selon la méthode spécifiée pour la partie 1)
    public void assignerObjets() {
        Set<String> objetsDisponibles = ressources; // Ensemble des objets encore disponibles pour les colons

        for (Colon colon : colons.values()) { // Parcourt tous les colons de la colonie
            for (String preference : colon.getPreferences()) { // Parcourt les préférences du colon
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
            String temp = colon1.getObjetAssigne(); // Stocke temporairement l'objet du premier colon
            colon1.setObjetAssigne(colon2.getObjetAssigne()); // Affecte l'objet du second colon au premier
            colon2.setObjetAssigne(temp); // Affecte l'objet initial du premier colon au second
        } else {
            System.out.println("Erreur : colon(s) introuvable(s)."); // Message d'erreur si un des colons n'existe pas
        }
    }

    // Méthode pour charger les données d'une colonie depuis un fichier texte
    public void chargerFichier(String cheminFichier) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(cheminFichier));
        String ligne;
        int numLigne = 0;
        String section = "colon"; // Indique la section actuelle : colons, ressources, relations et préférences

        while ((ligne = reader.readLine()) != null) { // Tant que le fichier texte n'as pas été lu entièrement, on boucle
            numLigne +=1;
            ligne = ligne.trim(); // Supprime les espaces blancs de la ligne
            if (ligne.isEmpty()) continue; // Passe à la ligne suivante si celle-ci est vide

            if (ligne.startsWith("colon(") && ligne.endsWith(").")) { // Vérifie que la ligne commence et termine avec les bons caractères
                if (!section.equals("colon")) { // Vérifie que la 1ére section est bien celle des colons
                    throw new IllegalArgumentException("Les colons doivent être définis avant toute autre section, (ligne " + numLigne + ").");
                }
                String nomColon = ligne.substring(6, ligne.length() - 2); // Récupère le nom du colon entre les parenthèses
                if (nomColon.split(",").length !=1){ // Vérifie que l'on n'a pas plusieurs paramètres
                    throw new IllegalArgumentException("L'élément 'colon' prends 1 unique paramètres, (ligne " + numLigne + ").");
                }
                ajouterColon(nomColon); // Ajoute le colon à la colonie
            }
            else if (ligne.startsWith("ressource(") && ligne.endsWith(").")) {
                if (!section.equals("ressource") && !section.equals("colon")) { // Vérifie que la 2e section ressource est celle des ressources
                    throw new IllegalArgumentException("Les ressources doivent être définies après les colons, (ligne " + numLigne + ").");
                }
                section = "ressource"; // Passe à la section "ressource"
                String nomRessource = ligne.substring(10, ligne.length() - 2); // Récupère le nom de la ressource entre les parenthèses
                if (nomRessource.split(",").length !=1){ // Vérifie que l'on n'a pas plusieurs paramètres
                    throw new IllegalArgumentException("L'élément 'ressource' prends 1 unique paramètres, (ligne " + numLigne + ").");
                }
                ressources.add(nomRessource); // Ajoute la ressource son ensemble
            }
            else if (ligne.startsWith("deteste(") && ligne.endsWith(").")) {
                if (!section.equals("deteste") && !section.equals("ressource")) { // Vérifie que la 3e section ressource est celle des relations
                    throw new IllegalArgumentException("Les relations 'deteste' doivent être définies après les ressources, (ligne " + numLigne + ").");
                }
                section = "deteste"; // Passe à la section "deteste"
                String contenu = ligne.substring(8, ligne.length() - 2); // Récupère les noms des colons entre les parenthèses
                String[] noms = contenu.split(",");
                if (noms.length != 2) { // Vérifie le nombre de noms spécifiés
                    throw new IllegalArgumentException("L'élément 'deteste' prends 2 paramètres et non " + noms.length + ", (ligne " + numLigne + ").");
                }
                try{ ajouterRelation(noms[0], noms[1]); } // Attrape les erreurs jetées
                catch (IllegalArgumentException e) { System.err.println(e.getMessage() + ", (ligne " + numLigne + ").");}
            }
            else if (ligne.startsWith("preferences(") && ligne.endsWith(").")) {
                if (!section.equals("preferences") && !section.equals("deteste")) {  // Vérifie que la 4e section ressource est celle des préférences
                    throw new IllegalArgumentException("Les préférences doivent être définies après les relations, (ligne " + numLigne + ").");
                }
                section = "preferences"; // Passe à la section "preferences"
                String contenu = ligne.substring(12, ligne.length() - 2); // Récupère le nom du colon concerné et ces ressources voulues
                String[] parties = contenu.split(",");
                String nomColon = parties[0]; // Nom du colon
                List<String> prefs = Arrays.stream(parties).skip(1).toList(); // Ajoute les préférences (partie[n] sans n = 0) à la liste prefs
                try{ ajouterPreferences(nomColon, prefs); } // Attrape les erreurs jetées
                catch (IllegalArgumentException e) { System.err.println(e.getMessage() + ", (ligne " + numLigne + ").");}
            }
            else { // Si la ligne ne contient aucun début de phrase connu, ou que la syntaxe n'est pas respecté
                throw new IllegalArgumentException("Ligne inconnue ou mauvaise syntaxe (ligne " + numLigne + ").");
            }
        }

        reader.close();
    }

    public void enregistreFichier(String cheminFichier) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier));
        for (Colon colon : colons.values()) { // Écrit pour chaque colon de la colonie son nom et sa ressource donnée
            writer.write(colon.getNom() +":"+colon.getObjetAssigne()+"\n");
        }
        writer.close();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Colonie:\n");
        for (Colon colon : colons.values()) {
            sb.append(colon.toString()).append("\n");
        }
        return sb.toString();
    }

    public void setRessources(int nombreColons) { // Dans le cas de l'entrée par le shell, on crée les ressources
        Set<String> ressources = new HashSet<>();
        for (int i = 1; i <= nombreColons; i++) {
            ressources.add(String.valueOf(i));
        }
        this.ressources = ressources;
    }

    public void setColons(int nombreColons) { //  Dans le cas de l'entrée par le shell, on crée les colons
        for (int i = 0; i < nombreColons; i++) {
            String nom = Character.toString((char) ('A' + i));
            ajouterColon(nom);
        }
    }
}