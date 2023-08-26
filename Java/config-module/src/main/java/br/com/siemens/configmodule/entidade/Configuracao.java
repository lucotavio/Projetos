/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.entidade;

import br.com.siemens.configmodule.util.Mensagem;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "CONFIGURACAO")
public class Configuracao extends AbstractPersistable<Integer> {

    private String chave;

    private String valor;

    private String rotulo;

    private String descricao;

    private String validacao;
    
    private final Mensagem mensagem;

    public Configuracao() {
        this.mensagem = Mensagem.getInstance();
    }

    @Id
    @Override
    @Column(name = "ID")
    public Integer getId() {
        return this.id;
    }

    @Column(name = "CHAVE")
    public String getChave() {
        return chave;
    }

    @Column(name = "VALOR")
    public String getValor() {
        return valor;
    }

    @Column(name = "ROTULO")
    public String getRotulo() {
        return rotulo;
    }

    @Column(name = "DESCRICAO")
    public String getDescricao() {
        return descricao;
    }
    
    @Column(name = "VALIDACAO", nullable = true)
    public String getValidacao() {
        return validacao;
    }
    
    @Transient
    public String getRotuloInternacionalizado() {
        return mensagem.getMessage(this.rotulo);
    }
    
    @Transient
    public String getDescricaoInternacionalizado() {
        return mensagem.getMessage(this.descricao);
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValidacao(String validacao) {
        this.validacao = validacao;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id = ");
        stringBuilder.append(this.id);
        stringBuilder.append(", Chave = ");
        stringBuilder.append(this.chave);
        stringBuilder.append(", Valor = ");
        stringBuilder.append(this.valor);
        return stringBuilder.toString();
    }

}
