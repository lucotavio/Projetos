/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.converter;

import br.com.siemens.configmodule.entidade.AbstractPersistable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "entidadeConverter")
public class EntidadeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        if (value != null) {
            return component.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        String result = "";

        if (value != null) {
            AbstractPersistable<?> abstractPersistable = (AbstractPersistable) value;

            if (abstractPersistable.getId() != null) {
                component.getAttributes().put(abstractPersistable.getId().toString(), abstractPersistable);
                result = String.valueOf(abstractPersistable.getId());
            }
        }

        return result;
    }
}
