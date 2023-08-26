package br.com.storm.ordemservico.domain;

import br.com.storm.ordemservico.dto.TecnicoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.springframework.beans.BeanUtils;


@Entity
public class Tecnico  extends Pessoa{
    
    
    @JsonIgnore
    @OneToMany(mappedBy = "tecnico")
    private List<OrdemServico> listaOrdemServicos;
    
    
    public Tecnico(TecnicoDTO tecnicoDTO) {
        BeanUtils.copyProperties(tecnicoDTO, this);
    }

    public Tecnico() {
        this(null, null, null);
    }
    
   
    public Tecnico(String nome, String cpf, String telefone) {
        super(nome, cpf, telefone);
        this.listaOrdemServicos = new ArrayList<>();
    }
    
    public List<OrdemServico> getListaOrdemServicos() {
        return Collections.unmodifiableList(listaOrdemServicos);
    }
}
