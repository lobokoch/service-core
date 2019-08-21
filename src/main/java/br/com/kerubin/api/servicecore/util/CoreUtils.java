package br.com.kerubin.api.servicecore.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

public class CoreUtils {
	
	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";
	public static final String ENTITIES = "entities";
	public static final String _ENTITIES_ = "/entities/";
	public static final String AUTO_COMPLETE = "autocomplete";
	
	public static boolean allCharsIsEquals(String value) {
		if(isEmpty(value)) {
			return true;
		}
		
		int index = 0;
		char actual = value.charAt(index++);
		char lastChar = actual; 
		while (lastChar == actual && index < value.length()) {
			lastChar = actual;
			actual = value.charAt(index++);			
		}
		
		return index == value.length();
	}
	
	public static boolean isNotEmpty(Object value) {
		return !isEmpty(value);
	}
	
	public static boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		}
		
		if (value instanceof String) {
			return ((String) value).trim().isEmpty();
		}
		
		if (value instanceof Collection) {
			return ((Collection<?>) value).isEmpty();
		}
		
		if (value.getClass().isArray()) {
			return Arrays.asList(value).isEmpty();
		}
		
		return false;
	}
	
	public static String getFirstName(String fullName) {
		String firstName = fullName.substring(0, fullName.indexOf(' ')).trim();
		if (firstName.isEmpty()) {
			firstName = fullName;
		}
		
		return firstUpper(firstName);
	}
	
	public static String firstUpper(String str) {
		if (isEmpty(str)) {
			return str;
		}
		
		if (Character.isUpperCase(str.charAt(0))) {
			return str;
		}
		
		if (str.length() == 1) {
			return str.toUpperCase();
		}
		
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	public static String lowerWithFirstUpper(String str) {
		if (isEmpty(str)) {
			return str;
		}
		
		String result = firstUpper(str.toLowerCase());
		return result;
	}
	
	public static String firstLower(String str) {
		if (isEmpty(str)) {
			return str;
		}
		
		if (Character.isLowerCase(str.charAt(0))) {
			return str;
		}
		
		if (str.length() == 1) {
			return str.toLowerCase();
		}
		
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}
	
	public static String getStringAlternate(String strIfTrue, String strIfFalse, BooleanWrapper booleanWrapper) {
		boolean flag = booleanWrapper.isValue();
		booleanWrapper.setValue(!flag);
		return flag ? strIfTrue : strIfFalse;
	}
	
	public static void saveText(String text, String fileName) {
		try {
			Charset charset = Charset.defaultCharset();
			FileUtils.writeStringToFile(new File(fileName), text, charset, false);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot save text for file: " + fileName, e);
		}
	}
	
	public static String formatDate(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return formatter.format(date);
	}
	
	public static String formatDateAndTime(LocalDateTime dateAnTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return formatter.format(dateAnTime);
	}
	
	public static BigDecimal getSafeValue(BigDecimal value) {
		return value != null ? value : new BigDecimal(0.0);
	}
	
	public static BigDecimal getSafePositiveValue(BigDecimal value) {
		return isGtZero(value) ? value : new BigDecimal(0.0);
	}
	
	/**
	 * Returns true if valueA is equals valueB, or false otherwise.
	 * */
	public static boolean isEquals(BigDecimal valueA, BigDecimal valueB) {
		if (isEmpty(valueA) && isEmpty(valueB)) {
			return true;
		}
		
		if (isEmpty(valueA) || isEmpty(valueB)) {
			return false;
		}
		
		
		int compResult = valueA.compareTo(valueB);
		return compResult == 0;
	}
	
	/**
	 * Returns true if valueA is greater then or equals valueB, or false otherwise.
	 * */
	public static boolean isGte(BigDecimal valueA, BigDecimal valueB) {
		boolean result = isGt(valueA, valueB) || isEquals(valueA, valueB);
		return result;
	}
	
	/**
	 * Returns true if valueA is less then or equals valueB, or false otherwise.
	 * */
	public static boolean isLte(BigDecimal valueA, BigDecimal valueB) {
		boolean result = isLt(valueA, valueB) || isEquals(valueA, valueB);
		return result;
	}
	
	/**
	 * Returns true if value is equals then BigDecimal.ZERO, or false otherwise.
	 * */
	public static boolean isZero(BigDecimal value) {
		return BigDecimal.ZERO.equals(value);
	}
	
	/**
	 * Returns true if value is greater then o equals to BigDecimal.ZERO, or false otherwise.
	 * */
	public static boolean isGteZero(BigDecimal value) {
		return isZero(value) || isGt(value, BigDecimal.ZERO);
	}
	
	/**
	 * Returns true if value is greater then BigDecimal.ZERO, or false otherwise.
	 * */
	public static boolean isGtZero(BigDecimal value) {
		return isGt(value, BigDecimal.ZERO);
	}
	
	/**
	 * Returns true if value is less then BigDecimal.ZERO, or false otherwise.
	 * */
	public static boolean isLtZero(BigDecimal value) {
		return isLt(value, BigDecimal.ZERO);
	}
	
	/**
	 * Returns true if value is less then o equals to BigDecimal.ZERO, or false otherwise.
	 * */
	public static boolean isLteZero(BigDecimal value) {
		return isZero(value) || isLt(value, BigDecimal.ZERO);
	}
	
	/**
	 * Returns true if valueA is greater then valueB, or false otherwise.
	 * */
	public static boolean isGt(BigDecimal valueA, BigDecimal valueB) {
		if (isEmpty(valueA) && isEmpty(valueB)) {
			return false;
		}
		
		if (isEmpty(valueA)) {
			return false;
		}
		
		if (isEmpty(valueB)) {
			return true;
		}
		
		
		int compResult = valueA.compareTo(valueB);
		return compResult == 1;
	}
	
	/**
	 * Returns true if valueA is less then valueB, or false otherwise.
	 * */
	//public static boolean isLessThan(BigDecimal valueA, BigDecimal valueB) {
	public static boolean isLt(BigDecimal valueA, BigDecimal valueB) {
		if (isEmpty(valueA) && isEmpty(valueB)) {
			return false;
		}
		
		if (isEmpty(valueA)) {
			return true;
		}
		
		if (isEmpty(valueB)) {
			return false;
		}
		
		
		int compResult = valueA.compareTo(valueB);
		return compResult == -1;
	}
	
	public static long getValue(Long value, long defaultValue) {
		return isNotEmpty(value) ? value : defaultValue;
	}
	
	public static final String ifThen(boolean condition, String trueValue, String falseValue) {
		return condition ? trueValue : falseValue;
	}
	
	public static boolean isAutoCompleteURL(String requestMethod, String url) {
		boolean isGET = GET.equals(requestMethod.toUpperCase());
		if (!isGET || isEmpty(url)) {
			return false;
		}
		
		return url.toLowerCase().contains(AUTO_COMPLETE);
	}
	
	public static boolean isListURL(String requestMethod, String url) {
		// http://localhost:9090/api/financeiro/contas_pagar/entities/contaPagar?size=10&dataVencimentoFrom=2019-08-01&dataVencimentoTo=2019-09-05&dataPagamentoIsNull=true&sort=dataVencimento,asc
		// http://localhost:9090/api/financeiro/contas_pagar/entities/contaPagar
		// http://localhost:9090/api/financeiro/contas_pagar/entities/contaPagar/aaaa-bbb-111-2222
		boolean isGET = GET.equals(requestMethod.toUpperCase());
		if (!isGET || isEmpty(url)) {
			return false;
		}
		
		int index = url.lastIndexOf(_ENTITIES_);
		if (index == -1) {
			return false;
		}
		
		String str = url.substring(index + _ENTITIES_.length());
		int posBarra = -1;
		int posInter = -1;
		for (int i = 0; i < str.length(); i++) {
			if (posBarra == -1 && str.charAt(i) == '/') {
				posBarra = i;
			}
			else if (posInter == -1 && str.charAt(i) == '?') {
				posInter = i;
			} 
			
			if (posBarra >= 0 || posInter >= 0) {
				break;
			}
		}
		
		if ((posBarra == -1 && posInter == -1) || posInter >= 0) {
			return true; // Is list, e. g.: /entities/contaPagar or /entities/contaPagar?xxx
		}
		
		if (posBarra >= 0) {
			str = str.substring(posBarra + 1);
			return str.trim().length() == 0;
		}
		
		return false;
	}
	
}
