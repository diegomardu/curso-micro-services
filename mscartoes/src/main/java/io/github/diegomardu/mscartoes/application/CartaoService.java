package io.github.diegomardu.mscartoes.application;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.diegomardu.mscartoes.domain.Cartao;
import io.github.diegomardu.mscartoes.infra.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartaoService {
	
	private final CartaoRepository cartaoRepository;
	
	@Transactional
	public Cartao save(Cartao carta){
		return cartaoRepository.save(carta);
	}
	
	public List<Cartao> getCartoesRendaMenorIgual(Long renda){
		var rendaBigDecimal = BigDecimal.valueOf(renda);
		return cartaoRepository.findByRendaLessThanEqual(rendaBigDecimal);
	}

}
