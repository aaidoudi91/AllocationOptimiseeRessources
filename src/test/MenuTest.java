package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import app.Menu;

class MenuTest {
    // Sauvegarde de la sortie standard pour la restaurer après les tests
    private final PrintStream originalOut = System.out;
    // Flux pour capturer la sortie de la méthode print()
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    // Méthode exécutée avant chaque test pour rediriger la sortie standard
    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent)); // Redirige System.out vers outContent
    }

    // Méthode exécutée après chaque test pour restaurer la sortie standard originale
    @AfterEach
    void tearDown() {
        System.setOut(originalOut); // Restaure la sortie standard
    }

    // Test lorsque aucun fichier n'est passé en argument
    @Test
    void testLancerSansFichier() {
        String[] args = {}; // Aucun fichier passé en argument
        // Simulation d'une entrée utilisateur
        ByteArrayInputStream inContent = new ByteArrayInputStream("3\n".getBytes());
        System.setIn(inContent); // Redirige System.in vers inContent pour simuler une entrée

        // Appel de la méthode à tester
        Menu.lancer(args);

        // Vérification de la sortie pour s'assurer que le programme se comporte correctement
        String output = outContent.toString();
        assertTrue(output.contains("Entrez le nombre de colons")); // Vérifie que l'invite est présente
        assertTrue(output.contains("Fin du programme")); // Vérifie que le programme se termine correctement
    }

    // Test lorsqu'un fichier est passé en argument
    @Test
    void testLancerAvecFichier() {
        String[] args = {"colonie.txt"}; // Chemin vers un fichier valide
        ByteArrayInputStream inContent = new ByteArrayInputStream("3\n".getBytes());
        System.setIn(inContent);

        // Appel de la méthode
        Menu.lancer(args);

        // Vérification de la sortie
        String output = outContent.toString();
        assertTrue(output.contains("--> Colonie correctement initialisée avec le fichier.")); // Message de succès pour fichier
        assertTrue(output.contains("Fin du programme"));
    }

    // Test pour vérifier la gestion des erreurs d'entrée dans le menu
    @Test
    void testMenuInteractionSansFichier() {
        String[] args = {}; // Aucun fichier passé
        // Simulation d'une entrée incorrecte suivie d'une entrée correcte
        ByteArrayInputStream inContent = new ByteArrayInputStream("0\n1\n3\n".getBytes());
        System.setIn(inContent);

        Menu.lancer(args);

        // Vérification de la sortie
        String output = outContent.toString();
        assertTrue(output.contains("Erreur : veuillez entrer un entier entre 1 et 3")); // Vérifie l'erreur pour une mauvaise option
        assertTrue(output.contains("--> La relation des colons a été ajoutée.")); // Vérifie que l'action correcte a été réalisée
        assertTrue(output.contains("Fin du programme"));
    }

    // Test pour vérifier l'ajout d'une relation avec des erreurs de format
    @Test
    void testAjouterRelationException() {
        String[] args = {};
        ByteArrayInputStream inContent = new ByteArrayInputStream("1\nA B\n1\n".getBytes());
        System.setIn(inContent);

        Menu.lancer(args);

        // Vérification que le message d'erreur s'affiche correctement
        String output = outContent.toString();
        assertTrue(output.contains("Erreur : Vous devez entrer exactement deux noms séparés"));
    }

    // Test pour vérifier l'ajout de préférences avec des erreurs de format
    @Test
    void testAjouterPreferencesException() {
        String[] args = {};
        ByteArrayInputStream inContent = new ByteArrayInputStream("2\nA 1 2\n3\n".getBytes());
        System.setIn(inContent);

        Menu.lancer(args);

        // Vérification du message d'erreur
        String output = outContent.toString();
        assertTrue(output.contains("Erreur : Le format est incorrect. Entrez un nom suivi de"));
    }

    // Test pour vérifier la gestion des erreurs de sauvegarde de fichier
    @Test
    void testSauvegardeFichier() {
        String[] args = {"testfile.txt"}; // Fichier de test
        ByteArrayInputStream inContent = new ByteArrayInputStream("2\n".getBytes());
        System.setIn(inContent);

        Menu.lancer(args);

        // Vérification de la sortie d'erreur liée à l'écriture dans le fichier
        String output = outContent.toString();
        assertTrue(output.contains("Erreur liée au fichier"));
    }

    // Test pour vérifier l'échange de ressources entre colons
    @Test
    void testEchangeRessources() {
        String[] args = {};
        ByteArrayInputStream inContent = new ByteArrayInputStream("1\nA B\n3\n".getBytes());
        System.setIn(inContent);

        Menu.lancer(args);

        // Vérification que l'échange a bien eu lieu
        String output = outContent.toString();
        assertTrue(output.contains("--> L'échange a été éffectué."));
    }

    // Test pour vérifier la gestion d'un fichier inexistant
    @Test
    void testVerifieFichierInexistant() {
        String[] args = {"nonexistentfile.txt"}; // Chemin vers un fichier inexistant
        ByteArrayInputStream inContent = new ByteArrayInputStream("3\n".getBytes());
        System.setIn(inContent);

        Menu.lancer(args);

        // Vérification du message d'erreur lié au fichier inexistant
        String output = outContent.toString();
        assertTrue(output.contains("Erreur liée au fichier"));
    }

    // Test pour vérifier que le programme quitte correctement
    @Test
    void testQuitterProgramme() {
        String[] args = {};
        ByteArrayInputStream inContent = new ByteArrayInputStream("3\n".getBytes());
        System.setIn(inContent);

        Menu.lancer(args);

        // Vérification que le programme se termine correctement
        String output = outContent.toString();
        assertTrue(output.contains("Fin du programme"));
    }

}