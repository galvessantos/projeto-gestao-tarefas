package project.study.gestao_tarefas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.study.gestao_tarefas.dto.TaskDTO;
import project.study.gestao_tarefas.entity.Task;
import project.study.gestao_tarefas.mapper.TaskMapper;
import project.study.gestao_tarefas.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskDTO save (TaskDTO taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        Task taskSaved = taskRepository.save(task);
        return taskMapper.toDTO(taskSaved);
    }

    public TaskDTO update (Long id, TaskDTO taskDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada."));

        existingTask.setName(taskDTO.name());
        existingTask.setDescription(taskDTO.description());
        existingTask.setStatus(taskDTO.status());

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toDTO(updatedTask);
    }

    public TaskDTO findById (Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada."));
        return taskMapper.toDTO(task);
    }

    public List<TaskDTO> findAll () {
        return taskRepository.findAll().stream().map(taskMapper::toDTO).collect(Collectors.toList());
    }

    public List<TaskDTO> findAllByUserId(Long userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(taskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void delete (Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada."));
        taskRepository.delete(task);
    }
}
