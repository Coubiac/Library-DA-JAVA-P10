package com.library.emprunts.controller;

import com.library.emprunts.data.EmpruntEntity;
import com.library.emprunts.repository.EmpruntEntityRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@RepositoryRestController
public class EmpruntController {

    private final EmpruntEntityRepository repository;

    public EmpruntController(EmpruntEntityRepository repository) {
        this.repository = repository;
    }
    @DeleteMapping(path="/{empruntId}")
    public @ResponseBody ResponseEntity deleteEmprunt(@PathVariable Long empruntId){
        System.out.println("inside controller");
        Optional<EmpruntEntity> theEmprunt = repository.findById(empruntId);
        if (theEmprunt.isPresent()){
            repository.delete(theEmprunt.get());
            return ResponseEntity.noContent().build();
        }else return ResponseEntity.notFound().build();
    }
}
