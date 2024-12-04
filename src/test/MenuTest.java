package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import app.Menu;

class MenuTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void testLancerSansFichier() {
        String[] args = {};
        // Simuler l'entrée utilisateur dans le terminal
        ByteArrayInputStream inContent = new ByteArrayInputStream("3\n".getBytes());
        System.setIn(inContent);

        // Appel de la méthode
        Menu.lancer(args);

        // Vérification du résultat
        String output = outContent.toString();
        assertTrue(output.contains("Entrez le nombre de colons"));
        assertTrue(output.contains("Fin du programme"));
    }

    @Test
    void testLancerAvecFichier() {
        String[] args = {"path/to/file.txt"};
        // On simule un fichier valide
        ByteArrayInputStream inContent = new ByteArrayInputStream("3\n".getBytes());
        System.setIn(inContent);

        // Appel de la méthode
        Menu.lancer(args);

        // Vérification du résultat
        String output = outContent.toString();
        assertTrue(output.contains("--> Colonie correctement initialisée avec le fichier."));
        assertTrue(output.contains("Fin du programme"));
    }

    @Test
    void testMenuInteractionSansFichier() {
        String[] args = {};
        // Simulation d'une entrée incorrecte suivie d'une entrée correcte
        ByteArrayInputStream inContent = new ByteArrayInputStream("0\n1\n3\n".getBytes());
        System.setIn(inContent);

        Menu.lancer(args);

        // Vérifier que l'option choisie était valide
        String output = outContent.toString();
        assertTrue(output.contains("Erreur : veuillez entrer un entier entre 1 et 3"));
        assertTrue(output.contains("--> La relation des colons a été ajoutée."));
        assertTrue(output.contains("Fin du programme"));
    }

    @Test
    void testAjouterRelationException() {
        String[] args = {};
        ByteArrayInputStream inContent = new ByteArrayInputStream("1\nA B\n1\n".getBytes());
        System.setIn(inContent);

        Menu.lancer(args);

        String output = outContent.toString();
        assertTrue(output.contains("Erreur : Vous devez entrer exactement deux noms séparés"));
    }

    @Test
    void testAjouterPreferencesException() {
        String[] args = {};
        ByteArrayInputStream inContent = new ByteArrayInputStream("2\nA 1 2\n3\n".getBytes());
        System.setIn(inContent);

        Menu.lancer(args);

        String output = outContent.toString();
        assertTrue(output.contains("Erreur : Le format est incorrect. Entrez un nom suivi de"));
    }

    @Test
    void testSauvegardeFichier() {
        String[] args = {"testfile.txt"};
        ByteArrayInputStream inContent = new ByteArrayInputStream("2\n".getBytes());
        System.setIn(inContent);

        Menu.lancer(args);

        String output = outContent.toString();
        assertTrue(output.contains("Erreur liée au fichier"));
    }

    @Test
    void testEchangeRessources() {
        String[] args = {};
        ByteArrayInputStream inContent = new ByteArrayInputStream("1\nA B\n3\n".getBytes());
        System.setIn(inContent);

        Menu.lancer(args);

        String output = outContent.toString();
        assertTrue(output.contains("--> L'échange a été éffectué."));
    }

    @Test
    void testVerifieFichierInexistant() {
        String[] args = {"nonexistentfile.txt"};
        // On redirige l'output
        ByteArrayInputStream inContent = new ByteArrayInputStream("3\n".getBytes());
        System.setIn(inContent);

        Menu.lancer(args);

        // Vérifier le message d'erreur
        String output = outContent.toString();
        assertTrue(output.contains("Erreur liée au fichier"));
    }

    @Test
    void testQuitterProgramme() {
        String[] args = {};
        ByteArrayInputStream inContent = new ByteArrayInputStream("3\n".getBytes());
        System.setIn(inContent);

        Menu.lancer(args);

        // Vérifier que l'on a bien quitté le programme
        String output = outContent.toString();
        assertTrue(output.contains("Fin du programme"));
    }
}
