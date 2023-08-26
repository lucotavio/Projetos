package br.com.storm.ordemservico.service;

import br.com.storm.ordemservico.domain.Tecnico;
import br.com.storm.ordemservico.exception.DataIntegrityViolationException;
import br.com.storm.ordemservico.exception.ObjectNotFoundException;
import br.com.storm.ordemservico.repository.TecnicoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TecnicoService {

    @Autowired
    private TecnicoRepository repository;
    
    public Tecnico findById(Integer id){
        
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Tecnico não encontrado"));
    }
    
    
    public List<Tecnico> findAll(){
        return repository.findAll();
    }
    
    
    public Tecnico create(Tecnico tecnico){
        
        boolean existeCpf = existCpf(tecnico.getCpf());
        
        if(existeCpf){
            throw  new DataIntegrityViolationException("CPF já cadastrado");
        }
        
        return repository.save(tecnico);
    }
    
    
     public Tecnico update(Tecnico tecnico) {

        Tecnico tecnicoPesquisado = repository.findByIdAndCpf(tecnico.getId(), tecnico.getCpf());

        /*
          Estamos atualizando os campos de um tecnico, e o 
          unico campo que não foi atualizado nesse tecnico
          é o campo CPF, por isso nao e necessario que seja feita
          a verificacao de cpf existente
         */
        if (tecnicoPesquisado != null) {

            return repository.save(tecnico);
        }

        /*
          Aqui estamos atualizando os campos de um tecnico, onde
          o CPF foi alterado, por isso e necessario saber se existe
          esse CPF alterado no banco
          
         */
        boolean cpfJaCadastrado = existCpf(tecnico.getCpf());

        if (cpfJaCadastrado) {
            throw new DataIntegrityViolationException("Cpf ja castrado");
        }

        return repository.save(tecnico);
    }
    
    
    private boolean existCpf(String cpf){
        
        Tecnico tecnico = repository.findByCpf(cpf);
        
        if(tecnico != null){
            return true;
        }
        
        return false;
    }
}
