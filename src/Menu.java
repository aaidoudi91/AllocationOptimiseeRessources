import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// Classe Interface : affiche et lis les données des colonies et colons dans le terminal
class Menu {
    public static void lancer(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Colonie colonie = new Colonie();

        // Partie A
        if (args.length == 0) { // Si l'utilisateur ne donne pas d'argument, définition de la colonie par ligne de cmd
            colonie = sansFichier();
        }
        else if (args.length == 1) { //Si l'utilisateur donne un argument, on charge la colonie depuis le fichier
            colonie = avecFichier(args[0]);
        }
        else { //Si l'utilisateur donne plusieurs arguments, il y a un problème
            System.err.println("Trop d'arguments : uniquement le chemin du fichier est demandé");
            System.exit(0);
        }

        // Partie B
        boolean fin = false;
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
                    try {
                        colonie.echangerRessources(nom1, nom2);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erreur : " + e.getMessage());
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
            System.out.println(colonie.toString());
        }
        scanner.close();
    }

    private static Colonie sansFichier(){
        Colonie colonie = new Colonie();
        Scanner scanner = new Scanner(System.in);

        // Initialisation des colons et ressources
        int nombreColons = 0;
        boolean valide = false;
        System.out.print("Entrez le nombre de colons (entre 1 et 26) : ");
        while (!valide) { // Tant que l'utilisateur ne rentre pas un entier, on boucle
            try {
                nombreColons = scanner.nextInt();
                if (nombreColons < 1 || nombreColons > 26) {
                    throw new InputMismatchException();
                }
                valide = true; // Si pas d'exception, sortie de la boucle
            } catch (InputMismatchException e) { // Exception levée si l'entrée n'est pas du type attendu
                System.err.println("Erreur : veuillez entrer un entier entre 1 et 26");
                scanner.nextLine(); // Vide le tampon d'entrée
            }
        }

        colonie.setColons(nombreColons); // Défini les colons avec les lettres A, B, C, ...
        colonie.setRessources(nombreColons); // Défini les ressources avec les numéros 1, 2, 3, ...
        System.out.println("--> " + nombreColons + " colons ajoutés de noms suivant les lettres de l'alphabet.\n");



        boolean fin = false;
        while (!fin) {
            System.out.println("1) Ajouter une relation entre deux colons");
            System.out.println("2) Ajouter les préférences d’un colon");
            System.out.println("3) Fin");
            System.out.print("Choisissez une option : ");
            int choix = 0;

            valide = false;
            while (!valide) { // Tant que l'utilisateur ne rentre pas un entier, on boucle
                try {
                    choix = scanner.nextInt();
                    if (choix != 1 && choix != 2 && choix != 3) {
                        throw new InputMismatchException();
                    }
                    valide = true; // Si pas d'exception, sortie de la boucle
                } catch (InputMismatchException e) { // Exception levée si l'entrée n'est pas du type attendu
                    System.err.println("Erreur : veuillez entrer un entier entre 1 et 3");
                    scanner.nextLine(); // Vide le tampon d'entrée
                }
            }

            switch (choix) {
                case 1:
                    System.out.print("\nEntrez le nom des deux colons (format 'X Y'): ");
                    valide = false;
                    while (!valide) { // Tant que l'utilisateur ne rentre pas un entier, on boucle
                        String nom1 = scanner.next();
                        String nom2 = scanner.next();
                        try {
                            colonie.ajouterRelation(nom1, nom2);
                            valide = true; // Si pas d'exception, sortie de la boucle
                            System.out.println("--> La relation des colons a été ajoutée.\n");
                        } catch (Exception e) {
                            System.err.println("Erreur : " + e.getMessage() + " Réessayer.");
                            scanner.nextLine(); // Vide le tampon d'entrée
                        }
                    }
                    break;
                case 2:
                    System.out.print("\nEntrez le nom du colon et ses " + nombreColons + " préférences (format 'X 1 2 ..'): ");
                    ArrayList<String> preferences = new ArrayList<>();
                    valide = false;
                    while (!valide) { // Tant que l'utilisateur ne rentre pas un entier, on boucle
                        String nomColon = scanner.next();
                        // Lire les n préférences de l'utilisateur
                        for (int i = 0; i < nombreColons; i++) {
                            preferences.add(scanner.next());
                        }
                        try {
                            colonie.ajouterPreferences(nomColon, preferences);
                            valide = true; // Si pas d'exception, sortie de la boucle
                            System.out.println("--> Les préférences du colons ont été ajoutées.\n");
                        } catch (IllegalArgumentException e) {
                            System.err.println("Erreur : " + e.getMessage());
                            preferences.clear(); // Vide la liste
                            scanner.nextLine(); // Vide le tampon d'entrée
                        }
                    }
                    break;

                case 3:
                    if (colonie.verifierPreferencesCompletes()) { // Si les préférences sont completes, on affiche le résultat
                        colonie.assignerObjets();
                        System.out.println(colonie.toString());
                        fin = true;
                    }
                    break;
                default:
                    System.err.println("\nErreur : veuillez entrer un entier entre 1 et 3");
            }
        }
        return colonie;
    }

    private static Colonie avecFichier(String cheminFichier) {
        Colonie colonie = new Colonie();
        try {
            colonie.chargerFichier(cheminFichier);
            colonie.verifierPreferencesCompletes();
            colonie.assignerObjets();
            colonie.enregistreFichier("final.txt");
            System.out.println(colonie.toString());
        } catch (IOException e) {
            System.err.println("Erreur liée au fichier: " + e.getMessage());
            System.exit(0);
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
            System.exit(0);
        }
        return colonie;
    }

}
