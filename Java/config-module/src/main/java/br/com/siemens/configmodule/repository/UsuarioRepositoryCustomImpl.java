/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.repository;

import br.com.siemens.configmodule.entidade.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("usuarioRepositoryImpl")
public class UsuarioRepositoryCustomImpl implements UsuarioRepositoryCustom{
    
    @PersistenceContext(name = "config-modulePU")
    private EntityManager em;

    @Override
    public List<Usuario> pesquisarUsuarioPorCampo(String campoSendoPesquisado, String valorCampoSendoPesquisado) {
        Session session = this.em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Usuario.class);
        criteria.add(Restrictions.like(campoSendoPesquisado, "%" + valorCampoSendoPesquisado + "%").ignoreCase());
        return (List<Usuario>) criteria.list();
    }
}
