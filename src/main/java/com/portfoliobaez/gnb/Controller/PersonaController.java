
package com.portfoliobaez.gnb.Controller;

import com.portfoliobaez.gnb.Entity.Persona;
import com.portfoliobaez.gnb.Interface.IPersonaService;
import com.portfoliobaez.gnb.Service.ImpPersonaService;
import java.util.List;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/personas")
//@CrossOrigin (origins = "https://frontendgnb.web.app")
public class PersonaController {
    
    private IPersonaService ipersonaService;
    
    public PersonaController(ImpPersonaService personaService){
        this.ipersonaService = personaService;
    }
    
    @GetMapping("/lista")
    public List<Persona> getPersona(){
        return ipersonaService.getPersona();
    }
    @PostMapping("/crear")
    public String createPersona(@RequestBody Persona persona){
        ipersonaService.savePersona(persona);
        return "La persona fue creada correctamente";
    }
    @DeleteMapping("/borrar/{id}")
    public String deletePersona(@PathVariable Long id){
        ipersonaService.deletePersona(id);
        return "La persona fue eliminada";
    }
    
    @PutMapping("/editar/{id}")
    public Persona editPersona (@PathVariable Long id,
                                @RequestParam("nombre") String nuevoNombre,
                                @RequestParam("apellido") String nuevoApellido){
        Persona persona = ipersonaService.findPersona(id);
        
        persona.setNombre(nuevoNombre);
        persona.setApellido(nuevoApellido);
        
        ipersonaService.savePersona(persona);
        return persona;        
    }
    
    @GetMapping("/lista/perfil")
    public Persona findPersona(){
        return ipersonaService.findPersona(2L);
    }
            
    
}

