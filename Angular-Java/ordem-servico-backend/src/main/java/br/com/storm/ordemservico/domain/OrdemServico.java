package br.com.storm.ordemservico.domain;

import br.com.storm.ordemservico.enumeration.Prioridade;
import br.com.storm.ordemservico.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "ORDEM_SERVICO")
public class OrdemServico {

    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "DATA_ABERTURA")
    private LocalDateTime dataAbertura;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    @Column(name = "DATA_FECHAMENTO")
    private LocalDateTime dataFechamento;
    
    @Column(name = "PRIORIDADE")
    private Integer prioridade;
    
    @Column(name = "STATUS")
    private Integer status;
    
    @Column(name = "OBSERVACOES")
    private String observacoes;
    
    @ManyToOne
    @JoinColumn(name = "ID_TECNICO")
    private Tecnico tecnico;
    
    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;

    
    public OrdemServico() {
        this(Prioridade.BAIXA, Status.ABERTO, "", null, null);
    }
    
    
    public OrdemServico(Prioridade prioridade, Status status, String observacoes, Tecnico tecnico, Cliente cliente) {
        
        this.dataAbertura = LocalDateTime.now();
        this.prioridade = (prioridade == null) ? 0 : prioridade.getCodigo();
        this.status = (status == null) ? 0 : status.getCodigo();
        this.tecnico = tecnico;
        this.cliente = cliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Prioridade getPrioridade() {
        return Prioridade.toEnum(prioridade);
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade.getCodigo();
    }

    public Status getStatus() {
        return Status.toEnum(status);
    }

    public void setStatus(Status status) {
        this.status = status.getCodigo();
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrdemServico other = (OrdemServico) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
