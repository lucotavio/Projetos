package br.com.storm.ordemservico.service;

import br.com.storm.ordemservico.domain.Cliente;
import br.com.storm.ordemservico.domain.OrdemServico;
import br.com.storm.ordemservico.domain.Tecnico;
import br.com.storm.ordemservico.enumeration.Prioridade;
import br.com.storm.ordemservico.enumeration.Status;
import br.com.storm.ordemservico.repository.ClienteRepository;
import br.com.storm.ordemservico.repository.OrdemServicoRepository;
import br.com.storm.ordemservico.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class DbService {
    
    @Autowired
    private TecnicoRepository tecnicoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private OrdemServicoRepository ordemServicoRepository;
    
    public void instancia(){
        
        Tecnico tecnico1 = new Tecnico("Luciano", "786.845.660-41", "997977747");
        Tecnico tecnico2 = new Tecnico("Pedro", "257.727.560-95", "985942358");
        Tecnico tecnico3 = new Tecnico("Andre", "214.623.570-59", "997977747");
        
        Tecnico tecnico4 = new Tecnico("Antonio", "319.170.490-64", "985942358");
        Tecnico tecnico5 = new Tecnico("Lucas", "869.338.890-84", "997977747");
        Tecnico tecnico6 = new Tecnico("Marcos", "953.480.710-99", "985942358");
        Tecnico tecnico7 = new Tecnico("Joel", "109.107.810-60", "997977747");
        Tecnico tecnico8 = new Tecnico("João", "684.398.790-75", "985942358");

        tecnicoRepository.save(tecnico1);
        tecnicoRepository.save(tecnico2);
        tecnicoRepository.save(tecnico3);
        
        tecnicoRepository.save(tecnico4);
        tecnicoRepository.save(tecnico5);
        tecnicoRepository.save(tecnico6);
        tecnicoRepository.save(tecnico7);
        tecnicoRepository.save(tecnico8);

        
        
        
        Cliente cliente1 = new Cliente("Karine", "593.391.190-96", "992000611");
        Cliente cliente2 = new Cliente("Joelma", "174.063.850-64", "992000611");
        clienteRepository.save(cliente1);
        clienteRepository.save(cliente2);
        
        
        OrdemServico ordemServico1 = new OrdemServico(Prioridade.BAIXA, Status.ABERTO, "Observações", tecnico1, cliente2);
        OrdemServico ordemServico2 = new OrdemServico(Prioridade.MEDIA, Status.ANDAMENTO, "NovasObservações", tecnico1, cliente2);
        ordemServicoRepository.save(ordemServico1);
        ordemServicoRepository.save(ordemServico2);
    }
}
