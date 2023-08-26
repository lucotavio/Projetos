/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.service;

import br.com.siemens.configmodule.entidade.Banco;
import br.com.siemens.configmodule.entidade.TipoBanco;
import br.com.siemens.configmodule.enumeracao.ValidacaoBanco;
import java.util.List;

public interface BancoService {

    /**
     * Salva uma entidade banco, caso nenhum erro de validacao ocorra
     * uma lista vazia e retornada e o banco e salvo, senao retorna uma lista com todos
     * os erros e o banco nao e salvo
     * @param banco
     * @return 
     */
    List<ValidacaoBanco> salvarBanco(Banco banco);

    /**
     * Altera uma entidade banco, caso nenhum erro de validacao ocorra
     * uma lista vazia e retornada e o banco e salvo, senao retorna uma lista com todos
     * os erros e o banco nao e salvo
     * @param banco
     * @return 
     */
    List<ValidacaoBanco> alterarBanco(Banco banco);

    /**
     * Retorna uma lista com todos os tipos de bancos cadastrados
     * @return 
     */
    List<TipoBanco> pesquisarTodosTiposBancos();

    /**
     * Pesquisa um banco por alias
     * @param alias
     * @return 
     */
    List<Banco> pesquisarBancosPorAlias(String alias);

    /**
     * Retorna uma lista com todos os bancos cadastrados
     * @return 
     */
    List<Banco> pesquisarTodosBancos();

    
    /**
     * Exclui um banco atraves do seu id
     * @param id 
     */
    void excluirBanco(Integer id);

    /**
     * pesquisa um banco atraves do seu id
     * @param id 
     * @return  
     */
    Banco persquisarPorId(Integer id);
    
    boolean testarConexaoBancoFalha(Banco banco);

}
