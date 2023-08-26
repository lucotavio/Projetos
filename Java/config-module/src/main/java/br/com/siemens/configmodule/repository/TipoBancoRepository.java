/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.repository;

import br.com.siemens.configmodule.entidade.TipoBanco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoBancoRepository extends JpaRepository<TipoBanco, Integer> {

}
