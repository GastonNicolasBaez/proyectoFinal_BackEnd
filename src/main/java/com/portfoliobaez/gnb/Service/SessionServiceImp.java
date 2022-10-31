package com.portfoliobaez.gnb.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfoliobaez.gnb.DTO.UserRequestDTO;
import com.portfoliobaez.gnb.DTO.UserResponseDTO;
import com.portfoliobaez.gnb.Entity.Rol;
import com.portfoliobaez.gnb.Entity.RolE;
import com.portfoliobaez.gnb.Entity.User;
import com.portfoliobaez.gnb.Exception.UserNotFoundException;
import com.portfoliobaez.gnb.Interface.ISessionService;
import com.portfoliobaez.gnb.Repository.UserRepository;
import static com.portfoliobaez.gnb.Utils.CONSTANTS.SECRET_KEY_TOKEN;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImp implements ISessionService {

    private final UserRepository userRepository;

    public SessionServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO login(UserRequestDTO user) {
        //Voy a la base de datos y reviso que el usuario y contraseña existan.
        // ToDo: se podria agregar alguna libreria para encriptar la password
        String username = user.getUsername();
        User usuario = userRepository.findByUsernameAndPassword(username, user.getPassword())
                .orElseThrow(UserNotFoundException::new);

        List<String> roles = usuario.getRoles()
                .stream()
                .map(e -> e.getRol().getText())
                .collect(Collectors.toList());

        String token = getJWTToken(username, roles);

        return new UserResponseDTO(username, token);
    }

    private String getJWTToken(String username, List<String> roles) {

        List<GrantedAuthority> grantedAuthorities = roles
                .stream()
                .map(AuthorityUtils::commaSeparatedStringToAuthorityList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        LocalDateTime expired = LocalDateTime.now()
                .plusMinutes(10);
        Date expiredTime = Date.from(expired.atZone(ZoneId.systemDefault())
                .toInstant());

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                )
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiredTime)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY_TOKEN.getBytes())
                .compact();

        return "Bearer " + token;
    }

    private static Claims decodeJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY_TOKEN.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public static String getUsername(String token) {
        Claims claims = decodeJWT(token);
        return claims.get("sub", String.class);
    }

    @Override
    public void regis(UserRequestDTO user) {
        ObjectMapper mapper = new ObjectMapper();
        User userGuardar = mapper.convertValue(user, User.class);
        List<Rol> roles = new ArrayList<>();
        Rol admin = new Rol();
        admin.setRol(RolE.ADMIN);
        roles.add(admin);
        userGuardar.setRoles(roles);
        userRepository.save(userGuardar);
    }

}
