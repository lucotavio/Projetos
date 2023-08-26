package br.com.storm.ordemservico.error;

import br.com.storm.ordemservico.exception.*;
import br.com.storm.ordemservico.error.StandardError;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ValidationError extends StandardError{
    
    private List<FieldMessageError> errors;

    public ValidationError() {
        
        this(null, null, null);
    }

    public ValidationError(Long timestamp, Integer status, String error) {
        super(timestamp, status, error);
        this.errors = new ArrayList<>();
    }

    public List<FieldMessageError> getErrors() {
        return errors;
    }
    

    public void addFieldMessageError(String fieldName, String message) {
        FieldMessageError fieldMessageError = new FieldMessageError(fieldName, message);
        this.errors.add(fieldMessageError);
    
    }

}
