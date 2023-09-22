package br.com.luciano.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private Long id;
	
	@Getter
	@Setter
	private String firstName;
	
	@Getter
	@Setter
	private String lastName;
	
	@Getter
	@Setter
	private String address;
	
	@Getter
	@Setter
	private String gender;

}
