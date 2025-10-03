package project.study.gestao_tarefas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.study.gestao_tarefas.dto.UserDTO;
import project.study.gestao_tarefas.entity.User;
import project.study.gestao_tarefas.enums.UserRole;
import project.study.gestao_tarefas.exception.BusinessException;
import project.study.gestao_tarefas.mapper.UserMapper;
import project.study.gestao_tarefas.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDTO registerUser(String name, String username, String email, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new BusinessException("Nome de usuário já está em uso");
        }
        
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException("E-mail já está em uso");
        }

        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singleton(UserRole.ROLE_USER));
        
        User userSaved = userRepository.save(user);
        return userMapper.toDTO(userSaved);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserDTO save(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.username())) {
            throw new BusinessException("Nome de usuário já está em uso");
        }
        
        if (userRepository.existsByEmail(userDTO.email())) {
            throw new BusinessException("E-mail já está em uso");
        }

        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public Optional<UserDTO> update(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (!existingUser.getUsername().equals(userDTO.username()) && 
                        userRepository.existsByUsername(userDTO.username())) {
                        throw new BusinessException("Nome de usuário já está em uso");
                    }
                    if (!existingUser.getEmail().equals(userDTO.email()) && 
                        userRepository.existsByEmail(userDTO.email())) {
                        throw new BusinessException("E-mail já está em uso");
                    }
                    
                    existingUser.setName(userDTO.name());
                    existingUser.setUsername(userDTO.username());
                    existingUser.setEmail(userDTO.email());
                    if (userDTO.password() != null && !userDTO.password().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(userDTO.password()));
                    }
                    
                    User updatedUser = userRepository.save(existingUser);
                    return userMapper.toDTO(updatedUser);
                });
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
