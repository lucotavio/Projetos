/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.controller;

import br.com.siemens.configmodule.entidade.Autorizacao;
import br.com.siemens.configmodule.entidade.Usuario;
import br.com.siemens.configmodule.enumeracao.ValidacaoUsuario;
import br.com.siemens.configmodule.service.UsuarioService;
import br.com.siemens.configmodule.session.Identidade;
import br.com.siemens.configmodule.util.Mensagem;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ViewScoped
@ManagedBean(name = "usuarioController")
public class UsuarioController implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @ManagedProperty("#{usuarioServiceImpl}")
    private UsuarioService usuarioService;

    @ManagedProperty(value = "#{identidade}")
    private Identidade identidade;

    private Usuario usuario;

    private List<Usuario> listaUsuarios;

    private String campoSendoPesquisado;

    private String valorCampoSendoPesquisado;

    private boolean novoUsuario;

    private Mensagem mensagem;

    /**
     * inicializa os campos da tela.
     */
    private void inicializar() {
        this.usuario = new Usuario();
        this.novoUsuario = true;
        try {
            if (this.valorCampoSendoPesquisado == null || this.valorCampoSendoPesquisado.isEmpty()) {
                this.listaUsuarios = this.usuarioService.pesquisarTodosUsuarios();
            } else {
                this.listaUsuarios = this.usuarioService.pesquisarUsuarios(this.campoSendoPesquisado, this.valorCampoSendoPesquisado);
            }
        } catch (PersistenceException ex) {
            this.mensagem.add(FacesMessage.SEVERITY_ERROR, "ERRO_ACESSAR_BANCO_DADOS");
        }
    }

    /**
     * inicializa os campos na tela apos a criacao do managedbean.
     */
    @PostConstruct
    public void aposConstrucao() {
        this.mensagem = Mensagem.getInstance();
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String login = authentication.getName();
        Usuario usuarioLogado = this.usuarioService.pesquisarPorLogin(login);
        this.identidade.setUsuario(usuarioLogado);
        this.campoSendoPesquisado = "nome";
        this.inicializar();
    }

    /**
     * pesquisa uma lista de usuarios de acordo com campo selecionado na tela.
     */
    public void pesquisarUsuario() {
        try {
            MDC.put("idUsuario", identidade.getUsuario().getLogin());
            this.logger.info("Pesquisou os usarios");
            this.listaUsuarios = this.usuarioService.pesquisarUsuarios(this.campoSendoPesquisado, this.valorCampoSendoPesquisado);
        } catch (PersistenceException ex) {
            this.mensagem.add(FacesMessage.SEVERITY_ERROR, "ERRO_ACESSAR_BANCO_DADOS");
        }
    }

    /**
     * salva um usuario.
     */
    public void salvarUsuario() {

        try {
            Autorizacao autorizacao = new Autorizacao();
            autorizacao.setId(1);
            autorizacao.setPapel("ROLE_ADMIN");
            this.usuario.setAutorizacao(autorizacao);
            List<ValidacaoUsuario> validacaoUsuario = this.usuarioService.salvarUsuario(this.usuario);
            /*
             Se a lista estiver vazia quer dizer que não existe erro, e o usuario
             pode ser cadastrado 
             */
            if (validacaoUsuario.isEmpty()) {
                MDC.put("idUsuario", identidade.getUsuario().getLogin());
                this.logger.info("Salvou o usuario: " + this.usuario.toString());
                this.inicializar();
                this.mensagem.add(FacesMessage.SEVERITY_INFO, "USUARIO_SALVO_SUCESSO");
            } else {
                /*
                 caso a lista possua algum elemento quer dizer que ocorreu um erro ao 
                 tentar adicionar o usuario e ai vai acontecer uma iteracao por
                 essa lista e essas mensagem serao mostradas na tela para o usuario.
                 */
                for (ValidacaoUsuario validacao : validacaoUsuario) {
                    this.mensagem.add(FacesMessage.SEVERITY_ERROR, validacao.getRotulo());
                }
                this.listaUsuarios = this.usuarioService.pesquisarTodosUsuarios();
            }
        } catch (PersistenceException ex) {
            this.mensagem.add(FacesMessage.SEVERITY_ERROR, "ERRO_ACESSAR_BANCO_DADOS");
        }
    }

    /**
     * altera um usuario.
     */
    public void alterarUsuario() {
        try {
            List<ValidacaoUsuario> validacaoUsuario = this.usuarioService.alterarUsuario(this.usuario);
            /*
             Se a lista estiver vazia quer dizer que não existe erro, e o usuario
             pode ser alterado
             */
            if (validacaoUsuario.isEmpty()) {
                MDC.put("idUsuario", identidade.getUsuario().getLogin());
                this.logger.info("Alterou o usuario: " + this.usuario.toString());
                this.inicializar();
                this.mensagem.add(FacesMessage.SEVERITY_INFO, "USUARIO_ALTERADO_SUCESSO");
            } else {
                /*
                 caso a lista possua algum elemento quer dizer que ocorreu um erro ao 
                 tentar adicionar o usuario e ai vai acontecer uma iteracao por
                 essa lista e essas mensagem serao mostradas na tela para o usuario.
                 */
                for (ValidacaoUsuario validacao : validacaoUsuario) {
                    this.mensagem.add(FacesMessage.SEVERITY_ERROR, validacao.getRotulo());
                }
                this.listaUsuarios = this.usuarioService.pesquisarTodosUsuarios();
            }
        } catch (PersistenceException ex) {
            this.mensagem.add(FacesMessage.SEVERITY_ERROR, "ERRO_ACESSAR_BANCO_DADOS");
        }
    }

    /**
     * Metodo utilizado para se selecionar o usuario onde um ou mais campos serao alterados.
     *
     * @param usuarioSelecionado Usuario selecionado para ter seus campos alterados
     */
    public void selecionarUsuarioParaAlterarCampos(Usuario usuarioSelecionado) {
        MDC.put("idUsuario", identidade.getUsuario().getLogin());
        this.logger.info("Usuario selecionado para ter seus campos alterados: " + usuarioSelecionado.toString());
        this.usuario = usuarioSelecionado;
        this.novoUsuario = false;
    }

    /**
     * exclui um usuario pelo id.
     *
     * @param id Id do usuario a ser excluido
     */
    public void excluirUsuario(Integer id) {
        try {
            Usuario usuarioSendoExcluido = this.usuarioService.pesquisarPorId(id);
            MDC.put("idUsuario", identidade.getUsuario().getLogin());
            this.logger.info("Excluiu o usuario: " + usuarioSendoExcluido.toString());
            this.usuarioService.excluirUsuario(id);
            this.listaUsuarios = this.usuarioService.pesquisarTodosUsuarios();
            this.mensagem.add(FacesMessage.SEVERITY_INFO, "USUARIO_EXCLUIDO_SUCESSO");
        } catch (PersistenceException ex) {
            this.mensagem.add(FacesMessage.SEVERITY_ERROR, "ERRO_ACESSAR_BANCO_DADOS");
        }
    }

    /**
     * Faz a alteracao da senha do usuario
     */
    public void alterarSenha() {
        try {
            List<ValidacaoUsuario> validacaoUsuario = this.usuarioService.alterarSenha(this.identidade.getUsuario());

            if (validacaoUsuario.isEmpty()) {
                MDC.put("idUsuario", identidade.getUsuario().getLogin());
                this.logger.info("Alterou senha do usuario: " + this.usuario.getNome());
                this.inicializar();
                this.mensagem.add(FacesMessage.SEVERITY_INFO, "SENHA_ALTERADA_SUCESSO");
            } else {
                /*
                 caso a lista possua algum elemento quer dizer que ocorreu um erro ao 
                 tentar adicionar o usuario e ai vai acontecer uma iteracao por
                 essa lista e essas mensagem serao mostradas na tela para o usuario.
                 O primeiro argumento do metodo mensagem.add é o id do campo na tela ou seja,
                 o valor desse argumento tem que ser igual ao id do elemtento na tela, exemplo:
                 <p:inputText id="login"/> o valor do argumento tem que ser login
                 */
                for (ValidacaoUsuario validacao : validacaoUsuario) {
                    this.mensagem.add(FacesMessage.SEVERITY_ERROR, validacao.getRotulo());
                }
                this.listaUsuarios = this.usuarioService.pesquisarTodosUsuarios();
            }
        } catch (PersistenceException ex) {
            this.mensagem.add(FacesMessage.SEVERITY_ERROR, "ERRO_ACESSAR_BANCO_DADOS");
        }
    }

    /**
     * Cancela alteracao do usuario que foi selecionado
     */
    public void cancelar() {
        MDC.put("idUsuario", identidade.getUsuario().getLogin());
        this.logger.info("Cancelada alteracao do usuario: " + this.usuario);
        this.usuario = new Usuario();
        this.novoUsuario = true;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public String getCampoSendoPesquisado() {
        return campoSendoPesquisado;
    }

    public String getValorCampoSendoPesquisado() {
        return valorCampoSendoPesquisado;
    }

    public Identidade getIdentidade() {
        return identidade;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public void setCampoSendoPesquisado(String campoSendoPesquisado) {
        this.campoSendoPesquisado = campoSendoPesquisado;
    }

    public void setValorCampoSendoPesquisado(String valorCampoSendoPesquisado) {
        this.valorCampoSendoPesquisado = valorCampoSendoPesquisado;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void setIdentidade(Identidade identidade) {
        this.identidade = identidade;
    }

    public boolean isNovoUsuario() {
        return novoUsuario;
    }

    public void setNovoUsuario(boolean novoUsuario) {
        this.novoUsuario = novoUsuario;
    }

}
