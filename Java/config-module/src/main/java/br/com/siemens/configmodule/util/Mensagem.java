/*
 * Copyright (c) 2015, Siemens AG and/or its affiliates. All rights reserved.
 * SIEMENS AG PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package br.com.siemens.configmodule.util;

import java.io.Serializable;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class Mensagem implements Serializable {

    private static Mensagem instance;

    private final ResourceBundle bundle;

    protected Mensagem() {
        bundle = ResourceBundle.getBundle("ValidationMessages");
    }

    /**
     * Adiciona mensagem no contexto.
     *
     * @param severity tipo de mensagem
     * @param str      conteudo da mensagem
     */
    public void add(Severity severity, String str) {
        try {
            String message = bundle.getString(str);
            if (severity == null) {
                severity = FacesMessage.SEVERITY_INFO;
            }

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, ""));
        } catch (MissingResourceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, "?" + str + "?", ""));
        }
    }

    /**
     * Adiciona mensagem no contexto.
     * @param idComponente
     * @param severity
     * @param chave 
     */
    public void add(String idComponente, Severity severity, String chave) {
        try {
            String message = bundle.getString(chave);
            if (severity == null) {
                severity = FacesMessage.SEVERITY_INFO;
            }

            FacesContext.getCurrentInstance().addMessage(idComponente, new FacesMessage(severity, "", message));
        } catch (MissingResourceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, "?" + chave + "?", ""));
        }
    }

    /**
     * Adiciona mensagem com parametros no contexto.
     * @param severity tipo de mensagem
     * @param key      conteudo da mensagem
     * @param params   parametros da mensagem
     */
    public void add(Severity severity, String key, Object... params) {
        if (severity == null) {
            severity = FacesMessage.SEVERITY_INFO;
        }

        String message = bundle.getString(key);

        for (int i = 0; i < params.length; i++) {
            message = message.replace("{" + (i) + "}", (String) params[i]);
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, message, ""));
    }

    /**
     * Traduz uma mensagem.
     * @param key identificador da mensagem (property key)
     * @return a mensagem traduzida ou a chave entre '?', ex: ?MENSAGEM_NAO_ENCONTRADA?
     */
    public String getMessage(String key) {
        if (bundle.containsKey(key)) {
            return bundle.getString(key);
        } else {
            return "?" + key + "?";
        }
    }

    /**
     * Traduz uma mensagem com parametros.
     * @param key identificador da mensagem (property key)
     * @param args parametros da mensagem
     * @return mensagem traduzida com os parametros
     */
    public String getMessage(String key, String... args) {
        String message = this.getMessage(key);

        for (int i = 0; i < args.length; i++) {
            message = (message != null && args[i] != null)
                    ? message.replace("{" + i + "}", args[i] != null ? args[i] : "") : null;
        }

        return message;
    }

    /**
     * Traduz uma mensagem com parametros.
     * @param key identificador da mensagem (property key)
     * @param args parametros da mensagem
     * @return mensagem traduzida com os parametros
     */
    public String getMessage(String key, List<String> args) {
        String message = this.getMessage(key);

        for (int i = 0; i < args.size(); i++) {
            message = message.replace("{" + i + "}", args.get(i) != null ? args.get(i) : "");
        }

        return message;
    }

    public static Mensagem getInstance() {
        if (instance == null) {
            instance = new Mensagem();
        }

        return instance;
    }
}
