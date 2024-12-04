package test;

import app.Colon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de tests pour la classe Colon.
 */
class ColonTest {
    private Colon colon;

    @BeforeEach
    void setUp() {
        colon = new Colon("Colon A");
    }

    @Test
    void testNom() {
        assertEquals("Colon A", colon.getNom());
    }

    @Test
    void testAjouterPreference() {
        colon.ajouterPreference("Objet 1");
        assertTrue(colon.getPreferences().contains("Objet 1"));
    }

    @Test
    void testAjouterRelation() {
        Colon autreColon = new Colon("Colon B");
        colon.ajouterRelation(autreColon);
        assertTrue(colon.getRelations().contains(autreColon));
    }

    @Test
    void testObjetAssigne() {
        colon.setObjetAssigne("Objet Assigné");
        assertEquals("Objet Assigné", colon.getObjetAssigne());
    }

    @Test
    void testSupprimerPreferences() {
        colon.ajouterPreference("Objet 1");
        colon.supprimerPreferences();
        assertTrue(colon.getPreferences().isEmpty());
    }

    @Test
    void testToString() {
        colon.ajouterPreference("Objet 1");
        String expected = "Colon: Colon A, Préférences: [Objet 1], Pas de relation, Objet assigné: null";
        assertEquals(expected, colon.toString());
    }

    // Cas où les relations sont vides
    @Test
    void testToStringAvecRelationsVides() {
        colon.ajouterPreference("Objet 1");
        String expected = "Colon: Colon A, Préférences: [Objet 1], Pas de relation, Objet assigné: null";
        assertEquals(expected, colon.toString());
    }

    // Cas où il n'y a aucune préférence
    @Test
    void testToStringSansPreferences() {
        String expected = "Colon: Colon A, Préférences: [], Pas de relation, Objet assigné: null";
        assertEquals(expected, colon.toString());
    }

    // Tester les relations avec d'autres colons
    @Test
    void testAjouterRelationMultiple() {
        Colon colonB = new Colon("Colon B");
        Colon colonC = new Colon("Colon C");

        colon.ajouterRelation(colonB);
        colon.ajouterRelation(colonC);

        assertTrue(colon.getRelations().contains(colonB));
        assertTrue(colon.getRelations().contains(colonC));
    }

    // Tester l'ajout de préférences en doublon
    @Test
    void testAjouterPreferenceDoublon() {
        // Ajouter la première préférence
        colon.ajouterPreference("Objet 1");
        // Vérifier que l'exception est bien lancée lors de l'ajout de la préférence en doublon
        NullPointerException thrown = assertThrows(NullPointerException.class, () -> {
            colon.ajouterPreference("Objet 1"); // Tentative d'ajout de la préférence en doublon
        });
        // Vérifier le message de l'exception
        assertEquals("La préférence existe deja.", thrown.getMessage());
        // Vérifier que la taille de la liste des préférences est toujours 1
        assertEquals(1, colon.getPreferences().size());
    }


    // Tester l'ajout de relation en doublon
    @Test
    void testAjouterRelationDoublon() {
        Colon colonB = new Colon("Colon B");
        // Ajouter la première relation
        colon.ajouterRelation(colonB);
        // Vérifier que l'exception est bien lancée lors de l'ajout d'une relation en doublon
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            colon.ajouterRelation(colonB); // Tentative d'ajout d'une relation en doublon
        });
        // Vérifier le message de l'exception
        assertEquals("La relation existe deja.", thrown.getMessage());
        // Vérifier que la taille de la liste des relations est toujours 1
        assertEquals(1, colon.getRelations().size());
    }


    // Tester ajout d'objet null dans les préférences (devrait échouer ou se comporter selon la logique)
    @Test
    void testAjouterPreferenceNull() {
        assertThrows(NullPointerException.class, () -> colon.ajouterPreference(null), "La préférence ne peut pas être nulle");
    }

    // Tester ajout d'objet null dans les relations (devrait échouer ou se comporter selon la logique)
    @Test
    void testAjouterRelationNull() {
        assertThrows(NullPointerException.class, () -> colon.ajouterRelation(null), "La relation ne peut pas être nulle");
    }

    // Tester l'assignation d'objet null (si cette opération doit être autorisée ou non)
    @Test
    void testSetObjetAssigneNull() {
        assertThrows(NullPointerException.class, () -> colon.setObjetAssigne(null), "L'objet assigné ne peut pas être nul");
    }

    // Tester la suppression des préférences quand il n'y a aucune préférence
    @Test
    void testSupprimerPreferencesVide() {
        colon.supprimerPreferences(); // La liste est déjà vide
        assertTrue(colon.getPreferences().isEmpty()); // Elle doit rester vide
    }

    // Tester si le colon sans relation et sans préférence affiche correctement ses informations
    @Test
    void testColonSansRelationEtPreference() {
        Colon colonSansRelation = new Colon("Colon Sans Relation");
        String expected = "Colon: Colon Sans Relation, Préférences: [], Pas de relation, Objet assigné: null";
        assertEquals(expected, colonSansRelation.toString());
    }
}
