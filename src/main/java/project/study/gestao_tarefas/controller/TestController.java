package project.study.gestao_tarefas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("Este é um endpoint público. Todos podem acessar.");
    }

    @GetMapping("/private")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> privateEndpoint() {
        return ResponseEntity.ok("Este é um endpoint privado. Apenas usuários autenticados podem acessar.");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("Este é um endpoint de administrador. Apenas administradores podem acessar.");
    }
}
