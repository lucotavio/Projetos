/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.repository;

import br.com.siemens.configmodule.entidade.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, UsuarioRepositoryCustom {

    /**
     * Pesquisa um usuario pelo seu login e senha
     * @param login
     * @param senha
     * @return Usuario caso exista um usuario cadastrado com essas credenciais
     * senao retorna null
     */
    Usuario findByLoginAndSenha(String login, String senha);

    
    /**
     * Pesquisa um usuario pelo seu login
     * @param login
     * @return 
     */
    Usuario findByLogin(String login);
    
    
     /**
     * Pesquisa um usuario pelo seu email
     * @param email
     * @return 
     */
    Usuario findByEmail(String email);
    
    /**
     * pesquisa por um usuario ja cadastrado 
     * com o mesmo login passado
     * como argumento 
     * @param login
     * @param id
     * @return usuario se ja existe um usuario cadastrado com esse login
     */
    Usuario findByLoginAndIdNot(String login, Integer id);

    /**
     * pesquisa por um usuario ja cadastrado 
     * com o mesmo email passado
     * como argumento 
     * @param email
     * @param id
     * @return usuario se ja existe um usuario cadastrado com esse email
     * ou null caso não exista
     */
    Usuario findByEmailAndIdNot(String email, Integer id);

}
