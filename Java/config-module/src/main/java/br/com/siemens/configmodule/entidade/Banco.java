/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TB_BANCO")
public class Banco extends AbstractPersistable<Integer> {

    private String alias;

    private String url;

    private String usuario;

    private String senha;

    private TipoBanco tipoBanco;

    @Id
    @Override
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceBanco")
    @SequenceGenerator(name = "sequenceBanco", sequenceName = "SQ_BANCO", allocationSize = 1, initialValue = 1000)
    public Integer getId() {
        return this.id;
    }

    @Column(name = "ALIAS")
    @Size(min = 1, message = "{CAMPO_ALIAS_VAZIO}")
    public String getAlias() {
        return alias;
    }

    @Column(name = "URL")
    @Size(min = 1, message = "{CAMPO_URL_VAZIO}")
    public String getUrl() {
        return url;
    }

    @Column(name = "USUARIO")
    @Size(min = 1, message = "{CAMPO_USUARIO_VAZIO}")
    public String getUsuario() {
        return usuario;
    }

    @Column(name = "SENHA")
    @Size(min = 1, message = "{CAMPO_SENHA_VAZIO}")
    public String getSenha() {
        return senha;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_TIPO_BANCO", foreignKey = @ForeignKey(name = "FK_TIPO_BANCO_BD"))
    @NotNull(message = "{CAMPO_TIPO_BANCO_VAZIO}")
    public TipoBanco getTipoBanco() {
        return tipoBanco;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setTipoBanco(TipoBanco tipoBanco) {
        this.tipoBanco = tipoBanco;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id = ");
        stringBuilder.append(this.id);
        stringBuilder.append(", Alias = ");
        stringBuilder.append(this.alias);
        stringBuilder.append(", URL = ");
        stringBuilder.append(this.url);
        return stringBuilder.toString();
    }

}
