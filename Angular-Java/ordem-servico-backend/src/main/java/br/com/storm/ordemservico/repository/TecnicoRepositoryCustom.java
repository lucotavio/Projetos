package br.com.storm.ordemservico.repository;

import br.com.storm.ordemservico.domain.Tecnico;


public interface TecnicoRepositoryCustom {
    
    Tecnico procurarPorId(Integer id);
}
