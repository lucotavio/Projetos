/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.repository;

import br.com.siemens.configmodule.entidade.AplicacaoConfiguracao;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class AplicacaoRepository {

    private final EntityManager em;

    private EntityTransaction transaction;

    public AplicacaoRepository(EntityManager em) {
        this.em = em;
        this.transaction = this.em.getTransaction();
    }

    /**
     * Salva objeto do tipo AplicacaoConfiguracao
     * @param aplicacaoConfiguracao 
     */
    public void salvarAplicacaoConfiguracao(AplicacaoConfiguracao aplicacaoConfiguracao) {
        this.em.merge(aplicacaoConfiguracao);
    }

    /**
     * Retorna uma lista com todas as AplicacaoConfiguracao cadastradas
     * @return 
     */
    public List<AplicacaoConfiguracao> pesquisartodaAplicacaoConfiguracao() {
        return em.createQuery("SELECT ac FROM AplicacaoConfiguracao ac", AplicacaoConfiguracao.class)
                .getResultList();
    }

    /**
     * Inicia uma transacao
     */
    public void iniciarTransacao() {
        this.transaction.begin();
    }

    
    /**
     * Efetua commit na transacao
     */
    public void commit() {
        this.transaction.commit();
    }

    /**
     * Fecha o EntityManager
     */
    public void fecharEntityManager() {
        this.em.close();
    }

    /**
     * Pesquisa por id
     * @param id
     * @return 
     */
    public AplicacaoConfiguracao pesquisarPorId(Integer id) {
        return this.em.find(AplicacaoConfiguracao.class, id);
    }
}
