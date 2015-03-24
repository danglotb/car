package rest.html;

public class GenerateHTML {

	public static String getHeader() {
		return "<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "<title>Liste des fichiers du répertoire courant</title>\n"
				+ "</head>\n" 
				+ "	<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js\"></script>"
				+ "<body>\n"
				+ "<a href=\"logout/\">Logout</a>\n"
				+ "<form method=\"post\" action=\".\" enctype=\"multipart/form-data\">\n"
				+ "<input type=\"text\" name=\"name\" placeholder=\"File name\"/>\n"
				+ "<input type=\"file\" name=\"content\">\n"
				+ "<input type=\"submit\" value=\"Upload file\">\n"
				+ "</form>\n" +
				"<script>" +
				"$(function(){" +
				"	$('input[type=submit]').on('click', function(){" +
				"		location.reload(true);"+
				"	});" +
				"});" +
				"</script>";
	}
}
