/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_TIPO_BANCO")
public class TipoBanco extends AbstractPersistable<Integer> {

    private String nome;

    private String driver;

    private String dialeto;

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

    @Column(name = "DRIVER")
    public String getDriver() {
        return driver;
    }

    @Column(name = "DIALETO")
    public String getDialeto() {
        return dialeto;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setDialeto(String dialeto) {
        this.dialeto = dialeto;
    }

}
