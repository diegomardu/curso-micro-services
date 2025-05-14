package io.github.diegomardu.clientapi.application.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import io.github.diegomardu.clientapi.application.service.ClienteService;
import io.github.diegomardu.clientapi.domain.Cliente;
import io.github.diegomardu.clientapi.domain.dto.ClienteDTO;
import io.github.diegomardu.clientapi.infra.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService{
	
	private final ClienteRepository clienteRepository;
	
	public ClienteServiceImpl(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@Override
	@Transactional
	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Override
	public Cliente buscarPorCpf(String cpf) {
		return clienteRepository.findByCpf(cpf);
	}
	
	public Cliente toModel(ClienteDTO dto) {
		Cliente cliente = new Cliente();
		cliente.setCpf(dto.getCpf());
		cliente.setNome(dto.getNome());
		cliente.setIdade(dto.getIdade());
		return cliente;
	}

}
