package project.study.gestao_tarefas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.study.gestao_tarefas.dto.UserDTO;
import project.study.gestao_tarefas.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(UserDTO dto);
}
