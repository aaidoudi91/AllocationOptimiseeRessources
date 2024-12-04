import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * La classe Menu gère l'interaction avec l'utilisateur dans le terminal.
 * Elle permet de lancer l'application en fonction des arguments donnés, de gérer les colonies,
 * les colons et leurs préférences, ainsi que de charger ou de sauvegarder des fichiers.
 */
class Menu {
    /**
     * Lance le programme en fonction des arguments fournis.
     * @param args Les arguments de la ligne de commande. Si aucun argument n'est donné, la colonie est définie
     *             manuellement, sinon elle est chargée depuis un fichier.
     */
    public static void lancer(String[] args) {
        if (args.length == 0) { // Si l'utilisateur ne donne pas d'argument, définition de la colonie par ligne de cmd
            sansFichier();
        }
        else if (args.length == 1) { //Si l'utilisateur donne un argument, on charge la colonie depuis le fichier
            avecFichier(args[0]);
        }
        else { //Si l'utilisateur donne plusieurs arguments, il y a un problème
            System.err.println("Trop d'arguments : uniquement le chemin du fichier est demandé");
            System.exit(1);
        }
    }

    /**
     * Initialisation de la colonie sans fichier. Cette méthode permet à l'utilisateur de définir le nombre de colons
     * et leurs ressources, puis d'ajouter des relations ou des préférences via le terminal.
     */
    private static void sansFichier(){
        Colonie colonie = new Colonie();
        Scanner scanner = new Scanner(System.in);

        // Initialisation des colons et ressources
        int nombreColons = 0;
        boolean valide = false;
        System.out.print("Entrez le nombre de colons (entre 1 et 26) : ");
        while (!valide) { // Tant que l'utilisateur ne rentre pas un entier, on boucle
            try {
                nombreColons =  Integer.parseInt(scanner.nextLine().trim()); // Déclenche une NumberFormatException si ce n'est pas un int
                if ((nombreColons < 1) || (nombreColons > 26)){
                    throw new InputMismatchException();
                }
                valide = true; // Si pas d'exception, sortie de la boucle
            } catch (NumberFormatException | InputMismatchException e) { // Exception levée si l'entrée n'est pas du type attendu
                System.err.println("Erreur : veuillez entrer un entier entre 1 et 26.");
            }
        }

        colonie.setColons(nombreColons); // Défini les colons avec les lettres A, B, C, ...
        colonie.setRessources(nombreColons); // Défini les ressources avec les numéros 1, 2, 3, ...
        System.out.println("--> " + nombreColons + " colons ajoutés de noms suivant les lettres de l'alphabet.\n");


        // Partie A
        boolean fin = false;
        while (!fin) {
            System.out.println("1) Ajouter une relation entre deux colons");
            System.out.println("2) Ajouter les préférences d’un colon");
            System.out.println("3) Fin");
            System.out.print("Choisissez une option : ");
            int choix = 0;

            valide = false;
            choix = getChoix(scanner, choix, valide);

            switch (choix) {
                case 1:
                    System.out.print("\nEntrez le nom des deux colons (format 'X Y'): ");

                    while (!valide) {
                        String ligne = scanner.nextLine();
                        String[] noms = ligne.split(" "); // Diviser la ligne en mots par les espaces
                        try {
                            if (noms.length != 2) { // Vérifier qu'il y a exactement deux noms
                                throw new IllegalArgumentException("Vous devez entrer exactement deux noms séparés " +
                                        "par un unique espace.");
                            }
                            // Ajouter la relation entre les colons
                            String nom1 = noms[0];
                            String nom2 = noms[1];
                            colonie.ajouterRelation(nom1, nom2);
                            valide = true; // Si pas d'exception, sortie de la boucle
                            System.out.println("--> La relation des colons a été ajoutée.\n");
                        } catch (Exception e) {
                            System.err.println("Erreur : " + e.getMessage() + " Réessayer.");
                        }
                    }
                    break;
                case 2:
                    System.out.print("\nEntrez le nom du colon et ses " + nombreColons + " préférences (format 'X 1 2 ..'): ");
                    ArrayList<String> preferences = new ArrayList<>();
                    while (!valide) {
                        String ligne = scanner.nextLine();
                        String[] elements = ligne.split(" "); // Diviser la ligne en mots par les espaces
                        try {
                            // Vérifier que l'entrée contient au moins un nom + nombreColons préférences
                            if (elements.length != nombreColons + 1) {
                                throw new IllegalArgumentException("Le format est incorrect. Entrez un nom suivi de "
                                        + nombreColons + " préférences, séparés d'uniques espaces");
                            }
                            String nomColon = elements[0];

                            // Extraire les préférences
                            preferences.clear();
                            preferences.addAll(Arrays.asList(elements).subList(1, nombreColons + 1));

                            // Tenter d'ajouter les préférences
                            colonie.ajouterPreferences(nomColon, preferences);
                            valide = true; // Si pas d'exception, sortie de la boucle
                            System.out.println("--> Les préférences du colon ont été ajoutées.\n");
                        } catch (IllegalArgumentException e) {
                            System.err.println("Erreur : " + e.getMessage());
                        } catch (Exception e) {
                            System.err.println("Erreur inattendue : " + e.getMessage());
                        }
                    }

                    break;

                case 3:
                    if (colonie.verifierPreferencesCompletes()) { // Si les préférences sont completes, on affiche le résultat
                        colonie.assignerObjets();
                        System.out.println(colonie);
                        fin = true;
                    }
                    break;
                default:
                    System.err.println("\nErreur : veuillez entrer un entier entre 1 et 3");
            }
        }

        // Partie B
        fin = false;
        while (!fin) {
            System.out.println("1) Échanger les ressources de deux colons");
            System.out.println("2) Afficher le nombre de colons jaloux");
            System.out.println("3) Fin");
            System.out.print("Choisissez une option : ");
            int choix = 0;

            valide = false;
            choix = getChoix(scanner, choix, valide);

            switch (choix) {
                case 1:
                    System.out.print("\nEntrez le nom des deux colons (format 'X Y'): ");
                    while (!valide) {
                        String ligne = scanner.nextLine();
                        String[] noms = ligne.split(" "); // Diviser la ligne en mots par les espaces
                        try {
                            if (noms.length != 2) { // Vérifier qu'il y a exactement deux noms
                                throw new IllegalArgumentException("Vous devez entrer exactement deux noms séparés " +
                                        "par un unique espace.");
                            }
                            // Ajouter la relation entre les colons
                            String nom1 = noms[0];
                            String nom2 = noms[1];
                            colonie.echangerRessources(nom1, nom2);
                            valide = true; // Si pas d'exception, sortie de la boucle
                            System.out.println("--> L'échange a été éffectué.\n");
                        } catch (Exception e) {
                            System.err.println("Erreur : " + e.getMessage() + " Réessayer.");
                        }
                    }
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
            System.out.println(colonie);
        }
        scanner.close();
    }

    /**
     * Charge la colonie depuis un fichier spécifié par le chemin fourni. Cette méthode gère également les interactions
     * avec l'utilisateur pour la gestion des colons, relations et préférences.
     * @param cheminFichier Le chemin du fichier contenant les données de la colonie.
     */
    private static void avecFichier(String cheminFichier) {
        Colonie colonie = new Colonie();
        try {
            colonie.chargerFichier(cheminFichier);
            System.out.println("--> Colonie correctement initialisée avec le fichier.\n");
        } catch (IOException e) {
            System.err.println("Erreur liée au fichier: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);
        boolean fin = false;
        while (!fin) {
            System.out.println("1) Résolution automatique");
            System.out.println("2) Sauvegarder la solution actuelle");
            System.out.println("3) Fin");
            System.out.print("Choisissez une option : ");
            int choix = 0;

            boolean valide = false;
            choix = getChoix(scanner, choix, valide);

            switch (choix) {
                case 1:
                    System.out.print("\n");
                    try {
                        colonie.verifierPreferencesCompletes();
                        colonie.assignerObjets();
                        System.out.println(colonie);
                    } catch (Exception e) {
                        System.err.println("Erreur : " + e.getMessage() + " Réessayer.");
                        }

                    break;
                case 2:
                    System.out.print("\nEntrer le chemin du fichier vers lequel enregistrer la solution : ");
                    while (!valide) { // Tant que l'utilisateur ne rentre pas un entier, on boucle
                        String cheminFichierEcriture = scanner.next();
                        try {
                            colonie.enregistreFichier(cheminFichierEcriture);
                            valide = true; // Si pas d'exception, sortie de la boucle
                            System.out.println("--> Fichier enregistré.\n");
                        } catch (IOException e) {
                            System.err.println("Erreur liée au fichier : " + e.getMessage());
                        } catch (NullPointerException e){
                            System.err.println("Erreur : " + e.getMessage());

                        }
                    }
                    break;

                case 3:
                    System.exit(0);
                    break;
                default:
                    System.err.println("\nErreur : veuillez entrer un entier entre 1 et 3");
            }
        }
    }

    /**
     * Choix dans un menu à 3 options.
     * @param scanner le scanner ouvert lors du choix.
     * @param choix l'entier choisi par l'utilisateur.
     * @param valide le boolean valant false tant que l'entrée n'est pas conforme.
     * @return choix
     */
    private static int getChoix(Scanner scanner, int choix, boolean valide) {
        while (!valide) { // Tant que l'utilisateur ne rentre pas un entier, on boucle
            try {
                choix = Integer.parseInt(scanner.nextLine().trim()); // Déclenche une NumberFormatException si ce n'est pas un int
                if (choix != 1 && choix != 2 && choix != 3) {
                    throw new InputMismatchException();
                }
                valide = true; // Si pas d'exception, sortie de la boucle
            } catch (NumberFormatException | InputMismatchException e) { // Exception levée si l'entrée n'est pas du type attendu
                System.err.println("Erreur : veuillez entrer un entier entre 1 et 3");
            }
        }
        return choix;
    }
}
