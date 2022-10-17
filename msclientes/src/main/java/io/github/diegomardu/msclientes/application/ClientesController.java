package io.github.diegomardu.msclientes.application;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.diegomardu.msclientes.application.representation.ClienteSaveRequest;
import io.github.diegomardu.msclientes.domain.Cliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClientesController {
	
	private final ClienteService service;
	
	/*
	 * @GetMapping public String status() { return "ok"; }
	 */
	
	@PostMapping
	public ResponseEntity save(@RequestBody ClienteSaveRequest request) {
		//Cliente cliente = request.toModel();
		var cliente = request.toModel();
		service.salvar(cliente);
		URI headerLocation = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.query("cpf={cpf}")
				.buildAndExpand(cliente.getCpf())
				.toUri();
		log.info("Cliente cadastrado.");
		return ResponseEntity.created(headerLocation).build();
		
	}
	
	@GetMapping
	public ResponseEntity dadosCliente(@RequestParam String cpf) {
		var cliente = service.getByCPF(cpf);
		if(cliente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(cliente);
	}
}
