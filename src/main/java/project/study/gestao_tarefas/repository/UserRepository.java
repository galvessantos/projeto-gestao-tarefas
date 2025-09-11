package project.study.gestao_tarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.study.gestao_tarefas.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
