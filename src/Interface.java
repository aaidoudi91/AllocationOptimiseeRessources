import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe Interface : affiche et lis les données des colonies et colons dans le terminal
public class Interface{
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

