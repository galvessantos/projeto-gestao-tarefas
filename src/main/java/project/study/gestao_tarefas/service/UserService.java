package project.study.gestao_tarefas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.study.gestao_tarefas.entity.User;
import project.study.gestao_tarefas.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);   
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
