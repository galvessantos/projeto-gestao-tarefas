package project.study.gestao_tarefas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import project.study.gestao_tarefas.enums.TaskStatus;

public record TaskDTO(
        @NotBlank(message = "Nome é obrigatório.")
        String name,

        @Size(max = 255, message = "A descrição tem um tamanho máximo de 255 caracteres.")
        @NotBlank(message = "Descrição é obrigatório.")
        String description,

        @NotNull(message = "Status é obrigatório.")
        TaskStatus status

) {
}
