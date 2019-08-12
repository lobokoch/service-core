package br.com.kerubin.api.servicecore.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BooleanWrapper {
	
	private boolean value;
	
	public BooleanWrapper(boolean value) {
		this.value = value;
	}

}
