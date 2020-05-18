package br.com.kerubin.api.servicecore.util;

import static br.com.kerubin.api.servicecore.util.CoreUtils.isEmpty;

import java.text.MessageFormat;
import java.util.Base64;

public class Configs {
	
	private static final String KB_WEB_CLIENT = "KB_WEB_CLIENT";
	private static final String KB_WEB_CLIENT_SECRET = "KB_WEB_CLIENT_SECRET";
	private static final String KB_JWT_SIGNING_KEY = "KB_JWT_SIGNING_KEY";
	
	private static final String KB_ANONYMOUS_USER = "KB_ANONYMOUS_USER";
	
	private Configs() {
		// Helper class.
	}
	
	public static String getWebClient() {
		return getPropStrict(KB_WEB_CLIENT);
	}
	
	public static String getWebClientSecret() {
		return getPropStrictEncoded(KB_WEB_CLIENT_SECRET);
	}
	
	public static String getJWTSigningKey() {
		return getPropStrict(KB_JWT_SIGNING_KEY);
	}
	
	public static String getAnonymousUser() {
		return getPropStrict(KB_ANONYMOUS_USER);
	}
	
	
	//////////// TODO: UTIS - mudar mais tarde
	private static String getPropStrict(String name) {
		return getPropStrict(name, null);
	}
	
	private static String getPropStrictEncoded(String name) {
		byte[] decodedBytes = Base64.getDecoder().decode(getPropStrict(name, null));
		String decodedString = new String(decodedBytes);
		return decodedString;
	}
	
	/*private static String getEnvDef(String name, String defValue) {
		String value = System.getenv(name);
		return isNotEmpty(value) ? value : defValue;
	}*/
	
	private static String getProp(String name, String defVal) {
		String value = System.getProperty(name);
		if (isEmpty(value)) {
			value = System.getenv(name);
		}
		if (isEmpty(value)) {
			value = defVal;
		}
		return value;
	}
	
	private static String getPropStrict(String name, String defVal) {
		String value = getProp(name, defVal);
		
		if (isEmpty(value)) {
			throw new IllegalArgumentException(MessageFormat.format("Property \"{0}\" does not have a value.", name)) ;
		}
		
		return value;
	}
	
	/*private static int getPropInt(String name, int defVal) {
		String valueStr = System.getProperty(name);
		if (isEmpty(valueStr)) {
			valueStr = System.getenv(name);
		}
		
		return isNotEmpty(valueStr) ? Integer.parseInt(valueStr) : defVal;
	}*/
	
}
