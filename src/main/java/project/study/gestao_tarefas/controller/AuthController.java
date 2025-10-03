package project.study.gestao_tarefas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.study.gestao_tarefas.dto.UserDTO;
import project.study.gestao_tarefas.dto.auth.AuthenticationRequest;
import project.study.gestao_tarefas.dto.auth.AuthenticationResponse;
import project.study.gestao_tarefas.dto.auth.RegisterRequest;
import project.study.gestao_tarefas.entity.User;
import project.study.gestao_tarefas.service.UserService;
import project.study.gestao_tarefas.security.jwt.JwtService;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody AuthenticationRequest request) {
        log.info("Tentativa de login para o usuário: {}", request.username());
        
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );

            User user = (User) authentication.getPrincipal();
            String jwtToken = jwtService.generateToken(user);
            
            log.info("Login bem-sucedido para o usuário: {}", request.username());
            return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
            
        } catch (Exception ex) {
            log.warn("Falha na autenticação para o usuário: {} - {}", request.username(), ex.getMessage());
            throw ex;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(
            @Valid @RequestBody RegisterRequest request) {
        log.info("Registrando novo usuário: {}", request.username());
        
        UserDTO newUser = userService.registerUser(
                request.name(),
                request.username(),
                request.email(),
                request.password()
        );
        
        log.info("Usuário registrado com sucesso: {}", newUser.username());
        return ResponseEntity
                .created(URI.create("/api/users/" + newUser.username()))
                .body(newUser);
    }
}