package io.github.diegomardu.clientapi.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.diegomardu.clientapi.application.service.impl.ClienteServiceImpl;
import io.github.diegomardu.clientapi.domain.Cliente;
import io.github.diegomardu.clientapi.domain.dto.ClienteDTO;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	private final ClienteServiceImpl clienteServiceImpl;
	
	public ClienteController(ClienteServiceImpl clienteServiceImpl) {
		this.clienteServiceImpl = clienteServiceImpl;
	}
	
	@PostMapping
	public Cliente salvar(@RequestBody ClienteDTO clientedto) {
		Cliente cliente = clienteServiceImpl.toModel(clientedto);
		return clienteServiceImpl.salvar(cliente);
	}
	
	@GetMapping("/{cpf}")
	public Cliente buscarPorCpf(@RequestParam String cpf) {
		return clienteServiceImpl.buscarPorCpf(cpf);
	}

}
