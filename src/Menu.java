import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// Classe Interface : affiche et lis les données des colonies et colons dans le terminal
class Menu {
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

    private static void sansFichier(){
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


        // Partie A
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
                    while (!valide) {
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
                    while (!valide) {
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

        // Partie B
        fin = false;
        while (!fin) {
            System.out.println("1) Échanger les ressources de deux colons");
            System.out.println("2) Afficher le nombre de colons jaloux");
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
                    while (!valide) {
                        String nom1 = scanner.next();
                        String nom2 = scanner.next();
                        try {
                            colonie.echangerRessources(nom1, nom2);
                            valide = true; // Si pas d'exception, sortie de la boucle
                            System.out.println("--> L'échange a été éffectué.\n");
                        } catch (Exception e) {
                            System.err.println("Erreur : " + e.getMessage() + " Réessayer.");
                            scanner.nextLine(); // Vide le tampon d'entrée
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
            System.out.println(colonie.toString());
        }
        scanner.close();
    }

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
                    System.out.print("\n");
                    try {
                        colonie.verifierPreferencesCompletes();
                        colonie.assignerObjets();
                        System.out.println(colonie.toString());
                    } catch (Exception e) {
                        System.err.println("Erreur : " + e.getMessage() + " Réessayer.");
                        }

                    break;
                case 2:
                    System.out.print("\nEntrer le chemin du fichier vers lequel enregistrer la solution : ");
                    valide = false;
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

}
