package io.github.diegomardu.clientapi.application.service;

import org.springframework.stereotype.Service;

import io.github.diegomardu.clientapi.domain.Cliente;

@Service
public interface ClienteService {
	
	Cliente salvar(Cliente cliente);
	
	Cliente buscarPorCpf(String cpf);

}
