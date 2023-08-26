/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.service;

import br.com.siemens.configmodule.entidade.Banco;
import br.com.siemens.configmodule.entidade.TipoBanco;
import br.com.siemens.configmodule.enumeracao.ValidacaoBanco;
import br.com.siemens.configmodule.repository.BancoRepository;
import br.com.siemens.configmodule.repository.TipoBancoRepository;
import br.com.siemens.configmodule.util.JpaUtil;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bancoServiceImpl")
public class BancoServiceImpl implements BancoService {

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private TipoBancoRepository tipoBancoRepository;

    @Override
    public List<ValidacaoBanco> salvarBanco(Banco banco) {
        List<ValidacaoBanco> validacao = this.validarBancoNovo(banco);
        if (validacao.isEmpty()) {
            this.bancoRepository.save(banco);
        }
        return validacao;
    }

    @Override
    public List<ValidacaoBanco> alterarBanco(Banco banco) {
        List<ValidacaoBanco> validacao = this.validarBancoExistenteSendoAlterado(banco);
        if (validacao.isEmpty()) {
            this.bancoRepository.save(banco);
        }
        return validacao;
    }

    private List<ValidacaoBanco> validarBancoNovo(Banco banco) {
        List<ValidacaoBanco> validacao = new ArrayList<>();
        String alias = banco.getAlias();
        if (this.bancoRepository.findByAliasIgnoreCase(alias) != null) {
            validacao.add(ValidacaoBanco.ALIAS_EXISTE);
        }
        if (this.testarConexaoBancoFalha(banco)) {
            validacao.add(ValidacaoBanco.IMPOSSIVEL_CONECTAR_BANCO);
        }
        return validacao;
    }

    private List<ValidacaoBanco> validarBancoExistenteSendoAlterado(Banco banco) {
        List<ValidacaoBanco> validacao = new ArrayList<>();
        String alias = banco.getAlias();
        Integer id = banco.getId();
        if (this.bancoRepository.findByAliasIgnoreCaseAndIdNot(alias, id) != null) {
            validacao.add(ValidacaoBanco.ALIAS_EXISTE);
        }
        if (this.testarConexaoBancoFalha(banco)) {
            validacao.add(ValidacaoBanco.IMPOSSIVEL_CONECTAR_BANCO);
        }
        return validacao;
    }

    @Override
    public boolean testarConexaoBancoFalha(Banco banco) {
        try {
            new JpaUtil(banco).getEntityManager();
            return false;
        } catch (PersistenceException ex) {
            return true;
        }
    }

    @Override
    public List<TipoBanco> pesquisarTodosTiposBancos() {
        return this.tipoBancoRepository.findAll();
    }

    @Override
    public List<Banco> pesquisarBancosPorAlias(String alias) {
        return this.bancoRepository.findByAliasContainingIgnoreCase(alias);
    }

    @Override
    public List<Banco> pesquisarTodosBancos() {
        return this.bancoRepository.findAll();
    }

    @Override
    public void excluirBanco(Integer id) {
        this.bancoRepository.delete(id);
    }

    @Override
    public Banco persquisarPorId(Integer id) {
        return this.bancoRepository.findOne(id);
    }
}
