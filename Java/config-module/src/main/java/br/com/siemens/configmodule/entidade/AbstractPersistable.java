/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.entidade;

import java.io.Serializable;
import java.util.Objects;

public abstract class AbstractPersistable<PK extends Serializable> implements Persistable<PK> {

    protected PK id;

    public void setId(PK id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractPersistable<?> other = (AbstractPersistable<?>) obj;
        return this.id.equals(other.id);
    }

}
