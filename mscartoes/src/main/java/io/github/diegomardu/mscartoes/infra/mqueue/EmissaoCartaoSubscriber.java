package io.github.diegomardu.mscartoes.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.diegomardu.mscartoes.domain.Cartao;
import io.github.diegomardu.mscartoes.domain.ClienteCartao;
import io.github.diegomardu.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import io.github.diegomardu.mscartoes.infra.repository.CartaoRepository;
import io.github.diegomardu.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmissaoCartaoSubscriber {
	
	private final CartaoRepository cartaoRepository;
	
	private final ClienteCartaoRepository clienteCartaoRepository;
	
	@RabbitListener(queues = "${mq.queues.emissao-cartoes}")
	public void rebecerSolicitacaoEmissao(String payload) {
		try {
			var mapper = new ObjectMapper();
			DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);			
			Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
			
			ClienteCartao clienteCartao = new ClienteCartao();
			clienteCartao.setCartao(cartao);
			clienteCartao.setCpf(dados.getCpf());
			clienteCartao.setLimite(dados.getLimiteLiberado());
			
			clienteCartaoRepository.save(clienteCartao);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
