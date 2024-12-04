package app;

import java.io.*;
import java.util.*;

/**
 * Classe Colonie : gère l'ensemble des colons et leurs relations dans la colonie.
 */
public class Colonie {
    private final Map<String, Colon> colons; // Dictionnaire associant les noms de colons à leurs objets Colon respectifs
    private Set<String> ressources; // Ensemble des ressources disponibles dans la colonie

    /**
     * Constructeur de la classe Colonie, initialise les collections pour les colons et les ressources.
     */
    public Colonie() {
        this.colons = new HashMap<>();
        this.ressources = new HashSet<>();
    }

    /**
     * Ajoute un colon à la colonie.
     * @param nom du colon à ajouter.
     * @throws IllegalArgumentException Si le nom du colon est nul ou vide, ou si le colon existe déjà.
     */
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

    /**
     * Ajoute une relation entre deux colons.
     * @param nom1 du premier colon.
     * @param nom2 du second colon.
     * @throws IllegalArgumentException Si les colons n'existent pas, ou si un colon tente de se mettre en relation avec lui-même.
     */
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

    /**
     * Ajoute des préférences à un colon.
     * @param nom du colon.
     * @param preferences la liste des préférences du colon.
     * @throws IllegalArgumentException Si le colon n'existe pas, ou si les préférences ne correspondent pas aux ressources.
     */
    public void ajouterPreferences(String nom, List<String> preferences) {
        Colon colon = colons.get(nom); // Récupère le colon par son nom
        if (colon == null) { // Vérifie que le colon existe
            throw new IllegalArgumentException("Le colon n'existe pas");
        }
        Set<String> preferencesUnqiues = new HashSet<>(preferences); // Vérification que les préférences sont bien toutes différentes
        if (preferencesUnqiues.size() != ressources.size()) {
            // Vérifie que les préférences données ne se répètent pas
            throw new IllegalArgumentException("Les préférences ne peuvent pas se répéter");
        }
        if (!ressources.containsAll(preferencesUnqiues)){
            // Vérifie que les préférences données soient identiques qu'à l'origine
            throw new IllegalArgumentException("Les préférences ne peuvent pas se répéter");
        }
        if (!colon.getPreferences().isEmpty()) {
            colon.supprimerPreferences(); // Si on redéfinit des préférences, on supprime celle deja existantes
            System.out.println("Suppression des anciennes préférences...");
        }
        preferences.forEach(colon::ajouterPreference); // Ajoute chaque préférence à la liste des préférences du colon
    }

    /**
     * Vérifie si tous les colons ont une liste de préférences complète.
     * @return true si tous les colons ont leurs préférences, false sinon.
     */
    public boolean verifierPreferencesCompletes() {
        boolean complet = true;
        StringBuilder sb = new StringBuilder();
        for (Colon colon : colons.values()) {
            if (colon.getPreferences().isEmpty()) {
                sb.append("Préférences manquantes pour le colon ");
                sb.append(colon.getNom());
                sb.append(".\n");
                complet = false;
            }
        }
        if (!complet) System.out.println(sb);
        return complet;
    }

    /**
     * Assigne des objets aux colons selon leurs préférences.
     * Algorithme de la partie 1 du projet.
     */
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

    /**
     * Calcule le nombre de colons jaloux en fonction des objets assignés et des relations.
     * @return Le nombre de colons jaloux.
     */
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

    /**
     * Échange les objets assignés entre deux colons.
     * @param nom1 du premier colon.
     * @param nom2 du second colon.
     * @throws IllegalArgumentException Si l'un des colons n'existe pas.
     */
    public void echangerRessources(String nom1, String nom2) {
        Colon colon1 = colons.get(nom1); // Récupère le premier colon par son nom
        Colon colon2 = colons.get(nom2);
        if (colon1 == null || colon2 == null) {
            throw new IllegalArgumentException("Un ou les deux colons spécifiés n'existent pas.");
        }
        String temp = colon1.getObjetAssigne(); // Stocke temporairement l'objet du premier colon
        colon1.setObjetAssigne(colon2.getObjetAssigne()); // Affecte l'objet du second colon au premier
        colon2.setObjetAssigne(temp); // Affecte l'objet initial du premier colon au second

    }

    /**
     * Charge les données d'une colonie depuis un fichier texte.
     * @param cheminFichier Le chemin du fichier à charger.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la lecture du fichier.
     */
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

    /**
     * Sauvegarde l'état de la colonie dans un fichier texte.
     * @param cheminFichier Le chemin du fichier où sauvegarder l'état de la colonie.
     * @throws IOException Si une erreur d'entrée/sortie se produit lors de la sauvegarde dans le fichier.
     */
    public void enregistreFichier(String cheminFichier) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier));
        for (Colon colon : colons.values()) { // Écrit pour chaque colon de la colonie son nom et sa ressource donnée
            if (colon.getObjetAssigne() == null){
                throw new NullPointerException("Le colon " + colon.getNom() + " n'as pas de ressource assignée.");
            }
            writer.write(colon.getNom() +":"+colon.getObjetAssigne()+"\n");
        }
        writer.close();
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères de la colonie,
     * y compris tous les colons et leurs informations.
     * @return Une chaîne de caractères représentant l'état de la colonie.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Colonie:\n");
        for (Colon colon : colons.values()) {
            sb.append(colon.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Initialise les ressources en fonction du nombre de colons.
     * Chaque ressource est identifiée par un entier sous forme de chaîne (1, 2, ..., nombreColons).
     * @param nombreColons qui détermine également le nombre de ressources.
     */
    public void setRessources(int nombreColons) { // Dans le cas de l'entrée par le shell, on crée les ressources
        Set<String> ressources = new HashSet<>();
        for (int i = 1; i <= nombreColons; i++) {
            ressources.add(String.valueOf(i));
        }
        this.ressources = ressources;
    }

    /**
     * Crée les colons en fonction du nombre donné.
     * Les noms des colons sont générés par des lettres majuscules (A, B, C, ...).
     * @param nombreColons Le nombre de colons à créer.
     */
    public void setColons(int nombreColons) { //  Dans le cas de l'entrée par le shell, on crée les colons
        for (int i = 0; i < nombreColons; i++) {
            String nom = Character.toString((char) ('A' + i));
            ajouterColon(nom);
        }
    }
}