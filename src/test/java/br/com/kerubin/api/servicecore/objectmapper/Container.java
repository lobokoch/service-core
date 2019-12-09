package br.com.kerubin.api.servicecore.objectmapper;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Container {
	
	private String fieldA;
	private Long fieldB;
	private List<Father> fieldList;

}
