/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.repository;

import br.com.siemens.configmodule.entidade.Banco;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancoRepository extends JpaRepository<Banco, Integer> {

    /**
     * Pesquisa por um banco atraves de seu campo alias
     * sem diferenciar letras maiusculas ou minusculas
     * @param alias
     * @return 
     */
    Banco findByAliasIgnoreCase(String alias);

     /**
     * Pesquisa por um banco ja cadastrado 
     * com o mesmo alias passado
     * como argumento 
     * @param alias
     * @param id
     * @return usuario se ja existe um usuario cadastrado com esse login
     */
    Banco findByAliasIgnoreCaseAndIdNot(String alias, Integer id);

    /**
     * Pesquisa por bancos atraves de seu alias sem coincidir
     * a palavra inteira e sem diferenciar letras maiusculas ou
     * minusculas
     * @param alias
     * @return 
     */
    List<Banco> findByAliasContainingIgnoreCase(String alias);
}
