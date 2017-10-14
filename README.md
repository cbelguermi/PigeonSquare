Programmation objet avancée

UQAC, Semestre d'automne 2017

Damien Haurat - Chloé Belguermi

==================================================

Lancer l'application (en ligne de commande) :


Se placer dans le dossier PigeonSquare :

cd Pigeon

Créer un dossier build :

mkdir build 

Se placer dans le dossier build :

cd build

Compiler les fichiers source :

javac -d . ../*.java

Il peut être nécessaire de copier les images au sein du package :

cp -r ../images ./pigeonsquare/images

Exécuter l'application :

java pigeonsquare.SquareWindow


===================================================

Utilisation de l'application :

 - Un clic gauche permet placer un pigeon.
 - Un clic droit permet de placer de la nourriture.
 - Un clic à l'aide la molette de la souris permet de placer une personne. 