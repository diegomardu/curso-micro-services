package io.github.diegomardu.mscartoes.infra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.diegomardu.mscartoes.domain.ClienteCartao;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long>{
	
	List<ClienteCartao> findByCpf(String cpf);

}
