package br.com.storm.ordemservico.resource;

import br.com.storm.ordemservico.domain.Tecnico;
import br.com.storm.ordemservico.dto.TecnicoDTO;
import br.com.storm.ordemservico.service.TecnicoService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin("*")
@RestController
@RequestMapping("/tecnicos")
public class TecnicoResource {
    
    @Autowired
    private TecnicoService service;

    
    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){
        
        Tecnico tecnico = service.findById(id);
        TecnicoDTO tecnicoDTO = new TecnicoDTO(tecnico);
        
        return ResponseEntity.ok().body(tecnicoDTO);
    }
    
    
    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll(){
        
        List<Tecnico> listaTecnicos = service.findAll();
        
        List<TecnicoDTO> listaTecnicosDTO = listaTecnicos.stream()
                                                         .map(tecnico -> new TecnicoDTO(tecnico))
                                                         .collect(Collectors.toList());
        
        
        return ResponseEntity.ok().body(listaTecnicosDTO);
    }
    
    
    @PostMapping
    public ResponseEntity<TecnicoDTO> create(@RequestBody @Valid TecnicoDTO tecnicoDTO){
        
        Tecnico tecnico = new Tecnico(tecnicoDTO);
        tecnico = service.create(tecnico);
        tecnicoDTO = new TecnicoDTO(tecnico);
        /*
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                                             .path("/{id}")
                                             .buildAndExpand(tecnico.getId())
                                             .toUri();
       */
        
        return ResponseEntity.status(HttpStatus.CREATED).body(tecnicoDTO);
    }
    
    
    @PutMapping
    public ResponseEntity<TecnicoDTO> update(@RequestBody @Valid TecnicoDTO tecnicoDTO){
        
        Tecnico tecnico = new Tecnico(tecnicoDTO);
        tecnico = service.update(tecnico);
        tecnicoDTO = new TecnicoDTO(tecnico);
        
        return ResponseEntity.ok().body(tecnicoDTO);
    }
}
