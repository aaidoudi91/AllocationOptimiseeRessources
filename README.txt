README du projet de Programmation Avancée et Application
Groupe de Nicolas ADAMCZYK & Aaron AIDOUDI
Le .zip rendu contient une archive .jar exécutable, un dossier src contenant les packages app et test, avec respectivement les classes du projet et les tests unitaires, ainsi que le sujet du projet Sujet.pdf et une colonie colonie.txt


I) Exécution du programme
    Le programme est divisé en 4 Classes placées dans le package app :
    Main, prenant ou non en paramètre un unique argument indiquant le chemin du fichier texte avec les informations de la colonie et servant uniquement à lancer la classe Menu via sa méthode main.
    Menu, faisant le lien entre l'utilisateur (via le terminal et le fichier texte) et les classes Colon et Colonie.
    Colon & Colonie regroupent les fonctionnalités décrites dans la partie III) i. de ce document.
    Aussi, le programme comprend des tests unitaires dans le package test.

    Ainsi, pour exécuter le programme, il faut :
        i. Avec l'archive java :
            Exécuter le programme avec la commande 'java -jar ADAMCZYK_AIDOUDI_Projet.jar' avec ou non le chemin d'un fichier texte en argument.
        ii. Si le projet n'est pas compilé :
            - Compiler les fichiers du package projet avec la commande 'javac app/*.java' depuis le repertoire /app du projet.
            - Exécuter le programme avec la commande 'java app.Main', en ajoutant ou non le chemin vers un fichier texte à sa fin.



II) Algorithmes de résolution automatique :
    i. Algorithme naïf de la partie 1 :
        La méthode assignerObjets de la classe Colonie parcourt un à un chacun colon et assigne l'objet le plus préféré parmi ceux disponibles, en ignorant les relations.

    ii. Algorithme rapide et approximatif :
        La méthode assignerObjets2 de la classe Colonie est une approche rapide efficace pour des grands problèmes, mais ne donnant la solution optimale.
        L'algorithme commence par une assignation initiale aléatoire des objets aux colons, puis calcule la jalousie associée.
        Ensuite, l’algorithme explore les échanges d'objets entre paires de colons pour réduire la jalousie totale.
        Si un échange diminue la jalousie, il est adopté, et l'exploration continue jusqu'à ce qu'aucun échange n'améliore la situation.
        Ce processus glouton avec améliorations locales garantit une meilleure répartition des ressources, même si elle n'est pas forcément optimale globalement.
        Pour les 3 exemples de colonies données, on obtient un nombre de colons jaloux respectif de 1, 3, et 1 ; en un temps négligeable.

    iii. Algorithme optimal, utilisé par défaut dans le projet :
        La méthode assignerObjets3 de la classe Colonie vise l’assignation des objets garantit une solution optimale en minimisant la jalousie entre les colons.
        Elle repose sur une approche exhaustive, générant toutes les permutations possibles des objets disponibles et calculant la jalousie pour chaque assignation.
        Grâce à cette méthode, nous identifions l'assignation qui minimise le nombre de colons jaloux.
        Cependant, bien que cette approche soit optimale, elle est coûteuse en termes de complexité, avec une durée d'exécution proportionnelle à la factorielle du nombre de colons O(n!).
        Elle est donc adaptée uniquement pour des instances de petite taille, où le nombre de colons et d'objets reste raisonnable.
        Cette implémentation comprend également une gestion claire et efficace des permutations grâce à des méthodes récursives.
        Si le nombre de colons ou d'objets augmente, il est recommandé d'utiliser une approche heuristique, comme l'algorithme glouton, pour obtenir une solution rapide mais approximative.
        Pour les 3 exemples de colonies données, on obtient un nombre de colons jaloux respectif de 0, 1, et 0 ; en quelques longues secondes.


III) Fonctionnalités
    i. Fonctionnalités correctement implémentées
        - Liées à l'interaction entre l'utilisateur et le programme : le paramétrage de la colonie via le terminal, le paramétrage de la colonie via un fichier texte, la sauvegarde de la solution dans un fichier texte, ainsi que toutes les fonctionnalités décrites dans le sujet, et la gestion des exceptions dans chacune des fonctionnalités précédentes (erreurs dans l'entrée via le terminal / via le .txt).
        - Liées aux colons : ajout de préférences de ressources, d'une relation, affichage des informations du colon.
        - Liées à la colonie : ajout d'un colon, de relations, verification de la complétude des préférences, assignation des objets (selon les algorithmes de résolution automatique), calculer les colons jaloux, échanger des ressources, définition des noms des colons et des préférences dans le cas de l'entrée par le terminal, affichage des informations de la colonie.
        - Liées aux tests unitaires : placés dans le package test, ces derniers constituent un ensemble le plus complet possible pour chacune des classes du programme.

    ii. Fonctionnalités manquantes ou présentant des problèmes
        - (Bonus) Liées à JavaFX : pas d'interface graphique pour l'application, tout se fait via le terminal et fichier texte.
