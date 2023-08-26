package br.com.storm.ordemservico.error;

import br.com.storm.ordemservico.exception.*;
import java.io.Serializable;


public class FieldMessageError implements Serializable{

    private String fieldName;
    private String message;

    public FieldMessageError() {
    }

    public FieldMessageError(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldMessage() {
        return fieldName;
    }

    public void setFieldMessage(String fieldMessage) {
        this.fieldName = fieldMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
