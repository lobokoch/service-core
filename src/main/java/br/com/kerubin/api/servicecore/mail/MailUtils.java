package br.com.kerubin.api.servicecore.mail;

import static br.com.kerubin.api.servicecore.util.CoreUtils.*;

public class MailUtils {
	
	public static final String EMAIL_KERUBIN_FINANCEIRO = "kerubin.financeiro@gmail.com";
	public static final String EMAIL_KERUBIN_SUPORTE = "kerubin.suporte@gmail.com";
	public static final String EMAIL_KERUBIN_PLATFORM = "kerubin.platform@gmail.com";
	
	public static final String BR = "<br>";
	
	public static final String SPAN_OPEN = "<span>";
	public static final String SPAN_CLOSE = "</span>";
	
	public static final String DIV_OPEN = "<div>";
	public static final String DIV_CLOSE = "</div>";
	
	public static final String TR_OPEN = "<tr>";
	public static final String TR_CLOSE = "</tr>";
	
	public static final String TD_OPEN = "<td>";
	public static final String TD_CLOSE = "</td>";
	
	
	public static final String KERUBIN_URL = "http://www.kerubin.com.br";
	public static final String KERUBIN_HTML = "<span style=\"color: #1e94d2; font-weight: bold;\">Kerubin</span>";
	public static final String KERUBIN_LINK = "<span style=\"color: #1e94d2; font-weight: bold;\"><a href=\"" + KERUBIN_URL + "\">Kerubin</a></span>";
	public static final String KERUBIN_LOGO = "<img alt=\"Kerubin\" src=\"https://i.ibb.co/DRRnWT1/logo.jpg\">";
	
	
	
	public static String getUserNameForMail(String username) {
		StringBuilder sb = new StringBuilder()
		.append("<span style=\"color:#1e94d2;\">")
		.append(getFirstName(username))
		.append("</span>");
		
		return sb.toString();
	}
	
	public static String buildKerubinLogoLine() {
		StringBuilder sb = new StringBuilder()
		.append("<tr>")
		.append("<td align=\"center\" valign=\"middle\" bgcolor=\"#1e94d2\">")
		
		.append(KERUBIN_LOGO)
		
		.append("</td>")
		.append("</tr>");
		
		return sb.toString();
	}
	
	public static String builEmailHTMLHeader(String title, String cssStyle) {
		StringBuilder sb = new StringBuilder()
		.append("<!DOCTYPE html>")
		.append("<html lang=\"pt-br\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">")
		.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">")
		.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">")
		.append("<title>");
		
		if (isNotEmpty(title)) {
			sb.append(title);
		}
		sb.append("</title>");
		
		sb.append("<style type=\"text/css\">");
		
		sb.append("table, td { font-family: Helvetica,Arial,sans-serif; font-size: 1.1em;}");
		
		if (isNotEmpty(cssStyle)) {
			sb.append(cssStyle);
		}
		sb.append("</style>");
		
		sb.append("</head>")
		.append("<body style=\"margin:0; padding:20px; background-color:#F2F2F2;\">")
		
		.append("<center>")
		.append("<table width=\"70%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">")
		.append(buildKerubinLogoLine());
		
		return sb.toString();
	}
	
	public static String builEmailHTMLFooter() {
		StringBuilder sb = new StringBuilder()
		
		.append(buildKerubinFooter())
		.append(buildEmptyLine())
		
		.append("</table>")
		.append("</center>")
		.append("</body>")
		.append("</html>");
		
		return sb.toString();
	}
	
	
	public static String buildKerubinHTML() {
		return "<span style=\"color: #1e94d2; font-weight: bold;\">Kerubin</span>";
	}
	
	public static String toStrong(String value) {
		return "<strong>" + value + "</strong>";
	}
	
	public static String builEmailHTMLSubject(String username, String text) {
		StringBuilder sb = new StringBuilder("<tr>")
		.append(" <td align=\"center\" valign=\"middle\">")
		.append("<span style=\"font-size: 1.5em; font-weight: bold;\">")
		.append("Olá ")
		.append("<span style=\"color:#1e94d2;\">")
		.append(username).append("!")
		.append("</span>").append(BR)
		.append(text)
		.append("</span>")
		.append("</td>")
		.append("</tr>");
		
		return sb.toString();
	}
	
	public static String buildKerubinFooter() {
		StringBuilder sb = new StringBuilder("<tr>")
		.append(" <td align=\"center\" valign=\"middle\">");
		
		sb.append("<p>Para mais detalhes, acesse a plataforma ")
		.append(KERUBIN_LINK).append(".")
		.append("</p>");
		
		sb.append("Abraços,<br>Equipe ").append(KERUBIN_HTML).append(".");
		
		sb.append("</td>")
		.append("</tr>");
		
		return sb.toString();
	}
	
	public static String buildEmptyLine() {
		StringBuilder sb = new StringBuilder("<tr>")
		.append(" <td>&nbsp;</td>")
		.append("</tr>");
		
		return sb.toString();
	}

}
