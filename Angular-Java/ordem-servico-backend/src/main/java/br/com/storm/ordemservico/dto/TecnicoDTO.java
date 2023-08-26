package br.com.storm.ordemservico.dto;

import br.com.storm.ordemservico.domain.Tecnico;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.BeanUtils;

public class TecnicoDTO implements Serializable{

    private Integer id;

    @NotEmpty(message = "O campo nome é requerido")
    private String nome;

    @CPF(message = "CPF inválido")
    private String cpf;

    @NotEmpty(message = "O campo telefone é requerido")
    private String telefone;

    public TecnicoDTO() {
        this(null, null, null, null);

    }

    public TecnicoDTO(Tecnico tecnico) {
        BeanUtils.copyProperties(tecnico, this);
    }

    public TecnicoDTO(Integer id, String nome, String cpf, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
