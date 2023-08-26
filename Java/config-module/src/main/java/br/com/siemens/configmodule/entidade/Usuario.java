/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.entidade;

import br.com.siemens.bean.validation.interfaces.Email;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "TB_USUARIO")
public class Usuario extends AbstractPersistable<Integer> {

    private String nome;

    private String login;

    private String email;

    private String senha;

    private String confirmaSenha;

    private String senhaAntiga;

    private Autorizacao autorizacao;

    private Boolean ativo;

    public Usuario() {
        this.ativo = true;
    }

    @Id
    @Override
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceUsuario")
    @SequenceGenerator(name = "sequenceUsuario", sequenceName = "SQ_USUARIO", allocationSize = 1, initialValue = 1000)
    public Integer getId() {
        return this.id;
    }

    @Column(name = "NOME")
    @Size(min = 1, message = "{CAMPO_NOME_VAZIO}")
    public String getNome() {
        return nome;
    }

    @Column(name = "LOGIN", unique = true)
    @Size(min = 1, message = "{CAMPO_LOGIN_VAZIO}")
    public String getLogin() {
        return login;
    }

    @Column(name = "EMAIL", unique = true)
    @Email(message = "{EMAIL_INVALIDO}")
    public String getEmail() {
        return email;
    }

    @Column(name = "SENHA")
    @Size(min = 8, message = "{TAMANHO_SENHA_INVALIDO}")
    public String getSenha() {
        return senha;
    }

    @Transient
    @Size(min = 8, message = "{TAMANHO_CONFIRMA_SENHA_INVALIDO}")
    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    @Transient
    @Size(min = 8, message = "{TAMANHO_SENHA_ANTIGA_INVALIDO}")
    public String getSenhaAntiga() {
        return senhaAntiga;
    }

    @ManyToOne
    @JoinColumn(name = "ID_AUTORIZACAO", foreignKey = @ForeignKey(name = "FK_AUTORIZACAO_USU"))
    public Autorizacao getAutorizacao() {
        return autorizacao;
    }

    @Column(name = "ATIVO",  columnDefinition = "CHAR", length = 1)
    public Boolean getAtivo() {
        return ativo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public void setSenhaAntiga(String senhaAntiga) {
        this.senhaAntiga = senhaAntiga;
    }

    public void setAutorizacao(Autorizacao autorizacao) {
        this.autorizacao = autorizacao;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id = ");
        stringBuilder.append(this.id);
        stringBuilder.append(", nome = ");
        stringBuilder.append(this.nome);
        stringBuilder.append(", login = ");
        stringBuilder.append(this.login);
        stringBuilder.append(", email = ");
        stringBuilder.append(this.email);
        return stringBuilder.toString();
    }

}
