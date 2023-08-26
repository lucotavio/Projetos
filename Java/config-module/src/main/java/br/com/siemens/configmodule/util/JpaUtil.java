/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.util;

import br.com.siemens.configmodule.entidade.Banco;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Classe helper de Jpa.
 * @author luciano
 */
public class JpaUtil {

    private final Banco banco;

    public JpaUtil(Banco banco) {
        this.banco = banco;
    }

    public EntityManager getEntityManager() {
        Map<String, String> propriedades = new HashMap<>();
        propriedades.put("javax.persistence.jdbc.url", banco.getUrl());
        propriedades.put("javax.persistence.jdbc.driver", banco.getTipoBanco().getDriver());
        propriedades.put("javax.persistence.jdbc.dialect", banco.getTipoBanco().getDialeto());
        propriedades.put("javax.persistence.jdbc.user", banco.getUsuario());
        propriedades.put("javax.persistence.jdbc.password", banco.getSenha());
//        propriedades.put("javax.persistence.schema-generation.database.action", "create");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("aplicacao-modulePU", propriedades);
        return emf.createEntityManager();
    }
}
