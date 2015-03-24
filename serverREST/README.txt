Passerelle REST 

Benjamin DANGLOT
Antoine PHILIPPE
24/04/2015

Introduction :

Il s'agit d'implémenter une passerelle REST qui fait le lien entre le client et un serveur FTP.

Architecture :

	- La Class Starter initialise la passerelle, et ajoute l'handler de Service.
	- L'handler du service est assuré par la classe FileRestService.java. Elle fait correspondre des requetes a des methodes du service.
	- Le service (communication avec le serveur FTP) est assuré par la classe FileService.java. Elle entreprend la connection, et toutes
		les autres requetes du client.
	- La classe GenerateHTML permet de generer des pages HTML affichees au client.
	
Exception :
Resolution :

Code Samples :


