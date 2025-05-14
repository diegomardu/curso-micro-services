package io.github.diegomardu.clientapi.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteDTO {
	
	private String nome;
	private String cpf;
	private Integer idade;

}
