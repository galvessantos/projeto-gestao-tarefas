package project.study.gestao_tarefas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import project.study.gestao_tarefas.dto.UserDTO;
import project.study.gestao_tarefas.entity.User;
import project.study.gestao_tarefas.enums.UserRole;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "roles", target = "authorities", qualifiedByName = "toAuthorities")
    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(target = "authorities", ignore = true) // Ignore authorities when mapping from DTO to Entity
    User toEntity(UserDTO dto);

    @Named("toAuthorities")
    default Set<String> toAuthorities(Set<UserRole> roles) {
        if (roles == null) {
            return Set.of();
        }
        return roles.stream()
                .map(UserRole::name)
                .collect(Collectors.toSet());
    }
}