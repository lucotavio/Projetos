/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.service;

import br.com.siemens.configmodule.entidade.AplicacaoConfiguracao;
import br.com.siemens.configmodule.entidade.Banco;
import br.com.siemens.configmodule.repository.AplicacaoRepository;
import br.com.siemens.configmodule.util.JpaUtil;
import java.util.List;
import javax.persistence.EntityManager;

public class AplicacaoService {

    private AplicacaoRepository aplicacaoRepository;

    private Banco banco;

    public AplicacaoService(Banco banco) {
        this.banco = banco;
        EntityManager em = new JpaUtil(banco).getEntityManager();
        this.aplicacaoRepository = new AplicacaoRepository(em);
    }

    /**
     * Pesquisa todoas as aplicacoes no banco
     * @return 
     */
    public List<AplicacaoConfiguracao> pesquisartodaAplicacaoConfiguracao() {
        EntityManager em = new JpaUtil(banco).getEntityManager();
        this.aplicacaoRepository = new AplicacaoRepository(em);
        this.aplicacaoRepository.iniciarTransacao();
        List<AplicacaoConfiguracao> retorno = this.aplicacaoRepository.pesquisartodaAplicacaoConfiguracao();
        this.aplicacaoRepository.commit();
        this.aplicacaoRepository.fecharEntityManager();
        return retorno;
    }

    /**
     * Salva todas as alteracoes nas propriedades no banco
     * @param aplicacaoConfiguracao
     * @return 
     */
    public List<String> salvarConfiguracoes(AplicacaoConfiguracao aplicacaoConfiguracao) {
        List<String> mensagensDeErro = aplicacaoConfiguracao.validarCampos();
        if (mensagensDeErro.isEmpty()) {
            EntityManager em = new JpaUtil(banco).getEntityManager();
            this.aplicacaoRepository = new AplicacaoRepository(em);
            this.aplicacaoRepository.iniciarTransacao();
            this.aplicacaoRepository.salvarAplicacaoConfiguracao(aplicacaoConfiguracao);
            this.aplicacaoRepository.commit();
            this.aplicacaoRepository.fecharEntityManager();
        }
        return mensagensDeErro;
    }

    /**
     * Pesquisa uma AplicacaoConfiguracao por id
     * @param id
     * @return 
     */
    public AplicacaoConfiguracao pesquisarPorId(Integer id) {
        EntityManager em = new JpaUtil(banco).getEntityManager();
        this.aplicacaoRepository = new AplicacaoRepository(em);
        this.aplicacaoRepository.iniciarTransacao();
        AplicacaoConfiguracao retorno = this.aplicacaoRepository.pesquisarPorId(id);
        this.aplicacaoRepository.pesquisarPorId(id);
        return retorno;
    }

}
