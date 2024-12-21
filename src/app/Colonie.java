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
            throw new IllegalArgumentException("Le colon " + nom + " existe déjà.");
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
        Set<String> preferencesUniques = new HashSet<>(preferences); // Vérification que les préférences sont bien toutes différentes
        if (preferencesUniques.size() != ressources.size()) {
            // Vérifie que les préférences données ne se répètent pas
            throw new IllegalArgumentException("Les préférences ne peuvent pas se répéter");
        }
        if (!ressources.containsAll(preferencesUniques)){
            // Vérifie que les préférences données soient identiques qu'à l'origine
            throw new IllegalArgumentException("Les préférences ne peuvent pas se répéter");
        }
        if (!colon.getPreferences().isEmpty()) {
            colon.supprimerPreferences(); // Si on redéfinit des préférences, on supprime celle deja existantes
            System.out.println("--> Suppression des anciennes préférences...");
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
     * Assigne des objets aux colons selon leurs préférences.
     * Premier algorithme de la partie 2 du projet, expliqué en details dans le README.
     */
    public void assignerObjets2() {
        // Liste des colons et des objets disponibles
        List<Colon> colonList = new ArrayList<>(colons.values());
        List<String> objetsDisponibles = new ArrayList<>(ressources);

        // Créer une assignation initiale aléatoire
        Map<Colon, String> meilleureAssignation = new HashMap<>();
        for (Colon colon : colonList) {
            String objet = objetsDisponibles.removeFirst();
            meilleureAssignation.put(colon, objet);
        }

        // Calculer la jalousie pour la solution initiale
        int meilleureJalousie = calculerJalousie(meilleureAssignation);

        // Algorithme glouton avec amélioration locale
        boolean amelioration = true;
        while (amelioration) {
            amelioration = false;

            // Explorer les échanges entre tous les couples de colons
            for (int i = 0; i < colonList.size(); i++) {
                for (int j = i + 1; j < colonList.size(); j++) {
                    Colon colon1 = colonList.get(i);
                    Colon colon2 = colonList.get(j);

                    // Échanger les objets assignés entre colon1 et colon2
                    Map<Colon, String> nouvelleAssignation = new HashMap<>(meilleureAssignation);
                    String temp = nouvelleAssignation.get(colon1);
                    nouvelleAssignation.put(colon1, nouvelleAssignation.get(colon2));
                    nouvelleAssignation.put(colon2, temp);

                    // Calculer la jalousie après l'échange
                    int nouvelleJalousie = calculerJalousie(nouvelleAssignation);

                    // Si la nouvelle assignation est meilleure (moins de jalousie), on l'adopte
                    if (nouvelleJalousie < meilleureJalousie) {
                        meilleureAssignation = nouvelleAssignation;
                        meilleureJalousie = nouvelleJalousie;
                        amelioration = true; // Il y a eu une amélioration, donc on continue
                    }
                }
            }
        }

        // Appliquer la meilleure assignation trouvée
        for (Map.Entry<Colon, String> entry : meilleureAssignation.entrySet()) {
            entry.getKey().setObjetAssigne(entry.getValue());
        }
    }

    /**
     * Calcule le nombre de colons jaloux pour une assignation donnée.
     * Nécessaire pour l'algorithme de la méthode assignerObjets2().
     * @param assignation Une correspondance entre les colons et leurs objets assignés.
     * @return Le nombre total de colons jaloux.
     */
    private int calculerJalousie(Map<Colon, String> assignation) {
        int jalousie = 0;

        for (Map.Entry<Colon, String> entry : assignation.entrySet()) {
            Colon colon = entry.getKey();
            String objetAssigne = entry.getValue();

            for (Colon relation : colon.getRelations()) {
                String objetRelation = assignation.get(relation);

                if (colon.getPreferences().indexOf(objetRelation) < colon.getPreferences().indexOf(objetAssigne)) {
                    jalousie++;
                    break; // Un colon jaloux n'est compté qu'une fois
                }
            }
        }
        return jalousie;
    }

    /**
     * Assigne des objets aux colons selon leurs préférences.
     * Second algorithme de la partie 2 du projet, expliqué en details dans le README.
     * Attention, la complexité de cet algorithme est O(n!), avec n le nombre de colons.
     */
    public void assignerObjets3() {
        System.out.println("--> Cherche l'assignation optimale...");
        // Liste des colons et des objets disponibles
        List<Colon> colonList = new ArrayList<>(colons.values());
        List<String> objetsDisponibles = new ArrayList<>(ressources);

        // Initialiser les variables pour stocker la meilleure assignation
        Map<Colon, String> meilleureAssignation = new HashMap<>();
        int meilleureJalousie = Integer.MAX_VALUE;

        // Générer toutes les permutations possibles des objets disponibles
        List<List<String>> permutations = genererPermutations(objetsDisponibles);

        // Parcourir chaque permutation pour calculer la jalousie
        for (List<String> permutation : permutations) {
            // Créer une assignation actuelle en utilisant la permutation
            Map<Colon, String> assignationActuelle = new HashMap<>();
            for (int i = 0; i < colonList.size(); i++) {
                assignationActuelle.put(colonList.get(i), permutation.get(i));
            }

            // Calculer la jalousie pour cette assignation
            int jalousieActuelle = calculerJalousie(assignationActuelle);

            // Vérifier si cette assignation est meilleure que la meilleure trouvée jusqu'à présent
            if (jalousieActuelle < meilleureJalousie) {
                meilleureJalousie = jalousieActuelle; // Mettre à jour la jalousie minimale
                meilleureAssignation = new HashMap<>(assignationActuelle); // Enregistrer la nouvelle meilleure assignation
            }
        }

        // Appliquer la meilleure assignation trouvée à chaque colon
        for (Map.Entry<Colon, String> entry : meilleureAssignation.entrySet()) {
            entry.getKey().setObjetAssigne(entry.getValue());
        }
    }

    /**
     * Génère toutes les permutations possibles d'une liste donnée.
     * Nécessaire pour l'algorithme de la méthode assignerObjets3().
     * @param liste La liste des éléments pour lesquels générer les permutations.
     * @return Une liste contenant toutes les permutations possibles.
     */
    private List<List<String>> genererPermutations(List<String> liste) {
        List<List<String>> result = new ArrayList<>();
        permuter(result, new ArrayList<>(), liste);
        return result;
    }

    /**
     * Fonction récursive pour générer les permutations d'une liste.
     * Nécessaire pour l'algorithme de la méthode assignerObjets3().
     * @param result La liste finale contenant toutes les permutations.
     * @param temp Une liste temporaire pour construire les permutations actuelles.
     * @param liste La liste initiale des éléments à permuter.
     */
    private void permuter(List<List<String>> result, List<String> temp, List<String> liste) {
        // Condition de terminaison : si la permutation temporaire a la même taille que la liste de départ
        if (temp.size() == liste.size()) {
            result.add(new ArrayList<>(temp)); // Ajouter la permutation actuelle au résultat
            return;
        }
        // Explorer chaque élément de la liste
        for (int i = 0; i < liste.size(); i++) {
            if (temp.contains(liste.get(i))) continue; // Éviter d'ajouter deux fois le même élément
            temp.add(liste.get(i)); // Ajouter l'élément actuel à la permutation temporaire
            permuter(result, temp, liste); // Appel récursif pour compléter la permutation
            temp.removeLast(); // Retirer l'élément pour explorer d'autres possibilités
        }
    }
}