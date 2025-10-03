package project.study.gestao_tarefas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import project.study.gestao_tarefas.dto.TaskDTO;
import project.study.gestao_tarefas.entity.Task;
import project.study.gestao_tarefas.entity.User;
import project.study.gestao_tarefas.exception.ResourceNotFoundException;
import project.study.gestao_tarefas.mapper.TaskMapper;
import project.study.gestao_tarefas.repository.TaskRepository;
import project.study.gestao_tarefas.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    public TaskDTO save(TaskDTO taskDTO) {
        User user = userRepository.findById(taskDTO.userId())
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        
        Task task = taskMapper.toEntity(taskDTO);
        task.setUser(user);
        
        Task taskSaved = taskRepository.save(task);
        return taskMapper.toDTO(taskSaved);
    }

    public TaskDTO update(Long id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));

        existingTask.setName(taskDTO.name());
        existingTask.setDescription(taskDTO.description());
        existingTask.setStatus(taskDTO.status());

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toDTO(updatedTask);
    }

    public TaskDTO findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));
        return taskMapper.toDTO(task);
    }

    public List<TaskDTO> findAll() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> findAllByUserId(Long userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));
        taskRepository.delete(task);
    }
}
