package project.study.gestao_tarefas.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.study.gestao_tarefas.dto.TaskDTO;
import project.study.gestao_tarefas.entity.User;
import project.study.gestao_tarefas.exception.ResourceNotFoundException;
import project.study.gestao_tarefas.repository.UserRepository;
import project.study.gestao_tarefas.service.TaskService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(
            @Valid @RequestBody TaskDTO taskDTO,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        TaskDTO taskWithUserId = new TaskDTO(
                taskDTO.name(),
                taskDTO.description(),
                taskDTO.status(),
                user.getId()
        );

        TaskDTO createdTask = taskService.save(taskWithUserId);
        return ResponseEntity
                .created(URI.create("/api/tasks/" + createdTask.id()))
                .body(createdTask);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllUserTasks(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return ResponseEntity.ok(taskService.findAllByUserId(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        TaskDTO task = taskService.findById(id);

        // Check if the task belongs to the user
        if (!task.userId().equals(user.getId())) {
            throw new ResourceNotFoundException("Tarefa não encontrada");
        }

        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskDTO taskDTO,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        // First get the task to check ownership
        TaskDTO existingTask = taskService.findById(id);
        if (!existingTask.userId().equals(user.getId())) {
            throw new ResourceNotFoundException("Tarefa não encontrada");
        }

        // Update the task with the new data
        TaskDTO updatedTask = taskService.update(id, taskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        // First get the task to check ownership
        TaskDTO existingTask = taskService.findById(id);
        if (!existingTask.userId().equals(user.getId())) {
            throw new ResourceNotFoundException("Tarefa não encontrada");
        }

        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}