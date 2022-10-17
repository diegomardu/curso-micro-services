package io.github.diegomardu.msclientes.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.diegomardu.msclientes.domain.Cliente;
import io.github.diegomardu.msclientes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	public Optional<Cliente> getByCPF(String cpf){
		return clienteRepository.findByCpf(cpf);
	}
}
