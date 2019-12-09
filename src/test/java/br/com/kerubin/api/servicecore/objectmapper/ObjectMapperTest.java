package br.com.kerubin.api.servicecore.objectmapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
	
	@Test
	public void testWithList() {
		Container source = new Container();
		source.setFieldA("a");
		source.setFieldB(1230L);
		
		Father father1 = new Father();
		father1.setField1("f1");
		father1.setField2(100);
		
		Father father2 = new Father();
		father2.setField1("f2");
		father2.setField2(200);
		
		List<Father> fieldList = new ArrayList<>();
		fieldList.add(father1);
		fieldList.add(father2);
		
		source.setFieldList(fieldList);
		
		ObjectMapper mapper = new ObjectMapper();
		Container target = new Container();
		mapper.copyProperties(source, target, true);
		
		assertThat(target).isNotNull()
		.extracting(Container::getFieldA, Container::getFieldB)
		.contains(source.getFieldA(), source.getFieldB());
		
		assertThat(target.getFieldList()).hasSameElementsAs(target.getFieldList());
		
	}

}
