package project.study.gestao_tarefas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import project.study.gestao_tarefas.enums.TaskStatus;

public record TaskDTO(
        Long id,
        @NotBlank(message = "O nome da tarefa é obrigatório")
        @Size(max = 100, message = "O nome da tarefa deve ter no máximo 100 caracteres")
        String name,

        @NotBlank(message = "A descrição é obrigatória")
        @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres")
        String description,

        @NotNull(message = "O status da tarefa é obrigatório")
        TaskStatus status,

        Long userId
) {

        public TaskDTO(String name, String description, TaskStatus status) {
                this(null, name, description, status, null);
        }

        public TaskDTO(String name, String description, TaskStatus status, Long userId) {
                this(null, name, description, status, userId);
        }
}