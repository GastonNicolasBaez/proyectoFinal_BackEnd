package com.portfoliobaez.gnb.Interface;

import com.portfoliobaez.gnb.Entity.Persona;
import java.util.List;


public interface IPersonaService {
    //traer una persona
    public List<Persona> getPersona();
    
    //Guardar un objeto del tipo persona
    public void savePersona(Persona persona);
    
    //eliminar un usuario por id
    public void deletePersona(Long id);
    
    //buscar una persona por id
    public Persona findPersona(Long id);
}
