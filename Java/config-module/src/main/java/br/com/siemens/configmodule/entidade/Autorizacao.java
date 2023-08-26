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
@Table(name = "TB_AUTORIZACAO")
public class Autorizacao extends AbstractPersistable<Integer> {

    private String papel;

    @Id
    @Override
    @Column(name = "ID")
    public Integer getId() {
        return this.id;
    }

    @Column(name = "papel")
    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

}
