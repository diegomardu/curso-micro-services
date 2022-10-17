package io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import feign.FeignException;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.application.ex.DadosClienteNotFoundException;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.application.ex.ErroComuinicacaoMicroservicesException;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.application.ex.ErroSolicitacaoCartaoException;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.Cartao;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.CartaoAprovado;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.CartaoCliente;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.DadosCliente;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.DadosSolicitacaoEmissaoCartao;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.ProtocoloSolicitacaoCartao;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.RetornoAvaliacaoCliente;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.domain.model.SituacaoCliente;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.infra.clients.CartaoClienteResource;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.infra.clients.ClienteResourceClient;
import io.github.diegomardu.msavaliadorcartoes.msavaliadorcartoes.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

	private final ClienteResourceClient clienteResourceClient;

	private final CartaoClienteResource cartaoClienteResource;
	
	private final SolicitacaoEmissaoCartaoPublisher cartaoPublisher;

	public SituacaoCliente obterSituacaoCliente(String cpf)
			throws DadosClienteNotFoundException, ErroComuinicacaoMicroservicesException {

		try {

			ResponseEntity<DadosCliente> responseEntity = clienteResourceClient.dadosCliente(cpf);
			ResponseEntity<List<CartaoCliente>> cartoesResponse = cartaoClienteResource.getCartoesCliente(cpf);

			return SituacaoCliente.builder().cliente(responseEntity.getBody()).cartoes(cartoesResponse.getBody())
					.build();

		} catch (FeignException.FeignClientException e) {
			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			throw new ErroComuinicacaoMicroservicesException(e.getMessage(), status);
		}
	}

	public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
			throws DadosClienteNotFoundException, ErroComuinicacaoMicroservicesException {
		try {
			ResponseEntity<DadosCliente> clienteResponse = clienteResourceClient.dadosCliente(cpf);
			ResponseEntity<List<Cartao>> cartaoResponse = cartaoClienteResource.getCartoesRendaAte(renda);
			
			List<Cartao> cartoes = cartaoResponse.getBody();
			var listaCartoesAprovados = cartoes.stream().map(cartao -> {
				
				DadosCliente dadosCliente = clienteResponse.getBody();
				
				BigDecimal limiteBasico = cartao.getLimiteBasico();
				BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
				var fator = idadeBD.divide(BigDecimal.valueOf(10));
				BigDecimal limiteAprovado = fator.multiply(limiteBasico);
				
				
				CartaoAprovado aprovado = new CartaoAprovado();
				aprovado.setCartao(cartao.getNome());
				aprovado.setBandeira(cartao.getBandeira());
				aprovado.setLimiteAprovado(limiteAprovado);
				
				return aprovado;
			}).collect(Collectors.toList());
			
			return new RetornoAvaliacaoCliente(listaCartoesAprovados);
			
		} catch (FeignException.FeignClientException e) {
			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			throw new ErroComuinicacaoMicroservicesException(e.getMessage(), status);
		}
	}
	
	public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados) {
		try {
			cartaoPublisher.solicitarCartao(dados);
			var protocolo = UUID.randomUUID().toString();
			return new ProtocoloSolicitacaoCartao(protocolo);
		} catch (Exception e) {
			throw new ErroSolicitacaoCartaoException(e.getMessage());
		}
	}

}
