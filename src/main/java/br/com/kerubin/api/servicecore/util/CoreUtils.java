package br.com.kerubin.api.servicecore.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class CoreUtils {
	
	public static final String GET = "GET";
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	public static final String DELETE = "DELETE";
	public static final String ENTITIES = "entities";
	public static final String _ENTITIES_ = "/entities/";
	public static final String AUTO_COMPLETE = "autocomplete";
	
	public static final Locale LOCALE_PT_BR = new Locale("pt","BR");
	
	public static String format(String pattern, Object ... arguments) {
		return MessageFormat.format(pattern, arguments);
	}
	
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
	
	public static String formatMoney(DecimalFormat df, BigDecimal value) {
		BigDecimal val = getSafeValue(value);
		return df.format(val);
	}
	
	public static String formatMoney(BigDecimal value) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance(LOCALE_PT_BR);
		formatter.setMaximumFractionDigits(3);
		formatter.setMinimumFractionDigits(2);
		
		return formatter.format(value);
	}
	
	public static BigDecimal getSafeValue(BigDecimal value) {
		return value != null ? value : new BigDecimal(0.0);
	}
	
	public static BigDecimal getSafePositiveValue(BigDecimal value) {
		return isGtZero(value) ? value : new BigDecimal(0.0);
	}
	
	public static BigDecimal getDiff(BigDecimal value1, BigDecimal value2) {
		BigDecimal result = value1.subtract(value2).abs();
		return result;
	}
	
	public static boolean isNotEquals(BigDecimal valueA, BigDecimal valueB) {
		return !isEquals(valueA, valueB);
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
	 * Returns Convert a negative value to a positive value.
	 * */
	public static BigDecimal toPositive(BigDecimal value) {
		if (isLtZero(value)) {
			value = value.multiply(new BigDecimal(-1));
		}
		
		return value;
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
	
	public static <T> List<T> getRandomItemsOf(List<T> list, int size) {
		if (isEmpty(list) || size <= 0) {
			return Collections.emptyList();
		}
		List<T> result = new ArrayList<>();
		Random ran = new Random();
		int bound = list.size();
		int attempts = 0;
		do {
			attempts++;
			int index = ran.nextInt(bound);
			T item = list.get(index);
			if (!result.contains(item)) {
				result.add(item);
			}
		} while (result.size() < size && (attempts < Integer.MAX_VALUE));
		
		return result;
		
	}
	
	public static List<String> generateRandomStrings(String prefix, int begin, int end, int size) {
		String pre = isEmpty(prefix) ? "Some Prefix" : prefix;
		Random ran = new Random();
		int attempts= 0;
		
		if (size <= 0 || (end < begin)) {
			return Collections.emptyList();
		}
		
		List<String> result = new ArrayList<>();
		do {
			int nextInt = ran.nextInt(end);
			attempts++;
			if (isBetween(nextInt, begin, end)) {
				String str = pre + expandValue(nextInt);
				if (!result.contains(str)) {
					result.add(str);
				}
			}
			
		} while (result.size() < size && (attempts < Integer.MAX_VALUE)); // Prevent infinite loop.
		
		return result;
	}
	
	public static String expandValue(int value) {
		return expandValue("" + value);
	}
	
	public static String expandValue(String value) {
		String result = StringUtils.repeat(value, 3);
		return result;
	}
	
	public static boolean isBetween(int value, int begin, int end) {
		return value >= begin && value <= end;
	}
	
	public static String generateRandomString(int maxLength) {
		int length = (maxLength > 30) ? 30 : maxLength; 
		String chars = RandomStringUtils.randomAlphabetic(length - 1) + " ";
		String value = RandomStringUtils.random(length, chars).trim();
		
		// Must remove white spaces in the begining.
		int attempts= 0;
		while (value.length() < length && (attempts < Integer.MAX_VALUE) ) {
			attempts++;
			value = RandomStringUtils.random(length, chars).trim();
		} 

		return value;
	}
	
	public static LocalDate toLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static String trimLeft(String value, char target) {
		if (isEmpty(value)) {
			return value;
		}
		
		while (value.length() > 0 && value.charAt(0) == target) {
			value = value.substring(1);
		}
		
		return value;
	}
	
	public static List<String> getTokens(String str) {
		return CoreUtils.getTokens(str, /*minTokenLength=*/3, /*tokensToLower=*/true);
	}
	
	public static List<String> getTokens(String str, int minTokenLength, boolean tokensToLower) {
		if (isEmpty(str)) {
			return Collections.emptyList();
		}
		
		String separatorChars = " -.,/:";
		Set<String> tokens = new LinkedHashSet<>();
		
		int index = 0;
		int length = str.length();
		//"Conta de Luz Bradesco C-celesc Distr./sc"
		StringBuilder token = new StringBuilder();
		while (index < length) {
			char ch = str.charAt(index);
			index++;
			
			if (separatorChars.indexOf(ch) == -1) {
				token.append(ch);
			}
			else { // Break in a word
				String word = token.toString();
				if (word.length() >= minTokenLength) {
					if (tokensToLower) {
						word = word.toLowerCase();
					}
					tokens.add(word);
				}
				token = new StringBuilder();
			}
		} // while
		
		// Last token/word
		String word = token.toString();
		if (word.length() >= minTokenLength) {
			if (tokensToLower) {
				word = word.toLowerCase();
			}
			tokens.add(word);
		}
		
		return tokens.stream().collect(Collectors.toList());
	}
	
	public static <T> List<T> toSafeList(List<T> aList) {
		return isNotEmpty(aList) ? aList : Collections.emptyList();
	}
	
	
}
