/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.repository;

import br.com.siemens.configmodule.entidade.Usuario;
import java.util.List;

public interface UsuarioRepositoryCustom {
    
    /**
     * pesquisa por uma lista de usuarios baseado em um campo especifico.
     * @param campoSendoPesquisado      campo utilizado na pesquisa
     * @param valorCampoSendoPesquisado valor do campo
     * @return lista com usuarios pesquisados
     */
    List<Usuario> pesquisarUsuarioPorCampo(String campoSendoPesquisado, String valorCampoSendoPesquisado);
}
