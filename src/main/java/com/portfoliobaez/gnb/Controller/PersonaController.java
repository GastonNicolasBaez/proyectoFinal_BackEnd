
package com.portfoliobaez.gnb.Controller;

import com.portfoliobaez.gnb.Entity.Persona;
import com.portfoliobaez.gnb.Interface.IPersonaService;
import com.portfoliobaez.gnb.Service.ImpPersonaService;
import java.util.List;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin (origins = "http://localhost:4200")
public class PersonaController {
    
    private IPersonaService ipersonaService;
    
    public PersonaController(ImpPersonaService personaService){
        this.ipersonaService = personaService;
    }
    
    @GetMapping("/personas/traer")
    public List<Persona> getPersona(){
        return ipersonaService.getPersona();
    }
    @PostMapping("/personas/crear")
    public String createPersona(@RequestBody Persona persona){
        ipersonaService.savePersona(persona);
        return "La persona fue creada correctamente";
    }
    @DeleteMapping("/personas/borrar/{id}")
    public String deletePersona(@PathVariable Long id){
        ipersonaService.deletePersona(id);
        return "La persona fue eliminada";
    }
    
    @PutMapping("/personas/editar/{id}")
    public Persona editPersona (@PathVariable Long id,
                                @RequestParam("nombre") String nuevoNombre,
                                @RequestParam("apellido") String nuevoApellido){
        Persona persona = ipersonaService.findPersona(id);
        
        persona.setNombre(nuevoNombre);
        persona.setApellido(nuevoApellido);
        
        ipersonaService.savePersona(persona);
        return persona;        
    }
    
    @GetMapping("/personastraer/perfil")
    public Persona findPersona(){
        return ipersonaService.findPersona(2L);
    }
            
    
}

