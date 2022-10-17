package io.github.diegomardu.mscartoes.application.representation;

import java.math.BigDecimal;

import io.github.diegomardu.mscartoes.domain.BandeiraCartao;
import io.github.diegomardu.mscartoes.domain.Cartao;
import lombok.Data;

@Data
public class CartaoSaveRequest {

	private String nome;
	private BandeiraCartao bandeira;
	private BigDecimal renda;
	private BigDecimal limiteBasico;
	
	public Cartao toModel() {
		return new Cartao(nome, bandeira, renda, limiteBasico);
	}
}
