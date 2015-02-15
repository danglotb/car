Serveur FTP

Benjamin Danglot
Antoine Philippe

17 Février 2014

Introduction : 

Implémentation d'un serveur FTP en java. Il respect le RFC 959. 
(http://fr.wikipedia.org/wiki/File_Transfer_Protocol)

Architecture : 

package 'serverftp':
Une classe Server, qui crée un objet de type FtpRequest dans un nouveau Thread 
pour chaque connexion de client.
La classe FtpRequest attends une requete de client, la traite, et renvoie le code correspondant.

package 'tests':
Une classe ServerTest(encapsulation de Server.java),
 qui est lancé dans un Thread afin d'effectuer les tests.
La Classe FtpRequestTest effectue une série de requete sur le serveur lancer dans le Thread
(cf ci-dessus) et test les codes de retour du serveur.

Codes sample :

TAMER


