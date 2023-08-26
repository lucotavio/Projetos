/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.service;

import br.com.siemens.configmodule.entidade.Usuario;
import br.com.siemens.configmodule.enumeracao.ValidacaoUsuario;
import java.util.List;

public interface UsuarioService {

    /**
     * Salva uma entidade usuario, caso nenhum erro de validacao ocorra
     * uma lista vazia e retornada e o usuario e salvo, senao retorna uma lista com todos
     * os erros e o usuario nao e salvo
     * @param usuario
     * @return 
     */
    List<ValidacaoUsuario> salvarUsuario(Usuario usuario);

    
      /**
     * Altera uma entidade usuario, caso nenhum erro de validacao ocorra
     * uma lista vazia e retornada e o usuario e salvo, senao retorna uma lista com todos
     * os erros e o usuario nao e salvo
     * @param usuario
     * @return 
     */
    List<ValidacaoUsuario> alterarUsuario(Usuario usuario);

    
    /**
     * Retorna uma lista com todos os usuarios cadastrados
     * @return 
     */
    List<Usuario> pesquisarTodosUsuarios();

    
    /**
     * Exclui um usuario atraves do seu id
     * @param id 
     */
    void excluirUsuario(Integer id);

    /**
     * Retorna uma lista de usuarios de acordo com o 
     * campo pesquisado
     * o campo pesquisado
     * @param campoSendoPesquisado
     * @param valorCampoSendoPesquisado
     * @return 
     */
    List<Usuario> pesquisarUsuarios(String campoSendoPesquisado, String valorCampoSendoPesquisado);

    
    /**
     * Pesquisa um usuario atraves do seu id
     * @param id
     * @return 
     */
    Usuario pesquisarPorId(Integer id);

    /**
     * Altera a senha um usuario
     * @param usuario
     * @return 
     */
    List<ValidacaoUsuario> alterarSenha(Usuario usuario);

    /**
     * Pesquisa um usuario atraves do seu login
     * @param login
     * @return 
     */
    Usuario pesquisarPorLogin(String login);

}
