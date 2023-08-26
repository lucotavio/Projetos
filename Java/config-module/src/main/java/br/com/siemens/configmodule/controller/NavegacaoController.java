/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.controller;

import br.com.siemens.configmodule.constante.Navegacao;
import br.com.siemens.configmodule.session.Identidade;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.springframework.security.core.context.SecurityContextHolder;

@ViewScoped
@ManagedBean(name = "navegacaoController")
public class NavegacaoController implements Serializable {
    
    @ManagedProperty(value = "#{identidade}")
    private Identidade identidade;

    public String paginaCadastroBanco() {
        return Navegacao.CADASTRO_BANCO;
    }

    public String paginaCadastroUsuario() {
        return Navegacao.CADASTRO_USUARIO;
    }

    public String paginaAlterarSenha() {
        return Navegacao.ALTERAR_SENHA;
    }
    
    /**
     * Faz logout do sistema
     * @return 
     */
    public String logout(){
        this.identidade.setUsuario(null);
        SecurityContextHolder.clearContext();
        return Navegacao.LOGIN;
    }

    public void setIdentidade(Identidade identidade) {
        this.identidade = identidade;
    }
    
}
