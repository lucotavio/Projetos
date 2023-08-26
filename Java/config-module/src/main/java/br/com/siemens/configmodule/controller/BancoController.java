/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.controller;

import br.com.siemens.configmodule.entidade.Banco;
import br.com.siemens.configmodule.entidade.TipoBanco;
import br.com.siemens.configmodule.enumeracao.ValidacaoBanco;
import br.com.siemens.configmodule.service.BancoService;
import br.com.siemens.configmodule.session.Identidade;
import br.com.siemens.configmodule.util.Mensagem;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


@SessionScoped
@ManagedBean(name = "bancoController")
public class BancoController {

    private final Logger logger = LoggerFactory.getLogger(BancoController.class);

    @ManagedProperty("#{bancoServiceImpl}")
    private BancoService bancoService;

    @ManagedProperty(value = "#{identidade}")
    private Identidade identidade;

    private Banco banco;

    private List<Banco> listaBancos;

    private List<TipoBanco> listaTipoBanco;

    private String pesquisaPorAlias;

    private boolean novoBanco;

    private Mensagem mensagem;

    /**
     * inicializa os campos.
     */
    private void inicializar() {
        this.banco = new Banco();
        this.novoBanco = true;
        try {
            if (this.pesquisaPorAlias == null || this.pesquisaPorAlias.isEmpty()) {
                this.listaBancos = this.bancoService.pesquisarTodosBancos();
            } else {
                this.listaBancos = this.bancoService.pesquisarBancosPorAlias(this.pesquisaPorAlias);
            }
        } catch (PersistenceException ex) {
            this.mensagem.add(FacesMessage.SEVERITY_ERROR, "ERRO_ACESSAR_BANCO_DADOS");
        }
    }

    @PostConstruct
    public void aposConstrucao() {
        this.mensagem = Mensagem.getInstance();
        this.listaTipoBanco = this.bancoService.pesquisarTodosTiposBancos();
        this.inicializar();
    }

    /**
     * salva um banco.
     */
    public void salvarBanco() {
        try {
            List<ValidacaoBanco> listaValidacaoBanco = this.bancoService.salvarBanco(this.banco);
            if (listaValidacaoBanco.isEmpty()) {
                MDC.put("idUsuario", identidade.getUsuario().getLogin());
                this.logger.info("Salvou o banco: " + this.banco.toString());
                this.inicializar();
                this.mensagem.add(FacesMessage.SEVERITY_INFO, "BANCO_SALVO_SUCESSO");
            } else {
                for (ValidacaoBanco validacao : listaValidacaoBanco) {
                    this.mensagem.add(FacesMessage.SEVERITY_ERROR, validacao.getRotulo());
                }
                this.listaBancos = this.bancoService.pesquisarTodosBancos();
            }
        } catch (PersistenceException ex) {
            this.mensagem.add(FacesMessage.SEVERITY_ERROR, "ERRO_ACESSAR_BANCO_DADOS");
        }
    }

    /**
     * altera o banco.
     */
    public void alterarBanco() {
        try {
            List<ValidacaoBanco> listaValidacaoBanco = this.bancoService.alterarBanco(this.banco);
            if (listaValidacaoBanco.isEmpty()) {
                MDC.put("idUsuario", identidade.getUsuario().getLogin());
                this.logger.info("Alterou o banco: " + banco.toString());
                this.inicializar();
                this.mensagem.add(FacesMessage.SEVERITY_INFO, "BANCO_ALTERADO_SUCESSO");
            } else {
                for (ValidacaoBanco validacao : listaValidacaoBanco) {
                    this.mensagem.add(FacesMessage.SEVERITY_ERROR, validacao.getRotulo());
                }
                this.listaBancos = this.bancoService.pesquisarTodosBancos();
            }
        } catch (PersistenceException ex) {
            this.mensagem.add(FacesMessage.SEVERITY_ERROR, "ERRO_ACESSAR_BANCO_DADOS");
        }
    }

    /**
     * seleciona um banco para alterar suas configuracoes.
     * @param bancoSelecionado banco selecionado para alteracao
     */
    public void selecionarBancoParaAlterarCampos(Banco bancoSelecionado) {
        MDC.put("idUsuario", identidade.getUsuario().getLogin());
        this.logger.info("Usuario selecionado para ter seus campos alterados: " + bancoSelecionado.toString());
        this.banco = bancoSelecionado;
        this.novoBanco = false;
    }

    /**
     * Cancela a alteracao de um banco.
     */
    public void cancelar() {
        MDC.put("idUsuario", identidade.getUsuario().getLogin());
        this.logger.info("Cancelada alteracao do banco: " + this.banco);
        this.banco = new Banco();
        this.novoBanco = true;
    }

    /**
     * Exclui um banco do sistema.
     *
     * @param id Id do banco a ser excluido
     */
    public void excluirBanco(Integer id) {
        try {
            final Banco bancoSendoExcluido = this.bancoService.persquisarPorId(id);
            this.bancoService.excluirBanco(id);
            this.listaBancos = this.bancoService.pesquisarTodosBancos();
            MDC.put("idUsuario", identidade.getUsuario().getLogin());
            this.logger.info("Exluiu o banco: " + bancoSendoExcluido.getAlias());
            this.mensagem.add(FacesMessage.SEVERITY_INFO, "BANCO_EXCLUIDO_SUCESSO");
        } catch (PersistenceException es) {
            this.mensagem.add(FacesMessage.SEVERITY_ERROR, "ERRO_ACESSAR_BANCO_DADOS");
        }
    }

    /**
     * pesquisa um banco por seu alias.
     */
    public void pesquisarBancos() {
        try {
            MDC.put("idUsuario", identidade.getUsuario().getLogin());
            this.logger.info("Pesquisou o banco: " + banco.getAlias());
            this.listaBancos = this.bancoService.pesquisarBancosPorAlias(this.pesquisaPorAlias);
        } catch (PersistenceException ex) {
            this.mensagem.add("formCadastroBanco:hiddenCadastrarBanco", FacesMessage.SEVERITY_ERROR, "ERRO_ACESSAR_BANCO_DADOS");
        }
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public List<Banco> getListaBancos() {
        return listaBancos;
    }

    public void setListaBancos(List<Banco> listaBancos) {
        this.listaBancos = listaBancos;
    }

    public List<TipoBanco> getListaTipoBanco() {
        return listaTipoBanco;
    }

    public void setListaTipoBanco(List<TipoBanco> listaTipoBanco) {
        this.listaTipoBanco = listaTipoBanco;
    }

    public String getPesquisaPorAlias() {
        return pesquisaPorAlias;
    }

    public void setPesquisaPorAlias(String pesquisaPorAlias) {
        this.pesquisaPorAlias = pesquisaPorAlias;
    }

    public void setBancoService(BancoService bancoService) {
        this.bancoService = bancoService;
    }

    public void setIdentidade(Identidade identidade) {
        this.identidade = identidade;
    }

    public boolean isNovoBanco() {
        return novoBanco;
    }

    public void setNovoBanco(boolean novoBanco) {
        this.novoBanco = novoBanco;
    }

}
