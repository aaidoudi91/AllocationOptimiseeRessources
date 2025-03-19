# Projet d'Allocation Optimisée des Ressources

Ce projet implémente un algorithme d'allocation optimisée des ressources en fonction des préférences et des conflits entre individus. L'objectif est de minimiser la jalousie entre les participants tout en respectant les contraintes définies.

## Contenu du projet
- Une archive exécutable `ProgAvanceeProjet.jar`
- Un dossier `src` contenant les packages `app` et `test`, avec respectivement les classes principales et les tests unitaires
- Le sujet du projet `Sujet.pdf`
- Un fichier d'exemple `colonie.txt`

## I) Exécution du programme
Le programme est divisé en 4 Classes placées dans le package `app` :
- `Main`, prenant ou non en paramètre un unique argument indiquant le chemin du fichier texte avec les informations de la colonie et servant uniquement à lancer la classe Menu via sa méthode main.
- `Menu`, faisant le lien entre l'utilisateur (via le terminal et le fichier texte) et les classes Colon et Colonie. 
- `Colon` & `Colonie` regroupent les fonctionnalités décrites dans la partie III) i. de ce document.

Le programme comprend également des tests unitaires situés dans le package `test`.

### Exécution :
1. **Avec l'archive `.jar`** :
   ```sh
   java -jar AllocationRessources.jar [chemin_du_fichier]
   ```
2. **Si le projet n'est pas compilé** :
   - Compiler les fichiers :
     ```sh
     javac app/*.java
     ```
   - Exécuter le programme :
     ```sh
     java app.Main [chemin_du_fichier]
     ```

Dans les deux cas, [chemin_du_fichier] est un fichier .txt du même type que celui donné (`colonie.txt`) optionnel.

## II) Algorithmes d'allocation
### 1. Algorithme naïf
- Attribue les ressources en parcourant chaque individu et en lui attribuant son premier choix disponible.
- Ignorance des relations et des conflits.

### 2. Algorithme heuristique rapide (glouton amélioré)
- Assigne initialement les ressources de manière aléatoire.
- Réalise des échanges locaux pour minimiser la jalousie.
- Bonne solution en temps court, mais non optimale globalement.

### 3. Algorithme optimal (exploration exhaustive)
- Génère toutes les permutations possibles d'assignation.
- Sélectionne l'affectation minimisant la jalousie.
- Complexité en `O(n!)`, utilisable uniquement pour de petites instances.

## III) Fonctionnalités
### Fonctionnalités implémentées
- Gestion des entrées via terminal et fichier texte.
- Sauvegarde de l'affectation dans un fichier.
- Ajout et gestion des préférences et relations.
- Calcul et réduction de la jalousie.
- Tests unitaires pour assurer la robustesse.


---
Projet réalisé par Aidoudi Aaron
