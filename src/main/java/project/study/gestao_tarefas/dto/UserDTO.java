package project.study.gestao_tarefas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO (
        @NotBlank(message = "Nome é obrigatório.")
        String name,

        @NotBlank(message = "Usuário é obrigatório.")
        @Size(min = 3, max = 20, message = "Usuário deve ter entre 3 e 20 caracteres")
        String username,

        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, max = 20, message = "Senha deve ter entre 6 e 20 caracteres")
        String password,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email informado é inválido")
        String email
) {}
