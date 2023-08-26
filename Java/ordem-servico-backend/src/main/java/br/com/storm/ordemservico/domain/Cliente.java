package br.com.storm.ordemservico.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity
public class Cliente extends Pessoa{
    
    
    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<OrdemServico> listaOrdemServicos;

    public Cliente() {
        this(null, null, null);
    }

    public Cliente(String nome, String cpf, String telefone) {
        super(nome, cpf, telefone);
        this.listaOrdemServicos = new ArrayList<>();
    }

    public List<OrdemServico> getListaOrdemServicos() {
        return Collections.unmodifiableList(listaOrdemServicos);
    }
     
}
