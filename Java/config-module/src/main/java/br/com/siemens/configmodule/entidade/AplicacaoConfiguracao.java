/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.entidade;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "APLICACAO_CONFIGURACAO")
public class AplicacaoConfiguracao extends AbstractPersistable<Integer> {

    private String nome;

    private List<Configuracao> listaConfiguracao;

    @Id
    @Override
    @Column(name = "ID")
    public Integer getId() {
        return this.id;
    }

    @Column(name = "NOME")
    public String getNome() {
        return nome;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_APLICACAO_CONFIGURACAO", referencedColumnName = "ID", nullable = false)
    public List<Configuracao> getListaConfiguracao() {
        return listaConfiguracao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setListaConfiguracao(List<Configuracao> listaConfiguracao) {
        this.listaConfiguracao = listaConfiguracao;
    }

    public List<String> validarCampos() {
        List<String> rotulo = new ArrayList<>();
        for (Configuracao configuracao : this.listaConfiguracao) {
            String validacao = configuracao.getValidacao();
            if (validacao != null && !validacao.isEmpty()) {
                Pattern pattern = Pattern.compile(configuracao.getValidacao());
                Matcher matcher = pattern.matcher(configuracao.getValor());
                if (!matcher.matches()) {
                    rotulo.add(configuracao.getRotulo());
                }

            }
        }
        return rotulo;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id = ");
        stringBuilder.append(this.id);
        stringBuilder.append(", Nome = ");
        stringBuilder.append(this.nome);
        return stringBuilder.toString();
    }

}
