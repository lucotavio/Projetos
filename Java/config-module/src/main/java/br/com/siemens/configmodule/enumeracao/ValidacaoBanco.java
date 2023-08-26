/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.enumeracao;

public enum ValidacaoBanco {

    ALIAS_EXISTE("ALIAS_EXISTE", "O alias que voce tentou cadastrar ja existe no banco"),
    IMPOSSIVEL_CONECTAR_BANCO("NAO_POSSIVEL_CONECTAR_BANCO", "Nao foi possivel se conectar ao banco de dados pois "
            + "algum(s) dos parametros sao invalidos");

    private final String rotulo;
    private final String log;

    private ValidacaoBanco(String rotulo, String log) {
        this.rotulo = rotulo;
        this.log = log;
    }

    public String getRotulo() {
        return rotulo;
    }

    public String getLog() {
        return log;
    }

}
