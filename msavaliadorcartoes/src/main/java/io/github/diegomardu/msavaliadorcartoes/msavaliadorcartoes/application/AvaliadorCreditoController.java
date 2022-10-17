package io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.application.ex.DadosClienteNotFoundException;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.application.ex.ErroComuinicacaoMicroservicesException;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.application.ex.ErroSolicitacaoCartaoException;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.DadosAvaliacao;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.DadosSolicitacaoEmissaoCartao;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.ProtocoloSolicitacaoCartao;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.RetornoAvaliacaoCliente;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.SituacaoCliente;

@RestController
@RequestMapping("/v1/avaliador-cartoes")
public class AvaliadorCreditoController {

	@Autowired
	private AvaliadorCreditoService avaliadorCreditoService;

	@GetMapping
	public String status() {
		return "ok";
	}

	@GetMapping(value = "situacao-cliente", params = "cpf")
	public ResponseEntity<SituacaoCliente> consultaSituacaoCliente(@RequestParam("cpf") String cpf) {

		try {
			SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
			return ResponseEntity.ok(situacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.notFound().build();
		} catch (ErroComuinicacaoMicroservicesException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(e.getStatus()).build();
		}
	}
	
	@PostMapping
	public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dados) {
		try {
			RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
			return ResponseEntity.ok(retornoAvaliacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.notFound().build();
		} catch (ErroComuinicacaoMicroservicesException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(e.getStatus()).build();
		}
	}
	
	@PostMapping("solicitacoes-cartao")
	public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados) {
		try {
			ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = avaliadorCreditoService.solicitarEmissaoCartao(dados);
			return ResponseEntity.ok(protocoloSolicitacaoCartao);
		} catch (ErroSolicitacaoCartaoException e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
