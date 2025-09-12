package project.study.gestao_tarefas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.study.gestao_tarefas.dto.UserDTO;
import project.study.gestao_tarefas.entity.User;
import project.study.gestao_tarefas.mapper.UserMapper;
import project.study.gestao_tarefas.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO);
    }

    public Optional<UserDTO> update(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(userDTO.name());
                    existingUser.setUsername(userDTO.username());
                    existingUser.setEmail(userDTO.email());
                    existingUser.setPassword(userDTO.password());

                    User updatedUser = userRepository.save(existingUser);
                    return userMapper.toDTO(updatedUser);
                });
    }

    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}

