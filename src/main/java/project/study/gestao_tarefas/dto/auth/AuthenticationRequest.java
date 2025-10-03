package project.study.gestao_tarefas.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(
    @NotBlank(message = "Nome de usuário é obrigatório")
    String username,
    
    @NotBlank(message = "Senha é obrigatória")
    String password
) {}
