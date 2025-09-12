package project.study.gestao_tarefas.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import project.study.gestao_tarefas.dto.TaskDTO;
import project.study.gestao_tarefas.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDTO toDTO(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Task toEntity(TaskDTO taskDTO);
}
