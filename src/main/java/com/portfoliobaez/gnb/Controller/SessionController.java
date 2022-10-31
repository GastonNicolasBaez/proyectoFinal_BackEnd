package com.portfoliobaez.gnb.Controller;

import com.portfoliobaez.gnb.DTO.UserRequestDTO;
import com.portfoliobaez.gnb.DTO.UserResponseDTO;
import com.portfoliobaez.gnb.Interface.ISessionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin (origins = "http://localhost:4200")
public class SessionController {
    
    private final ISessionService service;

    public SessionController(ISessionService sessionService) {
        this.service = sessionService;
    }
    @PostMapping("/auth/login")
    public UserResponseDTO login(@RequestBody UserRequestDTO user) {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        return service.login(user);
    }
    
    @PostMapping("/register")
    public void registrar(@RequestBody UserRequestDTO user){
        service.regis(user);
    }

}
