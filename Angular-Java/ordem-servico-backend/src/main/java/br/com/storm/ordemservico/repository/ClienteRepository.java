package br.com.storm.ordemservico.repository;

import br.com.storm.ordemservico.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
