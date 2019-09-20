package br.com.kerubin.api.servicecore.objectmapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;

import br.com.kerubin.api.servicecore.mapper.ObjectMapper;

public class ObjectMapperTest {
	
	@Test
	public void test_getAllDeclaredFields() {
		
		Child child = new Child();
		List<Field> fields = ObjectMapper.getAllDeclaredFields(child.getClass());
		
		assertThat(fields).hasSize(4)
		.extracting(Field::getName)
		.containsExactly("field3", "field4", "field1", "field2");
	}

}
