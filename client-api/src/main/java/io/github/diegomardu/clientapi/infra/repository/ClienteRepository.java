package io.github.diegomardu.clientapi.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.diegomardu.clientapi.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	Cliente findByCpf(String cpf);

}
