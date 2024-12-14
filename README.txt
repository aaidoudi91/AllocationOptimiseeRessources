README du projet de Programmation Avancée et Application
Groupe de Nicolas ADAMCZYK & Aaron AIDOUDI


I) Exécution du programme
    Le programme est divisé en 4 Classes placées dans le package app :
    Main, prenant ou non en paramètre un unique argument indiquant le chemin du fichier texte avec les informations de la colonie et servant uniquement à lancer la classe Menu via sa méthode main.
    Menu, faisant le lien entre l'utilisateur (via le terminal et le fichier texte) et les classes Colon et Colonie.
    Colon & Colonie regroupent les fonctionnalités décrites dans la partie III) b. de ce document.
    Aussi, le programme comprend des tests unitaires dans le package test.

    Ainsi, pour exécuter le programme il faut :
        Si le projet n'est pas compilé :
            a. Compiler les fichiers du package projet avec la commande 'javac app/*.java' depuis le repertoire du projet.
            b. Exécuter le programme avec la commande 'java app.Main', en ajoutant ou non le chemin vers un fichier .txt à sa fin.
        Si le projet est dans une archive java :
            a. Exécuter le programme avec la commande 'java -jar ...'


II) Algorithme de résolution automatique
    //TO DO


III) Fonctionnalités
    a. Fonctionnalités correctement implémentées
        - Liées à l'interaction utilisateur - programme : le paramétrage de la colonie via le terminal, le paramétrage de la colonie via un fichier texte, la sauvegarde de la solution dans un fichier texte et la gestion des exceptions dans chacune des fonctionnalités précédentes (erreurs dans l'entrée via le terminal / via le .txt).
        - Liées aux colons : ajout de préférences de ressources, d'une relation, affichage des informations du colon.
        - Liées à la colonie : ajout d'un colon, de relations, verification de la complétude des préférences, assignation des objets (selon les algorithmes de résolution automatique), calculer les colons jaloux, échanger des ressources, définition des noms des colons et des préférences dans le cas de l'entrée par le terminal, affichage des informations de la colonie.
        - Liées aux tests unitaires : placés dans le package test, ces derniers constituent un ensemble le plus complet possible pour chacune des classes du programme.

    b. Fonctionnalités manquantes ou présentant des problèmes
        - (Bonus) Liées à JavaFX : pas d'interface graphique pour l'application, tout ce fait via le terminal et fichier texte.