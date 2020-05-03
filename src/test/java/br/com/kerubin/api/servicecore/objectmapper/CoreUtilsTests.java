package br.com.kerubin.api.servicecore.objectmapper;

import org.junit.Test;

import br.com.kerubin.api.servicecore.util.CoreUtils;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CoreUtilsTests {
	
	private static final String accents 	= "È,É,Ê,Ë,Û,Ù,Ï,Î,À,Â,Ô,è,é,ê,ë,û,ù,ï,î,à,â,ô,Ç,ç,Ã,ã,Õ,õ";
	private static final String expected	= "E,E,E,E,U,U,I,I,A,A,O,e,e,e,e,u,u,i,i,a,a,o,C,c,A,a,O,o";
	
	private static final String accents2	= "çÇáéíóúýÁÉÍÓÚÝàèìòùÀÈÌÒÙãõñäëïöüÿÄËÏÖÜÃÕÑâêîôûÂÊÎÔÛ";
	private static final String expected2	= "cCaeiouyAEIOUYaeiouAEIOUaonaeiouyAEIOUAONaeiouAEIOU";
	
	private static final String accents3	= "Gisele Bündchen da Conceição e Silva foi batizada assim em homenagem à sua conterrânea de Horizontina, RS.";
	private static final String expected3	= "Gisele Bundchen da Conceicao e Silva foi batizada assim em homenagem a sua conterranea de Horizontina, RS.";
	
	private static final String accents4	= "/Users/rponte/arquivos-portalfcm/Eletron/Atualização_Diária-1.23.40.exe";
	private static final String expected4	= "/Users/rponte/arquivos-portalfcm/Eletron/Atualizacao_Diaria-1.23.40.exe";
	
	@Test
	public void daysBetweenAbs() {
		assertThat(CoreUtils.daysBetweenAbs(LocalDate.of(2020, 3, 7), LocalDate.of(2020, 3, 7))).isZero();
		
		assertThat(CoreUtils.daysBetweenAbs(LocalDate.of(2020, 3, 7), LocalDate.of(2020, 3, 6))).isOne();
		assertThat(CoreUtils.daysBetweenAbs(LocalDate.of(2020, 3, 6), LocalDate.of(2020, 3, 7))).isOne();
		
		assertThat(CoreUtils.daysBetweenAbs(LocalDate.of(2020, 4, 10), LocalDate.of(2020, 4, 21))).isEqualTo(11);
		assertThat(CoreUtils.daysBetweenAbs(LocalDate.of(2020, 4, 21), LocalDate.of(2020, 4, 10))).isEqualTo(11);
		
		assertThat(CoreUtils.daysBetweenAbs(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 31))).isEqualTo(30);
		assertThat(CoreUtils.daysBetweenAbs(LocalDate.of(2020, 1, 31), LocalDate.of(2020, 1, 1))).isEqualTo(30);
		
		assertThat(CoreUtils.daysBetweenAbs(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 31))).isEqualTo(365);
		assertThat(CoreUtils.daysBetweenAbs(LocalDate.of(2020, 12, 31), LocalDate.of(2020, 1, 1))).isEqualTo(365);
	}
	
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
	
	// ****************************************
	@Test
	public void testFormatNumber1() {
		
		BigDecimal value = new BigDecimal("1234567890.987"); 
		String actual = CoreUtils.formatNumber(value);
		
		String expected = "1.234.567.890,987";
		
		assertThat(actual).isEqualTo(expected);
		
	}
	
	@Test
	public void testFormatNumber2() {
		
		BigDecimal value = new BigDecimal("1234567890.98"); // 2 casas decimais
		String actual = CoreUtils.formatNumber(value);
		
		String expected = "1.234.567.890,98"; // 3 casas decimais
		
		assertThat(actual).isEqualTo(expected);
		
	}
	
	@Test
	public void testFormatNumber3() {
		
		BigDecimal value = new BigDecimal("0"); // 2 casas decimais
		String actual = CoreUtils.formatNumber(value);
		
		String expected = "0,00"; 
		
		assertThat(actual).isEqualTo(expected);
		
	}
	// ****************************************
	
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
	
	@Test
	public void replacingAllAccents() {
		assertThat(expected).isEqualTo(CoreUtils.unaccent(accents));
		assertThat(expected2).isEqualTo(CoreUtils.unaccent(accents2));
		assertThat(expected3).isEqualTo(CoreUtils.unaccent(accents3));
		assertThat(expected4).isEqualTo(CoreUtils.unaccent(accents4));
	}

}
