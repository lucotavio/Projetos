package br.com.storm.ordemservico.repository;

import br.com.storm.ordemservico.domain.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>, TecnicoRepositoryCustom{

    Tecnico findByCpf(String cpf);
    
    Tecnico findByIdAndCpf(Integer id, String cpf);
}
