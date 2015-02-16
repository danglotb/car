Serveur FTP

Benjamin Danglot
Antoine Philippe

17 Février 2014

Introduction : 

Implémentation d'un serveur FTP en java. Il respecte le RFC 959. 
(http://fr.wikipedia.org/wiki/File_Transfer_Protocol)

Architecture : 

package 'serverftp':
Une classe Server, qui crée un objet de type FtpRequest dans un nouveau Thread 
pour chaque connexion de client.
La classe FtpRequest attend une requete de client, la traite, et renvoie le code correspondant.
Une Classe DefConstant.java qui contient toutes les constantes utilisées par le protocole :
	*les réquêtes faites par les clients.
	*les réponses faites par le serveur aux client.

package 'tests':
Une classe ServerTest(encapsulation de Server.java),
 qui est lancé dans un Thread afin d'effectuer les tests.
La Classe FtpRequestTest effectue une série de requêtes sur le serveur lancé dans le Thread
(cf ci-dessus) et test les codes de retour du serveur.

Codes sample :

La methode run() hérité de Thread effectue une boucle infinie sur la methode processRequest().
Cette méthode switch sur la requete et appelle la methode process associée. La plupart 
de ces méthodes renvoie une string, qui est le code reponse. 

  public void processRequest() throws IOException {
		InputStream in = this.serv.getInputStream();
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));
		String req = bf.readLine();
		Scanner sc = new Scanner(req);
		sc.useDelimiter(" ");
		String type = sc.next();
		String rep = "";
		if (type != DefConstant.USER && this.user.equals(""))
			rep = DefConstant.NEED_USER;
		/* switching on the type of the request */
		switch (type) {
		case DefConstant.USER:
		case DefConstant.AUTH:
			rep = processUser(sc.next());
			break;
			.
			.
			.
			.
		default:
			System.out.println("unknown message type : " + type);
		}
		/* send the response */
		OutputStream out;
		out = serv.getOutputStream();
		DataOutputStream db = new DataOutputStream(out);
		db.writeBytes(rep);
		/* close */
		sc.close();
	}
	
	


