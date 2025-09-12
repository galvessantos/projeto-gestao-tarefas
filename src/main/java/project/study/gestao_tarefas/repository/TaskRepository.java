package project.study.gestao_tarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.study.gestao_tarefas.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
