package io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.application.ex;

import lombok.Getter;

public class ErroComuinicacaoMicroservicesException extends Exception{
	
	@Getter
	private Integer status;

	public ErroComuinicacaoMicroservicesException(String msg, Integer status) {
		super(msg);
		this.status = status;
	}
}
