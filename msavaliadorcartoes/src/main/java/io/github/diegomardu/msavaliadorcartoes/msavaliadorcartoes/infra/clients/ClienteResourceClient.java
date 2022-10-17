package io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.infra.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.Cartao;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.DadosCliente;

@FeignClient(value = "msclientes", path = "/v1/clientes")
public interface ClienteResourceClient {

	@GetMapping(params = "cpf")
	ResponseEntity<DadosCliente> dadosCliente(@RequestParam("cpf") String cpf);

}
