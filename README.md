# Programmation Orientée Objet Avancée

## Projet « PigeonSquare »

#### UQAC, Semestre d'automne 2017

##### Damien Haurat - Chloé Belguermi

==================================================

Pour lancer l'application (en ligne de commande) :


1. Se placer dans le dossier `src/pigeonsquare` :

    `cd src/pigeonsquare`

2. Créer un dossier `build` :

    `mkdir build`

3. Se placer dans le dossier `build` :

    `cd build`

4. Compiler les fichiers source :

    `javac -d . ../*.java`

5. Il peut être nécessaire de copier les images au sein du paquetage :

    `cp -r ../images ./pigeonsquare/images`

6. Exécuter l'application :

    `java pigeonsquare.SquareWindow`

===================================================

Utilisation de l'application :

* Un clic gauche permet placer un pigeon.
* Un clic droit permet de placer de la nourriture.
* Un clic à l'aide la molette de la souris permet de placer une personne.

Jusqu'à dix pigeons, trois humains et dix éléments de nourriture peuvent être placés simultanément dans la fenêtre.

Une fois mangé, un élément de nourriture disparaît de la fenêtre et « libère » ainsi une place pour un autre élément de nourriture.

Une fois placé, un humain se déplace aléatoirement au sein de la fenêtre. Il ne peut être enlevé.

===================================================

Crédits images :

* `pigeon.png` : http://www.downloadclipart.net/download/3507/pigeon-svg
* `food.png` : créée avec l'application Pages.
* `passerby.png` : créée avec l'application Pages.