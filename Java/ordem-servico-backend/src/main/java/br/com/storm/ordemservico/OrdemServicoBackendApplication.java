
package br.com.storm.ordemservico;


import br.com.storm.ordemservico.domain.Tecnico;
import br.com.storm.ordemservico.repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class OrdemServicoBackendApplication implements CommandLineRunner{
    
    @Autowired
    private TecnicoRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(OrdemServicoBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Tecnico tecnico = repository.procurarPorId(1);
        System.out.println(tecnico);
    }

   
}
