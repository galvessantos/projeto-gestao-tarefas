package project.study.gestao_tarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.study.gestao_tarefas.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
