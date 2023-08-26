package br.com.storm.ordemservico.repository;

import br.com.storm.ordemservico.domain.Tecnico;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public class TecnicoRepositoryCustomImpl implements TecnicoRepositoryCustom{
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public Tecnico procurarPorId(Integer id) {
        return em.find(Tecnico.class, id);
    }
}
