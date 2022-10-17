package io.github.diegomardu.mscartoes.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.diegomardu.mscartoes.domain.ClienteCartao;
import io.github.diegomardu.mscartoes.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

	@Autowired private ClienteCartaoRepository cartaoRepository;
	
	public List<ClienteCartao> listarCartoesByCpf(String cpf){
		return cartaoRepository.findByCpf(cpf);
	}
}
