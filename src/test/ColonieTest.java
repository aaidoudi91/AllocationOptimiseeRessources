package test;
import app.Colonie;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.*;

class ColonieTest {
    private Colonie colonie;

    @BeforeEach
    void setUp() {
        colonie = new Colonie();
    }

    @Test
    void testAjouterColon() {
        colonie.ajouterColon("A");
        assertTrue(colonie.toString().contains("A"));

        // Test exception for empty name
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> colonie.ajouterColon(""));
        assertEquals("Le nom du colon ne peut pas être nul ou vide.", exception.getMessage());

        // Test exception for duplicate colon
        colonie.ajouterColon("B");
        IllegalArgumentException exceptionDuplicate = assertThrows(IllegalArgumentException.class, () -> colonie.ajouterColon("B"));
        assertEquals("Le colon B existe déjà.", exceptionDuplicate.getMessage());
    }

    @Test
    void testAjouterRelation() {
        colonie.ajouterColon("A");
        colonie.ajouterColon("B");

        colonie.ajouterRelation("A", "B");
        assertTrue(colonie.toString().contains("A"));
        assertTrue(colonie.toString().contains("B"));

        // Test exception for self relation
        IllegalArgumentException exceptionSelf = assertThrows(IllegalArgumentException.class, () -> colonie.ajouterRelation("A", "A"));
        assertEquals("Un colon ne peut pas être en inimité avec lui-même.", exceptionSelf.getMessage());

        // Test exception for non-existent colon
        IllegalArgumentException exceptionNonExistent = assertThrows(IllegalArgumentException.class, () -> colonie.ajouterRelation("A", "C"));
        assertEquals("Un ou les deux colons spécifiés n'existent pas.", exceptionNonExistent.getMessage());
    }

    @Test
    void testAjouterPreferences() {
        colonie.setRessources(2);
        colonie.setColons(2);
        colonie.ajouterPreferences("A", Arrays.asList("1", "2"));
        assertTrue(colonie.toString().contains("1"));
        assertTrue(colonie.toString().contains("2"));

        // Test exception for non-existent colon
        IllegalArgumentException exceptionNonExistentColon = assertThrows(IllegalArgumentException.class, () -> colonie.ajouterPreferences("C", Arrays.asList("1", "2")));
        assertEquals("Le colon n'existe pas", exceptionNonExistentColon.getMessage());
    }

    @Test
    void testVerifiePreferencesCompletes() {
        colonie.setRessources(3);
        colonie.setColons(3);
        colonie.ajouterPreferences("A", Arrays.asList("1", "2", "3"));
        colonie.ajouterPreferences("B", Arrays.asList("1", "2", "3"));

        assertFalse(colonie.verifierPreferencesCompletes());
    }

    @Test
    void testAssignerObjets() {
        colonie.setRessources(3);
        colonie.setColons(3);
        colonie.ajouterPreferences("A", Arrays.asList("1", "2", "3"));
        colonie.ajouterPreferences("B", Arrays.asList("1", "2", "3"));
        colonie.ajouterPreferences("C", Arrays.asList("1", "2", "3"));
        colonie.assignerObjets();

        assertNotNull(colonie.toString().contains("1"));
        assertNotNull(colonie.toString().contains("2"));
        assertNotNull(colonie.toString().contains("3"));
    }

    @Test
    void testCalculerColonsJaloux() {
        colonie.setRessources(3);
        colonie.setColons(3);
        colonie.ajouterRelation("A", "B");
        colonie.ajouterRelation("C", "B");
        colonie.ajouterPreferences("A", Arrays.asList("1", "2", "3"));
        colonie.ajouterPreferences("B", Arrays.asList("2", "3", "1"));
        colonie.ajouterPreferences("C", Arrays.asList("3", "1", "2"));
        colonie.assignerObjets();

        int colonsJaloux = colonie.calculerColonsJaloux();
        assertEquals(0, colonsJaloux);

    }

    @Test
    void testCalculerColonsJaloux2() {
        colonie.setRessources(3);
        colonie.setColons(3);
        colonie.ajouterRelation("A", "B");
        colonie.ajouterRelation("C", "B");
        colonie.ajouterPreferences("A", Arrays.asList("1", "2", "3"));
        colonie.ajouterPreferences("B", Arrays.asList("2", "3", "1"));
        colonie.ajouterPreferences("C", Arrays.asList("2", "3", "1"));
        colonie.assignerObjets();
        System.out.println(colonie);

        int colonsJaloux = colonie.calculerColonsJaloux();
        assertEquals(1, colonsJaloux);
    }

    @Test
    void testEchangerRessources() {
        colonie.setRessources(2);
        colonie.setColons(2);
        colonie.ajouterPreferences("A", Arrays.asList("1", "2"));
        colonie.ajouterPreferences("B", Arrays.asList("2", "1"));
        colonie.assignerObjets();

        colonie.echangerRessources("A", "B");

        // Test exception for non-existent colon
        IllegalArgumentException exceptionNonExistentColon = assertThrows(IllegalArgumentException.class, () -> colonie.echangerRessources("A", "C"));
        assertEquals("Un ou les deux colons spécifiés n'existent pas.", exceptionNonExistentColon.getMessage());
    }

    @Test
    void testChargerFichier() {
        // Test valid loading of file
        assertDoesNotThrow(() -> colonie.chargerFichier("colonie.txt"));

        // Test invalid file loading (non-existent file)
        assertThrows(IOException.class, () -> colonie.chargerFichier("fichier_inexistant.txt"));
    }

    @Test
    void testEnregistrerFichier() {
        colonie.setRessources(2);
        colonie.setColons(2);
        colonie.ajouterPreferences("A", Arrays.asList("1", "2"));
        colonie.ajouterPreferences("B", Arrays.asList("2", "1"));
        colonie.assignerObjets();

        assertDoesNotThrow(() -> colonie.enregistreFichier("fichier_sauvegarde.txt"));
    }
    @Test
    void testEchangerRessourcesTousAttribues() {
        colonie.setRessources(3);
        colonie.setColons(3);
        colonie.ajouterPreferences("A", Arrays.asList("1", "2", "3"));
        colonie.ajouterPreferences("B", Arrays.asList("1", "2", "3"));
        colonie.ajouterPreferences("C", Arrays.asList("1", "2", "3"));
        colonie.assignerObjets();

        // Test that an exchange is possible, even when all resources are assigned
        assertDoesNotThrow(() -> colonie.echangerRessources("A", "B"));
    }

    // Test pour vérifier si la méthode retourne une exception si une préférence est incomplète
    @Test
    void testSets() {
        colonie.setRessources(3);
        colonie.setColons(3);
        assertTrue(colonie.toString().contains("Colon: A"));
        assertTrue(colonie.toString().contains("Colon: B"));
        assertTrue(colonie.toString().contains("Colon: C"));
    }
}
