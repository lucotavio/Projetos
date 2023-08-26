package br.com.storm.ordemservico.config;

import br.com.storm.ordemservico.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories(basePackages = "br.com.storm.ordemservico.repository", repositoryImplementationPostfix = "CustomImpl")
public class DevConfig {

    @Autowired
    private DbService dbService;
    
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;
    
    @Bean
    public boolean instanciaDB(){
        
        if(ddl.equals("update")){
            
            this.dbService.instancia();
        }
        
        return false;
        
    }
}
