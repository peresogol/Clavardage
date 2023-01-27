# Projet Clavardage (INSA Toulouse 2022-2023)

Sacha Cauli - Marco Ribeiro Badejo

Ce projet s'inscrit dans le cadre des cours de Conception Orientée Objet, de Programmation avancée en Java, et de Conduite de projet. L'objectif était de produire une application de messagerie répondant au cahier des charges présent dans le dossier "Conception" de ce dépôt. 

Le fichier exécutable de ce projet se trouve dans le dossier "Exécutable", et a été généré en utilisant Java 11 et Maven 3.8.1. Les dépendances sont incluses dans le .jar, qui est donc supposé être exécutable sur n'importe quelle machine disposant d'un environnement logiciel équivalent.

Pour des raisons liées à la façon dont ce projet a été réalisé (impossibilité de réaliser des tests autrement qu'en local et d'obtenir l'aide d'un professeur), certaines fonctionnalités sont indisponibles (modification du nom d'un utilisateur), bien qu'une partie du code permettant de réaliser ces actions soit présent dans le projet. Pour les mêmes raisons, un package appelé "Launcher" a été créé, dans lequel se trouve 3 classes "main". La classe "Launcher" est le lanceur de l'application à utiliser lorsque l'on souhaite déployer l'application sur plusieurs machine, et est par conséquent celle utilisée dans l'exécutable généré (aucun test n'a pu être mené, son fonctionnement est donc pour le moins incertain). Les Launchers "AAALaunchers" et "BBBLaunchers" (noms choisis pour permettre une différenciation rapide, utile lors du débuggage), permettent quant à eux d'effectuer des tests en local (seuls les numéros de ports utilisés changent). Ils ne peuvent être lancé que depuis un IDE, et ne nécessitent pas de configuration particulière.
