package io.github.diegomardu.mscartoes.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.diegomardu.mscartoes.application.representation.CartaoSaveRequest;
import io.github.diegomardu.mscartoes.application.representation.CartoesPorClienteResponse;
import io.github.diegomardu.mscartoes.domain.Cartao;
import io.github.diegomardu.mscartoes.domain.ClienteCartao;

@RestController
@RequestMapping("/v1/cartoes")
public class CartoesResource {
	
	@Autowired
	private CartaoService service;
	
	@Autowired
	private ClienteCartaoService clienteCartaoService;

	@GetMapping
	public String status() {
		return "ok";
	}
	
	@PostMapping
	public ResponseEntity cadastrar(@RequestBody CartaoSaveRequest request) {
		
		Cartao cartao = request.toModel();
		service.save(cartao);
		return ResponseEntity.status(HttpStatus.CREATED).build();
		
	}
	
	@GetMapping(params = "renda")
	public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam(name = "renda") Long renda){
		List<Cartao> list = service.getCartoesRendaMenorIgual(renda);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesCliente(@RequestParam("cpf") String cpf){
		
		List<ClienteCartao> list = clienteCartaoService.listarCartoesByCpf(cpf);
		List<CartoesPorClienteResponse> result = list.stream()
				.map(CartoesPorClienteResponse::fromModel)
				.collect(Collectors.toList());

		return ResponseEntity.ok(result);
	}
	
	
}
