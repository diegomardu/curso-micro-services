package io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.infra.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.Cartao;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.CartaoCliente;

@FeignClient(value = "mscartoes", path = "/v1/cartoes")
public interface CartaoClienteResource {
	
	@GetMapping(params = "cpf")
	ResponseEntity<List<CartaoCliente>> getCartoesCliente(@RequestParam("cpf") String cpf);
	
	@GetMapping(params = "renda")
	ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam(name = "renda") Long renda);

}
