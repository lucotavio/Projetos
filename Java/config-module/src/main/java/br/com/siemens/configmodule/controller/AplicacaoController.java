/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.controller;

import br.com.siemens.configmodule.constante.Navegacao;
import br.com.siemens.configmodule.entidade.AplicacaoConfiguracao;
import br.com.siemens.configmodule.entidade.Banco;
import br.com.siemens.configmodule.entidade.Configuracao;
import br.com.siemens.configmodule.service.AplicacaoService;
import br.com.siemens.configmodule.session.Identidade;
import br.com.siemens.configmodule.util.Mensagem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import org.primefaces.event.RowEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

@SessionScoped
@ManagedBean(name = "aplicacaoController")
public class AplicacaoController {

    private final Logger logger = LoggerFactory.getLogger(AplicacaoController.class);

    @ManagedProperty(value = "#{identidade}")
    private Identidade identidade;

    private AplicacaoService aplicacaoService;

    private AplicacaoConfiguracao aplicacaoConfiguracao;

    private List<AplicacaoConfiguracao> listaAplicacaoConfiguracao;

    private Banco banco;

    private Mensagem mensagem;
    
    /*
      varialvel utilizada para renderizar na tela
      uma mensagem  de erro informando ao usuario que 
      aquele banco de dados não pussui as tabelas
      nescessarias para configurar uma aplicacao
    */
    private boolean bancoPossuiTabelas;

    @PostConstruct
    public void aposConstrucao() {
        this.mensagem = Mensagem.getInstance();
    }

    public AplicacaoConfiguracao getAplicacaoConfiguracao() {
        return aplicacaoConfiguracao;
    }

    /**
     * Seleciona o banco de dados que para alterar suas aplicacoes.
     * @return A navegacao padrao ao selecionar o banco
     */
    public String selecionarBanco() {
        try {
            this.bancoPossuiTabelas = true;
            MDC.put("idUsuario", identidade.getUsuario().getLogin());
            this.logger.info("Selecionou o banco para configurar suas aplicacoes: " + this.banco);
            this.aplicacaoConfiguracao = new AplicacaoConfiguracao();
            this.listaAplicacaoConfiguracao =  new ArrayList<>();
            this.aplicacaoService = new AplicacaoService(this.banco);
            this.listaAplicacaoConfiguracao = this.aplicacaoService.pesquisartodaAplicacaoConfiguracao();

        }catch (PersistenceException ex) {
            this.bancoPossuiTabelas = false;
            this.mensagem.add(FacesMessage.SEVERITY_ERROR, "BANCO_NAO_POSSUI_TABELA");
        }
        return Navegacao.CONFIGURAR_APLICACAO;
    }

    /**
     * Seleciona a aplicacao para alterar suas configuracaoes.
     */
    public void selecionarAplicacaoParaAlterarConfiguracao() {
        MDC.put("idUsuario", identidade.getUsuario().getLogin());
        StringBuilder stringLog = new StringBuilder();
        stringLog.append("Aplicacao selecionada para ser alterada: ");
        stringLog.append(this.aplicacaoConfiguracao);
        stringLog.append("  ");
        stringLog.append(this.criarLogStringPropriedades(this.aplicacaoConfiguracao.getListaConfiguracao()));
        this.logger.info(stringLog.toString());
    }

    /**
     * salva alteracoes feitas na aplicacao.
     * @param event
     */
    public void salvarConfiguracoes() {
        MDC.put("idUsuario", identidade.getUsuario().getLogin());
        StringBuilder stringLog = new StringBuilder();
        stringLog.append("Aplicacao alterada: ");
        stringLog.append(this.aplicacaoConfiguracao);
        stringLog.append("  ");
        stringLog.append(this.criarLogStringPropriedades(this.aplicacaoConfiguracao.getListaConfiguracao()));
        this.logger.info(stringLog.toString());
        List<String> listaRotuloCampoInvalido = this.aplicacaoService.salvarConfiguracoes(this.aplicacaoConfiguracao);
        if (listaRotuloCampoInvalido.isEmpty()) {
//            Configuracao configuracaoSelecionada = (Configuracao) event.getObject();
            StringBuilder mensagemInternacionalizada = new StringBuilder();
//            mensagemInternacionalizada.append(this.mensagem.getMessage("PROPRIEDADA_ALTERADA_SUCESSO"));
//            mensagemInternacionalizada.append(" ");
//            mensagemInternacionalizada.append(configuracaoSelecionada.getRotuloInternacionalizado());
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Propriedades salvas com sucesso", ""));
        } else {
            for (String rotuloCampoInvalido : listaRotuloCampoInvalido) {
                String invalido = this.mensagem.getMessage("INVALIDO");
                StringBuilder mensagemDeErro = new StringBuilder();
                mensagemDeErro.append(rotuloCampoInvalido);
                mensagemDeErro.append(" ");
                mensagemDeErro.append(invalido);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        mensagemDeErro.toString(), ""));
            }
        }
        this.aplicacaoConfiguracao = this.aplicacaoService.pesquisarPorId(this.aplicacaoConfiguracao.getId());
        this.listaAplicacaoConfiguracao = this.aplicacaoService.pesquisartodaAplicacaoConfiguracao();
    }
    
    public void salvarConfiguracao(RowEditEvent event){
       Configuracao  configuracao = (Configuracao) event.getObject(); 
       this.aplicacaoService.salvarConfiguracoes(this.aplicacaoConfiguracao);
       StringBuilder mensagemInternacionalizada =  new StringBuilder();
       mensagemInternacionalizada.append(this.mensagem.getMessage("PROPRIEDADE_ALTERADA"));
       mensagemInternacionalizada.append(" ");
       mensagemInternacionalizada.append(configuracao.getRotuloInternacionalizado());
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        mensagemInternacionalizada.toString(), ""));
    }

    /**
     * cria o onde serao mostradas todos os campos de uma propriedade.
     *
     * @param listaConfiguracoes Configuracoes a serem concatenadas
     * @return listaConfiguracoes de configuracoes concatenadas
     */
    private String criarLogStringPropriedades(List<Configuracao> listaConfiguracoes) {
        StringBuilder stringLog = new StringBuilder();
        stringLog.append("propriedades: ");
        for (Configuracao configuracao : listaConfiguracoes) {
            stringLog.append(configuracao);
            stringLog.append("   ");
        }
        return stringLog.toString();
    }

    public void setAplicacaoConfiguracao(AplicacaoConfiguracao aplicacaoConfiguracao) {
        this.aplicacaoConfiguracao = aplicacaoConfiguracao;
    }

    public List<AplicacaoConfiguracao> getListaAplicacaoConfiguracao() {
        return listaAplicacaoConfiguracao;
    }

    public void setListaAplicacaoConfiguracao(List<AplicacaoConfiguracao> listaAplicacaoConfiguracao) {
        this.listaAplicacaoConfiguracao = listaAplicacaoConfiguracao;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public boolean isBancoPossuiTabelas() {
        return bancoPossuiTabelas;
    }

    public void setBancoPossuiTabelas(boolean bancoPossuiTabelas) {
        this.bancoPossuiTabelas = bancoPossuiTabelas;
    }

    public void setIdentidade(Identidade identidade) {
        this.identidade = identidade;
    }
}
