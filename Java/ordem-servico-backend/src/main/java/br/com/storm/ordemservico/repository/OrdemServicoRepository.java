package br.com.storm.ordemservico.repository;

import br.com.storm.ordemservico.domain.OrdemServico;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Integer>{

}
