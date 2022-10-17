package io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.application.ex;

public class DadosClienteNotFoundException extends Exception{
	
	public DadosClienteNotFoundException() {
		super("Dados do cliente não encontrados para o CPF informado.");
	}

}
