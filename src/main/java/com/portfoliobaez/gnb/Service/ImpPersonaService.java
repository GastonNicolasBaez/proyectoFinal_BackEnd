package com.portfoliobaez.gnb.Service;

import com.portfoliobaez.gnb.Entity.Persona;
import com.portfoliobaez.gnb.Interface.IPersonaService;
import com.portfoliobaez.gnb.Repository.IPersonaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImpPersonaService implements IPersonaService {
    @Autowired IPersonaRepository ipersonaRepository;

    @Override
    public List<Persona> getPersona() {
        List<Persona> persona = ipersonaRepository.findAll(); 
        return persona;
    }

    @Override
    public void savePersona(Persona persona) {
        ipersonaRepository.save(persona); 
    }

    @Override
    public void deletePersona(Long id) {
        ipersonaRepository.deleteById(id); 
    }

    @Override
    public Persona findPersona(Long id) {
        System.out.println(id);
        Persona persona = ipersonaRepository.findById(id).orElse(null);
        //System.out.println(persona.getNombre());
        return persona;
    }
    
}
