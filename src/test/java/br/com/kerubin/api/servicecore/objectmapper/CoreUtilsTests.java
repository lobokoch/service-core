package br.com.kerubin.api.servicecore.objectmapper;

import org.junit.Test;

import br.com.kerubin.api.servicecore.util.CoreUtils;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CoreUtilsTests {
	
	@Test
	public void testFormatMoney1() {
		
		BigDecimal value = new BigDecimal("1234567890.987"); 
		String actual = CoreUtils.formatMoney(value);
		
		String expected = "R$ 1.234.567.890,987";
		
		assertThat(actual).isEqualTo(expected);
		
	}
	
	@Test
	public void testFormatMoney2() {
		
		BigDecimal value = new BigDecimal("1234567890.98"); // 2 casas decimais
		String actual = CoreUtils.formatMoney(value);
		
		String expected = "R$ 1.234.567.890,98"; // 3 casas decimais
		
		assertThat(actual).isEqualTo(expected);
		
	}
	
	@Test
	public void testFormatMoney3() {
		
		BigDecimal value = new BigDecimal("0"); // 2 casas decimais
		String actual = CoreUtils.formatMoney(value);
		
		String expected = "R$ 0,00"; 
		
		assertThat(actual).isEqualTo(expected);
		
	}
	
	@Test
	public void testGetTokensNormalCase() {
		String str = "Conta de Luz Bradesco C-celesc Distr./sc";
		List<String> expected = Arrays.asList("Conta", "Luz", "Bradesco", "celesc", "Distr");
		
		List<String> actual = CoreUtils.getTokens(str, 3, false);
		
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void testGetTokensLowerCase() {
		String str = "Conta de Luz Bradesco C-celesc Distr./sc";
		List<String> expected = Arrays.asList("conta", "luz", "bradesco", "celesc", "distr");
		
		List<String> actual = CoreUtils.getTokens(str, 3, true);
		
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void testGetTokens1() {
		String str = "Conta de Luz Bradesco C-celesc Distr./sc";
		List<String> expected = Arrays.asList("conta", "luz", "bradesco", "celesc", "distr");
		
		List<String> actual = CoreUtils.getTokens(str);
		
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void testGetTokens2() {
		String str = "Luz";
		List<String> expected = Arrays.asList("luz");
		
		List<String> actual = CoreUtils.getTokens(str);
		
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void testGetTokensEmpty() {
		String str = "";
		List<String> expected = Collections.emptyList();
		
		List<String> actual = CoreUtils.getTokens(str);
		
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void testGetTokensTwoSameWords() {
		String str = "Conta de Luz Bradesco C-celesc Distr./sc Luz";
		List<String> expected = Arrays.asList("conta", "luz", "bradesco", "celesc", "distr");
		
		List<String> actual = CoreUtils.getTokens(str);
		
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void testGetTokensWithManySpaces() {
		String str = "   Conta de    Luz    Bradesco  C-celesc   Distr . / sc    Luz   ";
		List<String> expected = Arrays.asList("conta", "luz", "bradesco", "celesc", "distr");
		
		List<String> actual = CoreUtils.getTokens(str);
		
		assertThat(actual).isEqualTo(expected);
	}

}