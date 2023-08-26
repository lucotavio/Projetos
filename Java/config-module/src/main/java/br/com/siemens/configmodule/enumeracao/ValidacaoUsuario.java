/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.enumeracao;

public enum ValidacaoUsuario {

    LOGIN_EXISTE("LOGIN_EXISTE", "O login que voce tentou cadastrar ja existe no banco", "login"),
    EMAIL_EXISTE("EMAIL_EXISTE", "O e-mail que voce tentou cadastrar ja existe no banco", "email"),
    SENHA_DIFERENTE_SENHA_CONFIRMACAO("SENHA_DIFERENTE_CONFIRMACAO", "A senha está diferente da confirmação", "senha"),
    SENHA_ANTIGA_INVALIDA("SENHA_ANTIGA_INVALIDA", "A senha antiga que foi inserida esta diferente da do banco", "senha"),
    SENHA_ANTIGA_IGUAL_NOVA("SENHA_ANTIGA_IGUAL_NOVA", "A senha antiga esta igual a senha nova sendo que elas não podem "
            + "ser iguais", "senha");

    private final String rotulo;
    private final String log;
    private final String campo;

    private ValidacaoUsuario(String rotulo, String log, String campo) {
        this.rotulo = rotulo;
        this.log = log;
        this.campo = campo;
    }

    public String getRotulo() {
        return rotulo;
    }

    public String getLog() {
        return log;
    }

    public String getCampo() {
        return campo;
    }

}
