package project.study.gestao_tarefas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import project.study.gestao_tarefas.enums.UserRole;

import java.util.Set;

@Builder
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
        String email,

        Set<UserRole> authorities
) {
    public UserDTO(String name, String username, String password, String email) {
        this(name, username, password, email, null);
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Set<UserRole> getAuthorities() {
        return authorities;
    }
}